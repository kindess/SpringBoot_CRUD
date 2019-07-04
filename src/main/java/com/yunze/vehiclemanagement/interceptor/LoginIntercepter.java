package com.yunze.vehiclemanagement.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.yunze.vehiclemanagement.pojo.User;
import com.yunze.vehiclemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * 用户未登录时检查cookie是否携带用户信息，
 * 如果存在此cookie，实现自动登录（/免登录）
 */
public class LoginIntercepter implements HandlerInterceptor {

    private static final String REMEMBERME_COOKIE= "rememberMe";

    /**
     * 拦截器中无法直接注入bean
     * 解决方法： 1、在配置类CustomWebMvcConfigurer中，使用@Bean先向容器中注入当前拦截器，然后在注册拦截器
     *           2、获取applicationContext对象，使用getBean方法
     */
    @Autowired
    private UserService userService;
    private static final String LOGIN_URL = "/user/login";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("当前请求路径 ："+request.getRequestURI());
        Cookie[] cookies = request.getCookies();
        User user = null;
        if (cookies != null && cookies.length >0){
            for (Cookie cookie : cookies){
                // 1、查找存储用户信息的cookie
                if (REMEMBERME_COOKIE.equals(cookie.getName())){
                    user = JSONObject.parseObject(URLDecoder.decode(cookie.getValue(),"UTF-8"),User.class);
                }
            }
           if (user != null){
               //校验用户是否有效
               User result = userService.queryUserByIdOrUsername(user);
               // 2、匹配密码
               if (result != null && (!StringUtils.isEmpty(result.getPassword())
                       && result.getPassword().equals(user.getPassword()))){
                   // 2.1用户登录
                   request.getSession().setAttribute("userInfo",request);
                   // 2.2如果请求路径为"/user/login"，重定向首页
                   if (LOGIN_URL.equals(request.getRequestURI())){
                       response.sendRedirect("/user/index");
                   }
               }
           }
        }
        return true;
    }
}