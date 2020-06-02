package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygonImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MapOthersPolygonImgDao extends JpaRepository<MapOthersPolygonImg, Integer> {
    /**
     * 根据面图元编号删除图片信息
     * @param polygonCode
     */
    @Modifying
    @Transactional
    @Query(value = "delete from MapOthersPolygonImg  where polygonCode = :polygonCode")
    Integer deleteAllByPolygonCode(@Param("polygonCode") Integer polygonCode);

}
