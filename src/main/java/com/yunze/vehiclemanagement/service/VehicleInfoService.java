package com.yunze.vehiclemanagement.service;

import com.github.pagehelper.PageInfo;
import com.yunze.vehiclemanagement.pojo.VehicleInfo;
import com.yunze.vehiclemanagement.util.ResultCode;
import com.yunze.vehiclemanagement.vo.VehicleInfoVO;

import java.util.List;

public interface VehicleInfoService {
    ResultCode deleteByPrimaryKey(String id);

    ResultCode deleteByPrimaryKeys(String[] ids);

    VehicleInfoVO insert(VehicleInfoVO record);

    VehicleInfoVO selectByPrimaryKey(String id);

    VehicleInfo updateByPrimaryKey(VehicleInfo record);

    List<VehicleInfoVO> selectRecordByPage(int pageNum, int pageSize);

    //请求修改页面数据
    ResultCode selectOldDataByVehicleId(String vehicleId);

    //修改车辆信息
    VehicleInfo editVehicleInfo(VehicleInfo vehicleInfo);

    // 根据条件检索数据()
    List<VehicleInfoVO> selectRecordByCondition(int pageNum, int pageSize,Integer vehicleBrandId, String vehicleLicense);
}
