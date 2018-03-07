package com.bizaia.zhongyin.module.discovery.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/3/6.
 */

public class RecommendNewsData implements Serializable {

    /**
     * code : 200
     * data : {"focusData":[{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"},{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"},{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"}],"listData":{"pageSize":5,"pageNo":1,"totalCount":8,"totalPage":2,"datas":[{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"},{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"},{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"},{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"},{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"}]}}
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

    public static class DataBean implements Serializable{
        /**
         * focusData : [{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"},{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"},{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"}]
         * listData : {"pageSize":5,"pageNo":1,"totalCount":8,"totalPage":2,"datas":[{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"},{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"},{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"},{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"},{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"}]}
         */

        private ListDataBean listData;
        private List<FocusDataBean> focusData;

        public ListDataBean getListData() {
            return listData;
        }

        public void setListData(ListDataBean listData) {
            this.listData = listData;
        }

        public List<FocusDataBean> getFocusData() {
            return focusData;
        }

        public void setFocusData(List<FocusDataBean> focusData) {
            this.focusData = focusData;
        }

        public static class ListDataBean implements Serializable{
            /**
             * pageSize : 5
             * pageNo : 1
             * totalCount : 8
             * totalPage : 2
             * datas : [{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"},{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"},{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"},{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"},{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","h5Url":"http://192.168.4.205:8091/newsDetails.html?newsId=1"}]
             */

            private int pageSize;
            private int pageNo;
            private int totalCount;
            private int totalPage;
            private List<DatasBean> datas;

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

            public List<DatasBean> getDatas() {
                return datas;
            }

            public void setDatas(List<DatasBean> datas) {
                this.datas = datas;
            }

            public static class DatasBean implements Serializable{
                /**
                 * id : 1
                 * title : 韩国人如何看三星困局？首尔现场直击！
                 * coverUrl : http://img02.tooopen.com/images/20140504/sy_60294738471.jpg
                 * h5Url : http://192.168.4.205:8091/newsDetails.html?newsId=1
                 */

                private long id;
                private String title;
                private String coverUrl;
                private String h5Url;

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

                public String getH5Url() {
                    return h5Url;
                }

                public void setH5Url(String h5Url) {
                    this.h5Url = h5Url;
                }
            }
        }

        public static class FocusDataBean implements Serializable{

            /**
             * id : 17
             * adminId : 1
             * title : 12131
             * coverUrl : download/img/1491648281809FIZrx.jpg
             * findId : 23
             * h5Url : 1
             * createTime : 1491644680000
             * sort : 100
             * findType : 2
             */

            private int id;
            private int adminId;
            private String title;
            private String coverUrl;
            private int findId;
            private String h5Url;
            private long createTime;
            private int sort;
            private int findType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getAdminId() {
                return adminId;
            }

            public void setAdminId(int adminId) {
                this.adminId = adminId;
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

            public int getFindId() {
                return findId;
            }

            public void setFindId(int findId) {
                this.findId = findId;
            }

            public String getH5Url() {
                return h5Url;
            }

            public void setH5Url(String h5Url) {
                this.h5Url = h5Url;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getFindType() {
                return findType;
            }

            public void setFindType(int findType) {
                this.findType = findType;
            }
        }
    }
}