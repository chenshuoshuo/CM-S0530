package com.lqkj.web.gnsc.modules.gns.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

public class GnsStoreItemVO {

    private Integer schoolId;

    private String itemKey;

    private String itemValue;

    private String storeName;

    private String contentType;

    public GnsStoreItemVO() {
    }

    public GnsStoreItemVO(Integer schoolId, String itemKey, String itemValue, String storeName, String contentType) {
        this.schoolId = schoolId;
        this.itemKey = itemKey;
        this.itemValue = itemValue;
        this.storeName = storeName;
        this.contentType = contentType;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
