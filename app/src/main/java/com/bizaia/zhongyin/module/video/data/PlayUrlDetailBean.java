package com.bizaia.zhongyin.module.video.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/4/19.
 */

public class PlayUrlDetailBean implements Serializable{


    /**
     * url : http://flvtx.plu.cn/onlive/f81f5d25a8cb4017b353492f0b56117c.flv?txSecret=da75247f558bf6830cc3804ed67305a7&txTime=58bd5343
     * name : address0
     * clarity : 1920x1080
     */

    private String url;
    private String name;
    private String clarity;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }
}
