package com.lqkj.web.gnsc.modules.portal.service;


import com.lqkj.web.gnsc.modules.portal.dao.MapRoomDao;
import com.lqkj.web.gnsc.modules.portal.dao.excel.MapRoomExcelModelDao;
import com.lqkj.web.gnsc.modules.portal.model.MapRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapRoomService {
    @Autowired
    MapRoomDao mapRoomDao;
    @Autowired
    MapRoomExcelModelDao mapRoomExcelModelDao;


    public Page<MapRoom> pageQuery(Long buildingMapCode, Integer campusCode, Integer typeCode, String roomName, Integer page, Integer pageSize) {

        PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
        return mapRoomDao.findAll(loadExample(buildingMapCode, campusCode, typeCode, roomName), pageRequest);
    }


    public List<MapRoom> listQuery(Long buildingMapCode, Integer campusCode, Integer typeCode, String RoomName) {
        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        return mapRoomDao.findAll(loadExample(buildingMapCode, campusCode, typeCode, RoomName), sort);
    }


    private Example<MapRoom> loadExample(Long buildingMapCode, Integer campusCode, Integer typeCode, String roomName){
        MapRoom mapRoom = new MapRoom();
        mapRoom.setCampusCode(campusCode);
        mapRoom.setTypeCode(typeCode);
        mapRoom.setRoomName(roomName);
        mapRoom.setBuildingMapCode(buildingMapCode);
        mapRoom.setDelete(false);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("campusCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("typeCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("roomName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("buildingMapCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("delete", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("roomCode");

        return Example.of(mapRoom, exampleMatcher);
    }
}
