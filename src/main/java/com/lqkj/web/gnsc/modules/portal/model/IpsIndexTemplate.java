package com.lqkj.web.gnsc.modules.portal.model;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Objects;

/**
 * 推送中控发布平台的数据结构
 */
public class IpsIndexTemplate extends MapIndexTemplate {
    private ObjectNode tags;

    public ObjectNode getTags() {
        return tags;
    }

    public void setTags(ObjectNode tags) {
        this.tags = tags;
    }

    public IpsIndexTemplate() {
    }

    public IpsIndexTemplate(ObjectNode tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        IpsIndexTemplate that = (IpsIndexTemplate) o;
        return Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tags);
    }
}
