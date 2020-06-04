package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsAcademy;
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
public interface GnsAcademyDao extends JpaRepository<GnsAcademy, Integer> {

    @Query(nativeQuery = true,
        value = "with t1 as(select academy_code,academy_name from gns.gns_academy where school_id = :schoolId order by order_id asc)" +
                "select array_to_json(array_agg(json_build_object('academyCode',academy_code,'academyName',academy_name)))\\:\\:varchar from t1")
    String findAllBySchoolId(Integer schoolId);

}
