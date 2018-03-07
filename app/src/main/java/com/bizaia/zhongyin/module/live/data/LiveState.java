package com.bizaia.zhongyin.module.live.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/4/11.
 */

public class LiveState implements Serializable{


    /**
     * code : 200
     * data : {"lecturerOnline":true,"onlineMemberNum":2}
     */

    private int code;
    private String msg;
    /**
     * lecturerOnline : true
     * onlineMemberNum : 2
     */

    private DataEntity data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
        private boolean lecturerOnline;
        private int onlineMemberNum;

        public boolean isLecturerOnline() {
            return lecturerOnline;
        }

        public void setLecturerOnline(boolean lecturerOnline) {
            this.lecturerOnline = lecturerOnline;
        }

        public int getOnlineMemberNum() {
            return onlineMemberNum;
        }

        public void setOnlineMemberNum(int onlineMemberNum) {
            this.onlineMemberNum = onlineMemberNum;
        }
    }
}
