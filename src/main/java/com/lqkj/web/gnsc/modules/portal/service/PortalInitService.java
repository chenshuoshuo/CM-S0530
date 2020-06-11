package com.lqkj.web.gnsc.modules.portal.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lqkj.web.gnsc.modules.base.BaseService;
import com.lqkj.web.gnsc.modules.portal.model.*;
import com.lqkj.web.gnsc.utils.*;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据初始service实现
 */
@Service
@EnableAsync
public class PortalInitService extends BaseService {

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    MapRoomTypeService roomTypeService;
    @Autowired
    MapBuildingTypeService buildingTypeService;
    @Autowired
    MapOthersPolygonTypeService othersPolygonTypeService;
    @Autowired
    MapBuildingService buildingService;
    @Autowired
    MapRoomService roomService;
    @Autowired
    MapOthersPolygonService othersPolygonService;

    private Logger logger = LoggerFactory.getLogger("地图数据初始");

    private Map<Long, MapBuilding> buildingMap;
    private Map<Long, MapRoom> roomMap;
    private Map<Long, MapOthersPolygon> polygonMap;
    //private Map<Long, MapPoint> pointMap;
    private Map<Long, MapBuilding> readBuildingMap;
    private Map<Long, MapBuilding> fullBuildingMap;
    private Map<Long, MapRoom> readRoomMap;
    private List<MapOthersPolygon> polygonList;

    private Integer loopCount;
    private Integer roomLoopCount;
    private List<String> mapCodeList = new ArrayList<>();

    private Map<Integer, MapBuildingType> buildingTypeMap;
    private Map<Integer, MapRoomType> roomTypeMap;
    private Map<Integer, MapOthersPolygonType> polygonTypeMap;

    private Boolean success = true;
    private Exception exception = null;


    public void initCategory() throws DocumentException {
        JSONObject jsonObject = JSON.parseObject(queryPublicCategory(null, 0, 1000));
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray jsonArray = dataObject.getJSONArray("content");
        List<CmGisPublicCategory> list = jsonArray.toJavaList(CmGisPublicCategory.class);

        Map<Integer, MapBuildingType> mapBuildingTypeMap = buildingTypeService.queryList(null)
                .stream()
                .collect(Collectors.toMap(MapBuildingType :: getTypeCode, MapBuildingType -> MapBuildingType));

        Map<Integer, MapRoomType> mapRoomTypeMap =roomTypeService.queryList(null)
                .stream()
                .collect(Collectors.toMap(MapRoomType :: getTypeCode, MapRoomType -> MapRoomType));

        Map<Integer, MapOthersPolygonType> mapOthersPolygonTypeMap = othersPolygonTypeService.queryList(null)
                .stream()
                .collect(Collectors.toMap(MapOthersPolygonType :: getTypeCode,
                        MapOthersPolygonType -> MapOthersPolygonType));

        List<MapBuildingType> buildingTypeList = new ArrayList<>();
        List<MapRoomType> roomTypeList = new ArrayList<>();
        List<MapOthersPolygonType> polygonTypeList = new ArrayList<>();
        for(CmGisPublicCategory cmGisPublicCategory : list){
            Element element = DocumentHelper
                    .parseText(cmGisPublicCategory.getContent())
                    .getRootElement();

            Integer typeCode = Integer.parseInt(element.attributeValue("id"));
            String typeName = element.attributeValue("name");
            String type = element.attributeValue("type");

            // 获取key的节点集合
            List<Element> keyElements = element.elements("key");
            if("closedway".equals(type)){
                // 这是面分类
                String polygonType = loadValueFromElementsWithKey(keyElements, "polygon_category");
                if("building".equals(polygonType)){
                    // 大楼分类
                    MapBuildingType mapBuildingType;
                    if(mapBuildingTypeMap.containsKey(typeCode)){
                        mapBuildingType = mapBuildingTypeMap.get(typeCode);
                        mapBuildingType.setTypeName(typeName);
                        mapBuildingType.setClick(true);
                        mapBuildingType.setSearch(true);
                        mapBuildingTypeMap.remove(typeCode);
                    } else {
                        mapBuildingType = new MapBuildingType(typeCode, null, typeName,
                                true, true, null, null, null);
                    }
                    buildingTypeList.add(mapBuildingType);
                } else if("room".equals(polygonType)){
                    // 房间分类
                    MapRoomType mapRoomType;
                    if(mapRoomTypeMap.containsKey(typeCode)){
                        mapRoomType = mapRoomTypeMap.get(typeCode);
                        mapRoomType.setSearch(true);
                        mapRoomType.setClick(true);
                        mapRoomType.setTypeName(typeName);

                        mapRoomTypeMap.remove(typeCode);
                    } else {
                        mapRoomType = new MapRoomType(typeCode, null, typeName,
                                true, true, null, null, null);
                    }

                    roomTypeList.add(mapRoomType);
                } else if("others".equals(polygonType)){
                    // 其他面图源分类
                    MapOthersPolygonType othersPolygonType = null;
                    if(mapOthersPolygonTypeMap.containsKey(typeCode)){
                        othersPolygonType = mapOthersPolygonTypeMap.get(typeCode);
                        othersPolygonType.setClick(true);
                        othersPolygonType.setSearch(true);
                        othersPolygonType.setTypeName(typeName);

                        mapOthersPolygonTypeMap.remove(typeCode);
                    } else {
                        othersPolygonType = new MapOthersPolygonType(typeCode, null, typeName,
                                true, true, null, null, null);
                    }

                    polygonTypeList.add(othersPolygonType);
                } else {
                    logger.info("其他面分类：" + typeCode + "/" + typeName + "/" + type + "/" + polygonType);
                }
            } else {
                logger.info("其他点分类：" + typeCode + "/" + typeName + "/" + type);
            }

        }
        buildingTypeService.saveAll(buildingTypeList);
        othersPolygonTypeService.saveAll(polygonTypeList);
        roomTypeService.saveAll(roomTypeList);

        if(mapBuildingTypeMap.keySet().size() > 0)
            buildingTypeService.bulkDelete(StringUtils.join(mapBuildingTypeMap.keySet(), ","));

        if(mapRoomTypeMap.keySet().size() > 0)
            roomTypeService.bulkDelete(StringUtils.join(mapRoomTypeMap.keySet(), ","));

        if(mapOthersPolygonTypeMap.keySet().size() > 0)
            othersPolygonTypeService.bulkDelete(StringUtils.join(mapOthersPolygonTypeMap.keySet(), ","));

    }

    public DataSynLog initPublicData(Integer zoneId) {
        // 如果不为null直接返回
        if(buildingMap != null){
            resetValue();
            return new DataSynLog(zoneId, false, "有用户正在同步数据，请重试");
        }
        try {
            // 构建CMDBE中数据的MAP
            // 用于更新后删除多余数据
            buildingMap = buildingService.listQuery(zoneId,null,null)
                    .stream()
                    .collect(Collectors.toMap(MapBuilding :: getMapCode, MapBuilding -> MapBuilding));

            fullBuildingMap = new HashMap<>();

            roomMap = roomService.listQuery(null,zoneId,null,null)
                    .stream()
                    .collect(Collectors.toMap(MapRoom :: getMapCode, MapRoom -> MapRoom));

            polygonMap = othersPolygonService.listQuery(zoneId,null,null)
                    .stream()
                    .collect(Collectors.toMap(MapOthersPolygon :: getMapCode, MapOthersPolygon -> MapOthersPolygon));

            buildingTypeMap = buildingTypeService.queryList(null)
                    .stream()
                    .collect(Collectors.toMap(MapBuildingType :: getTypeCode, MapBuildingType -> MapBuildingType));

            roomTypeMap =roomTypeService.queryList(null)
                    .stream()
                    .collect(Collectors.toMap(MapRoomType :: getTypeCode, MapRoomType -> MapRoomType));

            polygonTypeMap =othersPolygonTypeService.queryList(null)
                    .stream()
                    .collect(Collectors.toMap(MapOthersPolygonType :: getTypeCode,
                            MapOthersPolygonType -> MapOthersPolygonType));

            // 定义需要保存的列表
            readBuildingMap = new HashMap<>();
            readRoomMap = new HashMap<>();
            polygonList = new ArrayList<>();

            // 大楼
            JSONArray jsonArray = requestDataFromGis(zoneId, "polygon_category", "building", "polyline");
            // 其他面图元
            jsonArray.addAll(requestDataFromGis(zoneId, "polygon_category", "others", "polyline"));
            // 房间
            jsonArray.addAll(requestDataFromGis(zoneId, "polygon_category", "room", "polyline"));

            int dataLength = jsonArray.size();
            Integer readLoopCount = dataLength % 100 == 0 ? dataLength / 100 : dataLength / 100 + 1;
            loopCount = readLoopCount;
            for(int i = 0; i < readLoopCount; i++){
                readDataFromJsonObject(jsonArray, zoneId, i* 100, Math.min((i + 1) * 100, dataLength));
            }

            updateRoomBuildingName();
            saveData(zoneId);
            resetValue();
            logger.info("异常数据编号：" + mapCodeList);
            if(success){

                return new DataSynLog(zoneId, true, "同步成功");
            } else {
                return new DataSynLog(zoneId, false, exception);
            }
        } catch (Exception e) {
            logger.error("异常：", e);
            resetValue();
            return new DataSynLog(zoneId, false, e);
        }
    }

    public List<DataSynLog> initAllPublicData() {
        List<DataSynLog> list = new ArrayList<>();
        JSONObject mapZoneObject = JSON.parseObject(
                queryMapZone(false, true, 0, 100));
        JSONObject dataObject = mapZoneObject.getJSONObject("data");
        JSONArray jsonArray = dataObject.getJSONArray("content");
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Integer zoneId = jsonObject.getInteger("id");
            list.add(initPublicData(zoneId));
        }
        return list;
    }


    public DataSynLog pushDataToGis(Integer zoneId) {
        logger.info("开始执行数据推送,zoneId:"+zoneId);
        List<GisDataChangeSet> changeSetList = new ArrayList<>();
        Long[] wayIds = new Long[0];
        Long[] poiIds = new Long[0];
        List<MapBuilding> changeBuildingList = null;
        Map<Long, MapBuilding> changeBuildingMap = null;
        List<MapOthersPolygon> changePolygonList = null;
        Map<Long, MapOthersPolygon> changePolygonMap = null;
        List<MapRoom> changeRoomList = null;
        Map<Long, MapRoom> changeRoomMap = null;
        //List<MapPoint> changePointList = null;
        //Map<Long, MapPoint> changePointMap = null;
        //List<MapPoint> newPointList = null;
        List<GisDataChangeSet> newDataList = new ArrayList<>();
        //Map<Integer, MapPoint> newPointMap = null;
        // 大楼变更列表
        try {
            changeBuildingList = buildingService.queryChangeList(zoneId);
            List<GisDataChangeSet> buildingList = loadListFromBuildingList(changeBuildingList);
            changeBuildingMap = changeBuildingList
                    .stream()
                    .collect(Collectors.toMap(MapBuilding :: getMapCode, MapBuilding -> MapBuilding));

            changeSetList.addAll(buildingList);
            wayIds = idArrayCombine(wayIds, loadIdArray(buildingList));
        }catch (Exception e){
            logger.error("查询大楼变更列表异常：",e);
            return new DataSynLog(zoneId, false, "查询大楼变更列表异常:"+e.getMessage());
        }


        // 面图元变更列表
        try {
            changePolygonList = othersPolygonService.queryChangeList(zoneId);
            changePolygonMap = changePolygonList
                    .stream()
                    .collect(Collectors.toMap(MapOthersPolygon :: getMapCode, MapOthersPolygon -> MapOthersPolygon));
            List<GisDataChangeSet> polygonList = loadListFromPolygonList(changePolygonList);

            changeSetList.addAll(polygonList);
            wayIds = idArrayCombine(wayIds, loadIdArray(polygonList));
        }catch (Exception e){
            logger.error("查询面图元变更列表异常：",e);
            return new DataSynLog(zoneId, false, "查询面图元变更列表异常:"+e.getMessage());
        }


        // 房间变更列表
        try {
            changeRoomList = roomService.queryChangeList(zoneId);
            List<GisDataChangeSet> roomList = loadListFromRoomList(changeRoomList);
            changeRoomMap = changeRoomList
                    .stream()
                    .collect(Collectors.toMap(MapRoom :: getMapCode, MapRoom -> MapRoom));
            changeSetList.addAll(roomList);
            wayIds = idArrayCombine(wayIds, loadIdArray(roomList));
        }catch (Exception e){
            logger.error("查询房间变更列表异常：",e);
            return new DataSynLog(zoneId, false, "查询房间变更列表异常:"+e.getMessage());
        }

        logger.info("changeBuilding:" + changeBuildingList.size()
                + "\nchangeRoom:" + changeRoomList.size()
                + "\nchangeOthersPolygon:" + changePolygonList.size()
                + "\nwayIds:" + wayIds.length
                + "\npoiIds:" + poiIds.length);
        try {
            if(wayIds.length > 0 || poiIds.length > 0 || newDataList.size() > 0){
                // 获取线/面原始数据
                Map<String, String> waysMap = new HashMap<>();
                waysMap.put("ways", StringUtils.join(wayIds,","));
                String wayOsmString = queryWays(waysMap);
                // 获取点原始数据
                Map<String, String> nodesMap = new HashMap<>();
                nodesMap.put("nodes", StringUtils.join(poiIds,","));
                String nodeOsmString = queryNodes(nodesMap);
                // 创建新的变更集
                String changeSetId = createChangeSet(GisDataUpdateUtils.createChangesetString());
                // 构建变更集
                String changeSet = GisDataUpdateUtils.creatOsmChange(wayOsmString,
                        nodeOsmString,
                        changeSetList,
                        null,
                        changeSetId);
                // 推送变更集，获取结果

                String putResult = updateChangeSet(Integer.parseInt(changeSetId), changeSet);

                List<GisDataChangeSet> resultList = GisDataUpdateUtils.readOsmString(putResult);
                for(GisDataChangeSet data : resultList){
                    String category = data.getCategory();
                    if("way".equals(category)){
                        if(changeBuildingMap.containsKey(data.getId())){
                            MapBuilding mapBuilding = changeBuildingMap.get(data.getId());
                            mapBuilding.setVersionCode(data.getVersion());
                            mapBuilding.setSynStatus(true);
                            changeBuildingMap.put(data.getId(), mapBuilding);
                        } else if(changePolygonMap.containsKey(data.getId())){
                            MapOthersPolygon polygon = changePolygonMap.get(data.getId());
                            polygon.setVersionCode(data.getVersion());
                            polygon.setSynStatus(true);
                            changePolygonMap.put(data.getId(), polygon);
                        } else if(changeRoomMap.containsKey(data.getId())){
                            MapRoom room = changeRoomMap.get(data.getId());
                            room.setVersionCode(data.getVersion());
                            room.setSynStatus(true);
                            changeRoomMap.put(data.getId(), room);
                        }
                    }

                }
                buildingService.saveAll(new ArrayList<>(changeBuildingMap.values()));
                roomService.saveAll(new ArrayList<>(changeRoomMap.values()));
                othersPolygonService.saveAll(new ArrayList<>(changePolygonMap.values()));

                // 关闭变更集
                closeChangeSet(Integer.parseInt(changeSetId));
                return new DataSynLog(zoneId, true, "推送成功");
            } else {
                return new DataSynLog(zoneId, true, "数据已同步，无需再次同步");
            }

        } catch (Exception e) {
            logger.error("推送数据出错", e);
            // 关闭变更集
            return new DataSynLog(zoneId, false, e);
        }
    }


    public List<DataSynLog> pushAllDataToGis() {
        JSONObject mapZoneObject = JSON.parseObject(
                queryMapZone(false, true, 0, 100));
        JSONObject dataObject = mapZoneObject.getJSONObject("data");
        JSONArray jsonArray = dataObject.getJSONArray("content");
        List<DataSynLog> list = new ArrayList<>();
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Integer zoneId = jsonObject.getInteger("id");
            list.add(pushDataToGis(zoneId));
        }
        return list;
    }

    void readDataFromJsonObject(JSONArray jsonArray, Integer zoneId, Integer start, Integer end){
        //threadPoolTaskExecutor.submit(()->{
        for(int k = start; k < end; k++){
            try {
                JSONObject dataObject = jsonArray.getJSONObject(k);
                JSONObject propertiesObject = dataObject.getJSONObject("tags");

                // 信息ID
                String dataId = dataObject.getLong("id").toString();
                if(StringUtils.isNotBlank(dataId)){
                    // 信息名称
                    String dataName = propertiesObject.getString("name");
                    // 版本号
                    Integer versionCode = dataObject.getInteger("version");
                    // 英文名
                    String enName = propertiesObject.containsKey("en_name")
                            ? propertiesObject.getString("en_name") : null;
                    // 别名
                    String alias = propertiesObject.containsKey("another_name")
                            ? propertiesObject.getString("another_name") : null;
                    // 门牌号
                    String houseNumber = propertiesObject.containsKey("door_sn")
                            ? propertiesObject.getString("door_sn") : null;
                    // 简介
                    String brief = propertiesObject.containsKey("content")
                            ? propertiesObject.getString("content") : null;
                    // 联系方式
                    String contact = propertiesObject.containsKey("contact")
                            ? propertiesObject.getString("contact") : null;
                    // 楼层
                    Integer level = propertiesObject.containsKey("level")
                            ? propertiesObject.getInteger("level") : null;

                    // 父图元ID
                    Long parrentId = dataObject.containsKey("parentId")
                            ? dataObject.getLong("parentId") : null;

                    if(propertiesObject.containsKey("polygon_category")){
                        // 这是面

                        // 分类ID
                        Integer typeId = null;
                        //logger.info(propertiesObject.toJSONString());
                        String newId = propertiesObject.getString("newid");
                        if(StringUtils.isNotBlank(newId)){
                            typeId = Integer.parseInt(newId);
                        }else {
                            logger.info("newId为空,name:"+dataName+",parrentId:"+parrentId+",mapCode:"+dataId);
                        }

                        String polygonCategory = propertiesObject.getString("polygon_category");

                        //logger.info(dataId + ":" + dataName + "/" + typeId + "/" + polygonCategory);
                        if(typeId!= null && "building".equals(polygonCategory) && buildingTypeMap.containsKey(typeId)){
                            // 这是大楼
                            // 如果不存在该条数据
                            // 或者版本号小于地图数据的版本号
                            // 就更新数据

                            //获取坐标
                            String lngLatString = dataObject.containsKey("center")
                                    ? dataObject.getString("center") : null;

                            String rasterLngLatString = dataObject.containsKey("rasterCenter")
                                    ? dataObject.getString("rasterCenter") : null;
                            Geometry lngLat = null;
                            Geometry rasterLngLat = null;

                            if(transformLngLatToGeomString(lngLatString) != null){
                                lngLat =  GeoJSON.gjson.read(transformLngLatToGeomString(lngLatString));
                            }
                            if(transformLngLatToGeomString(rasterLngLatString) != null){
                                rasterLngLat = GeoJSON.gjson.read(transformLngLatToGeomString(rasterLngLatString));
                            }

                            if(!buildingMap.containsKey(Long.parseLong(dataId))){
                                MapBuilding mapBuilding = new MapBuilding(null, typeId, dataName,
                                        zoneId, Long.parseLong(dataId), enName, alias, brief,
                                        true, false, versionCode, lngLat, rasterLngLat,Integer.parseInt(dataId), null);
                                readBuildingMap.put(Long.parseLong(dataId),mapBuilding);
                                fullBuildingMap.put(Long.parseLong(dataId),mapBuilding);
                            } else {
                                MapBuilding mapBuilding = buildingMap.get(Long.parseLong(dataId));
                                    mapBuilding.setTypeCode(typeId);
                                    mapBuilding.setBuildingName(dataName);
                                    mapBuilding.setCampusCode(zoneId);
                                    mapBuilding.setEnName(enName);
                                    mapBuilding.setAlias(alias);
                                    mapBuilding.setBrief(brief);
                                    mapBuilding.setSynStatus(true);
                                    mapBuilding.setDelete(false);
                                    mapBuilding.setVersionCode(versionCode);
                                    mapBuilding.setLngLat(lngLat);
                                    mapBuilding.setRasterLngLat(rasterLngLat);

                                    readBuildingMap.put(Long.parseLong(dataId), mapBuilding);
                                    fullBuildingMap.put(Long.parseLong(dataId),mapBuilding);

                                buildingMap.remove(Long.parseLong(dataId));
                            }

                        } else if("room".equals(polygonCategory) && roomTypeMap.containsKey(typeId)){
                            // 这是房间

                            //获取坐标
                            String lngLatString = dataObject.containsKey("geom")
                                    ? dataObject.getString("geom") : null;
                            String geomString = transformPolygonToGeomString(lngLatString);
                            Geometry lngLat = null;
                            try{
                                if(geomString != null){
                                    lngLat = GeoJSON.gjson.read(geomString);
                                }
                            }catch (Exception e){
                                mapCodeList.add(dataId);
                            }

                            if(!roomMap.containsKey(Long.parseLong(dataId))){

                                readRoomMap.put(Long.parseLong(dataId), new MapRoom(null, typeId, dataName, enName,
                                        alias, houseNumber, zoneId, Long.parseLong(dataId),
                                        parrentId, null, level, brief, true, false,
                                        lngLat, Integer.parseInt(dataId), null,versionCode));

                            } else {
                                MapRoom mapRoom = roomMap.get(Long.parseLong(dataId));
                                mapRoom.setTypeCode(typeId);
                                mapRoom.setRoomName(dataName);
                                mapRoom.setEnName(enName);
                                mapRoom.setAlias(alias);
                                mapRoom.setHourseNumber(houseNumber);
                                mapRoom.setCampusCode(zoneId);
                                mapRoom.setSynStatus(true);
                                mapRoom.setDelete(false);
                                mapRoom.setLeaf(level);
                                mapRoom.setBrief(brief);
                                mapRoom.setVersionCode(versionCode);
                                mapRoom.setLngLat(lngLat);

                                readRoomMap.put(Long.parseLong(dataId), mapRoom);

                                //}
                                roomMap.remove(Long.parseLong(dataId));
                            }
//                            }
                        } else if("others".equals(polygonCategory) && polygonTypeMap.containsKey(typeId)){
                            // 这是其他面图源
                            // 如果不存在该条数据
                            // 或者版本号小于地图数据的版本号
                            // 就更新数据
                            if(!polygonMap.containsKey(Long.parseLong(dataId))){
                                polygonList.add(new MapOthersPolygon(null, typeId, dataName,
                                        zoneId, Long.parseLong(dataId), level, enName,
                                        alias, brief, true, false, versionCode,
                                        Integer.parseInt(dataId), null));
                            } else {
                                MapOthersPolygon polygon = polygonMap.get(Long.parseLong(dataId));
                                if(polygon.getVersionCode() == null || polygon.getVersionCode() < versionCode){
                                    polygon.setTypeCode(typeId);
                                    polygon.setEnName(enName);
                                    polygon.setPolygonName(dataName);
                                    polygon.setAlias(alias);
                                    polygon.setLeaf(level);
                                    polygon.setSynStatus(true);
                                    polygon.setDelete(false);
                                    polygon.setCampusCode(zoneId);
                                    polygon.setBrief(brief);
                                    polygon.setVersionCode(versionCode);
                                    polygonList.add(polygon);
                                }
                                polygonMap.remove(Long.parseLong(dataId));
                            }
                        } else {
                            logger.info("未归类面：" + dataId + "/" + dataName + "/" + polygonCategory + "/" + typeId);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("错误：", e);
                success = false;
                exception = e;
                continue;
            }
        }
    }

    private String loadValueFromElementsWithKey(List<Element> elements, String key){
        for(Element element : elements){
            if(element.attributeValue("key").equals(key)){
                return element.attributeValue("value");
            }
        }
        return null;
    }

    private Boolean existKeyInElements(List<Element> elements, String key){
        for(Element element : elements){
            if(element.attributeValue("key").equals(key)){
                return true;
            }
        }
        return false;
    }

    List<GisDataChangeSet> loadListFromBuildingList(List<MapBuilding> mapBuildingList){
        List<GisDataChangeSet> list = new ArrayList<>();
        for(MapBuilding mapBuilding : mapBuildingList){
            list.add(new GisDataChangeSet(mapBuilding.getMapCode() == null
                    ? mapBuilding.getBuildingCode() : mapBuilding.getMapCode(),
                    "building",
                    mapBuilding.getTypeCode(),
                    mapBuilding.getBuildingName(),
                    mapBuilding.getDelete() ? GisDataChangeSetType.delete : GisDataChangeSetType.modify,
                    null,
                    mapBuilding.getEnName(),
                    mapBuilding.getAlias(),
                    null,
                    mapBuilding.getBrief(),
                    null));
        }
        return list;
    }

    List<GisDataChangeSet> loadListFromPolygonList(List<MapOthersPolygon> polygonList){
        List<GisDataChangeSet> list = new ArrayList<>();
        for(MapOthersPolygon polygon : polygonList){
            list.add(new GisDataChangeSet(polygon.getMapCode() == null
                    ? polygon.getPolygonCode() : polygon.getMapCode(),
                    "others",
                    polygon.getTypeCode(),
                    polygon.getPolygonName(),
                    polygon.getDelete() ? GisDataChangeSetType.delete : GisDataChangeSetType.modify,
                    null,
                    polygon.getEnName(),
                    polygon.getAlias(),
                    null,
                    polygon.getBrief(),
                    polygon.getLeaf()));
        }
        return list;
    }

    List<GisDataChangeSet> loadListFromRoomList(List<MapRoom> roomList){
        List<GisDataChangeSet> list = new ArrayList<>();
        for(MapRoom room : roomList){
            list.add(new GisDataChangeSet(room.getMapCode() == null
                    ? room.getRoomCode() : room.getMapCode(),
                    "room",
                    room.getTypeCode(),
                    room.getRoomName(),
                    room.getDelete() ? GisDataChangeSetType.delete : GisDataChangeSetType.modify,
                    null,
                    room.getEnName(),
                    room.getAlias(),
                    room.getHourseNumber(),
                    room.getBrief(),
                    room.getLeaf()));
        }
        return list;
    }


    Long[] loadIdArray(List<GisDataChangeSet> list){
        Long[] idArray = new Long[list.size()];
        for(int i = 0; i < list.size(); i++){
            idArray[i] = list.get(i).getId();
        }
        return idArray;
    }

    /**
     * 数组合并
     * @param original
     * @param newArray
     * @return
     */
    Long[] idArrayCombine(Long[] original, Long[] newArray){
        Integer originalLength = original.length;
        Integer additionalLength = newArray.length;
        if(originalLength == 0){
            return newArray;
        }
        Long[] resultArray = new Long[originalLength + additionalLength];
        System.arraycopy(original, 0, resultArray, 0, originalLength);
        System.arraycopy(newArray, 0, resultArray, originalLength, additionalLength);

        return resultArray;
    }

    /**
     * 重置变量
     */
    void resetValue(){
        // 重置为null
        buildingMap = null;
        fullBuildingMap = null;
        roomMap = null;
        polygonMap = null;
        //pointMap = null;

        readBuildingMap = null;
        readRoomMap = null;
        polygonList = null;
        //pointList = null;

        buildingTypeMap = null;
        roomTypeMap = null;
        polygonTypeMap = null;
        //pointTypeMap = null;

        //roomIds = leafs = zoneIds = null;

        success = true;
        exception = null;
    }

    /**
     * 信息解析完成后进行数据存储
     * @param zoneId
     */
    void saveData(Integer zoneId){
        //if(roomLoopCount == 0){
        // 批量存储数据
        logger.info("roomLoopCount：" + roomLoopCount);
        List<MapBuilding> buildingList = new ArrayList<>(readBuildingMap.values());
        List<MapRoom> roomList = new ArrayList<>(readRoomMap.values());
        logger.info("--msg--" + buildingList.size() + "/" + roomList.size());
        buildingService.saveAll(buildingList);
        othersPolygonService.saveAll(polygonList);

        List<List<MapRoom>> mapRoomListList = ListUtils.partition(roomList, 500);
        for(List<MapRoom> mapRoomList : mapRoomListList){
            roomService.saveAll(mapRoomList);
        }

        // 这是GIS数据中没有的，需要删除
        if(buildingMap.keySet().size() > 0){
            buildingService.deleteAfterSyn(buildingMap.keySet()
                    .toArray(new Long[buildingMap.keySet().size()]), zoneId);
        }
        if(roomMap.keySet().size() > 0){
            roomService.deleteAfterSyn(roomMap.keySet().toArray(new Long[roomMap.keySet().size()]), zoneId);
        }
        if(polygonMap.keySet().size() > 0){
            othersPolygonService.deleteAfterSyn(polygonMap.keySet()
                    .toArray(new Long[polygonMap.keySet().size()]), zoneId);

        }

    }

    /**
     * 为房间信息补充大楼信息
     */
    void updateRoomBuildingName(){
        //if(loopCount == 0){
//            if(roomIds.length() > 0){
//                try {
//                    Map<String, String> map = new HashMap<>();
//                    map.put("wayId", roomIds.substring(1));
//                    map.put("zoneId", zoneIds.substring(1));
//                    map.put("level", leafs.substring(1));
//
//                    String parentWayString = cmGisRpc.loadParentWayWithWayId(map);
//                    logger.info("parentWayString:" + parentWayString);
//                    JSONArray jsonArray = JSON
//                            .parseObject(parentWayString)
//                            .getJSONArray("data");
//                    Integer dataLength = jsonArray.size();
//                    roomLoopCount = dataLength % 100 == 0 ? dataLength / 100 : dataLength / 100 + 1;
//                    Integer readRoomLoopCount = roomLoopCount;
//                    for(int k = 0; k < readRoomLoopCount; k++){
//                        updateRoom(jsonArray, k * 100, Math.min((k + 1) * 100, dataLength), zoneId);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    success = false;
//                    exception = e;
//                }
//            } else {
//                roomLoopCount = 0;
//                saveData(zoneId);
//            }
        for(Long roomMapCode : readRoomMap.keySet()){
            MapRoom mapRoom = readRoomMap.get(roomMapCode);
            MapBuilding mapBuilding = fullBuildingMap.getOrDefault(mapRoom.getBuildingMapCode(), null);
            if(mapBuilding != null){
                mapRoom.setBuildingMapCode(mapBuilding.getMapCode());
                mapRoom.setBuildingName(mapBuilding.getBuildingName());
                readRoomMap.put(mapRoom.getMapCode(), mapRoom);
            } else {
                logger.info("设置房间所属大楼失败：" + mapRoom.getMapCode());
            }
        }
        //saveData(zoneId);
        //}

    }

    void updateRoom(JSONArray jsonArray, Integer start, Integer end, Integer zoneId){

        for(int m = start; m < end; m++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(m);
                if(jsonObject.getLong("parentWayId") != 0L){
                    Long roomMapCode = jsonObject.getLong("wayId");
                    MapRoom mapRoom = readRoomMap.get(roomMapCode);
                    Long buildingMapCode = jsonObject.getLong("parentWayId");
                    MapBuilding mapBuilding = fullBuildingMap.getOrDefault(buildingMapCode, null);
                    if(mapBuilding != null){
                        mapRoom.setBuildingMapCode(mapBuilding.getMapCode());
                        mapRoom.setBuildingName(mapBuilding.getBuildingName());
                        readRoomMap.put(mapRoom.getMapCode(), mapRoom);
                    } else {
                        logger.info(roomMapCode + "/" + buildingMapCode);
                    }
                }
            } catch (Exception e) {
                logger.error("设置房间所属大楼失败：", e);
                success = false;
                exception = e;
                continue;
            }
        }
        if(success){
            roomLoopCount -= 1;
            saveData(zoneId);
        }
        //});
    }


    private JSONArray requestDataFromGis(Integer zoneId, String tagName, String textMatch, String dataType){
        int page = 0;
        int pageSize = 30000;
        boolean hasNext = true;

        JSONArray dataJsonArray = new JSONArray();

        while (hasNext){
            JSONObject responseData = JSON.parseObject(
                    searchWithTag(page, pageSize, zoneId, tagName, null, textMatch, dataType));
            if(responseData == null || responseData .getJSONObject("data") == null){
                logger.error("同步参数：-------》》" + textMatch + ",当前页：---》" + page);
                logger.error("同步对象：--------》》" + responseData);
            }else {
                dataJsonArray.addAll(responseData
                        .getJSONObject("data")
                        .getJSONArray("content"));
                hasNext = !responseData
                        .getJSONObject("data")
                        .getBoolean("last");
                page += 1;
            }

        }
        return dataJsonArray;
    }

    /**
     * 构建geomtry 对象
     */
    private String transformLngLatToGeomString(String lngLatString){

        if(StringUtils.isNotEmpty(lngLatString)){
            String[] lngLat = lngLatString.split(",");
            if(lngLat.length == 2){
                Double x = Double.parseDouble(lngLat[0]);
                Double y = Double.parseDouble(lngLat[1]);
                String point = "{\"type\":\"Point\",\"coordinates\":["+ x +","+ y +"]}";
                return point;
            }
            return null;
        }
        return null;
    }

    /**
     * 构建geomtry 对象
     */
    private String transformPolygonToGeomString(String lngLatString){

        if(StringUtils.isNotEmpty(lngLatString)){
            String polygon = "{\"type\":\"Polygon\",\"coordinates\":" + lngLatString +"}";
           // logger.info("lngLatString---->" + polygon);
            return polygon;
        }
        return null;
    }

}
