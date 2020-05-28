package com.lqkj.web.gnsc.modules.manager.dao;

import com.lqkj.web.gnsc.modules.manager.domain.GnsManageRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GnsManageRoleRepository extends JpaRepository<GnsManageRole, Long> {

    @Query(nativeQuery = true, value = "select r.* from gns.gns_manage_role r " +
            "inner join gns.gns_manage_user_role ur on r.rule_id = ur.rule_id " +
            "inner join gns.gns_manage_user u on u.user_id = ur.user_id " +
            "where u.user_code=:username and r.name like :keyword group by r.rule_id")
    Page<GnsManageRole> findSupportRules(@Param("username") String username,
                                                                                 @Param("keyword") String keyword,
                                                                                 Pageable pageable);

    //添加角色前，判断是否已经存在角色
    @Query(nativeQuery = true, value = "select r.* from gns.gns_manage_role as r where r.name=:name or r.content=:content")
    List<GnsManageRole> findByRuleName(@Param("name") String name, @Param("content") String content);

    //根据角色英文名获取
    GnsManageRole findByContent(String content);


    @Modifying
    @Query(nativeQuery = true,value = "DELETE FROM gns.gns_manage_role WHERE rule_id = :ruleId")
    void deleteUserRule(Long ruleId);
}
