package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface MapRoomDao extends JpaRepository<MapRoom, Integer> {

    /**
     * 获取点标注详情
     * @return
     */
    @Query(nativeQuery = true,
            value = "with t1 as(select room_code ,room_name ,brief ,audio_url ,video_url ,st_asgeojson(lng_lat)\\:\\:json lng_lat,thumbs_up_count ,photo_background ,roam_url ,open_gns_sign ,gns_sign_count  " +
                    "from portal.map_room where map_code = :mapCode)" +
                    "select json_build_object('infoCode',room_code,'infoName',room_name,'brief',brief,'audioUrl',audio_url,'videoUrl',video_url,'vectorGeom',lng_lat,'thumbsUpCount',thumbs_up_count,'photoBackground',photo_background,'roamUrl',roam_url,'openGnsSign',open_gns_sign,'gnsSignCount',gns_sign_count)\\:\\:varchar from t1")
    String queryDetailByMapCode(Long mapCode);

    MapRoom queryByMapCode(Long mapCode);

    Boolean existsByMapCode(Long mapCode);

    /**
     * 获取变更列表
     * @return 变更列表
     */
    @Query(value = "select t from MapRoom t where t.campusCode = :zoneId and t.synStatus = false order by t.roomCode")
    List<MapRoom> queryChangeList(@Param("zoneId") Integer zoneId);

    /**
     * 同步完成后
     * 变更非删除状态数据的同步状态
     */
    @Modifying
    @Query(value = "update MapRoom t set t.synStatus = true where t.campusCode = :zoneId and t.synStatus = false and t.delete = false ")
    void updateSynStatusAfterSyn(@Param("zoneId") Integer zoneId);

    /**
     * 同步完成后
     * 删除状态为删除的数据
     */
    @Modifying
    @Query(value = "delete from MapRoom t where t.campusCode = :zoneId and (t.delete = true or t.mapCode in :mapCodes)")
    void deleteAfterSyn(@Param("mapCodes") Long[] mapCodes,
                        @Param("zoneId") Integer zoneId);

    @Query(value = "select t from MapRoom  t where t.roomCode in :roomCodes")
    List<MapRoom> queryWithRoomCodes(@Param("roomCodes") Integer[] roomCodes);
}
