//package com.lqkj.web.gnsc.modules.portal.service;
//
//import com.lqkj.web.dbe.modules.portal.dao.MapPtExtendsDefineDao;
//import com.lqkj.web.dbe.modules.portal.model.MapPtExtendsDefine;
//import com.lqkj.web.dbe.modules.portal.model.MapPtExtendsDefinePK;
//import com.lqkj.web.dbe.modules.portal.service.MapPtExtendsDefineService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.*;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@Service
//public class MapPtExtendsDefineServiceImpl implements MapPtExtendsDefineService {
//    @Autowired
//    MapPtExtendsDefineDao mapPtExtendsDefineDao;
//
//    @Override
//    public Mono<Page<MapPtExtendsDefine>> pageQuery(Integer typeCode, Integer page, Integer pageSize) {
//        return Mono.just(new MapPtExtendsDefine())
//                .map(v ->{
//                    v.setTypeCode(typeCode);
//                    return v;
//                })
//                .map(v ->{
//                    ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
//                            .withMatcher("typeCode", ExampleMatcher.GenericPropertyMatchers.exact())
//                            .withIgnorePaths("columnId");
//
//                    return Example.of(v, exampleMatcher);
//                })
//                .map(v ->{
//                    PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
//                    return mapPtExtendsDefineDao.findAll(v, pageRequest);
//                })
//                ;
//    }
//
//    @Override
//    public Mono<List<MapPtExtendsDefine>> queryList(Integer typeCode) {
//        return Mono.just(typeCode)
//                .map(v -> mapPtExtendsDefineDao.queryByTypeCode(v));
//    }
//
//    @Override
//    public Mono<Integer> bulkSave(List<MapPtExtendsDefine> mapPtExtendsDefineList) {
//        return Mono.just(mapPtExtendsDefineList)
//                .map(v ->{
//                    for(MapPtExtendsDefine mapPtExtendsDefine : v){
//                        if(mapPtExtendsDefine.getColumnId() == null){
//                            mapPtExtendsDefine.setColumnId(mapPtExtendsDefineDao.queryMaxColumnId(mapPtExtendsDefine.getTypeCode()));
//                        }
//                        mapPtExtendsDefineDao.save(mapPtExtendsDefine);
//                    }
//                    return v.size();
//                });
//    }
//
//    @Override
//    public Mono<Integer> delete(MapPtExtendsDefinePK mapPtExtendsDefinePK) {
//        return Mono.just(mapPtExtendsDefinePK)
//                .map(v -> {
//                    mapPtExtendsDefineDao.deleteById(mapPtExtendsDefinePK);
//                    return 1;
//                });
//    }
//
//    @Override
//    public Mono<Integer> bulkDelete(List<MapPtExtendsDefinePK> mapPtExtendsDefinePKList) {
//        return Mono.just(mapPtExtendsDefinePKList)
//                .map(v ->{
//                    for(MapPtExtendsDefinePK mapPtExtendsDefinePK : v){
//                        mapPtExtendsDefineDao.deleteById(mapPtExtendsDefinePK);
//                    }
//                    return v.size();
//                });
//    }
//
//    @Override
//    public Mono<List<MapPtExtendsDefine>> loadAll() {
//        return Mono.just(mapPtExtendsDefineDao.queryAll());
//    }
//}
