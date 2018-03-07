package com.bizaia.zhongyin.module.live.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/4/11.
 */

public class ThumbState implements Serializable{


    /**
     * code : 200
     * data : {"lecturerOnline":true,"onlineMemberNum":2}
     */

    private int code;
    private String msg;
    private String thumb;

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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
