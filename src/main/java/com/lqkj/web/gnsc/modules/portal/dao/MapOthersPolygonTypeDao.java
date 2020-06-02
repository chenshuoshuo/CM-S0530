package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MapOthersPolygonTypeDao extends JpaRepository<MapOthersPolygonType, Integer> {


    @Query("select case when max(typeCode) is null then 1 else (max(typeCode) + 1) end from MapOthersPolygonType")
    Integer queryMaxTypeCode();
}
