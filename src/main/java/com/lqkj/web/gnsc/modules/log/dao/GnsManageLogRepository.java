package com.lqkj.web.gnsc.modules.log.dao;

import com.lqkj.web.gnsc.modules.log.domain.GnsManageLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface GnsManageLogRepository extends JpaRepository<GnsManageLog, UUID> {

    @Query("select log from GnsManageLog log where log.createTime>:startTime and log.createTime<:endTime order by log.createTime desc")
    Page<GnsManageLog> pageByTime(@Param("startTime") Timestamp startTime,
                                  @Param("endTime") Timestamp endTime,
                                  Pageable pageable);

    @Query("select log from GnsManageLog log where log.createTime>:startTime and log.createTime<:endTime")
    List<GnsManageLog> findAllByTime(@Param("startTime") Timestamp startTime,
                                     @Param("endTime") Timestamp endTime);

    @Query("select log from GnsManageLog log order by log.createTime desc")
    Page<GnsManageLog> findAllByTimeDesc(Pageable pageable);
}
