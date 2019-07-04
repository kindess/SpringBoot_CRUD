package com.yunze.vehiclemanagement.service;

import com.yunze.vehiclemanagement.pojo.Option;
import com.yunze.vehiclemanagement.pojo.OptionField;
import com.yunze.vehiclemanagement.util.ResultCode;
import com.yunze.vehiclemanagement.vo.OptionVo;

import java.util.List;
import java.util.Map;

public interface OptionService {
    ResultCode<Option> selectByPrimaryKey(Integer optionId);

    List<OptionField> selectOptionalFieldByOptionId(Integer optionId);

    Map<Object,List<Object>> selectAllOptionalField();
}