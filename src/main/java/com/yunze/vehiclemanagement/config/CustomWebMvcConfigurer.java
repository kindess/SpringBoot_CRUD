package com.yunze.vehiclemanagement.config;

import com.yunze.vehiclemanagement.interceptor.LoginIntercepter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc 扩展配置类，应用一启动，本类就会执行
 * springboot注册拦截器、静态资源路径映射
 */
@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {
    @Value("${uploadFile.urlMapping}")
    private String urlMapping;

    @Value("${uploadFile.location}")
    private String location;

    /**
     * 因为集成了security，登录时，通过UserDetailsService接口实现查询认证与授权数据，
     * 不再走controller层的登录，导致session与cookie都没有保存用户信息，因此就不在使用此cookie登录拦截器了
     * @return
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        // 登录页以及登出、注册请求不拦截、静态资源不拦截，错误页面不拦截
//        registry.addInterceptor(loginIntercepter()).addPathPatterns("/**")
//                //不拦截登录页(注意：这里的"/login.html"实际上也是一个url请求路径)
//                .excludePathPatterns("/login.html")
//                //不拦截请求
//                .excludePathPatterns("/druid/**")
//                .excludePathPatterns("/user/logout","/user/register")
//                //不拦截静态资源
//                .excludePathPatterns("/**/*.css","/**/*.js","/**/*.png",
//                        "/**/*jpg","/**/*.gif","/**/*.jpeg")
//                //不拦截错误页面
//                .excludePathPatterns("/error","/error/*");
//        WebMvcConfigurer.super.addInterceptors(registry);
//    }

    @Bean
    public LoginIntercepter loginIntercepter(){
        return new LoginIntercepter();
    }

    /**
     * 配置静态资源映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //就是说 url 中出现 urlMapping 匹配时，则映射到 location 中去,location 相当于虚拟路径
        //映射本地文件时，开头必须是 file:/// 开头，表示协议
        registry.addResourceHandler(urlMapping).addResourceLocations("file:///" + location);
    }
}