package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsReceptionPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReceptionPlaceDao extends JpaRepository<GnsReceptionPlace,Integer> {

    /**
     * h5迎新接待点列表查询
     * @param typeCode 校内校外
     * @return
     */
    @Query(nativeQuery = true,
        value = "select * from gns.gns_reception_place where type_code = :typeCode")
    List<GnsReceptionPlace> queryList(Integer typeCode);


    /**
     * 根据名称和校内外判断是否重复
     */
    GnsReceptionPlace findByTitleAndCampusCode(String replaceName, Integer campusCode);
}
