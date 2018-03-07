package com.bizaia.zhongyin.repository.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zyz on 2017/4/13.
 */

@Entity
public class HasWartchBean {

    @Id(autoincrement=true)
    private Long id;
    private long seeId;
    private String userId;
    @Generated(hash = 1433167471)
    public HasWartchBean(Long id, long seeId, String userId) {
        this.id = id;
        this.seeId = seeId;
        this.userId = userId;
    }
    @Generated(hash = 927697488)
    public HasWartchBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getSeeId() {
        return this.seeId;
    }
    public void setSeeId(long seeId) {
        this.seeId = seeId;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }




}
