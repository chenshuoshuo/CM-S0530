package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface MapPointDao extends JpaRepository<MapPoint, Integer> {

    /**
     * 获取点标注详情
     * @return
     */
    @Query(nativeQuery = true,
        value = "with t1 as(select point_code ,point_name ,brief ,audio_url ,video_url ,st_asgeojson(lng_lat)\\:\\:json lng_lat," +
                "st_asgeojson(raster_lng_lat)\\:\\:json raster_lng_lat, thumbs_up_count ,photo_background ,roam_url ,open_gns_sign ,gns_sign_count  " +
                "from portal.map_point where point_code = :pointCode)" +
                "select json_build_object('infoCode',point_code,'infoName',point_name,'brief',brief,'audioUrl',audio_url,'videoUrl',video_url,'vectorGeom',lng_lat,'rasterGeom',raster_lng_lat,'thumbsUpCount',thumbs_up_count,'photoBackground',photo_background,'roamUrl',roam_url,'openGnsSign',open_gns_sign,'gnsSignCount',gns_sign_count)\\:\\:varchar from t1")
    String queryDetailByPointCode(Integer pointCode);

    @Query(value = "select count(p.pointCode) from MapPoint p where p.typeCode = :typeCode and p.campusCode = :campusCode and p.delete = false")
    Integer countWithTypeCode(@Param("typeCode") Integer typeCode,
                              @Param("campusCode") Integer campusCode);

    //三维下点标注数量
    @Query(value = "select count(p.pointCode) from MapPoint p where p.typeCode = :typeCode and p.campusCode = :campusCode and p.delete = false and (p.leaf is null or p.rasterLngLat is not null)")
    Integer countWithTypeCodeFor3D(@Param("typeCode") Integer typeCode,
                                   @Param("campusCode") Integer campusCode);

    @Query(value = "select m from MapPoint m where m.typeCode in :typeCodes and m.campusCode = :campusCode and m.delete = false order by m.typeCode, m.orderId")
    List<MapPoint> queryAllByTypeCodes(@Param("typeCodes") Integer[] typeCodes,
                                       @Param("campusCode") Integer campusCode);

    MapPoint queryByMapCode(Long mapCode);

    MapPoint queryByPointCode(Integer pointCode);

    Boolean existsByMapCode(Long mapCode);

    Boolean existsByPointCode(Integer pointCode);

    /**
     * 获取变更列表
     * @return 变更列表
     */
    @Query(value = "select t from MapPoint t where t.campusCode = :zoneId and t.mapCode is not null and t.synStatus = false order by t.pointCode")
    List<MapPoint> queryChangeList(@Param("zoneId") Integer zoneId);

    /**
     * 获取新增列表
     * @return 删除列表
     */
    @Query(value = "select t from MapPoint t where t.campusCode = :zoneId and t.mapCode is null and t.delete = false order by t.pointCode")
    List<MapPoint> queryNewList(@Param("zoneId") Integer zoneId);

    /**
     * 同步完成后
     * 变更非删除状态数据的同步状态
     */
    @Modifying
    @Query(value = "update MapPoint t set t.synStatus = true where t.campusCode = :zoneId and t.synStatus = false and t.delete = false ")
    void updateSynStatusAfterSyn(@Param("zoneId") Integer zoneId);

    /**
     * 同步完成后
     * 删除状态为删除的数据
     */
    @Modifying
    @Query(value = "delete from MapPoint t where t.campusCode = :zoneId and (t.delete = true or t.mapCode in :mapCodes)")
    void deleteAfterSyn(@Param("mapCodes") Long[] mapCodes,
                        @Param("zoneId") Integer zoneId);

    @Query(value = "select t from MapPoint t where t.pointCode in :pointCodes")
    List<MapPoint> queryWithPointCodes(@Param("pointCodes") Integer[] pointCodes);
}
