package com.yunze.vehiclemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PagesController {

    /**
     * 首页页面映射
     * @return
     */
    @RequestMapping(value = "/user/index",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    /**
     * 登录页
     * @return
     */
    @RequestMapping(value = "/login.html",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    /**
     * 注册页
     * @return
     */
    @RequestMapping(value = "/user/register",method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    /**
     * 车辆信息页面映射
     * @return
     */
    @RequestMapping(value = "/vehicleInfoPage",method = RequestMethod.GET)
    public String vehicleInfoPage(){
        return "vehicleInfoPage";
    }
}
