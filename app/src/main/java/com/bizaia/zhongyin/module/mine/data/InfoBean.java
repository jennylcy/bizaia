package com.bizaia.zhongyin.module.mine.data;

/**
 * Created by Administrator
 * Created on 2017/7/7 16:06
 */

public class InfoBean {

    /**
     * code : 200
     * data : {"id":1,"memberId":10000018,"name":"徐娇娇","kana":"xujiaojiao","email":"xjj@163.com","birthday":"1996/1/8","gender":"0","genderChar":"女","career":"dancer","industry":"media","position":"dancer","duty":"A level","country":"中国","mobile":null,"company":null,"personalUrl":null,"companyUrl":null}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * memberId : 10000018
         * name : 徐娇娇
         * kana : xujiaojiao
         * email : xjj@163.com
         * birthday : 1996/1/8
         * gender : 0
         * genderChar : 女
         * career : dancer
         * industry : media
         * position : dancer
         * duty : A level
         * country : 中国
         * mobile : null
         * company : null
         * personalUrl : null
         * companyUrl : null
         */

        private long id;
        private long memberId;
        private String name;
        private String kana;
        private String email;
        private String birthday;
        private String gender;
        private String genderChar;
        private String career;
        private String industry;
        private String position;
        private String duty;
        private String country;
        private String mobile;
        private String company;
        private String personalUrl;
        private String companyUrl;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getMemberId() {
            return memberId;
        }

        public void setMemberId(long memberId) {
            this.memberId = memberId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKana() {
            return kana;
        }

        public void setKana(String kana) {
            this.kana = kana;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getGenderChar() {
            return genderChar;
        }

        public void setGenderChar(String genderChar) {
            this.genderChar = genderChar;
        }

        public String getCareer() {
            return career;
        }

        public void setCareer(String career) {
            this.career = career;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getDuty() {
            return duty;
        }

        public void setDuty(String duty) {
            this.duty = duty;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPersonalUrl() {
            return personalUrl;
        }

        public void setPersonalUrl(String personalUrl) {
            this.personalUrl = personalUrl;
        }

        public String getCompanyUrl() {
            return companyUrl;
        }

        public void setCompanyUrl(String companyUrl) {
            this.companyUrl = companyUrl;
        }
    }
}
