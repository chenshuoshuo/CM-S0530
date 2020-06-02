//package com.lqkj.web.gnsc.modules.portal.service;
//
//import com.lqkj.web.dbe.modules.portal.dao.MapBuildingDao;
//import com.lqkj.web.dbe.modules.portal.dao.excel.MapBuildingExcelModelDao;
//import com.lqkj.web.dbe.modules.portal.model.MapBuilding;
//import com.lqkj.web.dbe.modules.portal.model.excel.MapBuildingExcelModel;
//import com.lqkj.web.dbe.modules.portal.service.MapBuildingService;
//import com.lqkj.web.dbe.utils.GeoUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.*;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@Service
//public class MapBuildingServiceImpl implements MapBuildingService {
//    @Autowired
//    MapBuildingDao mapBuildingDao;
//    @Autowired
//    MapBuildingExcelModelDao mapBuildingExcelModelDao;
//
//    @Override
//    public Mono<Page<MapBuilding>> pageQuery(Integer campusCode, Integer typeCode, String buildingName, Integer page, Integer pageSize) {
//        return Mono.just(loadExample(campusCode, typeCode, buildingName))
//                .map(v ->{
//                    PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
//                    return mapBuildingDao.findAll(v, pageRequest);
//                })
//                ;
//    }
//
//    @Override
//    public Mono<List<MapBuilding>> listQuery(Integer campusCode, Integer typeCode, String buildingName) {
//        return Mono.just(loadExample(campusCode, typeCode, buildingName))
//                .map(v ->{
//                    Sort sort = new Sort(Sort.Direction.ASC, "orderId");
//                    return mapBuildingDao.findAll(v, sort);
//                })
//                ;
//    }
//
//    @Override
//    public Mono<MapBuilding> add(MapBuilding mapBuilding) {
//        return Mono.just(mapBuilding)
//                .map(v ->{
//                    v.setLngLat(GeoUtils.createPoint(v.getLngLatString()));
//                    v.setRasterLngLat(GeoUtils.createPoint(v.getRasterLngLatString()));
//                    return mapBuildingDao.save(v);
//                });
//    }
//
//    @Override
//    public Mono<MapBuilding> get(Integer buildingCode) {
//        return Mono.just(buildingCode)
//                .map(v -> mapBuildingDao.findById(v).get());
//    }
//
//    @Override
//    public Mono<MapBuilding> update(MapBuilding mapBuilding) {
//        return Mono.just(mapBuilding)
//                .map(v -> {
//
//                    v.setLngLat(GeoUtils.createPoint(v.getLngLatString()));
//                    v.setRasterLngLat(GeoUtils.createPoint(v.getRasterLngLatString()));
//                    v.setSynStatus(false);
//                    return v;
//                })
//                .map(v -> mapBuildingDao.save(v))
//                ;
//    }
//
//    @Override
//    public Mono<Integer> delete(Integer buildingCode) {
//        return Mono.just(buildingCode)
//                .map(v ->{
//                    MapBuilding mapBuilding =  mapBuildingDao.findById(v).get();
//                    mapBuilding.setSynStatus(false);
//                    mapBuilding.setDelete(true);
//                    mapBuildingDao.save(mapBuilding);
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
//                        MapBuilding mapBuilding =  mapBuildingDao.findById(Integer.parseInt(str)).get();
//                        mapBuilding.setSynStatus(false);
//                        mapBuilding.setDelete(true);
//                        mapBuildingDao.save(mapBuilding);
//                    }
//                    return v.length;
//                })
//                ;
//    }
//
//    @Override
//    public Mono<MapBuilding> queryByMapCode(Long mapCode) {
//        return Mono.just(mapCode)
//                .map(v -> mapBuildingDao.queryByMapCode(v));
//    }
//
//    @Override
//    public Mono<Boolean> existsByMapCode(Long mapCode) {
//        return Mono.just(mapCode)
//                .map(v -> mapBuildingDao.existsByMapCode(v));
//    }
//
//    @Override
//    public Mono<List<MapBuilding>> queryChangeList(Integer zoneId) {
//        return Mono.just(mapBuildingDao.queryChangeList(zoneId));
//    }
//
//    @Override
//    public void updateSynStatusAfterSyn(Integer zoneId) {
//        mapBuildingDao.updateSynStatusAfterSyn(zoneId);
//    }
//
//    @Override
//    public void deleteAfterSyn(Long[] mapCodes,Integer zoneId) {
//        mapBuildingDao.deleteAfterSyn(mapCodes, zoneId);
//    }
//
//    @Override
//    public void saveAll(List<MapBuilding> mapBuildingList) {
//        for(MapBuilding building : mapBuildingList){
//            try{
//                building.setLngLat(GeoUtils.createPoint(building.getLngLatString()));
//                building.setRasterLngLat(GeoUtils.createPoint(building.getRasterLngLatString()));
//            }catch (Exception e){
//                continue;
//            }
//        }
//        mapBuildingDao.saveAll(mapBuildingList);
//    }
//
//    @Override
//    public Mono<List<MapBuildingExcelModel>> queryExcelList(Integer typeCode) {
//        return Mono.just(typeCode)
//                .map(v -> mapBuildingExcelModelDao.queryExcelList(v));
//    }
//
//    @Override
//    public Mono<List<MapBuilding>> queryWithBuildingCodes(Integer[] buildingCodes) {
//        return Mono.just(buildingCodes)
//                .map(v -> mapBuildingDao.queryWithBuildingCodes(v));
//    }
//
//    private Example<MapBuilding> loadExample(Integer campusCode, Integer typeCode, String buildingName){
//        MapBuilding mapBuilding = new MapBuilding();
//        mapBuilding.setCampusCode(campusCode);
//        mapBuilding.setTypeCode(typeCode);
//        if(buildingName!=null){
//            mapBuilding.setBuildingName(buildingName);
//        }
//        mapBuilding.setDelete(false);
//
//        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
//                .withMatcher("campusCode", ExampleMatcher.GenericPropertyMatchers.exact())
//                .withMatcher("typeCode", ExampleMatcher.GenericPropertyMatchers.exact())
//                .withMatcher("buildingName", ExampleMatcher.GenericPropertyMatchers.contains())
//                .withMatcher("delete", ExampleMatcher.GenericPropertyMatchers.exact())
//                .withIgnorePaths("buildingCode");
//
//        return Example.of(mapBuilding, exampleMatcher);
//    }
//}
