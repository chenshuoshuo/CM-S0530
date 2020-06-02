//package com.lqkj.web.gnsc.modules.portal.service;
//
//import com.lqkj.web.dbe.modules.portal.dao.MapRtExtendsDefineDao;
//import com.lqkj.web.dbe.modules.portal.model.MapRtExtendsDefine;
//import com.lqkj.web.dbe.modules.portal.model.MapRtExtendsDefinePK;
//import com.lqkj.web.dbe.modules.portal.service.MapRtExtendsDefineService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.*;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@Service
//public class MapRtExtendsDefineServiceImpl implements MapRtExtendsDefineService {
//    @Autowired
//    MapRtExtendsDefineDao mapRtExtendsDefineDao;
//
//    @Override
//    public Mono<Page<MapRtExtendsDefine>> pageQuery(Integer typeCode, Integer page, Integer pageSize) {
//        return Mono.just(new MapRtExtendsDefine())
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
//                    return mapRtExtendsDefineDao.findAll(v, pageRequest);
//                })
//                ;
//    }
//
//    @Override
//    public Mono<List<MapRtExtendsDefine>> queryList(Integer typeCode) {
//        return Mono.just(typeCode)
//                .map(v -> mapRtExtendsDefineDao.queryByTypeCode(v));
//    }
//
//    @Override
//    public Mono<Integer> bulkSave(List<MapRtExtendsDefine> mapRtExtendsDefineList) {
//        return Mono.just(mapRtExtendsDefineList)
//                .map(v ->{
//                    for(MapRtExtendsDefine mapRtExtendsDefine : v){
//                        if(mapRtExtendsDefine.getColumnId() == null){
//                            mapRtExtendsDefine.setColumnId(mapRtExtendsDefineDao.queryMaxColumnId(mapRtExtendsDefine.getTypeCode()));
//                        }
//                        mapRtExtendsDefineDao.save(mapRtExtendsDefine);
//                    }
//                    return v.size();
//                });
//    }
//
//    @Override
//    public Mono<Integer> delete(MapRtExtendsDefinePK mapRtExtendsDefinePK) {
//        return Mono.just(mapRtExtendsDefinePK)
//                .map(v -> {
//                    mapRtExtendsDefineDao.deleteById(mapRtExtendsDefinePK);
//                    return 1;
//                });
//    }
//
//    @Override
//    public Mono<Integer> bulkDelete(List<MapRtExtendsDefinePK> mapRtExtendsDefinePKList) {
//        return Mono.just(mapRtExtendsDefinePKList)
//                .map(v ->{
//                    for(MapRtExtendsDefinePK mapRtExtendsDefinePK : v){
//                        mapRtExtendsDefineDao.deleteById(mapRtExtendsDefinePK);
//                    }
//                    return v.size();
//                });
//    }
//
//    @Override
//    public Mono<List<MapRtExtendsDefine>> loadAll() {
//        return Mono.just(mapRtExtendsDefineDao.queryAll());
//    }
//}
