package com.yunze.vehiclemanagement.mapper;

import com.yunze.vehiclemanagement.pojo.Option;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 必须使用@Mapper，使用@Repository会抛出找不到接口
 */
@Mapper
public interface OptionMapper {

    int deleteByPrimaryKey(Integer optionId);

    int insert(Option record);

    Option selectByPrimaryKey(Integer optionId);

    int updateByPrimaryKey(Option record);
}