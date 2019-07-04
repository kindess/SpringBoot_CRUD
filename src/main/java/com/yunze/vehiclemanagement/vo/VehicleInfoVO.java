package com.yunze.vehiclemanagement.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class VehicleInfoVO implements Serializable {
    private String id;

    // 车牌号
    private String vehicleLicense;

    // 车辆所有人
    private String owener;

    //对应的id
    private  Integer optionColorId;
    // 车牌颜色
    private String optionColor;

    // 发动机号
    private String engineNumber;

    private  Integer vehicleBrandId;
    // 车辆厂牌
    private String vehicleBrand;

    // 车辆机架号
    private String vehicleRackNumber;

    // 车辆型号
    private String vehicleModel;

    //注册日期
    private String registerDate;

    private  Integer vehicleTypeId;
    // 车辆类型
    private String vehicleType;

    // 初次登记日期
    private String dateInitialRegistration;

    // 车身颜色
    private String bodyColor;

    private Integer vehicleMaintenanceStatusId;
    //车辆检修状态
    private String vehicleMaintenanceStatus;

    //核定载客位(包含驾驶员)
    private Integer seatingCapacity;

    private Integer annualReviewStatusId;
    //车辆年审状态
    private String annualReviewStatus;

    private Integer fuelTypeId;
    //燃料类型
    private String fuelType;

    //下次年检时间
    private String nextAnnualInspectionTime;

    //排量
    private Integer displacement;

    private Integer levelId;
    //等级
    private String level;

    private Integer businessPurposeId;
    //业务用途
    private String businessPurpose;

    private Integer scopeOfBusinessId;
    //运营范围
    private String scopeOfBusiness;

    private Integer subordinateSuppliersId;
    //所属供应商
    private String subordinateSuppliers;

    //载客数(不包含驾驶员)
    private Integer seatsOfGuests;

    //里程数
    private Integer mileage;

    //行驶证号
    private String drivingLicenseNumber;

    private Integer dispatchingRightId;
    //调度权
    private String dispatchingRight;

    //备注
    private String remarks;

    //车辆全身照路径
    private String imageUrl;

    //所有的可选选项与可选参数
    private Map<Object,List<Object>> map;
}
