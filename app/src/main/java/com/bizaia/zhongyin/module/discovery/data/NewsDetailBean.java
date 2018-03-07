package com.bizaia.zhongyin.module.discovery.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/3/14.
 */

public class NewsDetailBean implements Serializable{


    /**
     * code : 200
     * data : {"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","adminId":1,"orgId":1,"cateId":1,"category":"教育","subcategory":"数学","areaId":10000,"country":"中国","province":"四川","lectureTime":1488268819000,"deadline":1488268819000,"venueAddress":"会场地址","numLimit":10,"admissionFee":9.99,"lectureData":"IT行业的 弊端","lecturers":"test，test1","introduction":"介绍一个 撒地方撒地方胜多负少地方","reprintAddress":"转载地址","createTime":1488263116000,"status":2,"orgName":"中樱","homeUrl":"www.baidu.com","avatarUrl":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/news-detail.html?id=1"}
     */

    private int code;
    /**
     * id : 1
     * title : 韩国人如何看三星困局？首尔现场直击！
     * coverUrl : http://img02.tooopen.com/images/20140504/sy_60294738471.jpg
     * adminId : 1
     * orgId : 1
     * cateId : 1
     * category : 教育
     * subcategory : 数学
     * areaId : 10000
     * country : 中国
     * province : 四川
     * lectureTime : 1488268819000
     * deadline : 1488268819000
     * venueAddress : 会场地址
     * numLimit : 10
     * admissionFee : 9.99
     * lectureData : IT行业的 弊端
     * lecturers : test，test1
     * introduction : 介绍一个 撒地方撒地方胜多负少地方
     * reprintAddress : 转载地址
     * createTime : 1488263116000
     * status : 2
     * orgName : 中樱
     * homeUrl : www.baidu.com
     * avatarUrl : http://up.qqjia.com/z/01/tu4488_33.jpg
     * h5Url : http://192.168.4.205:8090/dist/html/news-detail.html?id=1
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
        private int id;
        private String title;
        private String coverUrl;
        private int adminId;
        private int orgId;
        private int cateId;
        private String category;
        private String subcategory;
        private int areaId;
        private String country;
        private String province;
        private long lectureTime;
        private long deadline;
        private String venueAddress;
        private int numLimit;
        private double admissionFee;
        private String lectureData;
        private String lecturers;
        private String introduction;
        private String reprintAddress;
        private String createTime;
        private int status;
        private String orgName;
        private String homeUrl;
        private String avatarUrl;
        private String h5Url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public int getAdminId() {
            return adminId;
        }

        public void setAdminId(int adminId) {
            this.adminId = adminId;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public int getCateId() {
            return cateId;
        }

        public void setCateId(int cateId) {
            this.cateId = cateId;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSubcategory() {
            return subcategory;
        }

        public void setSubcategory(String subcategory) {
            this.subcategory = subcategory;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public long getLectureTime() {
            return lectureTime;
        }

        public void setLectureTime(long lectureTime) {
            this.lectureTime = lectureTime;
        }

        public long getDeadline() {
            return deadline;
        }

        public void setDeadline(long deadline) {
            this.deadline = deadline;
        }

        public String getVenueAddress() {
            return venueAddress;
        }

        public void setVenueAddress(String venueAddress) {
            this.venueAddress = venueAddress;
        }

        public int getNumLimit() {
            return numLimit;
        }

        public void setNumLimit(int numLimit) {
            this.numLimit = numLimit;
        }

        public double getAdmissionFee() {
            return admissionFee;
        }

        public void setAdmissionFee(double admissionFee) {
            this.admissionFee = admissionFee;
        }

        public String getLectureData() {
            return lectureData;
        }

        public void setLectureData(String lectureData) {
            this.lectureData = lectureData;
        }

        public String getLecturers() {
            return lecturers;
        }

        public void setLecturers(String lecturers) {
            this.lecturers = lecturers;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getReprintAddress() {
            return reprintAddress;
        }

        public void setReprintAddress(String reprintAddress) {
            this.reprintAddress = reprintAddress;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getHomeUrl() {
            return homeUrl;
        }

        public void setHomeUrl(String homeUrl) {
            this.homeUrl = homeUrl;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getH5Url() {
            return h5Url;
        }

        public void setH5Url(String h5Url) {
            this.h5Url = h5Url;
        }
    }
}
