package com.lqkj.web.gnsc.modules.gns.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.dao.GnsStoreItemDao;
import com.lqkj.web.gnsc.modules.gns.dao.PointTypeDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsDisplayPointType;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStoreItem;
import com.lqkj.web.gnsc.modules.portal.dao.*;
import com.lqkj.web.gnsc.modules.portal.model.*;
import com.lqkj.web.gnsc.modules.portal.service.*;
import com.lqkj.web.gnsc.utils.DisableSSLCertificateCheckUtil;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PointTypeService {
    @Autowired
    private PointTypeDao pointTypeDao;
    @Autowired
    private MapPointImgService pointImgService;
    @Autowired
    private MapPointExtendsService pointExtendsService;
    @Autowired
    private MapPointDao pointDao;
    @Autowired
    private MapBuildingDao buildingDao;
    @Autowired
    private MapBuildingImgService buildingImgService;
    @Autowired
    private MapBuildingExtendsService buildingExtendsService;
    @Autowired
    private MapRoomDao roomDao;
    @Autowired
    private MapRoomImgService roomImgService;
    @Autowired
    private MapRoomExtendsService roomExtendsService;
    @Autowired
    private MapOthersPolygonDao othersPolygonDao;
    @Autowired
    private MapOthersPolygonImgService othersPolygonImgService;
    @Autowired
    private MapOthersPolygonExtendsService othersPolygonExtendsService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 保存默认地标
     *
     * @param pointTypeList
     * @return
     */
    public MessageListBean add(List<GnsDisplayPointType> pointTypeList) {

        return MessageListBean.ok(pointTypeDao.saveAll(pointTypeList));
    }

    /**
     * 根据学校获取地标分类
     */
    public List<Map<String,Object>> queryList(Integer schoolId){
        List<Map<String,Object>> pointTypeList = pointTypeDao.queryList(schoolId);
        //添加热门地标
        Map<String,Object> hotPointType = new HashMap<>();
        hotPointType.put("typeCode",-1);
        hotPointType.put("typeName","热门地标");
        pointTypeList.add(0,hotPointType);

        return pointTypeList;
    }

    /**
     * 获取指定分类下地标列表
     */
    public JSONArray queryByType(Integer campusCode,Integer typeCode){
        JSONArray resultList = new JSONArray();
        if(typeCode == -1){
            String result = pointTypeDao.queryHotListWithType(campusCode);
            if(StringUtils.isNotBlank(result)){
                resultList = JSONArray.parseArray(result);
            }
        }else{
            String result = pointTypeDao.queryListWithType(campusCode,typeCode);
            if(StringUtils.isNotBlank(result)){
                resultList = JSONArray.parseArray(result);
            }
        }
        return resultList;
    }

    /**
     * 获取地标详情
     */
    public Object queryByMapCode(Integer mapCode,String mapType){
        try {
            Map<String,Object> resultMap = new HashMap<>();
            if("point".equals(mapType) && pointDao.existsByPointCode(mapCode)){
                Map<String,Object> linkedHashMap = pointDao.queryDetailByPointCode(mapCode);
                resultMap.putAll(linkedHashMap);
                resultMap.put("type", "point");
                resultMap.put("mapPointImgList",pointImgService.queryListWithPointCode(mapCode));
                resultMap.put("mapPointExtendsList",pointExtendsService.queryList(mapCode));

                if(linkedHashMap.get("vectorGeom") != null){
                    resultMap.put("vectorGeom", JSONObject.parseObject(linkedHashMap.get("vectorGeom").toString()));
                }
                return resultMap;

            } else{
                if(buildingDao.existsByMapCode(Long.parseLong(mapCode.toString()))){
                    Map<String,Object> linkedHashMap = buildingDao.queryDetailByMapCode(Long.parseLong(mapCode.toString()));
                    resultMap.putAll(linkedHashMap);
                    resultMap.put("type", "polygon");
                    resultMap.put("mapBuildingImgList",buildingImgService.queryListWithBuildingCode(Integer.parseInt(linkedHashMap.get("buildingCode").toString())));
                    resultMap.put("mapBuildingExtendsList",buildingExtendsService.queryList(Integer.parseInt(linkedHashMap.get("buildingCode").toString())));

                    if(linkedHashMap.get("vectorGeom") != null){
                        resultMap.put("vectorGeom", JSONObject.parseObject(linkedHashMap.get("vectorGeom").toString()));
                    }
                    return resultMap;
                }else if(roomDao.existsByMapCode(Long.parseLong(mapCode.toString()))){
                    Map<String,Object> linkedHashMap = roomDao.queryDetailByMapCode(Long.parseLong(mapCode.toString()));
                    resultMap.putAll(linkedHashMap);
                    resultMap.put("type", "polygon");
                    resultMap.put("mapRoomImgList",roomImgService.queryListWithRoomCode(Integer.parseInt(linkedHashMap.get("roomCode").toString())));
                    resultMap.put("mapRoomExtendsList",roomExtendsService.queryList(Integer.parseInt(linkedHashMap.get("roomCode").toString())));

                    if(linkedHashMap.get("vectorGeom") != null){
                        resultMap.put("vectorGeom", JSONObject.parseObject(linkedHashMap.get("vectorGeom").toString()));
                    }
                    return resultMap;
                }else if(othersPolygonDao.existsByMapCode(Long.parseLong(mapCode.toString()))){
                    Map<String,Object> linkedHashMap = othersPolygonDao.queryDetailByMapCode(Long.parseLong(mapCode.toString()));
                    resultMap.putAll(linkedHashMap);
                    resultMap.put("type", "polygon");
                    resultMap.put("mapOtherPolygonImgList",othersPolygonImgService.queryListWithPolygonCode(Integer.parseInt(linkedHashMap.get("polygonCode").toString())));
                    resultMap.put("mapOtherPolygonExtendsList",othersPolygonExtendsService.queryList(Integer.parseInt(linkedHashMap.get("polygonCode").toString())));

                    if(linkedHashMap.get("vectorGeom") != null){
                        resultMap.put("vectorGeom", JSONObject.parseObject(linkedHashMap.get("vectorGeom").toString()));
                    }
                    return resultMap;
                }else {
                    return null;
                }
            }
        } catch (Exception e) {
            ;logger.error(e.getMessage(),e);
            return null;
        }
    }
}
