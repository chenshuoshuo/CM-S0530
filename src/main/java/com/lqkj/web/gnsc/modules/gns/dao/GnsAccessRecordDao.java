package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsAccessRecord;
import com.lqkj.web.gnsc.modules.gns.domain.GnsUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GnsAccessRecordDao extends JpaRepository<GnsAccessRecord, String> {


}
