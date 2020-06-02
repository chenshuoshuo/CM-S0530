package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsApplication;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsApplicationVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GnsApplicationDao extends JpaRepository<GnsApplication, Integer> {
    @Query(nativeQuery = true,
            value = "SELECT app.en_name enName,app.application_name applicationName,app.logo,app.qr_code qrCode,app.clicked_logo clickLogo from gns.gns_application as app " +
                    "where app.application_open = true and app.school_id = :schoolId and app.parent_id is null order by app.order_id asc")
    List<Map<String,Object>> appList(Integer schoolId);

    /**
     * 判断英文名是否重复
     */
    @Query("select ap from GnsApplication ap where ap.enName = :enName and ap.schoolId = :schoolId")
    GnsApplication existWithEnName(String enName,Integer schoolId);

    /**
     * 根据父级ID获取
     */
    @Query(nativeQuery = true,
            value = "SELECT app.en_name enName,app.application_name applicationName,app.logo,app.clicked_logo clickLogo,app.qr_code qrCode from gns.gns_application as app " +
                    "where app.application_open = true and app.school_id = :schoolId and app.parent_id = :parentId order by app.order_id asc")
    List<Map<String,Object>> findAllByParentId(Integer parentId,Integer schoolId);

    /**
     * 获取当前最大排序值
     */
    @Query(nativeQuery = true,
            value = "SELECT MAX(sm.order_id) FROM gns.gns_application as sm WHERE sm.school_id = :schoolId")
    Integer getMaxOrder(Integer schoolId);

    /**
     * 自动排序(新增)
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_application set order_id = order_id + 1 where order_id >= :orderId and school_id = :schoolId ")
    void autoOrder(Integer orderId,Integer schoolId);

    /**
     * 自动排序（编辑）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_application set order_id = order_id + 1 where order_id >= :newOrderId and order_id <= :oldOrderId and school_id = :schoolId ")
    void autoOrderForUpdateDesc(Integer oldOrderId, Integer newOrderId,Integer schoolId);

    /**
     * 自动排序（编辑）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_application set order_id = order_id - 1 where order_id > :oldOrderId and order_id <= :newOrderId and school_id = :schoolId ")
    void autoOrderForUpdateAsc(Integer oldOrderId, Integer newOrderId,Integer schoolId);

    /**
     * 自动排序（删除）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_application set order_id = order_id - 1 where order_id > :oldOrderId and school_id = :schoolId")
    void autoOrderForDelete(Integer oldOrderId,Integer schoolId);
}
