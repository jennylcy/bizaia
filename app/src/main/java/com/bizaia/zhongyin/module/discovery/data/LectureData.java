package com.bizaia.zhongyin.module.discovery.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/3/22.
 */

public class LectureData  implements Serializable{


    /**
     * code : 200
     * data : {"focusData":[{"id":1,"adminId":1,"title":"水电费为服务费","coverUrl":"http://img4.imgtn.bdimg.com/it/u=3432487329,2901563519&fm=23&gp=0.jpg","findId":1,"sort":1,"findType":1}],"listData":{"pageSize":5,"pageNo":1,"totalCount":10,"totalPage":2,"datas":[{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","orgId":1,"category":"教育","subcategory":"数学","country":"中国","province":"四川","lectureTime":1488315619000,"admissionFee":9.99,"orgName":"中樱","h5Url":"http://192.168.4.205:8090/dist/html/news-detail.html?id=1"},{"id":2,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","orgId":1,"category":"教育","subcategory":"数学","country":"中国","province":"四川","lectureTime":1488315619000,"admissionFee":9.99,"orgName":"中樱","h5Url":"http://192.168.4.205:8090/dist/html/news-detail.html?id=2"},{"id":3,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","orgId":1,"category":"教育","subcategory":"数学","country":"中国","province":"四川","lectureTime":1488315619000,"admissionFee":9.99,"orgName":"中樱","h5Url":"http://192.168.4.205:8090/dist/html/news-detail.html?id=3"},{"id":4,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","orgId":1,"category":"教育","subcategory":"数学","country":"中国","province":"四川","lectureTime":1488315619000,"admissionFee":9.99,"orgName":"中樱","h5Url":"http://192.168.4.205:8090/dist/html/news-detail.html?id=4"},{"id":5,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","orgId":1,"category":"教育","subcategory":"数学","country":"中国","province":"四川","lectureTime":1488315619000,"admissionFee":9.99,"orgName":"中樱","h5Url":"http://192.168.4.205:8090/dist/html/news-detail.html?id=5"}]}}
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
         * focusData : [{"id":1,"adminId":1,"title":"水电费为服务费","coverUrl":"http://img4.imgtn.bdimg.com/it/u=3432487329,2901563519&fm=23&gp=0.jpg","findId":1,"sort":1,"findType":1}]
         * listData : {"pageSize":5,"pageNo":1,"totalCount":10,"totalPage":2,"datas":[{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","orgId":1,"category":"教育","subcategory":"数学","country":"中国","province":"四川","lectureTime":1488315619000,"admissionFee":9.99,"orgName":"中樱","h5Url":"http://192.168.4.205:8090/dist/html/news-detail.html?id=1"},{"id":2,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","orgId":1,"category":"教育","subcategory":"数学","country":"中国","province":"四川","lectureTime":1488315619000,"admissionFee":9.99,"orgName":"中樱","h5Url":"http://192.168.4.205:8090/dist/html/news-detail.html?id=2"},{"id":3,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","orgId":1,"category":"教育","subcategory":"数学","country":"中国","province":"四川","lectureTime":1488315619000,"admissionFee":9.99,"orgName":"中樱","h5Url":"http://192.168.4.205:8090/dist/html/news-detail.html?id=3"},{"id":4,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","orgId":1,"category":"教育","subcategory":"数学","country":"中国","province":"四川","lectureTime":1488315619000,"admissionFee":9.99,"orgName":"中樱","h5Url":"http://192.168.4.205:8090/dist/html/news-detail.html?id=4"},{"id":5,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","orgId":1,"category":"教育","subcategory":"数学","country":"中国","province":"四川","lectureTime":1488315619000,"admissionFee":9.99,"orgName":"中樱","h5Url":"http://192.168.4.205:8090/dist/html/news-detail.html?id=5"}]}
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
             * totalCount : 10
             * totalPage : 2
             * datas : [{"id":1,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","orgId":1,"category":"教育","subcategory":"数学","country":"中国","province":"四川","lectureTime":1488315619000,"admissionFee":9.99,"orgName":"中樱","h5Url":"http://192.168.4.205:8090/dist/html/news-detail.html?id=1"},{"id":2,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","orgId":1,"category":"教育","subcategory":"数学","country":"中国","province":"四川","lectureTime":1488315619000,"admissionFee":9.99,"orgName":"中樱","h5Url":"http://192.168.4.205:8090/dist/html/news-detail.html?id=2"},{"id":3,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","orgId":1,"category":"教育","subcategory":"数学","country":"中国","province":"四川","lectureTime":1488315619000,"admissionFee":9.99,"orgName":"中樱","h5Url":"http://192.168.4.205:8090/dist/html/news-detail.html?id=3"},{"id":4,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","orgId":1,"category":"教育","subcategory":"数学","country":"中国","province":"四川","lectureTime":1488315619000,"admissionFee":9.99,"orgName":"中樱","h5Url":"http://192.168.4.205:8090/dist/html/news-detail.html?id=4"},{"id":5,"title":"韩国人如何看三星困局？首尔现场直击！","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","orgId":1,"category":"教育","subcategory":"数学","country":"中国","province":"四川","lectureTime":1488315619000,"admissionFee":9.99,"orgName":"中樱","h5Url":"http://192.168.4.205:8090/dist/html/news-detail.html?id=5"}]
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
                 * orgId : 1
                 * category : 教育
                 * subcategory : 数学
                 * country : 中国
                 * province : 四川
                 * lectureTime : 1488315619000
                 * admissionFee : 9.99
                 * orgName : 中樱
                 * h5Url : http://192.168.4.205:8090/dist/html/news-detail.html?id=1
                 */

                private long id;
                private String title;
                private String coverUrl;
                private long orgId;
                private String category;
                private String subcategory;
                private String country;
                private String province;
                private String createTime;
                private String lectureTime;
                private double admissionFee;
                private String orgName;
                private String h5Url;
                private int type;
                private String deadline;
                private String pageViewNum;
                private int reserveStatus;
                private boolean bought;

                public boolean isBought() {
                    return bought;
                }

                public void setBought(boolean bought) {
                    this.bought = bought;
                }

                public int getReserveStatus() {
                    return reserveStatus;
                }

                public void setReserveStatus(int reserveStatus) {
                    this.reserveStatus = reserveStatus;
                }

                public String getPageViewNum() {
                    return pageViewNum;
                }

                public void setPageViewNum(String pageViewNum) {
                    this.pageViewNum = pageViewNum;
                }

                public String getDeadline() {
                    return deadline;
                }

                public void setDeadline(String deadline) {
                    this.deadline = deadline;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
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

                public long getOrgId() {
                    return orgId;
                }

                public void setOrgId(long orgId) {
                    this.orgId = orgId;
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

        public static class FocusDataBean implements Serializable{
            /**
             * id : 1
             * adminId : 1
             * title : 水电费为服务费
             * coverUrl : http://img4.imgtn.bdimg.com/it/u=3432487329,2901563519&fm=23&gp=0.jpg
             * findId : 1
             * sort : 1
             * findType : 1
             */

            private int id;
            private int adminId;
            private String title;
            private String coverUrl;
            private int findId;
            private int sort;
            private int findType;
            private String h5Url;

            public String getH5Url() {
                return h5Url;
            }

            public void setH5Url(String h5Url) {
                this.h5Url = h5Url;
            }

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
