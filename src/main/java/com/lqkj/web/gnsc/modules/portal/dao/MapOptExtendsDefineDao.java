package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapOptExtendsDefine;
import com.lqkj.web.gnsc.modules.portal.model.MapOptExtendsDefinePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapOptExtendsDefineDao extends JpaRepository<MapOptExtendsDefine, MapOptExtendsDefinePK> {

    /**
     * 根据分类编号获取扩展属性列表
     * @param typeCode
     * @return
     */
    @Query(value = "from MapOptExtendsDefine where typeCode = :typeCode order by orderid")
    List<MapOptExtendsDefine> queryByTypeCode(@Param("typeCode") Integer typeCode);

    /**
     * 获取当前最大columnId值
     * @param typeCode
     * @return
     */
    @Query(value = "select case when max(columnId) is null then 1 else (max(columnId) + 1) end from MapOptExtendsDefine where typeCode = :typeCode")
    Integer queryMaxColumnId(@Param("typeCode") Integer typeCode);

    @Query(value = "from MapOptExtendsDefine order by typeCode, orderid")
    List<MapOptExtendsDefine> queryAll();
}
