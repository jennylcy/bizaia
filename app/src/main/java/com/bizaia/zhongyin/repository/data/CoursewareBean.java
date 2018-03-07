package com.bizaia.zhongyin.repository.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by zyz on 2017/4/13.
 */

@Entity
public class CoursewareBean {

    @Id(autoincrement=true)
    private Long id;
    private long liveId;
    private int userId;
    private String pdfUrl;
    @Generated(hash = 880672729)
    public CoursewareBean(Long id, long liveId, int userId, String pdfUrl) {
        this.id = id;
        this.liveId = liveId;
        this.userId = userId;
        this.pdfUrl = pdfUrl;
    }
    @Generated(hash = 1948849149)
    public CoursewareBean() {
    }
    public int getUserId() {
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getLiveId() {
        return liveId;
    }

    public void setLiveId(long liveId) {
        this.liveId = liveId;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }
    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }


}
