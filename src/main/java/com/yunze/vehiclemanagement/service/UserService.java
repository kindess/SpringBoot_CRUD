package com.yunze.vehiclemanagement.service;

import com.yunze.vehiclemanagement.exception.CustomException;
import com.yunze.vehiclemanagement.pojo.User;
import com.yunze.vehiclemanagement.util.ResultCode;

public interface UserService {
    User queryUserByIdOrUsername(User user);

    /**
     * 登录测试
     * @param user
     * @return
     */
    ResultCode<String> login(User user) throws CustomException;

    ResultCode<String> register(User user) throws CustomException;
}