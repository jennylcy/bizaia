package com.bizaia.zhongyin.module.live.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/4/19.
 */

public class LecturerListEntity implements Serializable {

    private long id;
    private String name;
    private String orgName;
    private String job;
    private String avatarUrl;
    private String introduction;
    private int orgId;
    private String createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "LecturerListEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", orgName='" + orgName + '\'' +
                ", job='" + job + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", introduction='" + introduction + '\'' +
                ", orgId=" + orgId +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
