package com.lqkj.web.gnsc.modules.portal.service;


import com.lqkj.web.gnsc.modules.portal.dao.MapRtExtendsDefineDao;
import com.lqkj.web.gnsc.modules.portal.model.MapRtExtendsDefine;
import com.lqkj.web.gnsc.modules.portal.model.MapRtExtendsDefinePK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MapRtExtendsDefineService{
    @Autowired
    MapRtExtendsDefineDao mapRtExtendsDefineDao;


    public Page<MapRtExtendsDefine> pageQuery(Integer typeCode, Integer page, Integer pageSize) {
        MapRtExtendsDefine rtExtendsDefine = new MapRtExtendsDefine();
        rtExtendsDefine.setTypeCode(typeCode);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("typeCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("columnId");

        Example<MapRtExtendsDefine> example = Example.of(rtExtendsDefine, exampleMatcher);

        PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
        return mapRtExtendsDefineDao.findAll(example, pageRequest);
    }

    public List<MapRtExtendsDefine> queryList(Integer typeCode) {
        return mapRtExtendsDefineDao.queryByTypeCode(typeCode);
    }


    public Integer bulkSave(List<MapRtExtendsDefine> mapRtExtendsDefineList) {
        for(MapRtExtendsDefine mapRtExtendsDefine : mapRtExtendsDefineList){
            if(mapRtExtendsDefine.getColumnId() == null){
                mapRtExtendsDefine.setColumnId(mapRtExtendsDefineDao.queryMaxColumnId(mapRtExtendsDefine.getTypeCode()));
            }
            mapRtExtendsDefineDao.save(mapRtExtendsDefine);
        }
        return mapRtExtendsDefineList.size();
    }


    public Integer delete(MapRtExtendsDefinePK mapRtExtendsDefinePK) {
        mapRtExtendsDefineDao.deleteById(mapRtExtendsDefinePK);
        return 1;
    }


    public Integer bulkDelete(List<MapRtExtendsDefinePK> mapRtExtendsDefinePKList) {
        for(MapRtExtendsDefinePK mapRtExtendsDefinePK : mapRtExtendsDefinePKList){
            mapRtExtendsDefineDao.deleteById(mapRtExtendsDefinePK);
        }
        return mapRtExtendsDefinePKList.size();
    }


    public List<MapRtExtendsDefine> loadAll() {
        return mapRtExtendsDefineDao.queryAll();
    }
}
