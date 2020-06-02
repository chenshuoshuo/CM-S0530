package com.lqkj.web.gnsc.modules.portal.model.excel;

import javax.persistence.*;
import java.util.Objects;

/**
 * 房间信息实体EXCEL导出对象
 * @version 1.0
 * @author RY
 */

@Entity
@Table(name = "map_room", schema = "portal")
public class MapRoomExcelModel {
    /**
     * 房间编号
     */
    private Integer roomCode;
    /**
     * 房间类型编号
     */
    private Integer typeCode;
    /**
     * 房间名称
     */
    private String roomName;
    /**
     * 房间英文编号
     */
    private String enName;
    /**
     * 房间别名
     */
    private String alias;
    /**
     * 房间门牌号
     */
    private String hourseNumber;
    /**
     * 房间校区编码
     */
    private Integer campusCode;
    /**
     * 房间地图编码
     */
    private Long mapCode;
    /**
     * 房间所属大楼编号
     */
    private Long buildingMapCode;
    /**
     * 房间所属大楼名称
     */
    private String buildingName;
    /**
     * 地图楼层编码
     */
    private Integer leaf;
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
     * 房间排序
     */
    private Integer orderId;
    /**
     * 房间备注
     */
    private String memo;

    /**
     * 图片
     * @return
     */
    private String picUrl;

    @Id
    @Column(name = "room_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(Integer roomCode) {
        this.roomCode = roomCode;
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
    @Column(name = "room_name")
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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
    @Column(name = "building_map_code")
    public Long getBuildingMapCode() {
        return buildingMapCode;
    }

    public void setBuildingMapCode(Long buildingMapCode) {
        this.buildingMapCode = buildingMapCode;
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
    @Column(name = "hourse_number")
    public String getHourseNumber() {
        return hourseNumber;
    }

    public void setHourseNumber(String hourseNumber) {
        this.hourseNumber = hourseNumber;
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
    @Column(name = "pic_url")
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapRoomExcelModel mapRoom = (MapRoomExcelModel) o;
        return roomCode == mapRoom.roomCode &&
                Objects.equals(typeCode, mapRoom.typeCode) &&
                Objects.equals(roomName, mapRoom.roomName) &&
                Objects.equals(campusCode, mapRoom.campusCode) &&
                Objects.equals(mapCode, mapRoom.mapCode) &&
                Objects.equals(buildingMapCode, mapRoom.buildingMapCode) &&
                Objects.equals(buildingName, mapRoom.buildingName) &&
                Objects.equals(enName, mapRoom.enName) &&
                Objects.equals(alias, mapRoom.alias) &&
                Objects.equals(hourseNumber, mapRoom.hourseNumber) &&
                Objects.equals(synStatus, mapRoom.synStatus) &&
                Objects.equals(delete, mapRoom.delete) &&
                Objects.equals(orderId, mapRoom.orderId) &&
                Objects.equals(memo, mapRoom.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomCode, typeCode, roomName, campusCode, mapCode, buildingMapCode, buildingName, enName, alias, hourseNumber, synStatus, delete, orderId, memo);
    }

}
