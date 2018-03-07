package com.bizaia.zhongyin.module.message.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/6/22.
 */

public class UnreadMsgBean implements Serializable{


    /**
     * code : 200
     * data : 1000000
     */

    private int code;
    private String msg;
    private long data;

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

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }
}
