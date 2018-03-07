package com.bizaia.zhongyin.module.discovery.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/6/9.
 */

public class BuyStateBean {


    /**
     * code : 200
     * data : {"isBuy":true}
     */

    private int code;
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
         * isBuy : true
         */

        private boolean isBuy;

        public boolean isIsBuy() {
            return isBuy;
        }

        public void setIsBuy(boolean isBuy) {
            this.isBuy = isBuy;
        }
    }
}
