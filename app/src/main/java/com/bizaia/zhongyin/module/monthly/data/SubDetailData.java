package com.bizaia.zhongyin.module.monthly.data;

import java.io.Serializable;

/**
 * Created by yan on 2017/4/19.
 */

public class SubDetailData implements Serializable{


    private int code;
    private DataBean data;
    private String msg;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean implements Serializable{
       private String serviceStartTime;
        private String serviceEndTime;
        private long monthlyId;
        private String monthlyTitle;
        private int monthlyNum;
        private double monthlyPrice;

        public String getServiceStartTime() {
            return serviceStartTime;
        }

        public void setServiceStartTime(String serviceStartTime) {
            this.serviceStartTime = serviceStartTime;
        }

        public String getServiceEndTime() {
            return serviceEndTime;
        }

        public void setServiceEndTime(String serviceEndTime) {
            this.serviceEndTime = serviceEndTime;
        }

        public long getMonthlyId() {
            return monthlyId;
        }

        public void setMonthlyId(long monthlyId) {
            this.monthlyId = monthlyId;
        }

        public String getMonthlyTitle() {
            return monthlyTitle;
        }

        public void setMonthlyTitle(String monthlyTitle) {
            this.monthlyTitle = monthlyTitle;
        }

        public int getMonthlyNum() {
            return monthlyNum;
        }

        public void setMonthlyNum(int monthlyNum) {
            this.monthlyNum = monthlyNum;
        }

        public double getMonthlyPrice() {
            return monthlyPrice;
        }

        public void setMonthlyPrice(double monthlyPrice) {
            this.monthlyPrice = monthlyPrice;
        }
    }
}
