package com.lqkj.web.gnsc.modules.portal.dao.excel;


import com.lqkj.web.gnsc.modules.portal.model.excel.MapOthersPolygonExcelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapOthersPolygonExcelModelDao extends JpaRepository<MapOthersPolygonExcelModel, Integer> {

    @Query(nativeQuery = true, value = "select mb.*, mbia.pic_url from " +
            "(select * from portal.map_others_polygon where type_code = :typeCode) mb " +
            "left join (select mbi.polygon_code, string_agg(mbi.img_url, ',') pic_url from portal.map_others_polygon_img mbi group by mbi.polygon_code) mbia" +
            " on mb.polygon_code = mbia.polygon_code;")
    List<MapOthersPolygonExcelModel> queryExcelList(@Param("typeCode") Integer typeCode);
}
