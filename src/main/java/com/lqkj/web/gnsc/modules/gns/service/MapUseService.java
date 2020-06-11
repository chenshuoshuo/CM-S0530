package com.lqkj.web.gnsc.modules.gns.service;

import com.alibaba.fastjson.JSONArray;
import com.lqkj.web.gnsc.modules.gns.dao.InteractionStatisticDao;
import com.lqkj.web.gnsc.modules.gns.dao.MapUseDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsInteractionStatistic;
import com.lqkj.web.gnsc.modules.gns.domain.GnsMapUse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author cs
 * @Date 2020/6/1 19:53
 * @Version 2.2.2.0
 **/
@Service
@Transactional
public class MapUseService {
    @Autowired
    private MapUseDao mapUseDao;

    /**
     * 保存地图使用记录
     * @param campusCode
     * @param userCode
     * @param recordType
     * @param elementName
     * @return
     */
    public GnsMapUse save(Integer campusCode,String userCode,Integer recordType, String elementName) {
        GnsMapUse mapUse = new GnsMapUse(UUID.randomUUID().toString(),campusCode,userCode,recordType,elementName);
        return mapUseDao.save(mapUse);
    }


}
