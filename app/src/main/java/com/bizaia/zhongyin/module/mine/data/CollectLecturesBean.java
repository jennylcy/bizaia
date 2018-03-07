package com.bizaia.zhongyin.module.mine.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2017/4/25.
 */

public class CollectLecturesBean implements Serializable{


    /**
     * code : 200
     * data : {"pageSize":10,"pageNo":1,"totalCount":2,"totalPage":1,"datas":[{"id":16,"title":"股权分配及员工激励的法律风险防控","coverUrl":"https://img3.doubanio.com/view/event_poster/large/public/0edca844f589cdd.jpg","category":"讲座","subcategory":"中文","country":"中国","province":"四川","lectureTime":1492167600000,"admissionFee":30,"createTime":1491963150000,"orgName":"豆瓣同城","h5Url":"http://192.168.4.205:8090/dist/html/lectureDetail.html?id=16"},{"id":19,"title":"聚焦热点 高能商管讲座等你来-香港大学SPACE中国商业学院2017开放日","coverUrl":"https://img1.doubanio.com/view/event_poster/large/public/b269da1a98e74c7.jpg","category":"讲座","subcategory":"中文","country":"中国","province":"四川","lectureTime":1492758000000,"admissionFee":0,"createTime":1492478369000,"orgName":"a中樱","h5Url":"http://192.168.4.205:8090/dist/html/lectureDetail.html?id=19"}]}
     */

    private int code;
    /**
     * pageSize : 10
     * pageNo : 1
     * totalCount : 2
     * totalPage : 1
     * datas : [{"id":16,"title":"股权分配及员工激励的法律风险防控","coverUrl":"https://img3.doubanio.com/view/event_poster/large/public/0edca844f589cdd.jpg","category":"讲座","subcategory":"中文","country":"中国","province":"四川","lectureTime":1492167600000,"admissionFee":30,"createTime":1491963150000,"orgName":"豆瓣同城","h5Url":"http://192.168.4.205:8090/dist/html/lectureDetail.html?id=16"},{"id":19,"title":"聚焦热点 高能商管讲座等你来-香港大学SPACE中国商业学院2017开放日","coverUrl":"https://img1.doubanio.com/view/event_poster/large/public/b269da1a98e74c7.jpg","category":"讲座","subcategory":"中文","country":"中国","province":"四川","lectureTime":1492758000000,"admissionFee":0,"createTime":1492478369000,"orgName":"a中樱","h5Url":"http://192.168.4.205:8090/dist/html/lectureDetail.html?id=19"}]
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
         * id : 16
         * title : 股权分配及员工激励的法律风险防控
         * coverUrl : https://img3.doubanio.com/view/event_poster/large/public/0edca844f589cdd.jpg
         * category : 讲座
         * subcategory : 中文
         * country : 中国
         * province : 四川
         * lectureTime : 1492167600000
         * admissionFee : 30
         * createTime : 1491963150000
         * orgName : 豆瓣同城
         * h5Url : http://192.168.4.205:8090/dist/html/lectureDetail.html?id=16
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
            private int id;
            private String title;
            private String coverUrl;
            private String category;
            private String subcategory;
            private String country;
            private String province;
            private String lectureTime;
            private int admissionFee;
            private String createTime;
            private String orgName;
            private String h5Url;
            private long orgId;

            public long getOrgId() {
                return orgId;
            }

            public void setOrgId(long orgId) {
                this.orgId = orgId;
            }

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

            public int getAdmissionFee() {
                return admissionFee;
            }

            public void setAdmissionFee(int admissionFee) {
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
        }
    }
}
