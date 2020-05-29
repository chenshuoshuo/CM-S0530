package com.lqkj.web.gnsc.modules.gns.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@ApiModel(value = "迎新配置")
@Entity
@Table(name = "gns_store", schema = "gns")
public class GnsStore {

    @Id
    @Column(name = "sore_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "配置分类ID")
    private Integer id;

    @Column(name = "name", nullable = true, length = 128)
    @ApiModelProperty(value = "配置分类名称")
    private String name;

    public GnsStore() {
    }

    public GnsStore(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
