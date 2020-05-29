package com.lqkj.web.gnsc.modules.gns.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@ApiModel(value = "迎新配置信息")
@Entity
@Table(name = "gns_store_item", schema = "gns")
public class GnsStoreItem {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "配置信息ID")
    private Integer itemId;

    @Column(name = "school_id")
    @ApiModelProperty(value = "学校ID")
    private Integer schoolId;

    @Column(name = "item_key")
    @ApiModelProperty(value = "配置信息key")
    private String itemKey;

    @Column(name = "item_value")
    @ApiModelProperty(value = "配置信息value")
    private String itemValue;

    @Column(name = "sore_id")
    @ApiModelProperty(value = "配置分类ID")
    private Integer storeId;

    @Column(name = "content_type")
    @ApiModelProperty(value = "数据类型")
    private String contentType;

    public GnsStoreItem() {
    }

    public GnsStoreItem(Integer schoolId, String itemKey, String itemValue, Integer storeId, String contentType) {
        this.schoolId = schoolId;
        this.itemKey = itemKey;
        this.itemValue = itemValue;
        this.storeId = storeId;
        this.contentType = contentType;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
