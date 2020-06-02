package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface MapOthersPolygonDao extends JpaRepository<MapOthersPolygon, Integer> {

    /**
     * 获取点标注详情
     * @return
     */
    @Query(nativeQuery = true,
            value = "select polygon_code polygonCode,polygon_name polygonName,brief brief,audio_url audioUrl,video_url videoUrl, " +
                    "thumbs_up_count thumbsUpCount,photo_background photoBackground,roam_url roamUrl,open_gns_sign openGnsSign,gns_sign_count gnsSignCount " +
                    "from portal.map_others_polygon where map_code = :mapCode")
    Map<String,Object> queryDetailByMapCode(Long mapCode);

    MapOthersPolygon queryByMapCode(Long mapCode);

    Boolean existsByMapCode(Long mapCode);

    /**
     * 获取变更列表
     * @return 变更列表
     */
    @Query(value = "select t from MapOthersPolygon t where t.campusCode = :zoneId and t.synStatus = false order by t.polygonCode")
    List<MapOthersPolygon> queryChangeList(@Param("zoneId") Integer zoneId);

    /**
     * 同步完成后
     * 变更非删除状态数据的同步状态
     */
    @Modifying
    @Transactional
    @Query(value = "update MapOthersPolygon t set t.synStatus = true where t.campusCode = :zoneId and t.synStatus = false and t.delete = false ")
    void updateSynStatusAfterSyn(@Param("zoneId") Integer zoneId);

    /**
     * 同步完成后
     * 删除状态为删除的数据
     */
    @Modifying
    @Transactional
    @Query(value = "delete from MapOthersPolygon t where t.campusCode = :zoneId and (t.delete = true or t.mapCode in :mapCodes)")
    void deleteAfterSyn(@Param("mapCodes") Long[] mapCodes, @Param("zoneId") Integer zoneId);

    @Query(value = "select t from MapOthersPolygon t where t.polygonCode in :polygonCodes")
    List<MapOthersPolygon> queryWithPolygonCodes(@Param("polygonCodes") Integer[] polygonCodes);
}
