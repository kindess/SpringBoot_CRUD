package com.yunze.vehiclemanagement.service;
import com.yunze.vehiclemanagement.pojo.UserAuthority;
import java.util.List;

public interface UserAuthorityService {
    public List<UserAuthority> findByUserId(String userId);
}
