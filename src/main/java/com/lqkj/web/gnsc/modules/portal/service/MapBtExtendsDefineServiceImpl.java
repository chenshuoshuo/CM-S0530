//package com.lqkj.web.gnsc.modules.portal.service;
//
//import com.lqkj.web.dbe.modules.portal.dao.MapBtExtendsDefineDao;
//import com.lqkj.web.dbe.modules.portal.model.MapBtExtendsDefine;
//import com.lqkj.web.dbe.modules.portal.model.MapBtExtendsDefinePK;
//import com.lqkj.web.dbe.modules.portal.service.MapBtExtendsDefineService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.*;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@Service
//public class MapBtExtendsDefineServiceImpl implements MapBtExtendsDefineService {
//    @Autowired
//    MapBtExtendsDefineDao mapBtExtendsDefineDao;
//
//    @Override
//    public Mono<Page<MapBtExtendsDefine>> pageQuery(Integer typeCode, Integer page, Integer pageSize) {
//        return Mono.just(new MapBtExtendsDefine())
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
//                    return mapBtExtendsDefineDao.findAll(v, pageRequest);
//                })
//                ;
//    }
//
//    @Override
//    public Mono<List<MapBtExtendsDefine>> queryList(Integer typeCode) {
//        return Mono.just(typeCode)
//                .map(v -> mapBtExtendsDefineDao.queryByTypeCode(v));
//    }
//
//    @Override
//    public Mono<Integer> bulkSave(List<MapBtExtendsDefine> mapBtExtendsDefineList) {
//        return Mono.just(mapBtExtendsDefineList)
//                .map(v ->{
//                    for(MapBtExtendsDefine mapBtExtendsDefine : v){
//                        if(mapBtExtendsDefine.getColumnId() == null){
//                            mapBtExtendsDefine.setColumnId(mapBtExtendsDefineDao.queryMaxColumnId(mapBtExtendsDefine.getTypeCode()));
//                        }
//                        mapBtExtendsDefineDao.save(mapBtExtendsDefine);
//                    }
//                    return v.size();
//                });
//    }
//
//    @Override
//    public Mono<Integer> delete(MapBtExtendsDefinePK mapBtExtendsDefinePK) {
//        return Mono.just(mapBtExtendsDefinePK)
//                .map(v -> {
//                    mapBtExtendsDefineDao.deleteById(mapBtExtendsDefinePK);
//                    return 1;
//                });
//    }
//
//    @Override
//    public Mono<Integer> bulkDelete(List<MapBtExtendsDefinePK> mapBtExtendsDefinePKList) {
//        return Mono.just(mapBtExtendsDefinePKList)
//                .map(v ->{
//                    for(MapBtExtendsDefinePK mapBtExtendsDefinePK : v){
//                        mapBtExtendsDefineDao.deleteById(mapBtExtendsDefinePK);
//                    }
//                    return v.size();
//                });
//    }
//
//    @Override
//    public Mono<List<MapBtExtendsDefine>> loadAll() {
//        return Mono.just(mapBtExtendsDefineDao.queryAll());
//    }
//}
