package com.lqkj.web.gnsc.modules.portal.service;

import com.lqkj.web.gnsc.modules.portal.dao.MapBuildingImgDao;
import com.lqkj.web.gnsc.modules.portal.model.MapBuildingImg;
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
public class MapBuildingImgService{
    @Autowired
    MapBuildingImgDao mapBuildingImgDao;

    public List<MapBuildingImg> queryListWithBuildingCode(Integer buildingCode) {
        MapBuildingImg mapBuildingImg = new MapBuildingImg();
        mapBuildingImg.setBuildingCode(buildingCode);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("buildingCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("imgId");

        Example<MapBuildingImg> example  =  Example.of(mapBuildingImg, exampleMatcher);
        Sort sort = new Sort(Sort.Direction.ASC, "orderId");

        return mapBuildingImgDao.findAll(example, sort);
    }

    public Integer deleteAllByBuildingCode(Integer buildingCode) {
        return mapBuildingImgDao.deleteAllByBuildingCode(buildingCode);
    }

    public Integer saveImgList(List<MapBuildingImg> mapBuildingImgList) {
        mapBuildingImgDao.saveAll(mapBuildingImgList);
        return mapBuildingImgList.size();
    }
}
