package com.lqkj.web.gnsc.modules.portal.service;

import com.lqkj.web.gnsc.modules.portal.dao.MapPointExtendsDao;
import com.lqkj.web.gnsc.modules.portal.model.MapPointExtends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapPointExtendsService {
    @Autowired
    MapPointExtendsDao mapPointExtendsDao;

    public List<MapPointExtends> queryList(Integer pointCode) {
        MapPointExtends mapPointExtends = new MapPointExtends();
        mapPointExtends.setPointCode(pointCode);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("pointCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("columnId");
        Example<MapPointExtends> example = Example.of(mapPointExtends, exampleMatcher);

        Sort sort = new Sort(Sort.Direction.ASC, "mapPtExtendsDefine.orderid");
        return mapPointExtendsDao.findAll(example, sort);

    }


    public List<MapPointExtends> save(List<MapPointExtends> mapPointExtendsList) {
        if(mapPointExtendsList.size() > 0){
            mapPointExtendsDao.deleteAllByPointCode(mapPointExtendsList.get(0).getPointCode());
        }
        return  mapPointExtendsDao.saveAll(mapPointExtendsList);
    }
}
