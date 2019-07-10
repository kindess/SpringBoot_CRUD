package com.yunze.vehiclemanagement.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.yunze.vehiclemanagement.constant.ErrorMsg;
import com.yunze.vehiclemanagement.pojo.User;
import com.yunze.vehiclemanagement.service.UserService;
import com.yunze.vehiclemanagement.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * 检查请求是否携带cookie,并且cookie是否携带用户信息，
 * 如果存在此cookie，实现自动登录（/免登录），
 * 否则，都将会被转发到登录页
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
    // 登录表单action路径
    private static final String LOGIN_URL = "/user/login";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("当前请求路径 ："+request.getRequestURI());
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Cookie[] cookies = request.getCookies();
        User user = null;
        // 1、请求携带cookie
        if (cookies != null && cookies.length >0){
            for (Cookie cookie : cookies){
                // 1、查找存储用户信息的cookie
                if (REMEMBERME_COOKIE.equals(cookie.getName())){
                    user = JSONObject.parseObject(URLDecoder.decode(cookie.getValue(),"UTF-8"),User.class);
                }
            }
            // 1.1 cookie中不存在用户信息
           if (user == null){
               // 1.1.1 从登录页请求登录（进行登录）
               if (LOGIN_URL.equals(request.getRequestURI())){
                   return true;
               }
               // 1.1.2 不接受除登录页面外发送的请求（未认证）
               request.setAttribute("resultCode",ResultCode.failing(ErrorMsg.ERROR_100012));
               request.getRequestDispatcher("/login.html").forward(request,response);
               return false;
           }
           // 1.2 cookie中存在用户信息
           else{
               //  1.2.1 校验用户是否有效
               User result = userService.queryUserByIdOrUsername(user);
               // 匹配密码
               if (result != null && (!StringUtils.isEmpty(result.getPassword())
                       && result.getPassword().equals(user.getPassword()))){
                   // 1.2.2 用户登录
                   request.getSession().setAttribute("userInfo",result);
                   // 1.2.2.1 如果请求路径为"/user/login"，重定向首页
                   if (LOGIN_URL.equals(request.getRequestURI())){
                       response.sendRedirect("/user/index");
                       return false;
                   }
                   // 1.2.2.2 非首页的请求
                   return true;
               }
               return false;
           }
        }
        // 2、请求不携带cookie
        else{
            // 2.1 从登录页请求登录（进行登录）
            if (LOGIN_URL.equals(request.getRequestURI())){
                return true;
            }
            // 2.2 不接受除登录页面外发送的请求（未认证）
            request.setAttribute("resultCode",ResultCode.failing(ErrorMsg.ERROR_100012));
            request.getRequestDispatcher("/login.html").forward(request,response);
            return false;
        }
    }
}