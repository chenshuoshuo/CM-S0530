package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsClub;
import com.lqkj.web.gnsc.modules.gns.domain.GnsPushMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author cs
 * @since 2019/5/9
 */
@Repository
public interface GnsPushMessageDao extends JpaRepository<GnsPushMessage, UUID> {
    @Query("select m from GnsPushMessage m where m.pushId = :pushId ")
    GnsPushMessage findByPushId(UUID pushId);

}
