package com.bizaia.zhongyin.module.mine.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/3/13.
 */

public class UserInfoBean implements Serializable{


    /**
     * code : 200
     * data : {"id":10000091,"mobile":"15208376981","nickname":"xuanze","userType":0,"avatarUrl":"download/img/1490002382361eSffR.jpg","orgId":0,"createTime":"2017-05-22 04:08:44","allowPushNotifi":true,"memberSig":"eJxljsFOhDAURfd8BWGr0dJSoO4KBQWZmSjoOG4IQoE6EQiUCaPx31WcRBLf9px37-1QVFXVkii*yPK8HRuZymPHNfVK1YB2-ge7ThRpJlPUF-8gnzrR8zQrJe9nqGOMIQBLRxS8kaIUJ4MA27AZ8xG1GcTAZyZ0LGwCZAEKHc9YfA7FPp3rf6ON71xoYqIvFVHNcOXt3OCOBaYr68Dle9L7Nwdw2TLESs*-lauK8LzUkRxfeBM-RWsa1DR8tSZ4nSR08xy2u-bxYRjJ*kxunfeQ8qqux*IexcUmCqftolKKN34aRLABIYbLzQfeD6JtZgECHesQgZ-TlE-lCzfCYkk_","memberSigExpired":1510949324000,"balance":9999971.96,"interAreaCode":"86","subscribe":false}
     */

    private int code;
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
        /**
         * id : 10000091
         * mobile : 15208376981
         * nickname : xuanze
         * userType : 0
         * avatarUrl : download/img/1490002382361eSffR.jpg
         * orgId : 0
         * createTime : 2017-05-22 04:08:44
         * allowPushNotifi : true
         * memberSig : eJxljsFOhDAURfd8BWGr0dJSoO4KBQWZmSjoOG4IQoE6EQiUCaPx31WcRBLf9px37-1QVFXVkii*yPK8HRuZymPHNfVK1YB2-ge7ThRpJlPUF-8gnzrR8zQrJe9nqGOMIQBLRxS8kaIUJ4MA27AZ8xG1GcTAZyZ0LGwCZAEKHc9YfA7FPp3rf6ON71xoYqIvFVHNcOXt3OCOBaYr68Dle9L7Nwdw2TLESs*-lauK8LzUkRxfeBM-RWsa1DR8tSZ4nSR08xy2u-bxYRjJ*kxunfeQ8qqux*IexcUmCqftolKKN34aRLABIYbLzQfeD6JtZgECHesQgZ-TlE-lCzfCYkk_
         * memberSigExpired : 1510949324000
         * balance : 9999971.96
         * interAreaCode : 86
         * subscribe : false
         */

        private long id;
        private String mobile;
        private String nickname;
        private int userType;
        private String avatarUrl;
        private long orgId;
        private String createTime;
        private boolean allowPushNotifi;
        private String memberSig;
        private long memberSigExpired;
        private double balance;
        private String interAreaCode;
        private boolean subscribe;
        private boolean hasDetail;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public long getOrgId() {
            return orgId;
        }

        public void setOrgId(long orgId) {
            this.orgId = orgId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public boolean isAllowPushNotifi() {
            return allowPushNotifi;
        }

        public void setAllowPushNotifi(boolean allowPushNotifi) {
            this.allowPushNotifi = allowPushNotifi;
        }

        public String getMemberSig() {
            return memberSig;
        }

        public void setMemberSig(String memberSig) {
            this.memberSig = memberSig;
        }

        public long getMemberSigExpired() {
            return memberSigExpired;
        }

        public void setMemberSigExpired(long memberSigExpired) {
            this.memberSigExpired = memberSigExpired;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public String getInterAreaCode() {
            return interAreaCode;
        }

        public void setInterAreaCode(String interAreaCode) {
            this.interAreaCode = interAreaCode;
        }

        public boolean isSubscribe() {
            return subscribe;
        }

        public void setSubscribe(boolean subscribe) {
            this.subscribe = subscribe;
        }

        public boolean isHasDetail() {
            return hasDetail;
        }

        public void setHasDetail(boolean hasDetail) {
            this.hasDetail = hasDetail;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "id=" + id +
                    ", mobile='" + mobile + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", userType=" + userType +
                    ", avatarUrl='" + avatarUrl + '\'' +
                    ", orgId=" + orgId +
                    ", createTime='" + createTime + '\'' +
                    ", allowPushNotifi=" + allowPushNotifi +
                    ", memberSig='" + memberSig + '\'' +
                    ", memberSigExpired=" + memberSigExpired +
                    ", balance=" + balance +
                    ", interAreaCode='" + interAreaCode + '\'' +
                    ", subscribe=" + subscribe +
                    ", hasDetail=" + hasDetail +
                    '}';
        }
    }
}
