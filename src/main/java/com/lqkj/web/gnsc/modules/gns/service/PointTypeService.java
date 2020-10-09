package com.lqkj.web.gnsc.modules.gns.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.dao.GnsStoreItemDao;
import com.lqkj.web.gnsc.modules.gns.dao.HotPointVODao;
import com.lqkj.web.gnsc.modules.gns.dao.PointTypeDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsDisplayPointType;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStoreItem;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsHelperVO;
import com.lqkj.web.gnsc.modules.gns.domain.vo.HotPointVO;
import com.lqkj.web.gnsc.modules.portal.dao.*;
import com.lqkj.web.gnsc.modules.portal.model.*;
import com.lqkj.web.gnsc.modules.portal.service.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private HotPointVODao hotPointVODao;
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
     * 分页
     */
    public Page<HotPointVO> page(Integer schoolId,Integer campusCode, String pointName, Integer page, Integer pageSize){

        Pageable pageable = PageRequest.of(page,pageSize);
        return hotPointVODao.page(schoolId,campusCode,pointName,pageable);
    }

    /**
     * 开启/禁用
     */
    public MapPoint open(Integer pointCode,Boolean open){
        MapPoint point = pointDao.queryByPointCode(pointCode);
        point.setGnsHot(open);
        return pointDao.save(point);
    }

    /**
     * 编辑点击数
     */
    public MapPoint updateThumpsUp(Integer pointCode, Integer thumpsUpCount){
        MapPoint point = pointDao.queryByPointCode(pointCode);
        point.setThumbsUpCount(thumpsUpCount);
        return pointDao.save(point);
    }

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
        hotPointType.put("typecode",-1);
        hotPointType.put("typename","热门地标");
        hotPointType.put("vectoricon","/upload/images/icon-hot.png");
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
            JSONObject resultJson = new JSONObject();
            if("point".equals(mapType) && pointDao.existsByPointCode(mapCode)){
                String pointJsonString = pointDao.queryDetailByPointCode(mapCode);
                JSONObject pointJson = JSONObject.parseObject(pointJsonString);
                pointJson.put("type", "point");
                pointJson.put("mapPointImgList",pointImgService.queryListWithPointCode(mapCode));
                pointJson.put("mapPointExtendsList",pointExtendsService.queryList(mapCode));

                return pointJson;

            } else{
                if(buildingDao.existsByMapCode(Long.parseLong(mapCode.toString()))){
                    String buildingJsonString = buildingDao.queryDetailByMapCode(Long.parseLong(mapCode.toString()));
                    JSONObject buildingJson = JSONObject.parseObject(buildingJsonString);
                    buildingJson.put("type", "polygon");
                    buildingJson.put("mapBuildingImgList",buildingImgService.queryListWithBuildingCode(Integer.parseInt(buildingJson.get("buildingCode").toString())));
                    buildingJson.put("mapBuildingExtendsList",buildingExtendsService.queryList(Integer.parseInt(buildingJson.get("buildingCode").toString())));

                    return buildingJson;
                }else if(roomDao.existsByMapCode(Long.parseLong(mapCode.toString()))){
                    String roomJsonString = roomDao.queryDetailByMapCode(Long.parseLong(mapCode.toString()));
                    JSONObject roomJson = JSONObject.parseObject(roomJsonString);
                    roomJson.put("type", "polygon");
                    roomJson.put("mapRoomImgList",roomImgService.queryListWithRoomCode(Integer.parseInt(roomJson.get("roomCode").toString())));
                    roomJson.put("mapRoomExtendsList",roomExtendsService.queryList(Integer.parseInt(roomJson.get("roomCode").toString())));

                    return roomJson;
                }else if(othersPolygonDao.existsByMapCode(Long.parseLong(mapCode.toString()))){
                    String otherPolygonJsonString = othersPolygonDao.queryDetailByMapCode(Long.parseLong(mapCode.toString()));
                    JSONObject otherPolygonJson = JSONObject.parseObject(otherPolygonJsonString);
                    otherPolygonJson.put("type", "polygon");
                    otherPolygonJson.put("mapOtherPolygonImgList",othersPolygonImgService.queryListWithPolygonCode(Integer.parseInt(otherPolygonJson.get("polygonCode").toString())));
                    otherPolygonJson.put("mapOtherPolygonExtendsList",othersPolygonExtendsService.queryList(Integer.parseInt(otherPolygonJson.get("polygonCode").toString())));

                    return otherPolygonJson;
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
