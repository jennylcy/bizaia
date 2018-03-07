package com.bizaia.zhongyin.module.pay.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2017/6/15.
 */

public class ExchangeRates implements Serializable{


    /**
     * code : 200
     * data : [{"id":10000000,"currencyName":"人民币","currencySymbol":"CNY","payMethodId":"3","exchangeRate":16.013,"createTime":"2017-06-08 09:08:34","updateTime":"2017-06-14 07:15:52"},{"id":10000001,"currencyName":"泰铢","currencySymbol":"THB","payMethodId":"1,3,7","exchangeRate":3.2287,"createTime":"2017-06-08 17:25:47","updateTime":"2017-06-09 11:40:26"},{"id":10000003,"currencyName":"港币","currencySymbol":"HKD","payMethodId":"1,3,7","exchangeRate":14.1408,"createTime":"2017-06-09 11:33:03","updateTime":"2017-06-09 11:35:58"},{"id":10000005,"currencyName":"新加坡元","currencySymbol":"SND","payMethodId":"1,3,4","exchangeRate":79.713,"createTime":"2017-06-09 09:30:40","updateTime":"2017-06-09 09:30:40"},{"id":10000012,"currencyName":"日元","currencySymbol":"JPY","payMethodId":"1,3,4,6,7","exchangeRate":1,"createTime":"2017-06-12 09:05:23","updateTime":"2017-06-12 09:05:23"},{"id":10000013,"currencyName":"美元","currencySymbol":"USD","payMethodId":"1,3","exchangeRate":110.06,"createTime":"2017-06-14 07:14:19","updateTime":"2017-06-14 07:15:46"}]
     */

    private int code;
    private String msg;
    private List<DataEntity> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataEntity implements Serializable{
        /**
         * id : 10000000
         * currencyName : 人民币
         * currencySymbol : CNY
         * payMethodId : 3
         * exchangeRate : 16.013
         * createTime : 2017-06-08 09:08:34
         * updateTime : 2017-06-14 07:15:52
         */

        private int id;
        private String currencyName;
        private String currencySymbol;
        private String payMethodId;
        private double exchangeRate;
        private String createTime;
        private String updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCurrencyName() {
            return currencyName;
        }

        public void setCurrencyName(String currencyName) {
            this.currencyName = currencyName;
        }

        public String getCurrencySymbol() {
            return currencySymbol;
        }

        public void setCurrencySymbol(String currencySymbol) {
            this.currencySymbol = currencySymbol;
        }

        public String getPayMethodId() {
            return payMethodId;
        }

        public void setPayMethodId(String payMethodId) {
            this.payMethodId = payMethodId;
        }

        public double getExchangeRate() {
            return exchangeRate;
        }

        public void setExchangeRate(double exchangeRate) {
            this.exchangeRate = exchangeRate;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
