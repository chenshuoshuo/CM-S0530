package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapRoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRoomTypeDao extends JpaRepository<MapRoomType, Integer> {

    @Query("select case when max(typeCode) is null then 1 else (max(typeCode) + 1) end from MapRoomType")
    Integer queryMaxTypeCode();
}
