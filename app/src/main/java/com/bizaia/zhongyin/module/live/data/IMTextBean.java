package com.bizaia.zhongyin.module.live.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/3/18.
 */

public class IMTextBean implements Serializable{


    /**
     * headImg : http://upload.cbg.cn/2016/0325/1458852298185.jpg
     * news : Wwwwwwwwqqqqqq
     * nickName : 佚名
     */

    private String headImg;
    private String news;
    private String nickName;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
