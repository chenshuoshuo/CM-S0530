package com.lqkj.web.gnsc.modules.portal.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * CMGIS系统中的数据分类对象
 * @version 1.0
 * @author RY
 */
@XmlRootElement(name = "item")
public class CmGisPublicCategory {
    /**
     * 分类ID
     */
    private Integer id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 所属分组
     */
    private String groups;
    /**
     * 分类详情，是xml格式的字符串
     */
    private String content;
    /**
     * 类型，目前用来区分大楼/房间/其他面图元
     */
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
