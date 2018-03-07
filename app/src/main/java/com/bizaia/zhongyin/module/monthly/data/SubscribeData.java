package com.bizaia.zhongyin.module.monthly.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/3/11.
 */

public class SubscribeData  implements Serializable {

    /**
     * code : 200
     * data : {"pageSize":5,"pageNo":1,"totalCount":4,"totalPage":1,"datas":[{"id":10000005,"title":"单月刊","price":0.01,"monthNum":1,"createTime":1493851126166},{"id":10000004,"title":"季度刊","price":0.01,"monthNum":3,"createTime":1493851102408},{"id":10000002,"title":"半年刊","price":6,"monthNum":6,"createTime":1493706927902},{"id":10000003,"title":"年刊","price":12,"monthNum":12,"createTime":1493706963096}]}
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

    public static class DataBean  implements Serializable{
        /**
         * pageSize : 5
         * pageNo : 1
         * totalCount : 4
         * totalPage : 1
         * datas : [{"id":10000005,"title":"单月刊","price":0.01,"monthNum":1,"createTime":1493851126166},{"id":10000004,"title":"季度刊","price":0.01,"monthNum":3,"createTime":1493851102408},{"id":10000002,"title":"半年刊","price":6,"monthNum":6,"createTime":1493706927902},{"id":10000003,"title":"年刊","price":12,"monthNum":12,"createTime":1493706963096}]
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
             * id : 10000005
             * title : 单月刊
             * price : 0.01
             * monthNum : 1
             * createTime : 1493851126166
             */

            private int id;
            private String title;
            private double price;
            private int monthNum;
            private String createTime;

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

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getMonthNum() {
                return monthNum;
            }

            public void setMonthNum(int monthNum) {
                this.monthNum = monthNum;
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
