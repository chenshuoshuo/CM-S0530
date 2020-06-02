package com.lqkj.web.gnsc.modules.portal.dao.excel;

import com.lqkj.web.gnsc.modules.portal.model.excel.MapRoomExcelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapRoomExcelModelDao extends JpaRepository<MapRoomExcelModel, Integer> {

    @Query(nativeQuery = true, value = "select mb.*, mbia.pic_url from " +
            "(select * from portal.map_room where type_code = :typeCode) mb " +
            "left join (select mbi.room_code, string_agg(mbi.img_url, ',') pic_url from portal.map_room_img mbi group by mbi.room_code) mbia" +
            " on mb.room_code = mbia.room_code;")
    List<MapRoomExcelModel> queryExcelList(@Param("typeCode") Integer typeCode);
}
