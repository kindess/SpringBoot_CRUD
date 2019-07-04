package com.yunze.vehiclemanagement.service.impl;

import com.yunze.vehiclemanagement.mapper.OptionMapper;
import com.yunze.vehiclemanagement.pojo.Option;
import com.yunze.vehiclemanagement.pojo.OptionField;
import com.yunze.vehiclemanagement.service.OptionFieldService;
import com.yunze.vehiclemanagement.service.OptionService;
import com.yunze.vehiclemanagement.util.MapUtil;
import com.yunze.vehiclemanagement.util.ResultCode;
import com.yunze.vehiclemanagement.vo.OptionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionMapper optionMapper;

    @Autowired
    private OptionFieldService optionFieldService;

    @Override
    public ResultCode<Option> selectByPrimaryKey(Integer optionId){
        if (optionId != null){
            Option option = optionMapper.selectByPrimaryKey(optionId);
            return ResultCode.success(option);
        }
        return ResultCode.failing();
    }

    /**
     * 根据optionId查询可选参数
     * @param optionId
     * @return
     */
    @Cacheable(value = "cache",key = "'option_'+#optionId")
    @Override
    public List<OptionField> selectOptionalFieldByOptionId(Integer optionId) {
        if (optionId != null){
           List<OptionField> optionFields = optionFieldService.selectByPrimaryOptionId(optionId);
            return optionFields;
        }
        return null;
    }

    /**
     * 查询所有可选参数
     * @return
     */
    @Override
    @Cacheable(value = "cache",key = "'allOptional'")
    public Map<Object,List<Object>> selectAllOptionalField() {
        ResultCode<List<Map<String,OptionField>>> resultCode = optionFieldService.selectAll();
        List<Map<String,OptionField>> mapList = resultCode.getData()[0];
        Map<OptionField,String> localMap = new HashMap();
        if (mapList !=null && mapList.size()>0){
            for (Map map : mapList){
                OptionField optionField = new OptionField();
                optionField.setFieldId( (Integer) map.get("field_id"));
                optionField.setFieldName((String) map.get("field_name"));
                optionField.setOptionId((Integer) map.get("option_id"));
                Option currentoption = optionMapper.selectByPrimaryKey((Integer) map.get("option_id"));
                localMap.put(optionField,currentoption.getOptionFieldName());
            }
        }
        Map<Object,List<Object>> groupOfSameValue = MapUtil.GroupOfSameValue(localMap);
        return groupOfSameValue;
    }
}