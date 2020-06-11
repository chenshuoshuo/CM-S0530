package com.lqkj.web.gnsc.utils;


/**
 * 向GIS同步数据的变更集对象
 */
public class GisDataChangeSet {
    /**
     * 信息ID
     * 注意：CMGIS中的id对应的是门户里的mapCode字段
     */
    private Long id;
    /**
     * CMGIS系统中的分类
     * 地图数据在CMGIS中只分成两种：way（线/面）、node（点）
     * 在接收CMGIS的变更推送时
     * 需要过滤构成线/面的点
     * 并且，way和node的id可能重复，需要加以区分
     */
    private String category;
    /**
     * 分类ID
     */
    private Integer categoryId;
    /**
     * 名称
     */
    private String name;
    /**
     * 变更状态
     */
    private GisDataChangeSetType changeSetType;
    /**
     * 点标注坐标
     */
    private String lngLatString;
    /**
     * 英文名
     */
    private String enName;
    /**
     * 别名
     */
    private String alias;
    /**
     * 门牌号
     */
    private String houseNumber;
    /**
     * 简介
     */
    private String brief;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 楼层
     */
    private Integer leaf;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 新ID
     */
    private Long newId;

    private Integer parentCategoryId;

    public GisDataChangeSet(){}

    /**
     * 构造函数
     * @param id id
     * @param name name
     * @param changeSetType changeSetType
     */
    public GisDataChangeSet(Long id, String name, GisDataChangeSetType changeSetType) {
        this.id = id;
        this.name = name;
        this.changeSetType = changeSetType;
    }

    /**
     * 构造函数
     */
    public GisDataChangeSet(Long id, String category, Integer categoryId,
                            String name, GisDataChangeSetType changeSetType,
                            String lngLatString, String enName, String alias,
                            String houseNumber, String brief, Integer leaf) {
        this.id = id;
        this.category = category;
        this.categoryId = categoryId;
        this.name = name;
        this.changeSetType = changeSetType;
        this.lngLatString = lngLatString;
        this.enName = enName;
        this.alias = alias;
        this.houseNumber = houseNumber;
        this.brief = brief;
        this.leaf = leaf;
    }

    /**
     * 构造函数
     */
    public GisDataChangeSet(Long id, Long newId, String category, Integer version) {
        this.id = id;
        this.newId = newId;
        this.category = category;
        this.version = version;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GisDataChangeSetType getChangeSetType() {
        return changeSetType;
    }

    public void setChangeSetType(GisDataChangeSetType changeSetType) {
        this.changeSetType = changeSetType;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLngLatString() {
        return lngLatString;
    }

    public void setLngLatString(String lngLatString) {
        this.lngLatString = lngLatString;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getNewId() {
        return newId;
    }

    public void setNewId(Long newId) {
        this.newId = newId;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
