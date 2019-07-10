package com.yunze.vehiclemanagement.config;
import com.yunze.vehiclemanagement.mapper.UserMapper;
import com.yunze.vehiclemanagement.pojo.Authority;
import com.yunze.vehiclemanagement.pojo.User;
import com.yunze.vehiclemanagement.pojo.UserAuthority;
import com.yunze.vehiclemanagement.service.AuthorityService;
import com.yunze.vehiclemanagement.service.UserAuthorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 安全配置
 * AbstractAuthenticationProcessingFilter是Security过滤器的核心入口，
 * SecurityContextPersistenceFilter位于过滤器的顶端
 */
@Configuration
// 接管Web安全，开启自定义配置
@EnableWebSecurity
// 开启权限注解，默认为false
@EnableGlobalMethodSecurity(prePostEnabled=true,securedEnabled=true)
public class CustomWebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CustomWebSecurityConfig.class);

    /**
     * WebSecurityConfigurerAdapter类中关于configure方法的重载
     *        configure(AuthenticationManagerBuilder auth)配置在内存中进行注册公开内存的身份验证
     *        configure(WebSecurity web)配置拦截资源，例如过滤掉css/js/images等静态资源
     *        configure(HttpSecurity http)定义需要拦截的URL
     *
     * 登录、登出配置，静态资源过滤
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception { //配置策略
        http.csrf().disable(); //移除csrf配置
        http.authorizeRequests()
                //所有请求都需要认证
                .anyRequest().authenticated()
                //粗粒度的访问控制（使用注解则可以注释掉）
//                .antMatchers("/**").access( "hasRole('ROLE_ADMIN')")
                /**
                 * 允许所有人访问登录表单url，指定form 表单action提交路径。登录成功使用loginSuccessHandler处理,登录失败使用failureHandler处理
                 * /注意：loginPage与loginProcessingUrl的路径必须不一样，防止Security把表单提交的路径也作为登录页面的路径而跳过springSecurityFilterChain拦截器
                 */
                .and().formLogin().loginPage("/login.html")
                .loginProcessingUrl("/user/login") //指定form 表单action提交路径。
                .defaultSuccessUrl("/user/index") //指定登录成功后跳转路径
                .permitAll().successHandler(loginSuccessHandler()).failureHandler(failureHandler())
                /**
                 *  记住我，生成带有token的cookie。必须设置key，并且必须要与下面的TokenBasedRememberMeServices的key相同，
                 *  否则RememberMeAuthenticationProvider进行key对比时候抛出异常
                 */
                .and().rememberMe().key("userDetailsService").rememberMeServices(rememberMeServices())
                //登出授权与处理
                .and().logout().logoutUrl("/user/logout").permitAll().invalidateHttpSession(true).deleteCookies("rememberMe").logoutSuccessHandler(logoutSuccessHandler())
                // 控制单个用户能创建session的个数，即能在多地登录服务器登录次数，为1表示服务器只能存在一个登录用户，多地登录将被踢出
                .and().sessionManagement().maximumSessions(1).expiredUrl("/login.html");
    }

    /**
     * 解决静态资源被拦截的问题
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                // 路径匹配一定要以"/"开头，如下面的css资源还是会被拦截
//                .antMatchers("**/*.css")
                // 允许druid Web控制台
                .antMatchers("/druid/**")
                .antMatchers("/**/*.css", "/**/*.js","/**/*.jpg", "/**/*.png","/webjars/**", "/**/favicon.ico")
                // 不拦截错误页面
                .antMatchers("/error/**");
        super.configure(web);
    }

    /**
     * 配置在内存中进行注册公开内存的身份验证
     * @param auth
     * @throws Exception
     */
    @Autowired
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 认证信息获取来源是内存获取——inMemoryAuthentication
//        auth.inMemoryAuthentication().withUser("user1").password("123456").roles("USER");

        // 替换掉Security中默认的userDetailsService实现实例JdbcDaoImpl，并指定加密方式
        auth.userDetailsService(userDetailsService()).passwordEncoder(noOpPasswordEncoder());
        auth.eraseCredentials(false);
    }

    /**
     * 密码加密
     * @return
     */

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 该方法参数在[4,31]之间
        return new BCryptPasswordEncoder(4);
    }

    /**
     * 不加密(已被遗弃，不建议使用)
     * @return
     */
    @Bean
    public PasswordEncoder noOpPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 登出处理器
     * @return
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                try {
                    User user = (User) authentication.getPrincipal();
                    logger.info("USER : " + user.getUsername() + " LOGOUT SUCCESS !  ");
                } catch (Exception e) {
                    logger.info("LOGOUT EXCEPTION , e : " + e.getMessage());
                }
                httpServletResponse.sendRedirect("/login.html");
            }
        };
    }

    /**
     * 成功登入处理器
     * @return
     */
    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
                logger.info("USER : " + user.getUsername() + " LOGIN SUCCESS !  ");
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }

    /**
     * 失败登入处理器
     * @return
     */
    @Bean
    public AuthenticationFailureHandler failureHandler(){
         return new SimpleUrlAuthenticationFailureHandler(){
             @Override
             public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                 logger.info("USER LOGIN FAILURE ! THE ROOT CAUSE: " + exception.getMessage());
                 super.onAuthenticationFailure(request,response,exception);
             }
         };
    }

    /**
     * UserDetails是Spring Security验证框架内部提供的用户验证接口，框架提供的默认实现JdbcDaoImpl
     *
     * Security与Shiro中的用户、角色、权限有何不同？
     *      在Security中，角色和权限共用GrantedAuthority接口，唯一的不同角色就是多了个前缀"ROLE_"，
     * 而且它没有Shiro的那种从属关系，即一个角色包含哪些权限等等。在Security看来角色和权限时一样
     * 的，它认证的时候，把所有权限（角色、权限）都取出来，而不是分开验证。
     *      所以，在Security提供的UserDetailsService默认实现JdbcDaoImpl中，角色和权限都存储在auhtorities表中。
     * 而不是像Shiro那样，角色有个roles表，权限有个permissions表。以及相关的管理表等等。
     *
     * @return
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {    //用户认证与授权实现
        return new UserDetailsService() {
            @Autowired
            private UserMapper userMapper;
            @Autowired
            private AuthorityService authorityService;
            @Autowired
            private UserAuthorityService userAuthorityService;

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userMapper.getUserByUsername(username);
                if (user == null) {
                    throw new UsernameNotFoundException("Username '" + username + "' not found");
                }

                // 授权列表
                List<GrantedAuthority> authList = new ArrayList<>();
                // 查询授权
                List<UserAuthority> userAuthorities= userAuthorityService.findByUserId(user.getId());
                if (userAuthorities != null){
                    for (UserAuthority userAuthority : userAuthorities){
                       Authority authority = authorityService.findByAuthorityId(userAuthority.getAuthorityId());
                         if (authority != null){
                             // SimpleGrantedAuthority是GrantedAuthority的实现
                             authList.add(new SimpleGrantedAuthority(authority.getAuthority()));
                         }
                    }
                }
                // 返回实现了UserDetails接口的User类型
                return new org.springframework.security.core.userdetails.User(username,user.getPassword(),authList);
            }
        };
    }

    /**
     * 实现Security的RememberMe功能
     *      RememberMeServices接口是入口，进行实现之后，RememberMeAuthenticationFilter过滤器将会请求过滤
     *      使用非持久化TokenBasedRememberMeServices生成ccookie的策略
     */
    @Bean
    public RememberMeServices rememberMeServices(){
        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices("userDetailsService",userDetailsService());
        // request请求中是否携带此参数，若携带则生成cookie
        rememberMe.setParameter("rememberMe");
        // 指定cookie的key
        rememberMe.setCookieName("rememberMe");
        // 指定cookie的过期时间
        rememberMe.setTokenValiditySeconds(5*24*60*60);
        return rememberMe;
    }
}