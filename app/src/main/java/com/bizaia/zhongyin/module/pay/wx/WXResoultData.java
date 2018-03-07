package com.bizaia.zhongyin.module.pay.wx;

import java.io.Serializable;

/**
 * Created by yan on 2017/4/25.
 */

public class WXResoultData implements Serializable{

    /**
     * code : 200
     * data :
     */

    private int code;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
