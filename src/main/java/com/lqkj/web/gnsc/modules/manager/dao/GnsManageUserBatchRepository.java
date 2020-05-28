package com.lqkj.web.gnsc.modules.manager.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 批量更新用户
 */
@Repository
public class GnsManageUserBatchRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void bulkMergeUser(String sql){
        jdbcTemplate.execute(sql);
    }
}
