package com.lqkj.web.gnsc.modules.portal.model.excel;

import javax.persistence.*;

/**
 * 其他面图源实体EXCEL导出对象
 * @version 1.0
 * @author RY
 */

@Entity
@Table(name = "map_others_polygon", schema = "portal")
public class MapOthersPolygonExcelModel {
    /**
     * 面图源编号
     */
    private Integer polygonCode;
    /**
     * 面图源分类编号
     */
    private Integer typeCode;
    /**
     * 面图源名称
     */
    private String polygonName;
    /**
     * 面图源校区编号
     */
    private Integer campusCode;
    /**
     * 面图源地图编号
     */
    private Long mapCode;
    /**
     * 地图楼层编码
     */
    private Integer leaf;
    /**
     * 面图源英文名称
     */
    private String enName;
    /**
     * 面图源别名
     */
    private String alias;
    /**
     * 是否已同步
     */
    private Boolean synStatus;
    /**
     * 简介
     */
    private String brief;
    /**
     * 删除状态
     */
    private Boolean delete;
    /**
     * 版本号
     */
    private Integer versionCode;
    /**
     * 面图源排序
     */
    private Integer orderId;
    /**
     * 面图源备注
     */
    private String memo;
    /**
     * 图片
     */
    private String picUrl;

    @Id
    @Column(name = "polygon_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getPolygonCode() {
        return polygonCode;
    }

    public void setPolygonCode(Integer polygonCode) {
        this.polygonCode = polygonCode;
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
    @Column(name = "polygon_name")
    public String getPolygonName() {
        return polygonName;
    }

    public void setPolygonName(String polygonName) {
        this.polygonName = polygonName;
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
    @Column(name = "leaf")
    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
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
    @Column(name = "syn_status")
    public Boolean getSynStatus() {
        return synStatus;
    }

    public void setSynStatus(Boolean synStatus) {
        this.synStatus = synStatus;
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
    @Column(name = "delete")
    public Boolean getDelete() {
        return delete;
    }

    @Basic
    @Column(name = "version_code")
    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
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
    @Column(name = "pic_url")
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
