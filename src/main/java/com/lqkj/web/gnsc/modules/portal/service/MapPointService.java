package com.lqkj.web.gnsc.modules.portal.service;

import com.lqkj.web.gnsc.modules.portal.dao.MapPointDao;
import com.lqkj.web.gnsc.modules.portal.dao.excel.MapPointExcelModelDao;
import com.lqkj.web.gnsc.modules.portal.model.MapPoint;
import com.lqkj.web.gnsc.modules.portal.model.MapPointType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapPointService {
    @Autowired
    MapPointDao mapPointDao;
    @Autowired
    MapPointExcelModelDao mapPointExcelModelDao;

    public Page<MapPoint> pageQuery(Integer campusCode, Integer typeCode, Integer parentTypeCode, String pointName, Integer page, Integer pageSize) {

        PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
        return mapPointDao.findAll(loadExample(campusCode, typeCode, parentTypeCode, pointName), pageRequest);
    }

    public List<MapPoint> queryList(Integer campusCode, Integer typeCode, Integer parentTypeCode, String pointName) {

        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        return mapPointDao.findAll(loadExample(campusCode, typeCode, parentTypeCode, pointName), sort);
    }

    private Example<MapPoint> loadExample(Integer campusCode, Integer typeCode, Integer parentTypeCode, String pointName){
        MapPointType mapPointType = new MapPointType();
        mapPointType.setTypeCode(typeCode);
        mapPointType.setParentCode(parentTypeCode);

        MapPoint mapPoint = new MapPoint();
        mapPoint.setCampusCode(campusCode);
        mapPoint.setMapPointType(mapPointType);
        mapPoint.setPointName(pointName);
        mapPoint.setDelete(false);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("mapPointType.typeCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("mapPointType.parentCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("campusCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("delete", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("pointName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("pointCode");

        return Example.of(mapPoint, exampleMatcher);
    }

}
