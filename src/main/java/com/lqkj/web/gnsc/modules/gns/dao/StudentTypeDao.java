package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsStudentType;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StudentTypeDao extends JpaRepository<GnsStudentType, Integer> {

    List<GnsStudentType> findAllBySchoolId(Integer schoolId);

    @Query(nativeQuery = true,
        value = "with t1 as(SELECT studnet_type_code,type_name FROM gns.gns_student_type WHERE school_id = :schoolId),\n" +
                "t2 as(SELECT DISTINCT g.studnet_type_code,t1.type_name from gns.gns_guide g,t1 WHERE campus_code = :campusCode and g.studnet_type_code = t1.studnet_type_code)\n" +
                "SELECT t2.studnet_type_code typeCode,t2.type_name typeName from t2")
    List<Map<String,Object>> loadStudentTypeWithGuide(Integer schoolId, Integer campusCode);

    @Query("select t from GnsStudentType t where t.schoolId = :schoolId and t.defaultType = true ")
    GnsStudentType loadDefaultStudentType(Integer schoolId);


    GnsStudentType findBySchoolIdAndTypeName(Integer schoolId,String typeName);
}
