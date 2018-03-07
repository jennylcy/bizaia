package com.bizaia.zhongyin.module.pay.data;

import java.io.Serializable;

/**
 * Created by yan on 2017/4/21.
 */

public class RemainPayData implements Serializable{

    /**
     * code : 200
     * data : {"id":432,"orderNum":"149275113103950037","uid":10000037,"productId":11,"productTitle":"共享经济改变生活","productType":3,"status":1,"createTime":1492747542000,"payTime":1492751137840,"payPrice":10,"payMethodId":1,"isSettle":0,"price":10}
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

    public static class DataBean implements Serializable{
        /**
         * id : 432
         * orderNum : 149275113103950037
         * uid : 10000037
         * productId : 11
         * productTitle : 共享经济改变生活
         * productType : 3
         * status : 1
         * createTime : 1492747542000
         * payTime : 1492751137840
         * payPrice : 10.0
         * payMethodId : 1
         * isSettle : 0
         * price : 10.0
         */

        private int id;
        private String orderNum;
        private int uid;
        private int productId;
        private String productTitle;
        private int productType;
        private int status;
        private String createTime;
        private String payTime;
        private double payPrice;
        private int payMethodId;
        private int isSettle;
        private double price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public double getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(double payPrice) {
            this.payPrice = payPrice;
        }

        public int getPayMethodId() {
            return payMethodId;
        }

        public void setPayMethodId(int payMethodId) {
            this.payMethodId = payMethodId;
        }

        public int getIsSettle() {
            return isSettle;
        }

        public void setIsSettle(int isSettle) {
            this.isSettle = isSettle;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
