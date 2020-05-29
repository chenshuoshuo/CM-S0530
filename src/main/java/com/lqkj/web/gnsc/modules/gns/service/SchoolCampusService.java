package com.lqkj.web.gnsc.modules.gns.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.dao.GnsStoreItemDao;
import com.lqkj.web.gnsc.modules.gns.dao.SchoolCampusDao;
import com.lqkj.web.gnsc.modules.gns.dao.SchoolInfoDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsCampusInfo;
import com.lqkj.web.gnsc.modules.gns.domain.GnsSchool;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStoreItem;
import com.lqkj.web.gnsc.utils.DisableSSLCertificateCheckUtil;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchoolCampusService {
    @Autowired
    private SchoolCampusDao schoolCampusDao;
    @Autowired
    private SchoolInfoDao schoolInfoDao;

    @Autowired
    private GnsStoreItemDao storeItemDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 刷新校区
     */
    public MessageListBean refreshCampus(){

        try {
            GnsStoreItem serverApiUrl = storeItemDao.findMapConfig("otherConfigurations", "cmgisApiUrl");
            GnsStoreItem mapToken = storeItemDao.findMapConfig("otherConfigurations", "mapToken");
            DisableSSLCertificateCheckUtil.disableChecks();
            HttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(serverApiUrl.getItemValue() + "/map/v2/group?page=0&pageSize=50");
            request.addHeader("authorization", mapToken.getItemValue());
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity());
                JSONObject responseJson = JSONObject.parseObject(result);
                JSONObject dataObject = responseJson.getJSONObject("data");
                JSONArray jsonArray = dataObject.getJSONArray("content");

                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject contentJson = jsonArray.getJSONObject(i);
                    GnsCampusInfo schoolCampus = schoolCampusDao.findByCampusCode(contentJson.getInteger("groupId"));
                    if (schoolCampus == null) {
                        schoolCampus = new GnsCampusInfo();
                        schoolCampus.setCampusCode(contentJson.getInteger("groupId"));
                        schoolCampus.setCampusName(contentJson.getString("name"));
                        JSONArray jsonZoneArray = contentJson.getJSONArray("zones");
                        if (jsonZoneArray.size() > 0) {
                            for (int j = 0; j < jsonZoneArray.size(); j++) {
                                JSONObject jsonZoneObject = jsonZoneArray.getJSONObject(j);
                                JSONObject jsonObject = jsonZoneObject.getJSONObject("mapZoneByZoneId");

                                if (jsonObject.get("is2D").equals(true)) {
                                    schoolCampus.setVectorZoomCode(jsonObject.getInteger("id"));
                                }
                                if (jsonObject.get("is2D").equals(false)) {
                                    schoolCampus.setRasterZoomCode(jsonObject.getInteger("id"));
                                }
                            }
                        }
                        schoolCampusDao.save(schoolCampus);
                    } else {
                        return MessageListBean.error("校区已经存在，数据没有改变");
                    }
                }
            }
            List<GnsCampusInfo> list = schoolCampusDao.findAll();
            if (list.size() > 0) {
                return MessageListBean.ok(list);
            } else {
                return MessageListBean.error("没有查询到数据");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return MessageListBean.error("接口错误");
        }
    }


    /**
     * 获取指定学校校区
     * @param schoolId
     * @return
     */
    public GnsSchool loadWithSchool(Integer schoolId){
        GnsSchool school = schoolInfoDao.findBySchoolId(schoolId);
        List<GnsCampusInfo> campusInfo = schoolCampusDao.findBySchoolId(schoolId);
        school.setCampusInfoList(campusInfo);
        return school;
    }

    /**
     * 解绑校区
     */
    public MessageBean unBlindCampus(Integer campusCode,Integer schoolId){
        try {
            GnsCampusInfo schoolCampus = schoolCampusDao.findByCampusCodeAndSchoolId(campusCode,schoolId);
            if(schoolCampus != null) {
                schoolCampus.setSchoolId(null);
                schoolCampusDao.save(schoolCampus);
                return MessageBean.ok();
            }else{
                return MessageBean.error("没有相匹配的校区信息");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return MessageBean.error("接口错误");
        }
    }

    /**
     * 绑定校区
     * @param schoolId
     * @return
     */
    public MessageBean blindCampus(Integer schoolId,String ids){
        try {
            String[] idArray = ids.split(",");

            for(String str:idArray){
                GnsCampusInfo schoolCampus = schoolCampusDao.findByCampusCode(Integer.parseInt(str));
                schoolCampus.setSchoolId(schoolId);
                schoolCampusDao.save(schoolCampus);
            }
            return MessageBean.ok();

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return MessageBean.error("接口错误");
        }
    }

    /**
     * 获取可绑定校区
     */
    public List<GnsCampusInfo> canBlindCampus(String campusName){

        return schoolCampusDao.noBindingSchoolCampus(campusName);
    }


    /**
     * 获取其他已经绑定校区
     */
    public List<GnsSchool> hasBlindCampus(String campusName,String schoolName){
        List<GnsSchool> schoolList = schoolInfoDao.loadWithSchoolName(schoolName);
        List<GnsSchool> resultList = new ArrayList<>();
        if(schoolList.size() > 0){
            for (GnsSchool school : schoolList) {
                List<GnsCampusInfo>  campusInfo = schoolCampusDao.hasBindingSchoolCampus(campusName,school.getSchoolId());
                if(campusInfo.size() > 0){
                    school.setCampusInfoList(campusInfo);
                    resultList.add(school);
                }
            }
        }
        return resultList;
    }

}
