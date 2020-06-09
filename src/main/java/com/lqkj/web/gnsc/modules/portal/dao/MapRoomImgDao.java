package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapRoomImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MapRoomImgDao extends JpaRepository<MapRoomImg, Integer> {
    /**
     * 根据房间编号删除图片信息
     * @param roomCode
     */
    @Modifying
    @Query(value = "delete from MapRoomImg  where roomCode = :roomCode")
    Integer deleteAllByRoomCode(@Param("roomCode") Integer roomCode);
}
