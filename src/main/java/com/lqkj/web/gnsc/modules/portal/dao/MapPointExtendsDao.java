package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapPointExtends;
import com.lqkj.web.gnsc.modules.portal.model.MapPointExtendsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MapPointExtendsDao extends JpaRepository<MapPointExtends, MapPointExtendsPK> {

    /**
     * 根据点标注编码删除扩展属性
     * @param pointCode 点标注编码
     */
    @Transactional
    void deleteAllByPointCode(Integer pointCode);
}
