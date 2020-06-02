package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface MapBuildingDao extends JpaRepository<MapBuilding, Integer> {

    /**
     * 获取点标注详情
     * @return
     */
    @Query(nativeQuery = true,
            value = "select building_code buildingCode,building_name buildingName,brief brief,audio_url audioUrl,video_url videoUrl,st_asgeojson(lng_lat) vectorGeom, " +
                    "thumbs_up_count thumbsUpCount,photo_background photoBackground,roam_url roamUrl,open_gns_sign openGnsSign,gns_sign_count gnsSignCount " +
                    "from portal.map_building where map_code = :mapCode")
    Map<String,Object> queryDetailByMapCode(Long mapCode);

    MapBuilding queryByMapCode(Long mapCode);

    Boolean existsByMapCode(Long mapCode);

    /**
     * 获取变更列表
     * @return 变更列表
     */
    @Query(value = "select t from MapBuilding t where t.campusCode = :zoneId and t.synStatus = false order by t.buildingCode")
    List<MapBuilding> queryChangeList(@Param("zoneId") Integer zoneId);

    /**
     * 同步完成后
     * 变更非删除状态数据的同步状态
     */
    @Modifying
    @Transactional
    @Query(value = "update MapBuilding t set t.synStatus = true where t.campusCode = :zoneId and t.synStatus = false and t.delete = false ")
    void updateSynStatusAfterSyn(@Param("zoneId") Integer zoneId);

    /**
     * 同步完成后
     * 删除状态为删除的数据
     */
    @Modifying
    @Transactional
    @Query(value = "delete from MapBuilding t where t.campusCode = :zoneId and (t.delete = true or t.mapCode in :mapCodes)")
    void deleteAfterSyn(@Param("mapCodes") Long[] mapCodes,
                        @Param("zoneId") Integer zoneId);

    @Query(value = "select t from MapBuilding t where t.buildingCode in :buildingCodes")
    List<MapBuilding> queryWithBuildingCodes(@Param("buildingCodes") Integer[] buildingCodes);

}
