package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author cs
 * @Date 2020/6/1 19:52
 * @Version 2.2.2.0
 **/
@Repository
public interface FeedbackDao extends JpaRepository<GnsFeedback, Integer> {

    @Modifying
    @Query(nativeQuery = true, value = "insert into gns.gns_feedback (user_id, nickname, content,create_time)" +
            " values (:userId,:nickname,:contents,:create_time) ")
    void saveFeedback(@Param("userId") UUID userId,
                      @Param("nickname") String nickname,
                      @Param("contents") String contents,
                      @Param("create_time") Timestamp create_time);
}
