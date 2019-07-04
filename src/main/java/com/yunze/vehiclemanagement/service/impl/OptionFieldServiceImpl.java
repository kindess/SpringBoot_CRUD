package com.yunze.vehiclemanagement.service.impl;

import com.yunze.vehiclemanagement.mapper.OptionFieldMapper;
import com.yunze.vehiclemanagement.pojo.OptionField;
import com.yunze.vehiclemanagement.service.OptionFieldService;
import com.yunze.vehiclemanagement.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class OptionFieldServiceImpl implements OptionFieldService {
    @Autowired
    private OptionFieldMapper optionFieldMapper;

    @Override
    @Cacheable(value = "cache",key = "'optionField_'+#fieldId")
    public OptionField selectByPrimaryKey(Integer fieldId) {
        if (fieldId != null){
            return optionFieldMapper.selectByPrimaryKey(fieldId);
        }
        return null;
    }

    @Override
    public List<OptionField> selectByPrimaryOptionId(Integer optionId) {
        if (optionId != null){
            return optionFieldMapper.selectFieldsByOptionId(optionId);
        }
        return null;
    }

    @Override
    public ResultCode<List<Map<String,OptionField>>> selectAll(){
        return ResultCode.success(optionFieldMapper.selectAllFields());
    }

    @Override
    public ResultCode<Integer> selectFieldIdByCompoundKey(OptionField optionField) {
        if (optionField !=null){
            return ResultCode.success(optionFieldMapper.selectFieldIdByFieldNameAndOptionId(optionField));
        }
        return ResultCode.failing();
    }
}