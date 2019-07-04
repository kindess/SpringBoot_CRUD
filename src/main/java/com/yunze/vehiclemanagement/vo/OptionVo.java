package com.yunze.vehiclemanagement.vo;

import com.yunze.vehiclemanagement.pojo.OptionField;
import lombok.Data;

import java.util.List;

/**
 * 选项下的所有可选参数
 */
@Data
public class OptionVo {
    private int optionId;

    private List<OptionField> options;
}
