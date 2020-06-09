package com.lqkj.web.gnsc.modules.portal.service;


import com.lqkj.web.gnsc.modules.portal.dao.MapPtExtendsDefineDao;
import com.lqkj.web.gnsc.modules.portal.model.MapPtExtendsDefine;
import com.lqkj.web.gnsc.modules.portal.model.MapPtExtendsDefinePK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class MapPtExtendsDefineService {
    @Autowired
    MapPtExtendsDefineDao mapPtExtendsDefineDao;


    public Page<MapPtExtendsDefine> pageQuery(Integer typeCode, Integer page, Integer pageSize) {
        MapPtExtendsDefine extendsDefine = new MapPtExtendsDefine();
        extendsDefine.setTypeCode(typeCode);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("typeCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("columnId");

        Example<MapPtExtendsDefine> example =  Example.of(extendsDefine, exampleMatcher);

        PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
        return mapPtExtendsDefineDao.findAll(example, pageRequest);
    }


    public List<MapPtExtendsDefine> queryList(Integer typeCode) {
        return mapPtExtendsDefineDao.queryByTypeCode(typeCode);
    }


    public Integer bulkSave(List<MapPtExtendsDefine> mapPtExtendsDefineList) {
        for(MapPtExtendsDefine mapPtExtendsDefine : mapPtExtendsDefineList){
            if(mapPtExtendsDefine.getColumnId() == null){
                mapPtExtendsDefine.setColumnId(mapPtExtendsDefineDao.queryMaxColumnId(mapPtExtendsDefine.getTypeCode()));
            }
            mapPtExtendsDefineDao.save(mapPtExtendsDefine);
        }
        return mapPtExtendsDefineList.size();
    }


    public Integer delete(MapPtExtendsDefinePK mapPtExtendsDefinePK) {
        mapPtExtendsDefineDao.deleteById(mapPtExtendsDefinePK);
        return 1;
    }


    public Integer bulkDelete(List<MapPtExtendsDefinePK> mapPtExtendsDefinePKList) {
        for(MapPtExtendsDefinePK mapPtExtendsDefinePK : mapPtExtendsDefinePKList){
            mapPtExtendsDefineDao.deleteById(mapPtExtendsDefinePK);
        }
        return mapPtExtendsDefinePKList.size();
    }


    public List<MapPtExtendsDefine> loadAll() {
        return mapPtExtendsDefineDao.queryAll();
    }
}
