package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsGuide;
import org.apache.poi.sl.draw.geom.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuideDao extends JpaRepository<GnsGuide,Integer> {

    /**
     * 根据名称和校内外判断是否重复
     */
    @Query("select g from GnsGuide g where g.title=:title and g.campusCode=:campusCode")
    GnsGuide checkIsExistWithCampusId(String title, Integer campusCode);

    /**
     * 根据校区和分类获取
     */
    @Query(nativeQuery = true,
        value = "select g.* from gns.gns_guide g where 1=1 " +
                "and case when :campusCode = '0' then 1=1 else g.campus_code = :campusCode end " +
                "and case when :typeCode = '0' then 1=1 else g.studnet_type_code = :typeCode end order by g.order_id asc")
    List<GnsGuide> findAllByCampusCodeAndAndStudnetTypeCode(Integer campusCode,Integer typeCode);

    /**
     * 获取当前最大排序值
     */
    @Query(nativeQuery = true,
            value = "SELECT MAX(sm.order_id) FROM gns.gns_guide as sm WHERE sm.campus_code = :campusCode")
    Integer getMaxOrder(Integer campusCode);

    /**
     * 自动排序(新增)
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_guide set order_id = order_id + 1 where order_id >= :orderId and campus_code = :campusCode ")
    void autoOrder(Integer orderId,Integer campusCode);

    /**
     * 自动排序（编辑）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_guide set order_id = order_id + 1 where order_id >= :newOrderId and order_id <= :oldOrderId and campus_code = :campusCode ")
    void autoOrderForUpdateDesc(Integer oldOrderId, Integer newOrderId,Integer campusCode);

    /**
     * 自动排序（编辑）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_guide set order_id = order_id - 1 where order_id > :oldOrderId and order_id <= :newOrderId and campus_code = :campusCode ")
    void autoOrderForUpdateAsc(Integer oldOrderId, Integer newOrderId,Integer campusCode);

    /**
     * 自动排序（删除）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_guide set order_id = order_id - 1 where order_id > :oldOrderId and campus_code = :campusCode")
    void autoOrderForDelete(Integer oldOrderId,Integer campusCode);

}
