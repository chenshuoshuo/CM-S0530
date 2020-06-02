package com.lqkj.web.gnsc.modules.portal.model.excel;

import javax.persistence.*;

/**
 * 大楼信息实体导出对象
 * @version 1.0
 * @author RY
 */
@Entity
@Table(name = "map_building", schema = "portal")
public class MapBuildingExcelModel {
    /**
     * 大楼编号
     */
    private Integer buildingCode;
    /**
     * 分类编号
     */
    private Integer typeCode;
    /**
     * 大楼名称
     */
    private String buildingName;
    /**
     * 校区编号
     */
    private Integer campusCode;
    /**
     * 大楼地图编号
     */
    private Long mapCode;
    /**
     * 大楼英文名称
     */
    private String enName;
    /**
     * 大楼别名
     */
    private String alias;
    /**
     * 简介
     */
    private String brief;
    /**
     * 是否已同步
     */
    private Boolean synStatus;
    /**
     * 删除状态
     */
    private Boolean delete;
    /**
     * 版本号
     */
    private Integer versionCode;
    /**
     * 大楼排序
     */
    private Integer orderId;
    /**
     * 大楼备注
     */
    private String memo;
    /**
     * 图片地址
     */
    private String picUrl;

    @Id
    @Column(name = "building_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(Integer buildingCode) {
        this.buildingCode = buildingCode;
    }

    @Basic
    @Column(name = "type_code")
    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    @Basic
    @Column(name = "building_name")
    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    @Basic
    @Column(name = "campus_code")
    public Integer getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(Integer campusCode) {
        this.campusCode = campusCode;
    }

    @Basic
    @Column(name = "map_code")
    public Long getMapCode() {
        return mapCode;
    }

    public void setMapCode(Long mapCode) {
        this.mapCode = mapCode;
    }

    @Basic
    @Column(name = "en_name")
    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @Basic
    @Column(name = "alias")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Basic
    @Column(name = "brief")
    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    @Basic
    @Column(name = "syn_status")
    public Boolean getSynStatus() {
        return synStatus;
    }

    public void setSynStatus(Boolean synStatus) {
        this.synStatus = synStatus;
    }

    @Basic
    @Column(name = "delete")
    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    @Basic
    @Column(name = "version_code")
    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    @Basic
    @Column(name = "order_id")
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Basic
    @Column(name = "pic_url", insertable = false, updatable = false)
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
