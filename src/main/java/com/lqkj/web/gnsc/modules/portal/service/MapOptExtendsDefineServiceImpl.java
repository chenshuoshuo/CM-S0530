//package com.lqkj.web.gnsc.modules.portal.service;
//
//import com.lqkj.web.dbe.modules.portal.dao.MapOptExtendsDefineDao;
//import com.lqkj.web.dbe.modules.portal.model.MapOptExtendsDefine;
//import com.lqkj.web.dbe.modules.portal.model.MapOptExtendsDefinePK;
//import com.lqkj.web.dbe.modules.portal.service.MapOptExtendsDefineService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.*;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@Service
//public class MapOptExtendsDefineServiceImpl implements MapOptExtendsDefineService {
//    @Autowired
//    MapOptExtendsDefineDao mapOptExtendsDefineDao;
//
//    @Override
//    public Mono<Page<MapOptExtendsDefine>> pageQuery(Integer typeCode, Integer page, Integer pageSize) {
//        return Mono.just(new MapOptExtendsDefine())
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
//                    return mapOptExtendsDefineDao.findAll(v, pageRequest);
//                })
//                ;
//    }
//
//    @Override
//    public Mono<List<MapOptExtendsDefine>> queryList(Integer typeCode) {
//        return Mono.just(typeCode)
//                .map(v -> mapOptExtendsDefineDao.queryByTypeCode(v));
//    }
//
//    @Override
//    public Mono<Integer> bulkSave(List<MapOptExtendsDefine> mapOptExtendsDefineList) {
//        return Mono.just(mapOptExtendsDefineList)
//                .map(v ->{
//                    for(MapOptExtendsDefine mapOptExtendsDefine : v){
//                        if(mapOptExtendsDefine.getColumnId() == null){
//                            mapOptExtendsDefine.setColumnId(mapOptExtendsDefineDao.queryMaxColumnId(mapOptExtendsDefine.getTypeCode()));
//                        }
//                        mapOptExtendsDefineDao.save(mapOptExtendsDefine);
//                    }
//                    return v.size();
//                });
//    }
//
//    @Override
//    public Mono<Integer> delete(MapOptExtendsDefinePK mapOptExtendsDefinePK) {
//        return Mono.just(mapOptExtendsDefinePK)
//                .map(v -> {
//                    mapOptExtendsDefineDao.deleteById(mapOptExtendsDefinePK);
//                    return 1;
//                });
//    }
//
//    @Override
//    public Mono<Integer> bulkDelete(List<MapOptExtendsDefinePK> mapOptExtendsDefinePKList) {
//        return Mono.just(mapOptExtendsDefinePKList)
//                .map(v ->{
//                    for(MapOptExtendsDefinePK mapOptExtendsDefinePK : v){
//                        mapOptExtendsDefineDao.deleteById(mapOptExtendsDefinePK);
//                    }
//                    return v.size();
//                });
//    }
//
//    @Override
//    public Mono<List<MapOptExtendsDefine>> loadAll() {
//        return Mono.just(mapOptExtendsDefineDao.queryAll());
//    }
//}
