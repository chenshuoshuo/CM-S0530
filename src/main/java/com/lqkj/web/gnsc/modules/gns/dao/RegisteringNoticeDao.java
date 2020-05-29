package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsRegisteringNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface RegisteringNoticeDao extends JpaRepository<GnsRegisteringNotice,Integer> {

    /**
     * h5获取报道须知列表
     */
    @Query(nativeQuery = true,
        value = "select notice_id noticeId,title,update_time updteTime from gns.gns_registering_notice where school_id = :schoolId order by order_id asc")
    List<Map<String,Object>> queryList(Integer schoolId);

    /**
     * 根据报道名称判断是否重复
     */
    GnsRegisteringNotice findBySchoolIdAndTitle(Integer schoolId,String title);

}
