package com.lqkj.web.gnsc.modules.portal.service;


import com.lqkj.web.gnsc.modules.portal.dao.MapOthersPolygonDao;
import com.lqkj.web.gnsc.modules.portal.dao.excel.MapOthersPolygonExcelModelDao;
import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class MapOthersPolygonService {
    @Autowired
    MapOthersPolygonDao mapOthersPolygonDao;
    @Autowired
    MapOthersPolygonExcelModelDao mapOthersPolygonExcelModelDao;

    public Page<MapOthersPolygon> pageQuery(Integer campusCode, Integer typeCode, String polygonName, Integer page, Integer pageSize) {
        PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
        return mapOthersPolygonDao.findAll(loadExample(campusCode, typeCode, polygonName), pageRequest);
    }


    public List<MapOthersPolygon> listQuery(Integer campusCode, Integer typeCode, String polygonName) {
        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        return mapOthersPolygonDao.findAll(loadExample(campusCode, typeCode, polygonName), sort);
    }


    private Example<MapOthersPolygon> loadExample(Integer campusCode, Integer typeCode, String polygonName){
        MapOthersPolygon mapOthersPolygon = new MapOthersPolygon();
        mapOthersPolygon.setCampusCode(campusCode);
        mapOthersPolygon.setTypeCode(typeCode);
        mapOthersPolygon.setPolygonName(polygonName);
        mapOthersPolygon.setDelete(false);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("campusCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("typeCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("polygonName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("delete", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("polygonCode");

        return Example.of(mapOthersPolygon, exampleMatcher);
    }
}
