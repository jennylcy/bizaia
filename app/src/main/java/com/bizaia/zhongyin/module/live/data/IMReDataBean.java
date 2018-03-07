package com.bizaia.zhongyin.module.live.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/3/27.
 */

public class IMReDataBean implements Serializable{


    /**
     * headImg :
     * nickName :
     * news :
     * sendId :
     */

    private String headImg;
    private String nickName;
    private String news;
    private String sendId;
    private String id;
    private boolean agree;

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }
}
