package com.bizaia.zhongyin.module.pay.data;

import java.io.Serializable;

/**
 * Created by yan on 2017/4/17.
 */

public class OrderData implements Serializable {

    /**
     * code : 200
     * data : {"wallet":{"id":19,"uid":10000029,"balance":100000,"createTime":1492418972000},"order":{"orderNum":"149242441697690029","uid":10000029,"productId":12,"productTitle":"3234234","productType":1,"status":0,"price":34}}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * wallet : {"id":19,"uid":10000029,"balance":100000,"createTime":1492418972000}
         * order : {"orderNum":"149242441697690029","uid":10000029,"productId":12,"productTitle":"3234234","productType":1,"status":0,"price":34}
         */

        private WalletBean wallet;
        private OrderBean order;

        public WalletBean getWallet() {
            return wallet;
        }

        public void setWallet(WalletBean wallet) {
            this.wallet = wallet;
        }

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public static class WalletBean implements Serializable {
            /**
             * id : 19
             * uid : 10000029
             * balance : 100000.0
             * createTime : 1492418972000
             */

            private int id;
            private int uid;
            private double balance;
            private String createTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public double getBalance() {
                return balance;
            }

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

        public static class OrderBean implements Serializable {
            /**
             * orderNum : 149242441697690029
             * uid : 10000029
             * productId : 12
             * productTitle : 3234234
             * productType : 1
             * status : 0
             * price : 34.0
             */

            private String orderNum;
            private int uid;
            private long productId;
            private String productTitle;
            private int productType;
            private int status;
            private double price;

            public String getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public long getProductId() {
                return productId;
            }

            public void setProductId(long productId) {
                this.productId = productId;
            }

            public String getProductTitle() {
                return productTitle;
            }

            public void setProductTitle(String productTitle) {
                this.productTitle = productTitle;
            }

            public int getProductType() {
                return productType;
            }

            public void setProductType(int productType) {
                this.productType = productType;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }
        }
    }
}
