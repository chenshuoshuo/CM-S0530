package com.lqkj.web.gnsc.modules.portal.model;

import org.hsqldb.lib.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * 点标注分类 sql参数构造器
 */

public class MapPointTypeQuerySpecification implements Specification<MapPointType> {
    /**
     * 分类名称
     */
    private String typeName;
    /**
     * 是否显示
     */
    private Boolean display;

    public MapPointTypeQuerySpecification(String typeName, Boolean display){
        this.typeName = typeName;
        this.display = display;
    }

    @Override
    public Predicate toPredicate(Root<MapPointType> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        List<Expression<Boolean>> expressions = predicate.getExpressions();

        if(!StringUtil.isEmpty(typeName)){
            expressions.add(criteriaBuilder.like(root.get("typeName"), typeName));
        }

        if(display != null){
            expressions.add(criteriaBuilder.equal(root.get("display"), display));
        }

        expressions.add(criteriaBuilder.isNull(root.get("parentCode")));

        return predicate;
    }
}
