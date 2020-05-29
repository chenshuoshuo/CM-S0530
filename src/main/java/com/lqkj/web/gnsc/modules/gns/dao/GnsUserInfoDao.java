package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsStore;
import com.lqkj.web.gnsc.modules.gns.domain.GnsUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GnsUserInfoDao extends JpaRepository<GnsUserInfo, String> {
    /**
     * 根据学校ID和openId获取用户
     */
    GnsUserInfo findBySchoolIdAndOpenid(Integer schoolId,String openId);

}
