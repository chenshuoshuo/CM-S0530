package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygonExtends;
import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygonExtendsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MapOthersPolygonExtendsDao extends JpaRepository<MapOthersPolygonExtends, MapOthersPolygonExtendsPK> {

    /**
     * 根据面图元编码删除扩展属性
     * @param polygonCode 面图源编码
     */
    @Transactional
    void deleteAllByPolygonCode(Integer polygonCode);
}
