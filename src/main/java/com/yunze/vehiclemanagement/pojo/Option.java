package com.yunze.vehiclemanagement.pojo;

import java.io.Serializable;

public class Option implements Serializable {
    private Integer optionId;

    private String optionName;

    private String optionFieldName;

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName == null ? null : optionName.trim();
    }

    public String getOptionFieldName() {
        return optionFieldName;
    }

    public void setOptionFieldName(String optionFieldName) {
        this.optionFieldName = optionFieldName;
    }
}