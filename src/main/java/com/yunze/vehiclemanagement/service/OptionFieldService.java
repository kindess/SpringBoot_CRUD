package com.yunze.vehiclemanagement.service;
import com.yunze.vehiclemanagement.pojo.OptionField;
import com.yunze.vehiclemanagement.util.ResultCode;

import java.util.List;
import java.util.Map;

public interface OptionFieldService {
    OptionField selectByPrimaryKey(Integer fieldId);

    List<OptionField> selectByPrimaryOptionId(Integer optionId);

    ResultCode<List<Map<String,OptionField>>> selectAll();

    /**
     * 根据复合键FieldName、OptionId查询对应的主键
     * @param optionField
     * @return
     */
    ResultCode<Integer> selectFieldIdByCompoundKey(OptionField optionField);
}