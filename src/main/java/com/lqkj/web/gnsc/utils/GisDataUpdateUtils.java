package com.lqkj.web.gnsc.utils;

import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 更新数据到CMGIS的工具类
 */
public class GisDataUpdateUtils {
    public static Logger logger = LoggerFactory.getLogger("OSM处理");

    /**
     * 构建创建changeset的字符串
     * @return
     */
    public static String createChangesetString(){
        StringBuffer sb = new StringBuffer();
        sb.append("<osm>");
        sb.append("<changeset>");
        sb.append("<tag k=\"created_by\" v=\"CMIPS\"/>");
        sb.append("<tag k=\"comment\" v=\"update data from cmips\"/>");
        sb.append("</changeset>");
        sb.append("</osm>");

        return sb.toString();
    }

    /**
     * 根据原始数据osm、变更数据集、新的changeset值
     * 创建变更集xml
     * @param wayOsmString 面/线原始数据osm
     * @param nodeOsmString 点原始数据osm
     * @param changeSets 变更数据集
     * @param newDataSets 新增数据集
     * @param changeset 新的changeset值
     * @return 变更集xml
     * @throws DocumentException Document异常
     */
    public static String creatOsmChange(String wayOsmString, String nodeOsmString,
                                        List<GisDataChangeSet> changeSets, List<GisDataChangeSet> newDataSets,
                                        String changeset)
            throws DocumentException {
        // List转为MAP
        // 注意：这种转法只在JDK1.8及以上支持
        Map<Long, GisDataChangeSet> changeSetMap = changeSets.stream()
                .collect(Collectors.toMap(GisDataChangeSet :: getId, GisDataChangeSet -> GisDataChangeSet));

        // 创建osmChangeset的xml根节点
        Document osmDocument = DocumentHelper.createDocument();
        Element osmRoot = osmDocument.addElement("osmChange");
        osmRoot.addAttribute("version", "0.6");
        osmRoot.addAttribute("generator", "cmis update");

        // 修改集
        Element modifyElement = osmRoot.addElement("modify");
        // 删除集
        Element deleteElement = osmRoot.addElement("delete");
        // 新增集
        Element createElement = osmRoot.addElement("create");


        Document document = DocumentHelper.parseText(wayOsmString);
        Element element = document.getRootElement();
        List<Element> childElements = element.elements();
        for(Element childElement : childElements){
            Long dataId = Long.parseLong(childElement.attributeValue("id"));
            GisDataChangeSet changeSet = changeSetMap.get(dataId);

            // 修改changeset值
            Attribute changeSetAttribute = childElement.attribute("changeset");
            changeSetAttribute.setValue(changeset);

            // 修改属性
            updateValueFromTagList(childElement, "name", changeSet.getName());// 名称
            updateValueFromTagList(childElement, "newid", changeSet.getCategoryId().toString());// 分类
            updateValueFromTagList(childElement, "en_name", changeSet.getEnName());// 英文名
            updateValueFromTagList(childElement, "another_name", changeSet.getAlias());// 别名
            updateValueFromTagList(childElement, "door_sn", changeSet.getHouseNumber());// 门牌号
            updateValueFromTagList(childElement, "content", changeSet.getBrief());// 简介
            if(changeSet.getLeaf() != null){
                updateValueFromTagList(childElement, "level", changeSet.getLeaf().toString());// 楼层
            }
            updateValueFromTagList(childElement, "polygon_category", changeSet.getCategory());// 分类

            if(changeSet.getChangeSetType().equals(GisDataChangeSetType.delete)){
                deleteElement.add((Element) childElement.clone());
            } else if(changeSet.getChangeSetType().equals(GisDataChangeSetType.modify)){
                modifyElement.add((Element) childElement.clone());
            } else {
                createElement.add((Element) childElement.clone());
            }

            changeSetMap.remove(dataId);
        }

        Document nodeDocument = DocumentHelper.parseText(nodeOsmString);
        Element nodeRootElement = nodeDocument.getRootElement();
        List<Element> childNodeElements = nodeRootElement.elements();
        for(Element childNodeElement : childNodeElements){
            Integer dataId = Integer.parseInt(childNodeElement.attributeValue("id"));
            GisDataChangeSet changeSet = changeSetMap.get(dataId);

            // 修改changeset值
            Attribute changeSetAttribute = childNodeElement.attribute("changeset");
            changeSetAttribute.setValue(changeset);

            if(changeSet != null){
                // 修改属性
                // 修改点标注坐标
                if(changeSet.getLngLatString() != null && changeSet.getLngLatString().split(",").length == 2){
                    String[] lonLat = changeSet.getLngLatString().split(",");
                    childNodeElement.attributeValue("lon", lonLat[0]);
                    childNodeElement.attributeValue("lat", lonLat[1]);
                }
                // 修改其他属性
                updatePointTag(childNodeElement, changeSet);

                if(changeSet.getChangeSetType().equals(GisDataChangeSetType.delete)){
                    deleteElement.add((Element) childNodeElement.clone());
                } else if(changeSet.getChangeSetType().equals(GisDataChangeSetType.modify)){
                    modifyElement.add((Element) childNodeElement.clone());
                } else {
                    createElement.add((Element) childNodeElement.clone());
                }
            } else {
                deleteElement.add((Element) childNodeElement.clone());
            }

            changeSetMap.remove(dataId);
        }

        if(newDataSets!=null){
            for(GisDataChangeSet gisDataChangeSet : newDataSets){
                String[] lonLat = gisDataChangeSet.getLngLatString().split(",");
                Element newPointElement = createElement.addElement("node");
                newPointElement.addAttribute("changeset", changeset);
                newPointElement.addAttribute("id", gisDataChangeSet.getId().toString());
                newPointElement.addAttribute("lon", lonLat[0]);
                newPointElement.addAttribute("lat", lonLat[1]);

                updatePointTag(newPointElement, gisDataChangeSet);
            }
        }

        System.out.println(osmDocument.asXML());
        return osmDocument.asXML();
    }

    private static void updatePointTag(Element element, GisDataChangeSet changeSet){
        updateValueFromTagList(element, "name", changeSet.getName());// 名称
        updateValueFromTagList(element, "newid", changeSet.getCategoryId().toString());// 分类
        updateValueFromTagList(element, "content", changeSet.getBrief());// 简介
        updateValueFromTagList(element, "level", changeSet.getLeaf());// 楼层
        updateValueFromTagList(element, "poi", "cmips-add-poi");// 分类
    }

    /**
     * 根据k获取v
     * @return
     */
    private static void updateValueFromTagList(Element element, String key, Object value){
        if(value != null){
            List<Element> tagList = element.elements("tag");
            Boolean hasKey = false;
            for(Element tagElement : tagList){
                if(key.equals(tagElement.attributeValue("k"))){
                    tagElement.addAttribute("v", value.toString());
                    hasKey = true;
                    break;
                }
            }
            if(!hasKey){
                Element keyElement = element.addElement("tag");
                keyElement.addAttribute("k", key);
                keyElement.addAttribute("v", value.toString());
            }
        }
    }

    /**
     * 解析获取到的osm
     * @param osmString osm
     * @return
     * @throws DocumentException dom4j异常
     */
    public static List<GisDataChangeSet> readOsmString(String osmString) throws DocumentException {
        List<GisDataChangeSet> list = new ArrayList<>();

        Document document = DocumentHelper.parseText(osmString);
        Element rootElement = document.getRootElement();
        List<Element> childElements = rootElement.elements();
        for(Element childElement : childElements){
            Long id = Long.parseLong(childElement.attributeValue("old_id"));
            Long newId = Long.parseLong(childElement.attributeValue("new_id"));
            Integer version = Integer.parseInt(childElement.attributeValue("new_version"));
            String category = "";
            if("node".equals(childElement.getName())){
                category = "poi";
            } else if("way".equals(childElement.getName())){
                category = "way";
            }
            list.add(new GisDataChangeSet(id, newId, category, version));
        }
        return list;
    }

    /**
     * 根据key获取值
     * @param list list
     * @param key key
     * @return
     */
    private static String loadChildrenAttributeValueWithKey(List<Element> list, String key){
        for(Element element : list){
            if(key.equals(element.attributeValue("k"))){
                return element.attributeValue("v");
            }
        }
        return null;
    }

}
