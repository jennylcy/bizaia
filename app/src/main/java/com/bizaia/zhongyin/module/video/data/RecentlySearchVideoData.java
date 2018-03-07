package com.bizaia.zhongyin.module.video.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/3/3.
 */

public class RecentlySearchVideoData implements Serializable {

    /**
     * code : 200
     * data : {"pageSize":10,"pageNo":1,"totalCount":0,"totalPage":0,"datas":[{"id":10001670,"title":"6.8直播","coverUrl":"download/img/1496888919990HgYQx.jpg","orgId":10000009,"cateId":10000042,"category":"点播","subcategory":"コンサルタント業","areaId":10000006,"country":"中国","province":"上海","price":0.01,"lecturers":"宋","introduction":"在当今这个世界里，充满着各种机会，但是机会都是销纵即逝的，一旦有了机会，就应该及时把握，就要果断决策，勇敢地去行动，而不能犹豫不决，否则的话，你就只能永远站在那里看着别人成功。","createTime":"2017-06-08 02:28:25","status":3,"playUrl":"","buyNum":1,"type":1,"orgName":"1905","avatarUrl":"download/img/1494226655282MAnUb.png","isDel":false,"liveId":10000133,"shareUrl":"https://api.bizaia.com/api/share/html/demandShare.html?id=10001670","offStatus":2,"expireDate":"2017-06-09","pageViewNum":45,"del":false}]}
     */

    private int code;
    private DataEntity data;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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

    public static class DataEntity {
        /**
         * pageSize : 10
         * pageNo : 1
         * totalCount : 0
         * totalPage : 0
         * datas : [{"id":10001670,"title":"6.8直播","coverUrl":"download/img/1496888919990HgYQx.jpg","orgId":10000009,"cateId":10000042,"category":"点播","subcategory":"コンサルタント業","areaId":10000006,"country":"中国","province":"上海","price":0.01,"lecturers":"宋","introduction":"在当今这个世界里，充满着各种机会，但是机会都是销纵即逝的，一旦有了机会，就应该及时把握，就要果断决策，勇敢地去行动，而不能犹豫不决，否则的话，你就只能永远站在那里看着别人成功。","createTime":"2017-06-08 02:28:25","status":3,"playUrl":"","buyNum":1,"type":1,"orgName":"1905","avatarUrl":"download/img/1494226655282MAnUb.png","isDel":false,"liveId":10000133,"shareUrl":"https://api.bizaia.com/api/share/html/demandShare.html?id=10001670","offStatus":2,"expireDate":"2017-06-09","pageViewNum":45,"del":false}]
         */

        private int pageSize;
        private int pageNo;
        private int totalCount;
        private int totalPage;
        private List<DatasEntity> datas;

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<DatasEntity> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasEntity> datas) {
            this.datas = datas;
        }

        public static class DatasEntity {
            /**
             * id : 10001670
             * title : 6.8直播
             * coverUrl : download/img/1496888919990HgYQx.jpg
             * orgId : 10000009
             * cateId : 10000042
             * category : 点播
             * subcategory : コンサルタント業
             * areaId : 10000006
             * country : 中国
             * province : 上海
             * price : 0.01
             * lecturers : 宋
             * introduction : 在当今这个世界里，充满着各种机会，但是机会都是销纵即逝的，一旦有了机会，就应该及时把握，就要果断决策，勇敢地去行动，而不能犹豫不决，否则的话，你就只能永远站在那里看着别人成功。
             * createTime : 2017-06-08 02:28:25
             * status : 3
             * playUrl :
             * buyNum : 1
             * type : 1
             * orgName : 1905
             * avatarUrl : download/img/1494226655282MAnUb.png
             * isDel : false
             * liveId : 10000133
             * shareUrl : https://api.bizaia.com/api/share/html/demandShare.html?id=10001670
             * offStatus : 2
             * expireDate : 2017-06-09
             * pageViewNum : 45
             * del : false
             */

            private long id;
            private String title;
            private String coverUrl;
            private long orgId;
            private long cateId;
            private String category;
            private String subcategory;
            private int areaId;
            private String country;
            private String province;
            private double price;
            private String lecturers;
            private String introduction;
            private String createTime;
            private int status;
            private String playUrl;
            private int buyNum;
            private int type;
            private String orgName;
            private String avatarUrl;
            private boolean isDel;
            private long liveId;
            private String shareUrl;
            private int offStatus;
            private String expireDate;
            private int pageViewNum;
            private boolean del;

            public long getId() {
                return id;
            }

            public void setId(long id) {
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

            public long getOrgId() {
                return orgId;
            }

            public void setOrgId(long orgId) {
                this.orgId = orgId;
            }

            public long getCateId() {
                return cateId;
            }

            public void setCateId(long cateId) {
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

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
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

            public String getPlayUrl() {
                return playUrl;
            }

            public void setPlayUrl(String playUrl) {
                this.playUrl = playUrl;
            }

            public int getBuyNum() {
                return buyNum;
            }

            public void setBuyNum(int buyNum) {
                this.buyNum = buyNum;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public boolean isIsDel() {
                return isDel;
            }

            public void setIsDel(boolean isDel) {
                this.isDel = isDel;
            }

            public long getLiveId() {
                return liveId;
            }

            public void setLiveId(long liveId) {
                this.liveId = liveId;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public int getOffStatus() {
                return offStatus;
            }

            public void setOffStatus(int offStatus) {
                this.offStatus = offStatus;
            }

            public String getExpireDate() {
                return expireDate;
            }

            public void setExpireDate(String expireDate) {
                this.expireDate = expireDate;
            }

            public int getPageViewNum() {
                return pageViewNum;
            }

            public void setPageViewNum(int pageViewNum) {
                this.pageViewNum = pageViewNum;
            }

            public boolean isDel() {
                return del;
            }

            public void setDel(boolean del) {
                this.del = del;
            }
        }
    }
}
