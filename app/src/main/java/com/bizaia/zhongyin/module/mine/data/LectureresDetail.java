package com.bizaia.zhongyin.module.mine.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/6/2.
 */

public class LectureresDetail implements Serializable{


    /**
     * code : 200
     * data : {"id":10000008,"title":"城市《欢乐颂》\u2014\u2014探索城市更新的破局之路【剖面计划】第15期","coverUrl":"download/img/1496197338595wRCWp.jpg","category":"分享会","subcategory":"音乐分享","country":"中国","province":"北京","lectureTime":"2017-05-31 10:00:00","admissionFee":0.01,"createTime":"2017-05-31 03:26:24","orgName":"1905","h5Url":"http://api.forcetechcloud.com:8090/api/dist/html/lectureDetail.html?id=10000008","shareUrl":"http://api.forcetechcloud.com:8090/api/share/html/lectureDetail.html?id=10000008","orderNum":"149621972815480091","type":1,"pageViewNum":34,"adminId":10000000000001,"orgId":10000000000001,"introduction":""}
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
         * id : 10000008
         * title : 城市《欢乐颂》——探索城市更新的破局之路【剖面计划】第15期
         * coverUrl : download/img/1496197338595wRCWp.jpg
         * category : 分享会
         * subcategory : 音乐分享
         * country : 中国
         * province : 北京
         * lectureTime : 2017-05-31 10:00:00
         * admissionFee : 0.01
         * createTime : 2017-05-31 03:26:24
         * orgName : 1905
         * h5Url : http://api.forcetechcloud.com:8090/api/dist/html/lectureDetail.html?id=10000008
         * shareUrl : http://api.forcetechcloud.com:8090/api/share/html/lectureDetail.html?id=10000008
         * orderNum : 149621972815480091
         * type : 1
         * pageViewNum : 34
         * adminId : 10000000000001
         * orgId : 10000000000001
         * introduction :
         */

        private int id;
        private String title;
        private String coverUrl;
        private String category;
        private String subcategory;
        private String country;
        private String province;
        private String lectureTime;
        private double admissionFee;
        private String createTime;
        private String orgName;
        private String h5Url;
        private String shareUrl;
        private String orderNum;
        private int type;
        private int pageViewNum;
        private long adminId;
        private long orgId;
        private String introduction;

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

        public String getLectureTime() {
            return lectureTime;
        }

        public void setLectureTime(String lectureTime) {
            this.lectureTime = lectureTime;
        }

        public double getAdmissionFee() {
            return admissionFee;
        }

        public void setAdmissionFee(double admissionFee) {
            this.admissionFee = admissionFee;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getH5Url() {
            return h5Url;
        }

        public void setH5Url(String h5Url) {
            this.h5Url = h5Url;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPageViewNum() {
            return pageViewNum;
        }

        public void setPageViewNum(int pageViewNum) {
            this.pageViewNum = pageViewNum;
        }

        public long getAdminId() {
            return adminId;
        }

        public void setAdminId(long adminId) {
            this.adminId = adminId;
        }

        public long getOrgId() {
            return orgId;
        }

        public void setOrgId(long orgId) {
            this.orgId = orgId;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }
    }
}
