package com.lqkj.web.gnsc.modules.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lqkj.web.gnsc.modules.gns.dao.GnsStoreItemDao;
import com.lqkj.web.gnsc.modules.gns.dao.SchoolCampusDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStoreItem;
import com.lqkj.web.gnsc.modules.gns.domain.vo.PointVO;
import com.lqkj.web.gnsc.modules.portal.dao.MapBuildingTypeDao;
import com.lqkj.web.gnsc.modules.portal.model.*;
import com.lqkj.web.gnsc.modules.portal.model.vo.MapBuildingVO;
import com.lqkj.web.gnsc.modules.portal.model.vo.MapOthersPolygonVO;
import com.lqkj.web.gnsc.modules.portal.model.vo.MapPointVO;
import com.lqkj.web.gnsc.modules.portal.model.vo.MapRoomVO;
import com.lqkj.web.gnsc.modules.portal.service.MapBuildingTypeService;
import com.lqkj.web.gnsc.modules.portal.service.MapOthersPolygonTypeService;
import com.lqkj.web.gnsc.modules.portal.service.MapPointTypeService;
import com.lqkj.web.gnsc.modules.portal.service.MapRoomTypeService;
import com.lqkj.web.gnsc.utils.*;
import com.vividsolutions.jts.geom.Geometry;
import org.apache.commons.lang.StringUtils;
import org.geotools.geojson.geom.GeometryJSON;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 推送数据到搜索引擎的service实现
 * @author ry
 * @since 2019-7-31 19:07:34
 */
public class BaseService {

    @Autowired
    private GnsStoreItemDao storeItemDao;
    @Autowired
    private MapBuildingTypeService buildingTypeService;
    @Autowired
    private MapOthersPolygonTypeService othersPolygonTypeService;
    @Autowired
    private MapPointTypeService pointTypeService;
    @Autowired
    private MapRoomTypeService roomTypeService;
    @Autowired
    private SchoolCampusDao campusDao;

    protected static ObjectMapper objectMapper = new ObjectMapper();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 更新搜索引擎
     */
    protected String pushSearchIndex(MapIndexTemplate mapIndexTemplate){
        GnsStoreItem serverApiUrl = storeItemDao.findMapConfig("otherConfigurations", "cmgisApiUrl");
        GnsStoreItem mapToken = storeItemDao.findMapConfig("otherConfigurations", "mapToken");

        try {
            String resultJsonString = HttpClientUtil.sendDelete(serverApiUrl.getItemValue() + "/map/v2/search/index",
                    JSON.toJSONString(mapIndexTemplate),mapToken.getItemValue());

            if(StringUtils.isNotBlank(resultJsonString)){
                return resultJsonString;
            }else {
                return null;
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return null;
        }

    }

    /**
     * 批量更新搜索引擎
     */
    protected String bulkPushSearchIndex(List<MapIndexTemplate> mapIndexTemplateList){
        GnsStoreItem serverApiUrl = storeItemDao.findMapConfig("otherConfigurations", "cmgisApiUrl");
        GnsStoreItem mapToken = storeItemDao.findMapConfig("otherConfigurations", "mapToken");

        try {
            String resultJsonString = HttpClientUtil.sendDelete(serverApiUrl.getItemValue() + "/map/v2/search/bulkIndex",
                    JSON.toJSONString(mapIndexTemplateList),mapToken.getItemValue());

            if(StringUtils.isNotBlank(resultJsonString)){
                return resultJsonString;
            }else {
                return null;
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return null;
        }

    }



    /**
     *  删除搜索引擎
     */
    protected String removeSearchIndex(MapIndexTemplate mapIndexTemplate){
        GnsStoreItem serverApiUrl = storeItemDao.findMapConfig("otherConfigurations", "cmgisApiUrl");
        GnsStoreItem mapToken = storeItemDao.findMapConfig("otherConfigurations", "mapToken");

        try {
            String resultJsonString = HttpClientUtil.sendDelete(serverApiUrl.getItemValue() + "/map/v2/search/removeIndex",
                    JSON.toJSONString(mapIndexTemplate),mapToken.getItemValue());

            if(StringUtils.isNotBlank(resultJsonString)){
                return resultJsonString;
            }else {
                return null;
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return null;
        }

    }

    /**
     *  批量删除搜索引擎
     */
    protected String bulkRemoveSearchIndex(List<MapIndexTemplate> mapIndexTemplateList){
        GnsStoreItem serverApiUrl = storeItemDao.findMapConfig("otherConfigurations", "cmgisApiUrl");
        GnsStoreItem mapToken = storeItemDao.findMapConfig("otherConfigurations", "mapToken");

        try {
            String resultJsonString = HttpClientUtil.sendDelete(serverApiUrl.getItemValue() + "/map/v2/search/bulkRemoveIndex",
                    JSON.toJSONString(mapIndexTemplateList),mapToken.getItemValue());

            if(StringUtils.isNotBlank(resultJsonString)){
                return resultJsonString;
            }else {
                return null;
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     *     更新一个边/面的名称，同时更新搜索引擎
     */
    protected String updateWayName(String zoneId, String wayName, String contact,
                                   String content, String doorSign,String wayAliasName,
                                   String wayEnName,Long wayId)throws IOException{
        GnsStoreItem serverApiUrl = storeItemDao.findMapConfig("otherConfigurations", "cmgisApiUrl");
        GnsStoreItem mapToken = storeItemDao.findMapConfig("otherConfigurations", "mapToken");

        Map<String,Object> params = new HashMap<>();
        params.put("wayName",wayName);
        params.put("contact",contact);
        params.put("content",content);
        params.put("doorSign",doorSign);
        params.put("wayAliasName",wayAliasName);
        params.put("wayEnName",wayEnName);
        params.put("wayId",wayId);

        String jsonData = JSON.toJSONString(params);
        try {
            String resultJsonString = HttpClientUtil.sendPut(serverApiUrl.getItemValue() + "/map/v3/update/wayName/" + zoneId,
                    jsonData,mapToken.getItemValue());

            if(StringUtils.isNotBlank(resultJsonString)){
                return resultJsonString;
            }else {
                return null;
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return null;
        }
    }


    /**
     * 获取面/线的空间数据
     * @return
     */
    protected String queryWayGeoJson(Long[] id){
        GnsStoreItem serverApiUrl = storeItemDao.findMapConfig("otherConfigurations", "cmgisApiUrl");
        GnsStoreItem mapToken = storeItemDao.findMapConfig("otherConfigurations", "mapToken");

        try {
            String resultJsonString = HttpClientUtil.sendPut(serverApiUrl.getItemValue() + "/map/v2/data/geojson/way/select?is2D=true",
                    JSON.toJSONString(id),mapToken.getItemValue());

            if(StringUtils.isNotBlank(resultJsonString)){
                return resultJsonString;
            }else {
                return null;
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 根据多点位从cmgis获取路径规划结果
     * @return
     */
    protected String queryRouteWithMultiPoint(List<PointVO> pointList,Integer campusCode){
        GnsStoreItem serverApiUrl = storeItemDao.findMapConfig("otherConfigurations", "cmgisApiUrl");
        GnsStoreItem mapToken = storeItemDao.findMapConfig("otherConfigurations", "mapToken");

        try {
            String resultJsonString = HttpClientUtil.sendPut(serverApiUrl.getItemValue() + "map/route/v3/multiPoint/split/find/" + campusCode,
                    JSON.toJSONString(pointList),mapToken.getItemValue());

            if(StringUtils.isNotBlank(resultJsonString)){
                return resultJsonString;
            }else {
                return null;
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 根据二维的经纬度获取三维的经纬度
     */
    protected String transferVectorToRaster(Double lng, Double lat){
        GnsStoreItem serverApiUrl = storeItemDao.findMapConfig("otherConfigurations", "cmgisApiUrl");
        GnsStoreItem mapToken = storeItemDao.findMapConfig("otherConfigurations", "mapToken");

        try {
            Map<String,Object> params = new HashMap<>();
            params.put("lat",lat);
            params.put("lon",lng);
            String resultJsonString = HttpClientUtil.sendGet(serverApiUrl.getItemValue() + "/map/matrix/v2/projection/vector",
                   params);

            if(StringUtils.isNotBlank(resultJsonString)){
                return resultJsonString;
            }else {
                return null;
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return null;
        }

    }



    protected List<MapIndexTemplate> loadPushMapIndexTemplateFromBuilding(List<MapBuildingVO> buildingVOList){
        List<MapIndexTemplate> templateList = new ArrayList<>();

        Map<Integer, MapBuildingType> mapBuildingTypeMap =buildingTypeService.queryList(null)
                .stream()
                .collect(Collectors.toMap(MapBuildingType::getTypeCode, MapBuildingType -> MapBuildingType));
        for(MapBuildingVO mapBuildingVO : buildingVOList){
            if(mapBuildingVO.getMapCode() != null){
                String typeName = mapBuildingTypeMap.containsKey(mapBuildingVO.getTypeCode())
                        ? mapBuildingTypeMap.get(mapBuildingVO.getTypeCode()).getTypeName() :
                        null;
                org.json.simple.JSONObject center = null;
                org.json.simple.JSONObject polygon = null;
                if(mapBuildingVO.getGeoJson()!=null){
                    try {
                        GeometryJSON gjson = new GeometryJSON();
                        Reader reader = new StringReader(mapBuildingVO.getGeoJson().getJSONObject("geometry").toJSONString());
                        Geometry geometry = gjson.read(reader);
                        String lonlatStr = geometry.getCentroid().getX() + "," + geometry.getCentroid().getY();
                        center = loadJsonObjectFromLngLatString(lonlatStr);
                    }catch (IOException e){
                        logger.error(e.getMessage(),e);
                    }
                    try {
                        polygon = (org.json.simple.JSONObject) JSONValue.parse(mapBuildingVO.getGeoJson().getJSONObject("geometry").toJSONString());
                    }catch (Exception e){
                        logger.error(e.getMessage(),e);
                    }

                }

                MapIndexTemplate mapIndexTemplate = new MapIndexTemplate(SystemType.map, mapBuildingVO.getCampusCode(),
                        mapBuildingVO.getMapCode().toString(),null, null,
                        mapBuildingVO.getBuildingName(), null, mapBuildingVO.getAlias(),
                        mapBuildingVO.getEnName(), null, GeomType.polygon, center,
                        polygon, new Date(), new int[0], new String[0],
                        new String[]{typeName + "||polygon" },0);
                // 这里在分类名称里补了"||polygon",暂时解决搜索后获取详情的判断
                templateList.add(mapIndexTemplate);
            }
        }
        return templateList;
    }



    protected List<MapIndexTemplate> loadPushMapIndexTemplateFromOthersPolygon(List<MapOthersPolygonVO> polygonVOList){
        List<MapIndexTemplate> templateList = new ArrayList<>();

        Map<Integer, MapOthersPolygonType> polygonTypeMap = othersPolygonTypeService.queryList(null)
                .stream()
                .collect(Collectors.toMap(MapOthersPolygonType::getTypeCode,
                        MapOthersPolygonType -> MapOthersPolygonType));

        for(MapOthersPolygonVO othersPolygonVO : polygonVOList){
            if(othersPolygonVO.getMapCode() != null){
                String typeName = polygonTypeMap.containsKey(othersPolygonVO.getTypeCode())
                        ? polygonTypeMap.get(othersPolygonVO.getTypeCode()).getTypeName():
                        null;

                org.json.simple.JSONObject center = null;
                org.json.simple.JSONObject polygon = null;
                if(othersPolygonVO.getGeoJson()!=null){
                    try {
                        GeometryJSON gjson = new GeometryJSON();
                        Reader reader = new StringReader(othersPolygonVO.getGeoJson().getJSONObject("geometry").toJSONString());
                        Geometry geometry = gjson.read(reader);
                        String lonlatStr = geometry.getCentroid().getX() + "," + geometry.getCentroid().getY();
                        center = loadJsonObjectFromLngLatString(lonlatStr);
                    }catch (IOException e){
                        logger.error(e.getMessage(),e);
                    }
                    try {
                        polygon = (org.json.simple.JSONObject) JSONValue.parse(othersPolygonVO.getGeoJson().getJSONObject("geometry").toJSONString());
                    }catch (Exception e){
                        logger.error(e.getMessage(),e);
                    }
                }

                templateList.add(new MapIndexTemplate(SystemType.map, othersPolygonVO.getCampusCode(),
                        othersPolygonVO.getPolygonCode().toString(),null, null,
                        othersPolygonVO.getPolygonName(), null, othersPolygonVO.getAlias(),
                        othersPolygonVO.getEnName(), null, GeomType.polygon, center,
                        polygon, new Date(), new int[0], new String[0],
                        new String[]{typeName + "||polygon"},6));
            }
        }
        return templateList;
    }

    protected List<MapIndexTemplate> loadPushMapIndexTemplateFromPoint(List<MapPointVO> mapPointVOList) {
        List<MapIndexTemplate> templateList = new ArrayList<>();

        Map<Integer, MapPointType> pointTypeMap = pointTypeService.listAll()
                .stream()
                .collect(Collectors.toMap(MapPointType::getTypeCode, MapPointType -> MapPointType));

        for(MapPointVO pointVO : mapPointVOList){

            org.json.simple.JSONObject jsonObject =
                    loadJsonObjectFromLngLatString(pointVO.getLngLatString());



            String typeName = pointTypeMap.containsKey(pointVO.getTypeCode())
                    ? pointTypeMap.get(pointVO.getTypeCode()).getTypeName() :
                    null;

            Integer rasterCampusCode = campusDao.findByVectorZoomCode(pointVO.getCampusCode()).getRasterZoomCode();


            if (rasterCampusCode != null && StringUtils.isNotBlank(pointVO.getRasterLngLatString()) && pointVO.getLeaf() == null) {
                org.json.simple.JSONObject jsonRasterObject =
                        loadJsonObjectFromLngLatString(pointVO.getRasterLngLatString());
                templateList.add(new MapIndexTemplate(SystemType.pubpoi, rasterCampusCode,
                        pointVO.getPointCode().toString(), null, pointVO.getBuildingName(),
                        pointVO.getPointName(), null, null,
                        null, pointVO.getLeaf(), GeomType.point, jsonRasterObject,
                        jsonRasterObject, new Date(), new int[0], new String[0],
                        new String[]{typeName + "||point"}, 3));
            }

            templateList.add(new MapIndexTemplate(SystemType.pubpoi, pointVO.getCampusCode(),
                    pointVO.getPointCode().toString(), null, pointVO.getBuildingName(),
                    pointVO.getPointName(), null, null,
                    null, pointVO.getLeaf(), GeomType.point, jsonObject,
                    jsonObject, new Date(), new int[0], new String[0],
                    new String[]{typeName + "||point"}, 3));


        }
        return templateList;
    }

    protected List<MapIndexTemplate> loadPushMapIndexTemplateFromRoom(List<MapRoomVO> mapRoomVOList){
        List<MapIndexTemplate> templateList = new ArrayList<>();

        Map<Integer, MapRoomType> roomTypeMap = roomTypeService.queryList(null)
                .stream()
                .collect(Collectors.toMap(MapRoomType::getTypeCode, MapRoomType -> MapRoomType));

        for(MapRoomVO roomVO : mapRoomVOList){
            if(roomVO.getMapCode() != null){
                String typeName = roomTypeMap.containsKey(roomVO.getTypeCode())
                        ? roomTypeMap.get(roomVO.getTypeCode()).getTypeName() :
                        null;
                org.json.simple.JSONObject geometry = null;
                try {
                    geometry = roomVO.getGeoJson() == null ? null : (org.json.simple.JSONObject) JSONValue.parse(roomVO.getGeoJson().getJSONObject("geometry").toJSONString());
                }catch (Exception e){
                    logger.error(e.getMessage(),e);
                }
                templateList.add(new MapIndexTemplate(SystemType.map, roomVO.getCampusCode(),
                        roomVO.getMapCode().toString(),roomVO.getBuildingMapCode().toString(), roomVO.getBuildingName(),
                        roomVO.getRoomName(), roomVO.getHourseNumber(), roomVO.getAlias(),
                        roomVO.getEnName(), null, GeomType.polygon,
                        roomVO.getLngLatString()==null?null:loadJsonObjectFromLngLatString(roomVO.getLngLatString()),
                        geometry,
                        new Date(), new int[0], new String[0],
                        new String[]{typeName + "||polygon"},1));
            }
        }

        return templateList;
    }


    private org.json.simple.JSONObject loadJsonObjectFromLngLatString(String lngLatString){
        try {
            return (org.json.simple.JSONObject) JSONValue.parse(
                    GeoJSON.write(GeoUtils.createPoint(lngLatString)));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            //e.printStackTrace();
            return null;
        }
    }
}
