package com.bizaia.zhongyin.module.live.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/3/8.
 */

public class LiveFocusListEntity implements Serializable{


    /**
     * id : 13
     * title : 2342
     * coverUrl : http://img0.178.com/lol/201701/278561121930/278561810157.jpg
     * liveId : 105
     * h5Url : 111
     * type : 2
     * orgId : 1
     */

    private long id;
    private String title;
    private String coverUrl;
    private long liveId;
    private String h5Url;
    private int type;
    private int orgId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public long getLiveId() {
        return liveId;
    }

    public void setLiveId(long liveId) {
        this.liveId = liveId;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }
}
