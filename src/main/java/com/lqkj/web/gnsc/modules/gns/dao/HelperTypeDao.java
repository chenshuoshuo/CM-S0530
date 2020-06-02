package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsHelperType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelperTypeDao extends JpaRepository<GnsHelperType,Integer> {

    List<GnsHelperType> findAllBySchoolId(Integer schoolId);

    GnsHelperType findByTypeName(String typeName);
}
