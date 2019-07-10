package com.yunze.vehiclemanagement.mapper;

import com.yunze.vehiclemanagement.pojo.Authority;
import com.yunze.vehiclemanagement.pojo.UserAuthority;

import java.util.List;

public interface UserAuthorityMapper {
    int insert(Authority record);

    List<UserAuthority> selectByUserId(String userId);
}