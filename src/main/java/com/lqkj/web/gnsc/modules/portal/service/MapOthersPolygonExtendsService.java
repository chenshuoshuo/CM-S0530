package com.lqkj.web.gnsc.modules.portal.service;

import com.lqkj.web.gnsc.modules.portal.dao.MapOthersPolygonExtendsDao;
import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygonExtends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MapOthersPolygonExtendsService {
    @Autowired
    MapOthersPolygonExtendsDao mapOthersPolygonExtendsDao;

    public List<MapOthersPolygonExtends> queryList(Integer polygonCode) {
        MapOthersPolygonExtends othersPolygonExtends = new MapOthersPolygonExtends();
        othersPolygonExtends.setPolygonCode(polygonCode);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("polygonCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("columnId");
        Example<MapOthersPolygonExtends> example = Example.of(othersPolygonExtends, exampleMatcher);

        Sort sort = new Sort(Sort.Direction.ASC, "mapOptExtendsDefine.orderid");
        return mapOthersPolygonExtendsDao.findAll(example, sort);
    }

    public List<MapOthersPolygonExtends> save(List<MapOthersPolygonExtends> mapOthersPolygonExtendsList) {
        if(mapOthersPolygonExtendsList.size() > 0){
            mapOthersPolygonExtendsDao.deleteAllByPolygonCode(mapOthersPolygonExtendsList.get(0).getPolygonCode());
        }

        return  mapOthersPolygonExtendsDao.saveAll(mapOthersPolygonExtendsList);
    }
}
