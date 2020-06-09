package com.lqkj.web.gnsc.modules.portal.service;

import com.lqkj.web.gnsc.modules.portal.dao.MapOptExtendsDefineDao;
import com.lqkj.web.gnsc.modules.portal.model.MapOptExtendsDefine;
import com.lqkj.web.gnsc.modules.portal.model.MapOptExtendsDefinePK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class MapOptExtendsDefineService {
    @Autowired
    MapOptExtendsDefineDao mapOptExtendsDefineDao;

    public Page<MapOptExtendsDefine> pageQuery(Integer typeCode, Integer page, Integer pageSize) {
        MapOptExtendsDefine extendsDefine = new MapOptExtendsDefine();
        extendsDefine.setTypeCode(typeCode);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("typeCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("columnId");

        Example<MapOptExtendsDefine> example = Example.of(extendsDefine, exampleMatcher);
        PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
        return mapOptExtendsDefineDao.findAll(example, pageRequest);
    }


    public List<MapOptExtendsDefine> queryList(Integer typeCode) {
        return mapOptExtendsDefineDao.queryByTypeCode(typeCode);
    }


    public Integer bulkSave(List<MapOptExtendsDefine> mapOptExtendsDefineList) {
        for(MapOptExtendsDefine mapOptExtendsDefine : mapOptExtendsDefineList){
            if(mapOptExtendsDefine.getColumnId() == null){
                mapOptExtendsDefine.setColumnId(mapOptExtendsDefineDao.queryMaxColumnId(mapOptExtendsDefine.getTypeCode()));
            }
            mapOptExtendsDefineDao.save(mapOptExtendsDefine);
        }
        return mapOptExtendsDefineList.size();
    }


    public Integer delete(MapOptExtendsDefinePK mapOptExtendsDefinePK) {
        mapOptExtendsDefineDao.deleteById(mapOptExtendsDefinePK);
        return 1;
    }

    public Integer bulkDelete(List<MapOptExtendsDefinePK> mapOptExtendsDefinePKList) {
        for(MapOptExtendsDefinePK mapOptExtendsDefinePK : mapOptExtendsDefinePKList){
            mapOptExtendsDefineDao.deleteById(mapOptExtendsDefinePK);
        }
        return mapOptExtendsDefinePKList.size();
    }


    public List<MapOptExtendsDefine> loadAll() {
        return mapOptExtendsDefineDao.queryAll();
    }
}
