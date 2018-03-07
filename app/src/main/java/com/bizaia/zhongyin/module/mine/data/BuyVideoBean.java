package com.bizaia.zhongyin.module.mine.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2017/3/14.
 */

public class BuyVideoBean implements Serializable{


    /**
     * code : 200
     * data : {"pageSize":5,"pageNo":1,"totalCount":1,"totalPage":1,"datas":[{"id":1,"title":"金融科技Fintech如何创新并影响生活？","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","category":"教育","subcategory":"数学","country":"中国","province":"四川","price":10,"lecturers":"史珍香，史浩驰","buyNum":100,"type":1,"orgName":"中樱"}]}
     */

    private int code;
    /**
     * pageSize : 5
     * pageNo : 1
     * totalCount : 1
     * totalPage : 1
     * datas : [{"id":1,"title":"金融科技Fintech如何创新并影响生活？","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","category":"教育","subcategory":"数学","country":"中国","province":"四川","price":10,"lecturers":"史珍香，史浩驰","buyNum":100,"type":1,"orgName":"中樱"}]
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
        private int pageSize;
        private int pageNo;
        private int totalCount;
        private int totalPage;
        /**
         * id : 1
         * title : 金融科技Fintech如何创新并影响生活？
         * coverUrl : http://img02.tooopen.com/images/20140504/sy_60294738471.jpg
         * category : 教育
         * subcategory : 数学
         * country : 中国
         * province : 四川
         * price : 10
         * lecturers : 史珍香，史浩驰
         * buyNum : 100
         * type : 1
         * orgName : 中樱
         */

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

        public static class DatasEntity implements Serializable{
            private long id;
            private String title;
            private String coverUrl;
            private String category;
            private String subcategory;
            private String country;
            private String province;
            private double price;
            private String lecturers;
            private int buyNum;
            private int type;
            private String orgName;
            private String avatarUrl;
            private String expireDate;
            private String pageViewNum;

            private boolean isEdit;

            public boolean isEdit() {
                return isEdit;
            }

            public void setEdit(boolean edit) {
                isEdit = edit;
            }

            public String getPageViewNum() {
                return pageViewNum;
            }

            public void setPageViewNum(String pageViewNum) {
                this.pageViewNum = pageViewNum;
            }

            public String getExpireDate() {
                return expireDate;
            }

            public void setExpireDate(String expireDate) {
                this.expireDate = expireDate;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

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
        }
    }
}
