package com.bizaia.zhongyin.module.mine.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/3/18.
 */

public class ImInfo implements Serializable{


    /**
     * code : 200
     * data : {"memberSigExpired":1505216333000,"memberSig":"eJxljl1PgzAARd-5FQ3PZqGU8mGyRFzGYBUUN7OGF4LQkTIHBerQmP33KS6RxPt6Tu69XwoAQN0*bGZZnjfvtUzlp2AquAWqpt78QSF4kWYyRV3xD7IPwTuWZnvJuhFCjLGuaVOHF6yWfM*vho0QdGzLvmvbWd4cJ2JfHNJx7bfJ*K7RTezAqcLLEYbLeBG4rCRWQCn2h0Q3YuI2tNzFXv8WVitG-XvReeGwIQcx1C535ZYE1SNfR0n-uo5IvVtJ4icW8tvlopL02Y9O3pMdUOvFnc8nk5If2fWQ7ZimgRCa0BPret7Uo6BrEEMdaT9RlbNyAVZaXzk_","memberAccount":"83319878@qq.com"}
     */

    private int code;
    /**
     * memberSigExpired : 1505216333000
     * memberSig : eJxljl1PgzAARd-5FQ3PZqGU8mGyRFzGYBUUN7OGF4LQkTIHBerQmP33KS6RxPt6Tu69XwoAQN0*bGZZnjfvtUzlp2AquAWqpt78QSF4kWYyRV3xD7IPwTuWZnvJuhFCjLGuaVOHF6yWfM*vho0QdGzLvmvbWd4cJ2JfHNJx7bfJ*K7RTezAqcLLEYbLeBG4rCRWQCn2h0Q3YuI2tNzFXv8WVitG-XvReeGwIQcx1C535ZYE1SNfR0n-uo5IvVtJ4icW8tvlopL02Y9O3pMdUOvFnc8nk5If2fWQ7ZimgRCa0BPret7Uo6BrEEMdaT9RlbNyAVZaXzk_
     * memberAccount : 83319878@qq.com
     */

    private DataEntity data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable{
        private long memberSigExpired;
        private String memberSig;
        private String memberAccount;

        public long getMemberSigExpired() {
            return memberSigExpired;
        }

        public void setMemberSigExpired(long memberSigExpired) {
            this.memberSigExpired = memberSigExpired;
        }

        public String getMemberSig() {
            return memberSig;
        }

        public void setMemberSig(String memberSig) {
            this.memberSig = memberSig;
        }

        public String getMemberAccount() {
            return memberAccount;
        }

        public void setMemberAccount(String memberAccount) {
            this.memberAccount = memberAccount;
        }
    }
}
