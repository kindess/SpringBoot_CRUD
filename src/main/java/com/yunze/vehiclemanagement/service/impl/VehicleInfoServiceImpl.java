package com.yunze.vehiclemanagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunze.vehiclemanagement.annotation.CacheRemove;
import com.yunze.vehiclemanagement.mapper.VehicleInfoMapper;
import com.yunze.vehiclemanagement.pojo.OptionField;
import com.yunze.vehiclemanagement.pojo.VehicleInfo;
import com.yunze.vehiclemanagement.service.OptionFieldService;
import com.yunze.vehiclemanagement.service.OptionService;
import com.yunze.vehiclemanagement.service.VehicleInfoService;
import com.yunze.vehiclemanagement.util.ResultCode;
import com.yunze.vehiclemanagement.util.UUIDUtils;
import com.yunze.vehiclemanagement.vo.VehicleInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleInfoServiceImpl implements VehicleInfoService {

    @Autowired
    private VehicleInfoMapper vehicleInfoMapper;

    @Autowired
    private OptionFieldService optionFieldService;

    @Autowired
    private OptionService optionService;

    /**
     * 删除车辆信息
     * @param id
     * @return
     */
    @Override
    @CacheRemove(value = "cache",key="vehicle_page_") //清除所有分页数据
    @CacheEvict(value = "cache",key="'vehicle_'+#id")//清除此条数据
    public ResultCode deleteByPrimaryKey(String id) {
        if (id != null){
            int result  = vehicleInfoMapper.deleteByPrimaryKey(id);
            if (result != 0){
                return ResultCode.success(null);
            }
        }
        return ResultCode.failing();
    }

    @Override
    public ResultCode deleteByPrimaryKeys(String[] ids) {
        int result = 0;
        if (ids != null){
            for (String id : ids){
               if (id != null && !id.trim().equals("")){
                   result = vehicleInfoMapper.deleteByPrimaryKey(id);
               }
            }
            if (result != 0){
                return ResultCode.success(null);
            }
        }
        return ResultCode.failing();
    }


    @Override
    @CachePut(value = "cache",key = "'vehicle_'+#record.id")  //每调用一次，缓存一次
    public VehicleInfoVO insert(VehicleInfoVO record) {
        if (record != null){
            record.setId(UUIDUtils.generateUUID22());
            int result = vehicleInfoMapper.insert(record);
            if (result != 0){
                return record;
            }
        }
        return null;
    }

    /**
     * 以"vehicle_id"作为key（#id，可以取到方法参数中的id）,返回值作为value，
     * 键值对存储在"cache"缓存中（注意，必须保证与更新键相同，否则出现mysql与缓存数据不一致）
     */
    @Cacheable(value = "cache",key="'vehicle_'+#id")
    @Override
    public VehicleInfoVO selectByPrimaryKey(String id) {
        VehicleInfo vehicleInfo = vehicleInfoMapper.selectByPrimaryKey(id);
        if (vehicleInfo != null){
            return convertVehicleInfoToVO(vehicleInfo);
        }
        return null;
    }

    /**
     * 以"vehicle_id"作为key（#record.id，可以取到方法参数中record的id）,
     * 保证mysql与缓存数据一致性，在更新mysql数据，重新缓存数据。
     * 将返回值作为value，更新"cache"缓存中以此"vehicle_id"作为key的键值对
     */
    @CachePut(value = "cache",key="'vehicle_'+#record.id") //更新此条数据
    @CacheRemove(value ="cache",key = "'vehicle_page_'")  //移除所有分页数据
    @Override
    public VehicleInfo updateByPrimaryKey(VehicleInfo record) {
        if (record != null){
//            record.setId(UUIDUtils.generateMost22UUID());
            int result = vehicleInfoMapper.updateByPrimaryKey(record);
            if (result != 0){
                return record;
            }
        }
        return null;
    }

    /**
     * 根据车辆信息中的选项id查询具体的选项名称
     * @param fieldId
     * @return
     */
    public String selectOptionNameByFieldId(int fieldId){
        OptionField result= optionFieldService.selectByPrimaryKey(fieldId);
        return result.getFieldName();
    }

    /**
     * 将vehicleInfo进行组装转换为VO对象
     * @param vehicleInfo
     * @return
     */
    public VehicleInfoVO convertVehicleInfoToVO(VehicleInfo vehicleInfo){
        VehicleInfoVO vehicleInfoVO = new VehicleInfoVO();
        //ID
        vehicleInfoVO.setId(vehicleInfo.getId());
        // 车牌号
        vehicleInfoVO.setVehicleLicense(vehicleInfo.getVehicleLicense());
        //车辆所有人
        vehicleInfoVO.setOwener(vehicleInfo.getOwener());
        //发动机号
        vehicleInfoVO.setEngineNumber(vehicleInfo.getEngineNumber());
        //车辆机架号
        vehicleInfoVO.setVehicleRackNumber(vehicleInfo.getVehicleRackNumber());
        //车辆型号
        vehicleInfoVO.setVehicleModel(vehicleInfo.getVehicleModel());
        //注册日期
        vehicleInfoVO.setRegisterDate(vehicleInfo.getRegisterDate());
        //车身颜色
        vehicleInfoVO.setBodyColor(vehicleInfo.getBodyColor());
        //核定载客位
        vehicleInfoVO.setSeatingCapacity(vehicleInfo.getSeatingCapacity());
        //下次年检时间
        vehicleInfoVO.setNextAnnualInspectionTime(vehicleInfo.getNextAnnualInspectionTime());
        //排量
        vehicleInfoVO.setDisplacement(vehicleInfo.getDisplacement());
        //里程数
        vehicleInfoVO.setMileage(vehicleInfo.getMileage());
        //备注
        vehicleInfoVO.setRemarks(vehicleInfo.getRemarks());
        //图片
        vehicleInfoVO.setImageUrl(vehicleInfo.getImageUrl());
        // 行驶证编号
        vehicleInfoVO.setDrivingLicenseNumber(vehicleInfo.getDrivingLicenseNumber());
        //车牌颜色
        vehicleInfoVO.setOptionColorId(vehicleInfo.getOptionColorId());
        vehicleInfoVO.setOptionColor(selectOptionNameByFieldId(vehicleInfo.getOptionColorId()));
        //载客数
        vehicleInfoVO.setSeatsOfGuests(vehicleInfo.getSeatsOfGuests());
        // 上户日期
        vehicleInfoVO.setDateInitialRegistration(vehicleInfo.getDateInitialRegistration());
        // 车辆厂牌
        vehicleInfoVO.setVehicleBrandId(vehicleInfo.getVehicleBrandId());
        vehicleInfoVO.setVehicleBrand(selectOptionNameByFieldId(vehicleInfo.getVehicleBrandId()));
        // 车辆类型
        vehicleInfoVO.setVehicleTypeId(vehicleInfo.getVehicleTypeId());
        vehicleInfoVO.setVehicleType(selectOptionNameByFieldId(vehicleInfo.getVehicleTypeId()));
        //车辆检修状态
        vehicleInfoVO.setVehicleMaintenanceStatusId(vehicleInfo.getVehicleMaintenanceStatusId());
        vehicleInfoVO.setVehicleMaintenanceStatus(selectOptionNameByFieldId(vehicleInfo.getVehicleMaintenanceStatusId()));
        //车辆年审状态
        vehicleInfoVO.setAnnualReviewStatusId(vehicleInfo.getAnnualReviewStatusId());
        vehicleInfoVO.setAnnualReviewStatus(selectOptionNameByFieldId(vehicleInfo.getAnnualReviewStatusId()));
        //燃料类型
        vehicleInfoVO.setFuelTypeId(vehicleInfo.getFuelTypeId());
        vehicleInfoVO.setFuelType(selectOptionNameByFieldId(vehicleInfo.getFuelTypeId()));
        //等级
        vehicleInfoVO.setLevelId(vehicleInfo.getLevelId());
        vehicleInfoVO.setLevel(selectOptionNameByFieldId(vehicleInfo.getLevelId()));
        //业务用途
        vehicleInfoVO.setBusinessPurposeId(vehicleInfo.getBusinessPurposeId());
        vehicleInfoVO.setBusinessPurpose(selectOptionNameByFieldId(vehicleInfo.getBusinessPurposeId()));
        //运营范围
        vehicleInfoVO.setScopeOfBusinessId(vehicleInfo.getScopeOfBusinessId());
        vehicleInfoVO.setScopeOfBusiness(selectOptionNameByFieldId(vehicleInfo.getScopeOfBusinessId()));
        //所属供应商
        vehicleInfoVO.setSubordinateSuppliersId(vehicleInfo.getSubordinateSuppliersId());
        vehicleInfoVO.setSubordinateSuppliers(selectOptionNameByFieldId(vehicleInfo.getSubordinateSuppliersId()));
        //调度权
        vehicleInfoVO.setDispatchingRightId(vehicleInfo.getDispatchingRightId());
        vehicleInfoVO.setDispatchingRight(selectOptionNameByFieldId(vehicleInfo.getDispatchingRightId()));
        return vehicleInfoVO;
    }

    @Override
    @Cacheable(value = "cache",key= "'vehicle_page_'+#pageNum+#pageSize")
    public List <VehicleInfoVO> selectRecordByPage(int pageNum, int pageSize) {
        List<VehicleInfo> vehicleInfos=vehicleInfoMapper.selectRecordByPage();
        List <VehicleInfoVO> vehicleInfoVOS = new ArrayList<>();
        if(vehicleInfos != null &&vehicleInfos.size()>0){
            for (VehicleInfo vehicleInfo : vehicleInfos){
                VehicleInfoVO vehicleInfoVO = convertVehicleInfoToVO(vehicleInfo);
                vehicleInfoVOS.add(vehicleInfoVO);
            }
        }
       return vehicleInfoVOS;
    }

    @Override
    public ResultCode selectOldDataByVehicleId(String vehicleId) {
        if (vehicleId != null){
            VehicleInfoVO vehicleInfoVO = selectByPrimaryKey(vehicleId);
            return ResultCode.success(vehicleInfoVO,optionService.selectAllOptionalField());
        }
        return ResultCode.failing();
    }

    @Override
    @CachePut(value = "cache",key = "'vehicle_'+#vehicleInfo.id")
    // 清除缓存中以"vehicle_page_"为前缀的key
    @CacheRemove(value = "cache" ,key ="'vehicle_page_'")
    public VehicleInfo editVehicleInfo(VehicleInfo vehicleInfo) {
        if (vehicleInfo != null ){
            int result = vehicleInfoMapper.updateByPrimaryKey(vehicleInfo);
            if (result != 0){
                return vehicleInfo;
            }
        }
        return null;
    }

    /**
     * 根据条件检索数据
     * @param pageNum
     * @param pageSize
     * @param vehicleBrandId
     * @param vehicleLicense
     * @return
     */
    @Override
    public List<VehicleInfoVO> selectRecordByCondition(int pageNum, int pageSize,
                                    Integer vehicleBrandId, String vehicleLicense) {
        List<VehicleInfoVO> vehicleInfoVOS = new ArrayList<>();
        List<VehicleInfo> vehicleInfos = null;
        VehicleInfo vehicleInfo = new VehicleInfo();
        vehicleInfo.setVehicleBrandId(vehicleBrandId);
        vehicleInfo.setVehicleLicense(vehicleLicense);
        // 根据车牌查询
        if (vehicleBrandId != null ){
            vehicleInfos = vehicleInfoMapper.selectByCondition(vehicleInfo);
        }
        // 根据车牌号查询
        if (vehicleLicense != null && !vehicleLicense.trim().equals("")){
            vehicleInfos = vehicleInfoMapper.selectByCondition(vehicleInfo);
        }
        for (VehicleInfo localVehicleInfo:vehicleInfos){
            vehicleInfoVOS.add(convertVehicleInfoToVO(localVehicleInfo));
        }
        return vehicleInfoVOS;
    }
}