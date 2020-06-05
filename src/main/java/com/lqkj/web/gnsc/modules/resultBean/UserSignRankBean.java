package com.lqkj.web.gnsc.modules.resultBean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class UserSignRankBean {
    public SignRank userRank;
    public List<SignRank> signRankList = new ArrayList<>();

    public static class SignRank {
        private BigInteger rank;
        private String head;
        private String nickName;
        private Integer signCount;

        public SignRank(BigInteger rank, String head, String nickName, Integer signCount) {
            this.rank = rank;
            this.head = head;
            this.nickName = nickName;
            this.signCount = signCount;
        }

        public BigInteger getRank() {
            return rank;
        }

        public void setRank(BigInteger rank) {
            this.rank = rank;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public Integer getSignCount() {
            return signCount;
        }

        public void setSignCount(Integer signCount) {
            this.signCount = signCount;
        }
    }
}
