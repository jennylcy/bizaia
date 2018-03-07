package com.bizaia.zhongyin.module.mine.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/3/10.
 */

public class CompanyHostBean implements Serializable{


    /**
     * code : 200
     * data : {"id":10000009,"name":"1905","homeUrl":"http://www.1905.com/","avatarUrl":"download/img/1494226655282MAnUb.png","introduction":"","coverUrl":"download/img/1494237273046vklLw.jpg","vodId":10000002,"email":"1905@163.com","telephone":"1233444","h5Url":"http://api.forcetechcloud.com:8090/api/dist/html/homeIntroduce.html?id=10000009","playUrl":"","lecturerIds":"10000005","createTime":1494040282065}
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
         * id : 10000009
         * name : 1905
         * homeUrl : http://www.1905.com/
         * avatarUrl : download/img/1494226655282MAnUb.png
         * introduction :
         * coverUrl : download/img/1494237273046vklLw.jpg
         * vodId : 10000002
         * email : 1905@163.com
         * telephone : 1233444
         * h5Url : http://api.forcetechcloud.com:8090/api/dist/html/homeIntroduce.html?id=10000009
         * playUrl :
         * lecturerIds : 10000005
         * createTime : 1494040282065
         */

        private int id;
        private String name;
        private String homeUrl;
        private String avatarUrl;
        private String introduction;
        private String coverUrl;
        private int vodId;
        private String email;
        private String telephone;
        private String h5Url;
        private String playUrl;
        private String lecturerIds;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public int getVodId() {
            return vodId;
        }

        public void setVodId(int vodId) {
            this.vodId = vodId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getH5Url() {
            return h5Url;
        }

        public void setH5Url(String h5Url) {
            this.h5Url = h5Url;
        }

        public String getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }

        public String getLecturerIds() {
            return lecturerIds;
        }

        public void setLecturerIds(String lecturerIds) {
            this.lecturerIds = lecturerIds;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
