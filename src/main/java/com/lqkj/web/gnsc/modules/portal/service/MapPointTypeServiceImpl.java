//package com.lqkj.web.gnsc.modules.portal.service;
//
//import com.lqkj.web.dbe.modules.portal.dao.MapPointDao;
//import com.lqkj.web.dbe.modules.portal.dao.MapPointTypeDao;
//import com.lqkj.web.dbe.modules.portal.model.MapPointType;
//import com.lqkj.web.dbe.modules.portal.model.MapPointTypeQuerySpecification;
//import com.lqkj.web.dbe.modules.portal.service.MapPointTypeService;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.*;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@Service
//public class MapPointTypeServiceImpl implements MapPointTypeService {
//    @Autowired
//    MapPointTypeDao mapPointTypeDao;
//    @Autowired
//    MapPointDao mapPointDao;
//
//    @Override
//    public Mono<MapPointType> queryById(Integer typeCode) {
//        return Mono.just(typeCode)
//                .map(v -> mapPointTypeDao.findById(v).get());
//    }
//
//    @Override
//    public Mono<MapPointType> queryParentTypeById(Integer typeCode) {
//        return Mono.just(typeCode)
//                .map(v ->{
//                    MapPointType mapPointType = mapPointTypeDao.findById(v).get();
//                    return mapPointType;
//                })
//                .map(v ->{
//                    if(v.getParentCode() == null){
//                        return null;
//                    } else{
//                        return mapPointTypeDao.findById(v.getParentCode()).get();
//                    }
//                })
//                ;
//    }
//
//    @Override
//    public Mono<Page<MapPointType>> pageQuery(String typeName, String parentTypeName, Integer page, Integer pageSize) {
//        return Mono.just(new Sort(Sort.Direction.ASC, "orderId"))
//                .map(v ->{
//                    PageRequest pageRequest =  PageRequest.of(page, pageSize, v);
//                    return pageQuery(typeName, parentTypeName, pageRequest);
//                })
//                .map(v ->{
//                    Sort sort = new Sort(Sort.Direction.ASC, "orderId");
//                    for(MapPointType mapPointType : v.getContent()){
//                        mapPointType.setChildrenMapPointTypeList(mapPointTypeDao.findAll(loadExample(typeName, mapPointType.getTypeCode(), null), sort));
//                    }
//                    return v;
//                })
//                ;
//    }
//
//    @Override
//    public Mono<List<MapPointType>> listQuery(String typeName, String parentTypeName, Integer campusCode) {
//        return Mono.just(new MapPointTypeQuerySpecification(parentTypeName, null))
//                .map(v ->{
//                    Sort sort = new Sort(Sort.Direction.ASC, "orderId");
//                    return mapPointTypeDao.findAll(v, sort);
//                })
//                .map(v ->{
//                    Sort sort = new Sort(Sort.Direction.ASC, "orderId");
//                    for(MapPointType mapPointType : v){
//                        List<MapPointType> childrenList = mapPointTypeDao.findAll(loadExample(typeName, mapPointType.getTypeCode(), true), sort);
//                        for(MapPointType childType : childrenList){
//                            childType.setMapPointCount(mapPointDao.countWithTypeCode(childType.getTypeCode(), campusCode));
//                        }
//                        mapPointType.setChildrenMapPointTypeList(childrenList);
//                    }
//                    return v;
//                })
//                ;
//    }
//
//    @Override
//    public Mono<List<MapPointType>> queryByTypeAndCampusCode(String typeName, String parentTypeName, Integer campusCode,Boolean isVector) {
//        return Mono.just(new MapPointTypeQuerySpecification(parentTypeName, null))
//                .map(v ->{
//                    Sort sort = new Sort(Sort.Direction.ASC, "orderId");
//                    return mapPointTypeDao.findAll(v, sort);
//                })
//                .map(v ->{
//                    Sort sort = new Sort(Sort.Direction.ASC, "orderId");
//                    for(MapPointType mapPointType : v){
//                        List<MapPointType> childrenList = mapPointTypeDao.findAll(loadExample(typeName, mapPointType.getTypeCode(), true), sort);
//                        for(MapPointType childType : childrenList){
//                            if(isVector){
//                                childType.setMapPointCount(mapPointDao.countWithTypeCode(childType.getTypeCode(), campusCode));
//                            }else {
//                                childType.setMapPointCount(mapPointDao.countWithTypeCodeFor3D(childType.getTypeCode(), campusCode));
//                            }
//
//                        }
//                        mapPointType.setChildrenMapPointTypeList(childrenList);
//                    }
//                    return v;
//                })
//                ;
//    }
//
//    @Override
//    public Mono<List<MapPointType>> queryParentList() {
//        return Mono.just(new MapPointTypeQuerySpecification(null, null))
//                .map(v ->{
//                    Sort sort = new Sort(Sort.Direction.ASC, "orderId");
//                    return mapPointTypeDao.findAll(v, sort);
//                })
//                ;
//    }
//
//    @Override
//    public Mono<List<MapPointType>> queryListWithDisplayParentCode(Boolean display, Integer[] parentCodes, Integer campusCode,Boolean isVector) {
//        return Mono.just(display)
//                .map(v ->{
//                    if(parentCodes == null || parentCodes.length == 0){
//                        return mapPointTypeDao.findAll(new MapPointTypeQuerySpecification(null, v),
//                                new Sort(Sort.Direction.ASC, "orderId"));
//                    } else{
//                        List<MapPointType> mapPointTypeList = mapPointTypeDao.queryByParentCodesAndDisplay(parentCodes, v);
//                        for(MapPointType mapPointType : mapPointTypeList){
//                            if(isVector){
//                                mapPointType.setMapPointCount(mapPointDao.countWithTypeCode(mapPointType.getTypeCode(), campusCode));
//                            }else {
//                                mapPointType.setMapPointCount(mapPointDao.countWithTypeCodeFor3D(mapPointType.getTypeCode(), campusCode));
//                            }
//                        }
//                        return mapPointTypeList;
//                    }
//                })
//                ;
//    }
//
//    @Override
//    public Mono<MapPointType> add(MapPointType mapPointType) {
//        return Mono.just(mapPointType)
//                .map(v ->{
//                    v.setTypeCode(mapPointTypeDao.queryMaxTypeCode());
//                    return mapPointTypeDao.save(v);
//                });
//    }
//
//    @Override
//    public Mono<MapPointType> update(MapPointType mapPointType) {
//        return Mono.just(mapPointType)
//                .map(v -> mapPointTypeDao.save(v));
//    }
//
//    @Override
//    public Mono<Integer> delete(Integer typeCode) {
//        return Mono.just(typeCode)
//                .map(v -> {
//                    mapPointTypeDao.deleteById(v);
//                    return v;
//                });
//    }
//
//    @Override
//    public Mono<Integer> bulkDelete(String ids) {
//        return Mono.just(ids.split(","))
//                .map(v ->{
//                    for(String str : v){
//                        mapPointTypeDao.deleteById(Integer.parseInt(str));
//                    }
//                   return v.length;
//                });
//    }
//
//    @Override
//    public Mono<List<MapPointType>> queryByParentCodes(String parentCodes) {
//        return Mono.just(loadIntegerArrayFromString(parentCodes, ","))
//                .map(v-> mapPointTypeDao.queryByParentCodes(v));
//    }
//
//    @Override
//    public Mono<List<MapPointType>> listAllSubType() {
//        return Mono.just(mapPointTypeDao.queryAllSubType());
//    }
//
//    @Override
//    public Mono<List<MapPointType>> listAll() {
//        return Mono.just(mapPointTypeDao.listAll());
//    }
//
//    @Override
//    public Mono<Void> saveAll(List<MapPointType> pointTypeList) {
//        return Mono.just(pointTypeList)
//                .map(v -> {
//                    for(MapPointType type : v){
//                        if(type.getTypeCode() == null){
//                            type.setTypeCode(mapPointTypeDao.queryMaxTypeCode());
//                        }
//                    }
//                    mapPointTypeDao.saveAll(v);
//                    return null;
//                });
//    }
//
//    private Example<MapPointType> loadParentExample(String parentTypeName){
//
//        MapPointType mapPointType = new MapPointType();
//        mapPointType.setTypeName(parentTypeName);
//
//        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
//                .withMatcher("typeName", ExampleMatcher.GenericPropertyMatchers.contains())
//                .withMatcher("parentCode", ExampleMatcher.GenericPropertyMatchers.exact())
//                .withIgnorePaths("typeCode");
//
//        return Example.of(mapPointType, exampleMatcher);
//    }
//
//    private Example<MapPointType> loadExample(String typeName, Integer parentCode, Boolean display){
//
//        MapPointType mapPointType = new MapPointType();
//        mapPointType.setTypeName(typeName);
//        mapPointType.setParentCode(parentCode);
//        mapPointType.setDisplay(display);
//
//        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
//                .withMatcher("typeName", ExampleMatcher.GenericPropertyMatchers.contains())
//                .withMatcher("parentCode", ExampleMatcher.GenericPropertyMatchers.exact())
//                .withMatcher("display", ExampleMatcher.GenericPropertyMatchers.exact())
//                .withIgnorePaths("typeCode");
//
//        return Example.of(mapPointType, exampleMatcher);
//    }
//
//    private Integer[] loadIntegerArrayFromString(String str, String separator){
//        String[] stringArray = str.split(separator);
//        Integer[] integerArray = new Integer[stringArray.length];
//        for(int i = 0; i < stringArray.length; i++){
//            integerArray[i] = Integer.parseInt(stringArray[i]);
//        }
//        return integerArray;
//    }
//
//    private Page<MapPointType> pageQuery(String typeName, String parentTypeName, PageRequest pageRequest){
//        if(StringUtils.isNotEmpty(typeName) && StringUtils.isNotEmpty(parentTypeName)){
//            return mapPointTypeDao.pageQueryWithTypeNameAndParentName(typeName, parentTypeName, pageRequest);
//        } else if(StringUtils.isNotEmpty(typeName)){
//            return mapPointTypeDao.pageQueryWithTypeName(typeName, pageRequest);
//        } else if(StringUtils.isNotEmpty(parentTypeName)){
//            return mapPointTypeDao.pageQueryWithParentName(parentTypeName, pageRequest);
//        } else {
//            return mapPointTypeDao.pageQueryParentType(pageRequest);
//        }
//    }
//
//}
