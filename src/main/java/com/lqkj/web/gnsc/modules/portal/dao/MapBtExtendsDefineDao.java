package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapBtExtendsDefine;
import com.lqkj.web.gnsc.modules.portal.model.MapBtExtendsDefinePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapBtExtendsDefineDao extends JpaRepository<MapBtExtendsDefine, MapBtExtendsDefinePK> {

    /**
     * 根据分类编号获取扩展属性列表
     * @param typeCode
     * @return
     */
    @Query(value = "from MapBtExtendsDefine where typeCode = :typeCode order by orderid")
    List<MapBtExtendsDefine> queryByTypeCode(@Param("typeCode") Integer typeCode);

    /**
     * 获取当前最大columnId值
     * @param typeCode
     * @return
     */
    @Query(value = "select case when max(columnId) is null then 1 else (max(columnId) + 1) end from MapBtExtendsDefine where typeCode = :typeCode")
    Integer queryMaxColumnId(@Param("typeCode") Integer typeCode);

    @Query(value = "from MapBtExtendsDefine order by typeCode, orderid")
    List<MapBtExtendsDefine> queryAll();
}
