package com.lqkj.web.gnsc.modules.resultBean;

import java.io.Serializable;

public class RankingBean implements Serializable {
    private Integer rank;
    private String name;
    private Integer count;

    public RankingBean(Integer rank, String name, Integer count) {
        this.rank = rank;
        this.name = name;
        this.count = count;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
