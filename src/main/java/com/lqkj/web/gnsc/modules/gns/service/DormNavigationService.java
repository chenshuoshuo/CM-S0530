package com.lqkj.web.gnsc.modules.gns.service;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.JsonArray;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.dao.ClubDao;
import com.lqkj.web.gnsc.modules.gns.dao.ClubVODao;
import com.lqkj.web.gnsc.modules.gns.dao.DormNavigationDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsClub;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsClubVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author cs
 */
@Service
public class DormNavigationService {

    @Autowired
    private DormNavigationDao dormNavigationDao;

    /**
     *
     * 获取宿舍楼列表
     */
    public JSONArray queryDormList(Integer vectorZoomCode){
        String result = dormNavigationDao.queryList(vectorZoomCode);
        if(StringUtils.isNotBlank(result)){
            return JSONArray.parseArray(result);
        }
        return new JSONArray();
    }

    /**
     *
     * h5根据学校获取宿舍楼列表
     */
    public JSONArray queryDormListWithSchoolId(Integer schoolId){
        String result = dormNavigationDao.queryListWithSchoolId(schoolId);
        if(StringUtils.isNotBlank(result)){
            return JSONArray.parseArray(result);
        }
        return new JSONArray();
    }

}
