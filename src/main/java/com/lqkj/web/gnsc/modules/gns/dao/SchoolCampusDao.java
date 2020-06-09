package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsCampusInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolCampusDao extends JpaRepository<GnsCampusInfo,Integer> {

    /**
     * 根据主键获取
     */
    GnsCampusInfo findByCampusCode(Integer campusCode);

    /**
     * 根据二维ID 获取
     */
    GnsCampusInfo findByVectorZoomCode(Integer campusCode);

    /**
     * 获取默认校区
     * @param schoolId
     * @return
     */
    List<GnsCampusInfo> findBySchoolId(Integer schoolId);

    /**
     * 获取学校校区数量
     */
    @Query("select count(distinct s.campusCode) from GnsCampusInfo s where s.schoolId = :schoolId")
    Integer campusCount(Integer schoolId);


    /**
     * 根据校区名字获取校区ID
     * @param campusName
     * @return
     */

    GnsCampusInfo findByCampusName(String campusName);

    /**
     * 获取可绑定校区信息
     * @return
     */
    @Query(nativeQuery = true,
        value = "select c.* from gns.gns_campus_info c where 1=1 and c.school_id is null " +
                "and case when :campusName is null then 1=1 else c.campus_name like concat('%',:campusName,'%') end " +
                "order by c.campus_code asc ")
    List<GnsCampusInfo> noBindingSchoolCampus(String campusName);

    /**
     * 获取已绑定校区信息
     * @return
     */
    @Query(nativeQuery = true,
            value = "select c.* from gns.gns_campus_info c where 1=1 and c.school_id =:schoolId " +
                    "and case when :campusName is null then 1=1 else c.campus_name like concat('%',:campusName,'%') end " +
                    "order by c.campus_code asc ")
    List<GnsCampusInfo> hasBindingSchoolCampus(String campusName,Integer schoolId);

    /**
     * 删除指定学校校区
     */
    void deleteBySchoolId(Integer schoolId);

    /**
     * 根据校区ID和学校ID获取信息
     */
    GnsCampusInfo findByCampusCodeAndSchoolId(Integer campusCode,Integer schoolId);


}
