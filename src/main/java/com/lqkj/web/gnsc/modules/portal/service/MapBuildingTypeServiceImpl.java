//package com.lqkj.web.gnsc.modules.portal.service;
//
//import com.lqkj.web.dbe.modules.portal.dao.MapBuildingTypeDao;
//import com.lqkj.web.dbe.modules.portal.model.MapBuildingType;
//import com.lqkj.web.dbe.modules.portal.service.MapBuildingTypeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.*;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@Service
//public class MapBuildingTypeServiceImpl implements MapBuildingTypeService {
//    @Autowired
//    MapBuildingTypeDao mapBuildingTypeDao;
//
//    @Override
//    public Mono<Page<MapBuildingType>> pageQuery(String typeName, Integer page, Integer pageSize) {
//        return Mono.just(loadExample(typeName))
//                .map(v ->{
//                    PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
//                    return mapBuildingTypeDao.findAll(v, pageRequest);
//                })
//                ;
//    }
//
//    @Override
//    public Mono<List<MapBuildingType>> queryList(String typeName) {
//        return Mono.just(loadExample(typeName))
//                .map(v ->{
//                  Sort sort = new Sort(Sort.Direction.ASC, "orderId");
//                  return mapBuildingTypeDao.findAll(v, sort);
//                })
//                ;
//    }
//
//    @Override
//    public Mono<MapBuildingType> add(MapBuildingType mapBuildingType) {
//        return Mono.just(mapBuildingType)
//                .map(v ->{
//                    v.setTypeCode(mapBuildingTypeDao.queryMaxTypeCode());
//                    return mapBuildingTypeDao.save(v);
//                })
//                ;
//    }
//
//    @Override
//    public Mono<MapBuildingType> queryById(Integer typeCode) {
//        return Mono.just(typeCode)
//                .map(v -> mapBuildingTypeDao.findById(v).get());
//    }
//
//    @Override
//    public Mono<MapBuildingType> update(MapBuildingType mapBuildingType) {
//        return Mono.just(mapBuildingType)
//                .map(v -> mapBuildingTypeDao.save(v));
//    }
//
//    @Override
//    public Mono<Integer> delete(Integer typeCode) {
//        return Mono.just(typeCode)
//                .map(v ->{
//                    mapBuildingTypeDao.deleteById(v);
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
//                        mapBuildingTypeDao.deleteById(Integer.parseInt(str));
//                    }
//                    return v.length;
//                })
//                ;
//    }
//
//    @Override
//    public Mono<Void> saveAll(List<MapBuildingType> buildingTypeList) {
//        return Mono.just(buildingTypeList)
//                .map(v -> {
//                    for(MapBuildingType type : v){
//                        if(type.getTypeCode() == null){
//                            type.setTypeCode(mapBuildingTypeDao.queryMaxTypeCode());
//                        }
//                    }
//                    mapBuildingTypeDao.saveAll(v);
//                    return null;
//                })
//                ;
//    }
//
//    private Example<MapBuildingType> loadExample(String typeName){
//        MapBuildingType mapBuildingType = new MapBuildingType();
//        mapBuildingType.setTypeName(typeName);
//
//        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
//                .withMatcher("typeName", ExampleMatcher.GenericPropertyMatchers.contains())
//                .withIgnorePaths("typeCode");
//
//        return Example.of(mapBuildingType, exampleMatcher);
//    }
//}
