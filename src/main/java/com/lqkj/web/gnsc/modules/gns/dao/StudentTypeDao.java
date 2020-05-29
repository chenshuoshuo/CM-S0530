package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsStudentType;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentTypeDao extends JpaRepository<GnsStudentType, Integer> {

    List<GnsStudentType> findAllBySchoolId(Integer schoolId);

    @Query(nativeQuery = true,
        value = "with t1 as(SELECT campus_code,school_id FROM gns.gns_campus_info WHERE school_id = :schoolId),\n" +
                "t2 as (SELECT g.*,t1.school_id from gns.gns_guide g,t1 where g.campus_code = t1.campus_code),\n" +
                "t3 as(select s.* from gns.gns_student_type s,t2 where s.school_id = :schoolId and s.studnet_type_code = t2.studnet_type_code)\n" +
                "SELECT * from t3")
    List<GnsStudentType> loadStudentTypeWithGuide(Integer schoolId);

    @Query("select t from GnsStudentType t where t.schoolId = :schoolId and t.defaultType = true ")
    GnsStudentType loadDefaultStudentType(Integer schoolId);


    GnsStudentType findBySchoolIdAndTypeName(Integer schoolId,String typeName);
}
