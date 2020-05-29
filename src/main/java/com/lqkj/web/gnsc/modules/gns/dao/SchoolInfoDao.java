package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolInfoDao extends JpaRepository<GnsSchool,Integer> {

    GnsSchool findBySchoolId(Integer schoolId);

    /**
     * 根据学习名称获取学校信息
     */
    GnsSchool findBySchoolName(String schoolName);

    /**
     * 根据学习名称获取模糊查询
     */
    @Query(nativeQuery = true,
            value = "select * from gns.gns_school where 1=1 and case when :schoolName is not null then school_name like concat(:schoolName) else 1=1 end")
    List<GnsSchool> loadWithSchoolName(String schoolName);

    /**
     * 所有学校总数
     */
    @Query("select count(distinct s.schoolId) from GnsSchool s")
    Integer countSchool();
}
