package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsReceptionType;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStudentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReceptionTypeDao extends JpaRepository<GnsReceptionType, Integer> {

    List<GnsReceptionType> findAllBySchoolId(Integer schoolId);


    GnsReceptionType findBySchoolIdAndTypeName(Integer schoolId, String typeName);
}
