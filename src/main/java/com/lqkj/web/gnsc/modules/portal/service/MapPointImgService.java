package com.lqkj.web.gnsc.modules.portal.service;


import com.lqkj.web.gnsc.modules.portal.dao.MapPointImgDao;
import com.lqkj.web.gnsc.modules.portal.model.MapPointImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class MapPointImgService{
    @Autowired
    MapPointImgDao mapPointImgDao;

    public List<MapPointImg> queryListWithPointCode(Integer pointCode) {
        MapPointImg mapPointImg = new MapPointImg();
        mapPointImg.setPointCode(pointCode);
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("pointCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("imgId");

        Example<MapPointImg> example  =  Example.of(mapPointImg, exampleMatcher);

        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        return mapPointImgDao.findAll(example, sort);
    }

    public List<MapPointImg> saveImgList(List<MapPointImg> mapPointImgList) {
       return mapPointImgDao.saveAll(mapPointImgList);
    }

    public Integer deleteAllByPointCode(Integer pointCode) {
        return mapPointImgDao.deleteAllByPointCode(pointCode);
    }
}
