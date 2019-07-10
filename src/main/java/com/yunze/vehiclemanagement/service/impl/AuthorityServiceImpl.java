package com.yunze.vehiclemanagement.service.impl;

import com.yunze.vehiclemanagement.mapper.AuthorityMapper;
import com.yunze.vehiclemanagement.pojo.Authority;
import com.yunze.vehiclemanagement.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityMapper authorityMapper;
    @Override
    public Authority findByAuthorityId(int authorityId) {
        return authorityMapper.selectByAuthorityId(authorityId);
    }
}
