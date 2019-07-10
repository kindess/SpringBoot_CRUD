package com.yunze.vehiclemanagement.controller;
import com.alibaba.fastjson.JSONObject;
import com.yunze.vehiclemanagement.exception.CustomException;
import com.yunze.vehiclemanagement.pojo.User;
import com.yunze.vehiclemanagement.service.UserService;
import com.yunze.vehiclemanagement.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param user
     * @param model
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(User user, Model model, HttpServletRequest request,
                        HttpServletResponse response, HttpSession session){
        try {
            userService.login(user);
            // 1、是否勾选了记住我
            String rememberMe = request.getParameter("rememberMe");
            if ("rememberMe".equals(rememberMe)){
                Cookie cookie = null;
                try {
                    // 使用cookie存储对象
                    cookie = new Cookie("rememberMe", URLEncoder.encode(JSONObject.toJSONString(user),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                // 缓存一天
                cookie.setMaxAge( 60 * 60 * 24);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            session.setAttribute("userInfo",user);
            //重定向到映射（相当于地址栏输入url）
            return "redirect:/user/index";
        } catch (CustomException e) {
            model.addAttribute("resultCode",ResultCode.failing(e.getMessage()));
            //页面转发
            return "login";
        }
    }

    /**
     * 退出
     * @param response
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletResponse response, HttpSession session){
        // 1、清除session用户信息
//        session.removeAttribute("userInfo");
        session.invalidate();
        // 2、清除cookie
        Cookie rememberMeCookie = new Cookie("rememberMe","");
        rememberMeCookie.setMaxAge(0);
        rememberMeCookie.setPath("/");
        response.addCookie(rememberMeCookie);
        // 3、重定向
        return "redirect:/login.html";
    }

    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResultCode register(User user){
        ResultCode resultCode = userService.register(user);
        return resultCode;
    }
}