package com.yunze.vehiclemanagement.mapper;

import com.yunze.vehiclemanagement.pojo.VehicleInfo;

import java.util.List;

import com.yunze.vehiclemanagement.vo.VehicleInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface VehicleInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(VehicleInfoVO record);

    VehicleInfo selectByPrimaryKey(String id);

    int updateByPrimaryKey(VehicleInfo record);

    List<VehicleInfo> selectRecordByPage();

    //根据条件检索（单一条件查询，只接受一个参数）
    List<VehicleInfo> selectByCondition(VehicleInfo record);
}