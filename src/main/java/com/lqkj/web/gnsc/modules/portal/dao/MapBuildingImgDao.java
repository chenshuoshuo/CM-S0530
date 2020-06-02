package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapBuildingImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MapBuildingImgDao extends JpaRepository<MapBuildingImg, Integer> {
    /**
     * 根据大楼代码删除图片信息
     * @param buildingCode
     */
    @Modifying
    @Transactional
    @Query(value = "delete from MapBuildingImg  where buildingCode = :buildingCode")
    Integer deleteAllByBuildingCode(@Param("buildingCode") Integer buildingCode);
}
