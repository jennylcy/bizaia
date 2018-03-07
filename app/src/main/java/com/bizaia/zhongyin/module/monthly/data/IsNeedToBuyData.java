package com.bizaia.zhongyin.module.monthly.data;

import java.io.Serializable;

/**
 * Created by yan on 2017/4/19.
 */

public class IsNeedToBuyData implements Serializable {

    /**
     * code : 200
     * data : {"isBuy":false,"fileUrl":"download/pdf/1491361655908vOlYy.pdf"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * isBuy : false
         * fileUrl : download/pdf/1491361655908vOlYy.pdf
         */

        private boolean isBuy;
        private String fileUrl;

        public boolean isIsBuy() {
            return isBuy;
        }

        public void setIsBuy(boolean isBuy) {
            this.isBuy = isBuy;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }
    }
}
