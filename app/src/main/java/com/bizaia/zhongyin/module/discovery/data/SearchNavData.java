package com.bizaia.zhongyin.module.discovery.data;

import java.util.List;

/**
 * Created by yan on 2017/4/18.
 */

public class SearchNavData {


    /**
     * code : 200
     * data : {"news":[{"id":1,"category":"新闻","subcategory":"外语","productTypeId":1,"contentNum":100,"createTime":1490746607074},{"id":2,"category":"新闻","subcategory":"外语","productTypeId":1,"contentNum":100,"createTime":1490746607074}],"recruit":[{"id":25,"category":"求人","subcategory":"3424","productTypeId":7,"contentNum":0,"createTime":1492403022692},{"id":26,"category":"求人","subcategory":"534534","productTypeId":7,"contentNum":0,"createTime":1492403068782},{"id":9,"category":"求人","subcategory":"外语","productTypeId":7,"contentNum":100,"createTime":1490746607074}],"association":[{"id":23,"category":"社团","subcategory":"呵呵呵呵","productTypeId":6,"contentNum":0,"createTime":1492402314249},{"id":24,"category":"社团","subcategory":"嘻嘻嘻嘻嘻","productTypeId":6,"contentNum":0,"createTime":1492402331125},{"id":22,"category":"社团","subcategory":"嚯嚯嚯","productTypeId":6,"contentNum":0,"createTime":1492402294078},{"id":8,"category":"社团","subcategory":"外语","productTypeId":6,"contentNum":100,"createTime":1490746607074},{"id":28,"category":"社团","subcategory":"摄影","productTypeId":6,"contentNum":100,"createTime":1492482421000},{"id":29,"category":"社团","subcategory":"舞蹈","productTypeId":6,"contentNum":100,"createTime":1492482578000}],"lecture":[{"id":27,"category":"讲座","subcategory":"中文","productTypeId":5,"contentNum":100,"createTime":1492481448000},{"id":7,"category":"讲座","subcategory":"外语","productTypeId":5,"contentNum":100,"createTime":1490746607074}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<NewsBean> news;
        private List<RecruitBean> recruit;
        private List<AssociationBean> association;
        private List<LectureBean> lecture;

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public List<RecruitBean> getRecruit() {
            return recruit;
        }

        public void setRecruit(List<RecruitBean> recruit) {
            this.recruit = recruit;
        }

        public List<AssociationBean> getAssociation() {
            return association;
        }

        public void setAssociation(List<AssociationBean> association) {
            this.association = association;
        }

        public List<LectureBean> getLecture() {
            return lecture;
        }

        public void setLecture(List<LectureBean> lecture) {
            this.lecture = lecture;
        }

        public static class NewsBean {
            /**
             * id : 1
             * category : 新闻
             * subcategory : 外语
             * productTypeId : 1
             * contentNum : 100
             * createTime : 1490746607074
             */

            private int id;
            private String category;
            private String subcategory;
            private int productTypeId;
            private int contentNum;
            private String createTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public int getProductTypeId() {
                return productTypeId;
            }

            public void setProductTypeId(int productTypeId) {
                this.productTypeId = productTypeId;
            }

            public int getContentNum() {
                return contentNum;
            }

            public void setContentNum(int contentNum) {
                this.contentNum = contentNum;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

        public static class RecruitBean {
            /**
             * id : 25
             * category : 求人
             * subcategory : 3424
             * productTypeId : 7
             * contentNum : 0
             * createTime : 1492403022692
             */

            private int id;
            private String category;
            private String subcategory;
            private int productTypeId;
            private int contentNum;
            private String createTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public int getProductTypeId() {
                return productTypeId;
            }

            public void setProductTypeId(int productTypeId) {
                this.productTypeId = productTypeId;
            }

            public int getContentNum() {
                return contentNum;
            }

            public void setContentNum(int contentNum) {
                this.contentNum = contentNum;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

        public static class AssociationBean {
            /**
             * id : 23
             * category : 社团
             * subcategory : 呵呵呵呵
             * productTypeId : 6
             * contentNum : 0
             * createTime : 1492402314249
             */

            private int id;
            private String category;
            private String subcategory;
            private int productTypeId;
            private int contentNum;
            private String createTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public int getProductTypeId() {
                return productTypeId;
            }

            public void setProductTypeId(int productTypeId) {
                this.productTypeId = productTypeId;
            }

            public int getContentNum() {
                return contentNum;
            }

            public void setContentNum(int contentNum) {
                this.contentNum = contentNum;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

        public static class LectureBean {
            /**
             * id : 27
             * category : 讲座
             * subcategory : 中文
             * productTypeId : 5
             * contentNum : 100
             * createTime : 1492481448000
             */

            private int id;
            private String category;
            private String subcategory;
            private int productTypeId;
            private int contentNum;
            private String createTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public int getProductTypeId() {
                return productTypeId;
            }

            public void setProductTypeId(int productTypeId) {
                this.productTypeId = productTypeId;
            }

            public int getContentNum() {
                return contentNum;
            }

            public void setContentNum(int contentNum) {
                this.contentNum = contentNum;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
