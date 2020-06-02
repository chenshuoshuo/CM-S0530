//package com.lqkj.web.gnsc.modules.portal.service;
//
//import com.lqkj.web.dbe.modules.portal.dao.MapOthersPolygonTypeDao;
//import com.lqkj.web.dbe.modules.portal.model.MapOthersPolygonType;
//import com.lqkj.web.dbe.modules.portal.service.MapOthersPolygonTypeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.*;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@Service
//public class MapOthersPolygonTypeServiceImpl implements MapOthersPolygonTypeService {
//    @Autowired
//    MapOthersPolygonTypeDao mapOthersPolygonTypeDao;
//
//    @Override
//    public Mono<Page<MapOthersPolygonType>> pageQuery(String typeName, Integer page, Integer pageSize) {
//        return Mono.just(loadExample(typeName))
//                .map(v ->{
//                    PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
//                    return mapOthersPolygonTypeDao.findAll(v, pageRequest);
//                })
//                ;
//    }
//
//    @Override
//    public Mono<List<MapOthersPolygonType>> queryList(String typeName) {
//        return Mono.just(loadExample(typeName))
//                .map(v ->{
//                  Sort sort = new Sort(Sort.Direction.ASC, "orderId");
//                  return mapOthersPolygonTypeDao.findAll(v, sort);
//                })
//                ;
//    }
//
//    @Override
//    public Mono<MapOthersPolygonType> add(MapOthersPolygonType mapOthersPolygonType) {
//        return Mono.just(mapOthersPolygonType)
//                .map(v ->{
//                    v.setTypeCode(mapOthersPolygonTypeDao.queryMaxTypeCode());
//                    return mapOthersPolygonTypeDao.save(v);
//                })
//                ;
//    }
//
//    @Override
//    public Mono<MapOthersPolygonType> queryById(Integer typeCode) {
//        return Mono.just(typeCode)
//                .map(v -> mapOthersPolygonTypeDao.findById(v).get());
//    }
//
//    @Override
//    public Mono<MapOthersPolygonType> update(MapOthersPolygonType mapOthersPolygonType) {
//        return Mono.just(mapOthersPolygonType)
//                .map(v -> mapOthersPolygonTypeDao.save(v));
//    }
//
//    @Override
//    public Mono<Integer> delete(Integer typeCode) {
//        return Mono.just(typeCode)
//                .map(v ->{
//                    mapOthersPolygonTypeDao.deleteById(v);
//                    return v;
//                })
//                ;
//    }
//
//    @Override
//    public Mono<Integer> bulkDelete(String ids) {
//        return Mono.just(ids.split(","))
//                .map(v ->{
//                    for(String str : v){
//                        mapOthersPolygonTypeDao.deleteById(Integer.parseInt(str));
//                    }
//                    return v.length;
//                })
//                ;
//    }
//
//    @Override
//    public Mono<Void> saveAll(List<MapOthersPolygonType> polygonTypeList) {
//        return Mono.just(polygonTypeList)
//                .map(v -> {
//                    for(MapOthersPolygonType type : v){
//                        if(type.getTypeCode() == null){
//                            type.setTypeCode(mapOthersPolygonTypeDao.queryMaxTypeCode());
//                        }
//                    }
//                    mapOthersPolygonTypeDao.saveAll(v);
//                    return null;
//                })
//                ;
//    }
//
//    private Example<MapOthersPolygonType> loadExample(String typeName){
//        MapOthersPolygonType mapOthersPolygonType = new MapOthersPolygonType();
//        mapOthersPolygonType.setTypeName(typeName);
//
//        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
//                .withMatcher("typeName", ExampleMatcher.GenericPropertyMatchers.contains())
//                .withIgnorePaths("typeCode");
//
//        return Example.of(mapOthersPolygonType, exampleMatcher);
//    }
//}
