package com.lqkj.web.gnsc.modules.portal.dao;

import com.lqkj.web.gnsc.modules.portal.model.MapPointType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapPointTypeDao extends JpaRepository<MapPointType, Integer>, JpaSpecificationExecutor<MapPointType> {

    @Query(value = "select case when max(typeCode) is null then 1 else (max(typeCode) + 1) end from MapPointType")
    Integer queryMaxTypeCode();

    @Query(value = "select s from MapPointType s where s.parentCode in :parentCodes order by s.parentCode, s.typeCode")
    List<MapPointType> queryByParentCodes(@Param("parentCodes") Integer[] parentCodes);

    @Query(value = "select s from MapPointType s where s.parentCode in :parentCodes and s.display = :display order by s.parentCode, s.typeCode")
    List<MapPointType> queryByParentCodesAndDisplay(@Param("parentCodes") Integer[] parentCodes,
                                                    @Param("display") Boolean display);

    @Query(value = "select m from MapPointType m where m.parentCode is null and (m.typeName like %:parentTypeName% "
                 + "and "
                 + "m.typeCode in (select distinct mt.parentCode from MapPointType mt where mt.typeName like %:typeName% and mt.parentCode is not null))")
    Page<MapPointType> pageQueryWithTypeNameAndParentName(@Param("typeName") String typeName,
                                                          @Param("parentTypeName") String parentTypeName,
                                                          Pageable pageable);

    @Query(value = "select m from MapPointType m "
            + "where "
            + "m.typeCode in (select distinct mt.parentCode from MapPointType mt where mt.typeName like %:typeName% and mt.parentCode is not null)")
    Page<MapPointType> pageQueryWithTypeName(@Param("typeName") String typeName,
                                             Pageable pageable);

    @Query(value = "select m from MapPointType m where m.parentCode is null and m.typeName like %:parentTypeName% ")
    Page<MapPointType> pageQueryWithParentName(@Param("parentTypeName") String parentTypeName,
                                               Pageable pageable);

    @Query(value = "select m from MapPointType m where m.parentCode is null")
    Page<MapPointType> pageQueryParentType(Pageable pageable);

    @Query(value = "select t from MapPointType t where t.parentCode is not null order by t.typeCode")
    List<MapPointType> queryAllSubType();

    @Query(value = "select t from MapPointType t order by t.typeCode")
    List<MapPointType> listAll();
}
