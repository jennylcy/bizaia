package com.bizaia.zhongyin.module.mine.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/3/10.
 */

public class IsAttentionBean implements Serializable{


    /**
     * code : 200
     * data : false
     */

    private int code;
    private boolean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
