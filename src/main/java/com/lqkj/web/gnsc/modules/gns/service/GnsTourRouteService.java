package com.lqkj.web.gnsc.modules.gns.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.dao.*;
import com.lqkj.web.gnsc.modules.gns.domain.*;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsTourPointVO;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsTourRouteForm;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsTourRouteVO;
import com.lqkj.web.gnsc.modules.gns.domain.vo.PointVO;
import com.lqkj.web.gnsc.modules.portal.dao.MapBuildingDao;
import com.lqkj.web.gnsc.modules.portal.dao.MapOthersPolygonDao;
import com.lqkj.web.gnsc.modules.portal.dao.MapPointDao;
import com.lqkj.web.gnsc.modules.portal.dao.MapRoomDao;
import com.lqkj.web.gnsc.modules.portal.model.MapBuilding;
import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygon;
import com.lqkj.web.gnsc.modules.portal.model.MapPoint;
import com.lqkj.web.gnsc.modules.portal.model.MapRoom;
import com.lqkj.web.gnsc.utils.DisableSSLCertificateCheckUtil;
import com.lqkj.web.gnsc.utils.HttpClientUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author cs
 */
@Service
@Transactional
public class GnsTourRouteService {

    @Autowired
    private GnsTourRouteDao tourRouteDao;
    @Autowired
    private GnsTourRouteVODao routeVODao;
    @Autowired
    private GnsTourPointDao tourPointDao;
    @Autowired
    private MapPointDao pointDao;
    @Autowired
    private MapBuildingDao buildingDao;
    @Autowired
    private MapOthersPolygonDao othersPolygonDao;
    @Autowired
    private MapRoomDao roomDao;
    @Autowired
    private SchoolCampusDao campusDao;
    @Autowired
    private GnsStoreItemDao storeItemDao;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 分页
     */
    public Page<GnsTourRouteVO> page(Integer campusCode,String routeName,Integer page,Integer pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        return routeVODao.page(campusCode,routeName,pageable);
    }

    /**
     *
     * 根据校区获取路线
     */
    public List<Map<String,Object>> findByCampusCode(Integer campusCode){

        return tourRouteDao.findAllByCampusCode(campusCode);
    }

    /**
     * 根据主键获取
     */
    public GnsTourRoute get(Integer routeId){
        return tourRouteDao.findById(routeId).get();
    }

    /**
     * 获取指定推荐路线详情
     */
    public Object getRouteDetail(Integer routeId){
        //获取路线基本信息
        String routeInfo = tourRouteDao.findRouteById(routeId);
        if(StringUtils.isNotBlank(routeInfo)){
            JSONObject routeResult = JSONObject.parseObject(routeInfo);
            JSONArray properties = new JSONArray();
            //根据路线获取点位
            List<GnsTourPoint> pointList = tourPointDao.findAllByRouteId(routeId);
            if(pointList.size() > 0){
                pointList.forEach(v ->{
                    if("point".equals(v.getElementType()) && pointDao.existsByPointCode(Integer.parseInt(v.getMapCode().toString()))){
                        String pointJsonString = pointDao.queryDetailByPointCode(Integer.parseInt(v.getMapCode().toString()));
                        JSONObject pointJson = JSONObject.parseObject(pointJsonString);
                        properties.add(pointJson);
                    }else {
                        if(buildingDao.existsByMapCode(Long.parseLong(v.getMapCode().toString()))){
                            String buildingJsonString = buildingDao.queryDetailByMapCode(Long.parseLong(v.getMapCode().toString()));
                            JSONObject buildingJson = JSONObject.parseObject(buildingJsonString);
                            properties.add(buildingJson);
                        }else if(roomDao.existsByMapCode(Long.parseLong(v.getMapCode().toString()))){
                            String roomJsonString = roomDao.queryDetailByMapCode(Long.parseLong(v.getMapCode().toString()));
                            JSONObject roomJson = JSONObject.parseObject(roomJsonString);
                            properties.add(roomJson);
                        }else if(othersPolygonDao.existsByMapCode(Long.parseLong(v.getMapCode().toString()))){
                            String otherPolygonJsonString = othersPolygonDao.queryDetailByMapCode(Long.parseLong(v.getMapCode().toString()));
                            JSONObject otherPolygonJson = JSONObject.parseObject(otherPolygonJsonString);
                            properties.add(otherPolygonJson);
                        }
                    }
                });
            }
            if(properties.size() > 0){
                routeResult.put("tourPointList",properties);
            }
            return routeResult;
        }else {
            return null;
        }
    }

    /**
     * 新增
     * @return
     */
    public MessageBean add(GnsTourRouteForm tourRouteForm){
        //保存路线
        Integer maxOrder = tourRouteDao.getMaxOrder(tourRouteForm.getTourRoute().getCampusCode());
        if (maxOrder !=null && tourRouteForm.getTourRoute().getOrderId() != null && tourRouteForm.getTourRoute().getOrderId() < maxOrder) {
            tourRouteDao.autoOrder(tourRouteForm.getTourRoute().getOrderId(),tourRouteForm.getTourRoute().getCampusCode());
        }else {
            if (maxOrder == null) {
                tourRouteForm.getTourRoute().setOrderId(1);
            }else {
                tourRouteForm.getTourRoute().setOrderId(maxOrder + 1);
            }
        }
        return MessageBean.ok(this.save(tourRouteForm));
    }

    public MessageBean update(GnsTourRouteForm tourRouteForm){

        Integer maxOrder = tourRouteDao.getMaxOrder(tourRouteForm.getTourRoute().getCampusCode());
        GnsTourRoute oldTourRoute = tourRouteDao.findById(tourRouteForm.getTourRoute().getRouteId()).get();
        if(oldTourRoute.getOrderId() > tourRouteForm.getTourRoute().getOrderId()){
            tourRouteDao.autoOrderForUpdateDesc(oldTourRoute.getOrderId(),tourRouteForm.getTourRoute().getOrderId(),tourRouteForm.getTourRoute().getCampusCode());
        }else {
            if (maxOrder > tourRouteForm.getTourRoute().getOrderId() && oldTourRoute.getOrderId() < tourRouteForm.getTourRoute().getOrderId()) {
                tourRouteDao.autoOrderForUpdateAsc(oldTourRoute.getOrderId(),tourRouteForm.getTourRoute().getOrderId(),tourRouteForm.getTourRoute().getCampusCode());
            }else if(oldTourRoute.getOrderId() == tourRouteForm.getTourRoute().getOrderId()){
                tourRouteForm.getTourRoute().setOrderId(oldTourRoute.getOrderId());
            }else{
                tourRouteDao.autoOrderForUpdateAsc(oldTourRoute.getOrderId(),maxOrder,tourRouteForm.getTourRoute().getCampusCode());
                tourRouteForm.getTourRoute().setOrderId(maxOrder);
            }
        }
        return MessageBean.ok(this.save(tourRouteForm));
    }


    public int delete(Integer routeId){
        GnsTourRoute route = this.get(routeId);
        tourRouteDao.deleteById(routeId);
        //重新排序
        if(route != null){
            tourRouteDao.autoOrderForDelete(route.getOrderId(),route.getCampusCode());
        }
        return 1;
    }

    public int bulkDelete(String ids){
        String[] idArray = ids.split(",");
        for (String s : idArray) {
            this.delete(Integer.parseInt(s));
        }
        return idArray.length;
    }

    private GnsTourRoute save(GnsTourRouteForm tourRouteForm){
        GnsTourRoute tourRoute = tourRouteDao.save(tourRouteForm.getTourRoute());
        List<PointVO> pointList = new ArrayList<>();
        List<GnsTourPoint> tourPointList = new ArrayList<>();
        if(tourRouteForm.getTourPointList().size() > 0){
            tourRouteForm.getTourPointList().forEach(v ->{
                GnsTourPoint point = new GnsTourPoint(tourRoute.getRouteId(),v.getMapCode(),v.getElementType());
                tourPointList.add(point);
                v.getPoint().setPriorityType("elevator");
                v.getPoint().setRoadType("foot");
                v.getPoint().setWayId(0);
                pointList.add(v.getPoint());
            });
            tourRouteForm.getTourRoute().setPointCount(tourRouteForm.getTourPointList().size());
        }
        //根据多点位从cmgis获取路径规划结果
        GnsStoreItem serverApiUrl = storeItemDao.findMapConfig("otherConfigurations", "cmgisApiUrl");
        GnsStoreItem mapToken = storeItemDao.findMapConfig("otherConfigurations", "mapToken");
        try {
            DisableSSLCertificateCheckUtil.disableChecks();
            HttpClient client = new DefaultHttpClient();
            //post
            HttpPost httpPost = new HttpPost(serverApiUrl.getItemValue() + "map/route/v3/multiPoint/split/find/" + tourRoute.getCampusCode());
            // 构建消息实体
            StringEntity entity = new StringEntity(JSON.toJSONString(pointList), Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            httpPost.addHeader("authorization", mapToken.getItemValue());
            HttpResponse response = client.execute(httpPost);
            Double distance = 0.00;

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity());
                JSONObject responseJson = JSONObject.parseObject(result);
                JSONArray dataArray = responseJson.getJSONArray("data");
                if(dataArray.size() > 0){
                    //设置路线
                    tourRoute.setNavigation_route(dataArray);
                    //获取里程
                    for(int i = 0; i < dataArray.size(); i++){
                        JSONArray navigationArray = dataArray.getJSONArray(i);
                        if(navigationArray.size() > 0){
                            for(int j = 0; j < navigationArray.size(); j++){
                                JSONObject property = navigationArray.getJSONObject(j);
                                distance += property.getDouble("distance");
                            }
                        }
                    }
                    tourRoute.setMileage(Math.round(distance * 100) / 100.0 + "m");
                }
            }

            tourPointDao.saveAll(tourPointList);
            return tourRouteDao.save(tourRoute);
        }catch (IOException e){
            logger.error(e.getMessage(),e);
            return null;
        }
    }


}
