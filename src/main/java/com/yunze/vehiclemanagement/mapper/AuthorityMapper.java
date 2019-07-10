package com.yunze.vehiclemanagement.mapper;

import com.yunze.vehiclemanagement.pojo.Authority;

public interface AuthorityMapper {

    int insert(Authority record);

    int insertSelective(Authority record);

    Authority selectByAuthorityId(int authorityId);
}