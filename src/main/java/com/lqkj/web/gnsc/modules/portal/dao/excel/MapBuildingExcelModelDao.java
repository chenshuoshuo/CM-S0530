package com.lqkj.web.gnsc.modules.portal.dao.excel;

import com.lqkj.web.gnsc.modules.portal.model.excel.MapBuildingExcelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapBuildingExcelModelDao extends JpaRepository<MapBuildingExcelModel, Integer> {


    @Query(nativeQuery = true, value = "select mb.*, mbia.pic_url from " +
            "(select * from portal.map_building where type_code = :typeCode) mb " +
            "left join (select mbi.building_code, string_agg(mbi.img_url, ',') pic_url from portal.map_building_img mbi group by mbi.building_code) mbia" +
            " on mb.building_code = mbia.building_code;")
    List<MapBuildingExcelModel> queryExcelList(@Param("typeCode") Integer typeCode);
}
