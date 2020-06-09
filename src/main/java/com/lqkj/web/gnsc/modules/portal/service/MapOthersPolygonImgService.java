package com.lqkj.web.gnsc.modules.portal.service;

import com.lqkj.web.gnsc.modules.portal.dao.MapOthersPolygonImgDao;
import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygonImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MapOthersPolygonImgService{
    @Autowired
    MapOthersPolygonImgDao mapOthersPolygonImgDao;


    public List<MapOthersPolygonImg> queryListWithPolygonCode(Integer polygonCode) {

        MapOthersPolygonImg mapOthersPolygonImg = new MapOthersPolygonImg();
        mapOthersPolygonImg.setPolygonCode(polygonCode);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("polygonCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("imgId");

        Example<MapOthersPolygonImg> example = Example.of(mapOthersPolygonImg, exampleMatcher);

        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        return mapOthersPolygonImgDao.findAll(example, sort);
    }


    public List<MapOthersPolygonImg> saveImgList(List<MapOthersPolygonImg> mapOthersPolygonImgList) {
        return  mapOthersPolygonImgDao.saveAll(mapOthersPolygonImgList);
    }

    public Integer deleteAllByPolygonCode(Integer polygonCode) {
        return mapOthersPolygonImgDao.deleteAllByPolygonCode(polygonCode);
    }
}
