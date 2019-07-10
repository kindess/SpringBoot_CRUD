package com.yunze.vehiclemanagement.service.impl;

import com.yunze.vehiclemanagement.mapper.UserAuthorityMapper;
import com.yunze.vehiclemanagement.pojo.UserAuthority;
import com.yunze.vehiclemanagement.service.UserAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserAuthorityServiceImpl implements UserAuthorityService {
    @Autowired
    private UserAuthorityMapper userAuthorityMapper;

    @Override
    public List<UserAuthority> findByUserId(String userId) {
        return userAuthorityMapper.selectByUserId(userId);
    }
}
