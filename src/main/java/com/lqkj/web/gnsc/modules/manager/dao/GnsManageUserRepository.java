package com.lqkj.web.gnsc.modules.manager.dao;

import com.lqkj.web.gnsc.modules.manager.domain.GnsManageUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface GnsManageUserRepository extends JpaRepository<GnsManageUser, Long> {

    @QueryHints(value = {
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")
    })
    @Query("select u from GnsManageUser u where u.userCode=:name")
    GnsManageUser findByUserName(@Param("name") String name);

    @Query("select u.userGroup,count(u) from GnsManageUser u group by u.userGroup order by u.userGroup")
    List<Object[]> userStatistics();
    
    /**
     * @Author wells
     * @Description //TODO 根据角色查询用户信息
     * @Date 14:36 2020/2/11
     * @Param 
     * @return 
     **/
    @Query(nativeQuery = true, value = "SELECT u.* FROM gns.gns_manage_user as u LEFT JOIN gns.gns_manage_user_role as utr on u.user_id = utr.user_id WHERE utr.rule_id = :ruleId")
    List<GnsManageUser> ruleIdToUser(Long ruleId);
}
