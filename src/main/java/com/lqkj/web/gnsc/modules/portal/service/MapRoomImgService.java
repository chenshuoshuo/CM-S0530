package com.lqkj.web.gnsc.modules.portal.service;

import com.lqkj.web.gnsc.modules.portal.dao.MapRoomImgDao;
import com.lqkj.web.gnsc.modules.portal.model.MapRoom;
import com.lqkj.web.gnsc.modules.portal.model.MapRoomImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class MapRoomImgService {
    @Autowired
    MapRoomImgDao mapRoomImgDao;


    public List<MapRoomImg> queryListWithRoomCode(Integer roomCode) {
        MapRoomImg roomImg = new MapRoomImg();
        roomImg.setRoomCode(roomCode);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("roomCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("imgId");

        Example<MapRoomImg>  example = Example.of(roomImg, exampleMatcher);

        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        return mapRoomImgDao.findAll(example, sort);
    }



    public List<MapRoomImg> saveImgList(List<MapRoomImg> mapRoomImgList) {
        return  mapRoomImgDao.saveAll(mapRoomImgList);
    }


    public Integer deleteAllByRoomCode(Integer roomCode) {
        return mapRoomImgDao.deleteAllByRoomCode(roomCode);
    }
}
