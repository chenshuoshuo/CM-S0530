package com.lqkj.web.gnsc.modules.portal.dao.excel;

import com.lqkj.web.gnsc.modules.portal.model.excel.MapPointExcelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapPointExcelModelDao extends JpaRepository<MapPointExcelModel, Integer> {

    @Query(nativeQuery = true, value = "select mb.*, mbia.pic_url from " +
            "(select * from portal.map_point where type_code = :typeCode) mb " +
            "left join (select mbi.point_code, string_agg(mbi.img_url, ',') pic_url from portal.map_point_img mbi group by mbi.point_code) mbia" +
            " on mb.point_code = mbia.point_code;")
    List<MapPointExcelModel> queryExcelList(@Param("typeCode") Integer typeCode);
}
