package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapRoomExtends;
import com.lqkj.web.gnsc.modules.portal.model.MapRoomExtendsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MapRoomExtendsDao extends JpaRepository<MapRoomExtends, MapRoomExtendsPK> {

    /**
     * 根据房间编码删除扩展属性
     * @param roomCode 房间编码
     */
    @Transactional
    void deleteAllByRoomCode(Integer roomCode);
}
