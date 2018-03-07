package com.bizaia.zhongyin.module.live.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/4/8.
 */

public class CoursewareStreamAddressEntity implements Serializable{

    private String cid;
    private String pushUrl;
    private String pullFlvUrl;
    private String pullM3u8Url;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getPullFlvUrl() {
        return pullFlvUrl;
    }

    public void setPullFlvUrl(String pullFlvUrl) {
        this.pullFlvUrl = pullFlvUrl;
    }

    public String getPullM3u8Url() {
        return pullM3u8Url;
    }

    public void setPullM3u8Url(String pullM3u8Url) {
        this.pullM3u8Url = pullM3u8Url;
    }
}
