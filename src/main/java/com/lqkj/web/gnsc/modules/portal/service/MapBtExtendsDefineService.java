package com.lqkj.web.gnsc.modules.portal.service;


import com.lqkj.web.gnsc.modules.portal.dao.MapBtExtendsDefineDao;
import com.lqkj.web.gnsc.modules.portal.model.MapBtExtendsDefine;
import com.lqkj.web.gnsc.modules.portal.model.MapBtExtendsDefinePK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MapBtExtendsDefineService {
    @Autowired
    MapBtExtendsDefineDao mapBtExtendsDefineDao;


    public Page<MapBtExtendsDefine> pageQuery(Integer typeCode, Integer page, Integer pageSize) {
        MapBtExtendsDefine mapBtExtendsDefine = new MapBtExtendsDefine();
        mapBtExtendsDefine.setTypeCode(typeCode);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("typeCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("columnId");

        Example<MapBtExtendsDefine> example =  Example.of(mapBtExtendsDefine, exampleMatcher);
        PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
        return mapBtExtendsDefineDao.findAll(example, pageRequest);
    }


    public List<MapBtExtendsDefine> queryList(Integer typeCode) {
        return mapBtExtendsDefineDao.queryByTypeCode(typeCode);
    }


    public Integer bulkSave(List<MapBtExtendsDefine> mapBtExtendsDefineList) {
        if(mapBtExtendsDefineList.size() > 0){
            for(MapBtExtendsDefine mapBtExtendsDefine : mapBtExtendsDefineList){
                if(mapBtExtendsDefine.getColumnId() == null){
                    mapBtExtendsDefine.setColumnId(mapBtExtendsDefineDao.queryMaxColumnId(mapBtExtendsDefine.getTypeCode()));
                }
                mapBtExtendsDefineDao.save(mapBtExtendsDefine);
            }
            return mapBtExtendsDefineList.size();
        }
        return null;
    }


    public Integer delete(MapBtExtendsDefinePK mapBtExtendsDefinePK) {
        mapBtExtendsDefineDao.deleteById(mapBtExtendsDefinePK);
        return 1;
    }

    public Integer bulkDelete(List<MapBtExtendsDefinePK> mapBtExtendsDefinePKList) {
        if(mapBtExtendsDefinePKList.size() > 0){
            for(MapBtExtendsDefinePK mapBtExtendsDefinePK : mapBtExtendsDefinePKList){
                mapBtExtendsDefineDao.deleteById(mapBtExtendsDefinePK);
            }
            return mapBtExtendsDefinePKList.size();
        }
        return null;
    }

    public List<MapBtExtendsDefine> loadAll() {
        return mapBtExtendsDefineDao.queryAll();
    }
}
