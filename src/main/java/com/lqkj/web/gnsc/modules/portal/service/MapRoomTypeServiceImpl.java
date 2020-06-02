//package com.lqkj.web.gnsc.modules.portal.service;
//
//import com.lqkj.web.dbe.modules.portal.dao.MapRoomTypeDao;
//import com.lqkj.web.dbe.modules.portal.model.MapRoomType;
//import com.lqkj.web.dbe.modules.portal.service.MapRoomTypeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.*;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@Service
//public class MapRoomTypeServiceImpl implements MapRoomTypeService {
//    @Autowired
//    MapRoomTypeDao mapRoomTypeDao;
//
//    @Override
//    public Mono<Page<MapRoomType>> pageQuery(String typeName, Integer page, Integer pageSize) {
//        return Mono.just(loadExample(typeName))
//                .map(v ->{
//                    PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
//                    return mapRoomTypeDao.findAll(v, pageRequest);
//                })
//                ;
//    }
//
//    @Override
//    public Mono<List<MapRoomType>> queryList(String typeName) {
//        return Mono.just(loadExample(typeName))
//                .map(v ->{
//                  Sort sort = new Sort(Sort.Direction.ASC, "orderId");
//                  return mapRoomTypeDao.findAll(v, sort);
//                })
//                ;
//    }
//
//    @Override
//    public Mono<MapRoomType> add(MapRoomType mapRoomType) {
//        return Mono.just(mapRoomType)
//                .map(v ->{
//                    v.setTypeCode(mapRoomTypeDao.queryMaxTypeCode());
//                    return mapRoomTypeDao.save(v);
//                })
//                ;
//    }
//
//    @Override
//    public Mono<MapRoomType> queryById(Integer typeCode) {
//        return Mono.just(typeCode)
//                .map(v -> mapRoomTypeDao.findById(v).get());
//    }
//
//    @Override
//    public Mono<MapRoomType> update(MapRoomType mapRoomType) {
//        return Mono.just(mapRoomType)
//                .map(v -> mapRoomTypeDao.save(v));
//    }
//
//    @Override
//    public Mono<Integer> delete(Integer typeCode) {
//        return Mono.just(typeCode)
//                .map(v ->{
//                    mapRoomTypeDao.deleteById(v);
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
//                        mapRoomTypeDao.deleteById(Integer.parseInt(str));
//                    }
//                    return v.length;
//                })
//                ;
//    }
//
//    @Override
//    public Mono<Void> saveAll(List<MapRoomType> roomTypeList) {
//        return Mono.just(roomTypeList)
//                .map(v -> {
//                    for(MapRoomType type : v){
//                        if(type.getTypeCode() == null){
//                            type.setTypeCode(mapRoomTypeDao.queryMaxTypeCode());
//                        }
//                    }
//                    mapRoomTypeDao.saveAll(v);
//                    return null;
//                })
//                ;
//    }
//
//    private Example<MapRoomType> loadExample(String typeName){
//        MapRoomType mapRoomType = new MapRoomType();
//        mapRoomType.setTypeName(typeName);
//
//        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
//                .withMatcher("typeName", ExampleMatcher.GenericPropertyMatchers.contains())
//                .withIgnorePaths("typeCode");
//
//        return Example.of(mapRoomType, exampleMatcher);
//    }
//}
