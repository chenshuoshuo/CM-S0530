package com.lqkj.web.gnsc.modules.portal.service;

import com.lqkj.web.gnsc.modules.portal.dao.MapBuildingExtendsDao;
import com.lqkj.web.gnsc.modules.portal.model.MapBuildingExtends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class MapBuildingExtendsService {
    @Autowired
    MapBuildingExtendsDao mapBuildingExtendsDao;

    public List<MapBuildingExtends> queryList(Integer buildingCode) {
        MapBuildingExtends mapBuildingExtends = new MapBuildingExtends();
        mapBuildingExtends.setBuildingCode(buildingCode);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("buildingCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("columnId");
        Example<MapBuildingExtends> example = Example.of(mapBuildingExtends, exampleMatcher);

        Sort sort = new Sort(Sort.Direction.ASC, "mapBtExtendsDefine.orderid");
        return mapBuildingExtendsDao.findAll(example, sort);
    }

    public Integer save(List<MapBuildingExtends> mapBuildingExtendsList) {
        if (mapBuildingExtendsList.size() > 0) {
            mapBuildingExtendsDao.deleteAllByBuildingCode(mapBuildingExtendsList.get(0).getBuildingCode());
        }
        mapBuildingExtendsDao.saveAll(mapBuildingExtendsList);
        return  mapBuildingExtendsList.size();
    }
}
