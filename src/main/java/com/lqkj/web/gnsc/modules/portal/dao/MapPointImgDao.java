package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapPointImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MapPointImgDao extends JpaRepository<MapPointImg, Integer> {
    /**
     * 根据点标注编号删除图片信息
     * @param pointCode
     */
    @Modifying
    @Transactional
    @Query(value = "delete from MapPointImg  where pointCode = :pointCode")
    Integer deleteAllByPointCode(@Param("pointCode") Integer pointCode);

}
