package com.bizaia.zhongyin.repository.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zyz on 2017/4/13.
 */

@Entity
public class LiveRemindBean {

    @Id
    private long id;
    private long startTime;
    private int userId;
    @Generated(hash = 1411286388)
    public LiveRemindBean(long id, long startTime, int userId) {
        this.id = id;
        this.startTime = startTime;
        this.userId = userId;
    }
    @Generated(hash = 1980356589)
    public LiveRemindBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getStartTime() {
        return this.startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public int getUserId() {
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }


}
