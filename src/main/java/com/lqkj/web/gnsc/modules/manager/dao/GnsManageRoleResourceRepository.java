package com.lqkj.web.gnsc.modules.manager.dao;


import com.lqkj.web.gnsc.modules.manager.domain.GnsManageRoleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GnsManageRoleResourceRepository extends JpaRepository<GnsManageRoleResource, Long> {
    @Modifying
    @Query(value="DELETE from gns.gns_manage_role_resource as crta where crta.rule_id=:ruleId", nativeQuery = true)
    void deleteByRuleId(@Param("ruleId") Long ruleId);
}
