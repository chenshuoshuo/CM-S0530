package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsReceptionPlace;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsReceptionPlaceVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query(nativeQuery = true,
            value = "select g.* from gns.gns_reception_place g left join gns.gns_campus_info c on g.campus_code = c.campus_code " +
                    "left join gns.gns_reception_type t on g.type_code = t.type_code where t.school_id = :schoolId order by g.order_id asc")
    List<GnsReceptionPlace> findAllBySchoolId(Integer schoolId);

    /**
     * 获取当前最大排序值
     */
    @Query(nativeQuery = true,
            value = "SELECT MAX(sm.order_id) FROM gns.gns_reception_place as sm")
    Integer getMaxOrder();

    /**
     * 自动排序(新增)
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_reception_place set order_id = order_id + 1 where order_id >= :orderId ")
    void autoOrder(Integer orderId);

    /**
     * 自动排序（编辑）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_reception_place set order_id = order_id + 1 where order_id >= :newOrderId and order_id <= :oldOrderId")
    void autoOrderForUpdateDesc(Integer oldOrderId, Integer newOrderId);

    /**
     * 自动排序（编辑）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_reception_place set order_id = order_id - 1 where order_id > :oldOrderId and order_id <= :newOrderId ")
    void autoOrderForUpdateAsc(Integer oldOrderId, Integer newOrderId);

    /**
     * 自动排序（删除）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_reception_place set order_id = order_id - 1 where order_id > :oldOrderId ")
    void autoOrderForDelete(Integer oldOrderId);
}
