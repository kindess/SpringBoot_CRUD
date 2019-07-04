package com.yunze.vehiclemanagement.mapper;

import com.yunze.vehiclemanagement.pojo.OptionField;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OptionFieldMapper {

    int deleteByPrimaryKey(Integer fieldId);

    int insert(OptionField record);

    OptionField selectByPrimaryKey(Integer fieldId);

    List<OptionField> selectFieldsByOptionId(Integer optionId);

    List<Map<String,OptionField>> selectAllFields();

    int updateByPrimaryKey(OptionField record);

    // 为了防止同一个选项菜单下出现相同的可选项，将optionId与fieldName作为复合键
    int selectFieldIdByFieldNameAndOptionId(OptionField optionField);
}