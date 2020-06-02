package com.lqkj.web.gnsc.modules.portal.model.excel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import org.hsqldb.lib.StringUtil;

import javax.persistence.*;

/**
 * 点标注实体EXCEL导出对象
 * @author RY
 * @version 1.0
 * @since 2018-12-18 14:16:21
 */

@Entity
@Table(name = "map_point", schema = "portal")
public class MapPointExcelModel {
    /**
     * 点标注编号
     */
    private Integer pointCode;
    /**
     * 点标注分类编号
     */
    private Integer typeCode;
    /**
     * 点标注名称
     */
    private String pointName;
    /**
     * 校区编码
     */
    private Integer campusCode;
    /**
     * 楼层，null为室外
     */
    private Integer leaf;
    /**
     * 绑定位置
     */
    private String location;
    /**
     * 坐标
     */
    @JsonIgnore
    private Point lngLat;
    /**
     * 字符串格式坐标
     */
    private String lngLatString;
    /**
     * 三维坐标
     */
    @JsonIgnore
    private Point rasterLngLat;
    /**
     * 字符串格式三维坐标
     */
    private String rasterLngLatString;
    /**
     * 简介
     */
    private String brief;
    /**
     * 地图编码
     * 这里指在CMGIS中的编号
     */
    private Long mapCode;
    /**
     * 版本号
     */
    private Integer versionCode;
    /**
     * 是否已同步
     */
    private Boolean synStatus;
    /**
     * 删除状态
     */
    private Boolean delete;
    /**
     * 排序
     */
    private Integer orderId;
    /**
     * 备注
     */
    private String memo;
    private String picUrl;

    @Id
    @Column(name = "point_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getPointCode() {
        return pointCode;
    }

    public void setPointCode(Integer pointCode) {
        this.pointCode = pointCode;
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
    @Column(name = "point_name")
    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
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
    @Column(name = "leaf")
    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
    }

    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "lng_lat")
    public Point getLngLat() {
        return lngLat;
    }

    public void setLngLat(Point lngLat) {
        this.lngLat = lngLat;
    }

    @Transient
    public String getLngLatString(){
        if(!StringUtil.isEmpty(lngLatString)){
            return lngLatString;
        } else if(lngLat != null){
            Coordinate coordinate = lngLat.getCoordinate();
            return coordinate.x + "," + coordinate.y;
        } else{
            return "";
        }
    }

    @Basic
    @Column(name = "raster_lng_lat")
    public Point getRasterLngLat() {
        return rasterLngLat;
    }

    public void setRasterLngLat(Point rasterLngLat) {
        this.rasterLngLat = rasterLngLat;
    }

    @Transient
    public String getRasterLngLatString() {
        if(!StringUtil.isEmpty(rasterLngLatString)){
            return rasterLngLatString;
        } else if(rasterLngLat != null){
            Coordinate coordinate = rasterLngLat.getCoordinate();
            return coordinate.x + "," + coordinate.y;
        } else{
            return "";
        }
    }

    public void setRasterLngLatString(String rasterLngLatString) {
        this.rasterLngLatString = rasterLngLatString;
    }

    public void setLngLatString(String lngLatString) {
        this.lngLatString = lngLatString;
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
    @Column(name = "map_code")
    public Long getMapCode() {
        return mapCode;
    }

    public void setMapCode(Long mapCode) {
        this.mapCode = mapCode;
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
