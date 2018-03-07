package com.bizaia.zhongyin.module.mine.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2017/3/14.
 */

public class BillBean implements Serializable{


    /**
     * code : 200
     * data : {"tradeRecordList":{"pageSize":10,"pageNo":1,"totalCount":9,"totalPage":1,"datas":[{"id":8,"title":"视频","price":15,"createTime":1489389278000,"payMethodName":"余额","type":1},{"id":6,"title":"月刊","price":15,"createTime":1489043887000,"payMethodName":"余额","type":0},{"id":9,"title":"直播","price":15,"createTime":1489043887000,"payMethodName":"余额","type":0},{"id":7,"title":"直播","price":15,"createTime":1489043887000,"payMethodName":"余额","type":0},{"id":5,"title":"月刊","price":15,"createTime":1489043718000,"payMethodName":"余额","type":0},{"id":4,"title":"月刊","price":15,"createTime":1489042344000,"payMethodName":"余额","type":0},{"id":3,"title":"韩国人如何看三星困局？首尔现场直击！","price":9.99,"createTime":1489042335000,"payMethodName":"余额","type":0},{"id":2,"title":"韩国人如何看三星困局？首尔现场直击！","price":9.99,"createTime":1489042330000,"payMethodName":"余额","type":0},{"id":1,"title":"测试","price":9.99,"createTime":1488623324000,"payMethodName":"余额","type":0}]},"balance":925.03}
     */

    private int code;
    /**
     * tradeRecordList : {"pageSize":10,"pageNo":1,"totalCount":9,"totalPage":1,"datas":[{"id":8,"title":"视频","price":15,"createTime":1489389278000,"payMethodName":"余额","type":1},{"id":6,"title":"月刊","price":15,"createTime":1489043887000,"payMethodName":"余额","type":0},{"id":9,"title":"直播","price":15,"createTime":1489043887000,"payMethodName":"余额","type":0},{"id":7,"title":"直播","price":15,"createTime":1489043887000,"payMethodName":"余额","type":0},{"id":5,"title":"月刊","price":15,"createTime":1489043718000,"payMethodName":"余额","type":0},{"id":4,"title":"月刊","price":15,"createTime":1489042344000,"payMethodName":"余额","type":0},{"id":3,"title":"韩国人如何看三星困局？首尔现场直击！","price":9.99,"createTime":1489042335000,"payMethodName":"余额","type":0},{"id":2,"title":"韩国人如何看三星困局？首尔现场直击！","price":9.99,"createTime":1489042330000,"payMethodName":"余额","type":0},{"id":1,"title":"测试","price":9.99,"createTime":1488623324000,"payMethodName":"余额","type":0}]}
     * balance : 925.03
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
        /**
         * pageSize : 10
         * pageNo : 1
         * totalCount : 9
         * totalPage : 1
         * datas : [{"id":8,"title":"视频","price":15,"createTime":1489389278000,"payMethodName":"余额","type":1},{"id":6,"title":"月刊","price":15,"createTime":1489043887000,"payMethodName":"余额","type":0},{"id":9,"title":"直播","price":15,"createTime":1489043887000,"payMethodName":"余额","type":0},{"id":7,"title":"直播","price":15,"createTime":1489043887000,"payMethodName":"余额","type":0},{"id":5,"title":"月刊","price":15,"createTime":1489043718000,"payMethodName":"余额","type":0},{"id":4,"title":"月刊","price":15,"createTime":1489042344000,"payMethodName":"余额","type":0},{"id":3,"title":"韩国人如何看三星困局？首尔现场直击！","price":9.99,"createTime":1489042335000,"payMethodName":"余额","type":0},{"id":2,"title":"韩国人如何看三星困局？首尔现场直击！","price":9.99,"createTime":1489042330000,"payMethodName":"余额","type":0},{"id":1,"title":"测试","price":9.99,"createTime":1488623324000,"payMethodName":"余额","type":0}]
         */

        private TradeRecordListEntity tradeRecordList;
        private double balance;

        public TradeRecordListEntity getTradeRecordList() {
            return tradeRecordList;
        }

        public void setTradeRecordList(TradeRecordListEntity tradeRecordList) {
            this.tradeRecordList = tradeRecordList;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public static class TradeRecordListEntity implements Serializable{
            private int pageSize;
            private int pageNo;
            private int totalCount;
            private int totalPage;
            /**
             * id : 8
             * title : 视频
             * price : 15.0
             * createTime : 1489389278000
             * payMethodName : 余额
             * type : 1
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
                private double price;
                private String createTime;
                private String payMethodName;
                private int type;

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

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String  createTime) {
                    this.createTime = createTime;
                }

                public String getPayMethodName() {
                    return payMethodName;
                }

                public void setPayMethodName(String payMethodName) {
                    this.payMethodName = payMethodName;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }
            }
        }
    }
}
