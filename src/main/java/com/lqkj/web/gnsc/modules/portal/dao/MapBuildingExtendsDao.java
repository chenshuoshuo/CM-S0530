package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapBuildingExtends;
import com.lqkj.web.gnsc.modules.portal.model.MapBuildingExtendsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MapBuildingExtendsDao extends JpaRepository<MapBuildingExtends, MapBuildingExtendsPK> {

    /**
     * 根据大楼编码删除扩展属性
     * @param buildingCode 大楼编码
     */
    @Transactional
    void deleteAllByBuildingCode(Integer buildingCode);
}
