package com.lqkj.web.gnsc.modules.portal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.Date;

/**
 * 索引结构模板
 */
public class MapIndexTemplate implements java.io.Serializable{
    /**
     * 来源系统，map：GIS平台，zhuantitu：专题图
     */
    private SystemType systemType;
    /**
     * 区域ID
     */
    private Integer zoneid;
    /**
     * 主键ID号
     */
    private String id;
    /**
     * 父图元ID
     */
    private String parentId;
    /**
     * 父图元名称
     */
    private String parentName;
    /**
     * 名称
     */
    private String name;

    /**
     * 门牌号
     */
    private String houseNumber;
    /**
     * 别名
     */
    private String alias;
    /**
     * 英文名
     */
    private String enname;
    /**
     * 楼层
     */
    private Integer level;

    /**
     * 空间信息类型
     */
    private GeomType geomType;
    /**
     * 中心点,GeoJSON类型
     */
    private JSONObject center;

    /**
     * GeoJSON空间信息
     */
    private JSONObject geometry;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss" ,timezone="GMT+8")
    private Date createTime;
    /**
     * 角色
     */
    private int[] roles;

    /**
     * 权限编码
     */
    private String[] permissionCode;

    /**
     * 类型
     */
    private String[] category;
    /**
     * 0:大楼. 1:房间. 2:组织结构. 3:点标注. 4:全景点位. 5:道路. 6:其它面图元：绿化，湖泊，校门等
     **/
    private Integer searchType;



    public MapIndexTemplate() {
    }

    /**
     * 构造函数
     * 删除索引时使用
     */
    public MapIndexTemplate(SystemType systemType, String id, GeomType geomType, Integer zoneid) {
        this.systemType = systemType;
        this.id = id;
        this.geomType = geomType;
        this.zoneid = zoneid;
    }

    /**
     * 构造函数
     */
    public MapIndexTemplate(SystemType systemType, Integer zoneid, String id, String parentId,
                            String parentName, String name, String houseNumber, String alias,
                            String enname, Integer level, GeomType geomType, JSONObject center,
                            JSONObject geometry, Date createTime, int[] roles,
                            String[] permissionCode, String[] category,Integer searchType) {
        this.systemType = systemType;
        this.zoneid = zoneid;
        this.id = id;
        this.parentId = parentId;
        this.parentName = parentName;
        this.name = name;
        this.houseNumber = houseNumber;
        this.alias = alias;
        this.enname = enname;
        this.level = level;
        this.geomType = geomType;
        this.center = center;
        this.geometry = geometry;
        this.createTime = createTime;
        this.roles = roles;
        this.permissionCode = permissionCode;
        this.category = category;
        this.searchType = searchType;
    }

    public SystemType getSystemType() {
        return systemType;
    }

    public void setSystemType(SystemType systemType) {
        this.systemType = systemType;
    }

    public Integer getZoneid() {
        return zoneid;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public void setZoneid(Integer zoneid) {
        this.zoneid = zoneid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public GeomType getGeomType() {
        return geomType;
    }

    public void setGeomType(GeomType geomType) {
        this.geomType = geomType;
    }

    public JSONObject getCenter() {
        return center;
    }

    public void setCenter(JSONObject center) {
        this.center = center;
    }

    public JSONObject getGeometry() {
        return geometry;
    }

    public void setGeometry(JSONObject geometry) {
        this.geometry = geometry;
    }

    public int[] getRoles() {
        return roles;
    }

    public void setRoles(int[] roles) {
        this.roles = roles;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public String[] getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String[] permissionCode) {
        this.permissionCode = permissionCode;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    @Override
    public String toString() {
        return "MapIndexTemplate{" +
                "systemType=" + systemType +
                ", zoneid=" + zoneid +
                ", id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", name='" + name + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", alias='" + alias + '\'' +
                ", enname='" + enname + '\'' +
                ", level=" + level +
                ", geomType=" + geomType +
                ", center=" + center +
                ", geometry=" + geometry +
                ", createTime=" + createTime +
                ", roles=" + Arrays.toString(roles) +
                ", permissionCode=" + Arrays.toString(permissionCode) +
                ", category=" + Arrays.toString(category) +
                ", searchType=" + searchType +
                '}';
    }
}
