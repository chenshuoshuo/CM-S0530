package com.lqkj.web.gnsc.modules.portal.service;


import com.lqkj.web.gnsc.modules.portal.dao.MapRoomExtendsDao;
import com.lqkj.web.gnsc.modules.portal.model.MapRoomExtends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapRoomExtendsService{
    @Autowired
    MapRoomExtendsDao mapRoomExtendsDao;

    public List<MapRoomExtends> queryList(Integer roomCode) {

        MapRoomExtends roomExtends = new MapRoomExtends();
        roomExtends.setRoomCode(roomCode);
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("roomCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("columnId");
        Example<MapRoomExtends> example =  Example.of(roomExtends, exampleMatcher);

        Sort sort = new Sort(Sort.Direction.ASC, "mapRtExtendsDefine.orderid");
        return mapRoomExtendsDao.findAll(example, sort);

    }

    public List<MapRoomExtends> save(List<MapRoomExtends> mapRoomExtendsList) {
        if(mapRoomExtendsList.size() > 0){
            mapRoomExtendsDao.deleteAllByRoomCode(mapRoomExtendsList.get(0).getRoomCode());
        }
        return mapRoomExtendsDao.saveAll(mapRoomExtendsList);
    }
}
