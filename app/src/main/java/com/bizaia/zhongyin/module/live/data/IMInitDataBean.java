package com.bizaia.zhongyin.module.live.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/3/16.
 */

public class IMInitDataBean implements Serializable{


    /**
     * code : 200
     * msg : 登录成功
     * data : {"accountType":"1","createTime":1488423641000,"headImg":"http://up.qqya.com/allimg/201710-t/17-101803_106599.jpg","id":3,"nickName":"user3","updateTime":1488423643000,"userAccount":"user3","userSig":"eJxljs1Og0AYRfc8BWFbY2cGZqQmLrAtUrEqmUKLG0Jgil8rPxmG1sb47io2kcS7Pefm3g9N13Vj9cAv0yyru0ol6tQIQ7-WDWRc-MGmgTxJVWLK-B8U7w1IkaRbJWQPMaWUIDR0IBeVgi2cja4V0hzgNt8n-cZv3-ouE2uC7aECRQ*X83C6CKZdLEGkVRF43vo0u6uxn7FZtLPKjmK*GwFjHnOcx-16UyyKjRsSxuOX5VvAfct9nkTH2-vSjUckfRofIzt45f48JGNYOTeDSQWlOB*ybZviK8oG9CBkC3XVCwRhiomJfmJon9oXeI1bcw__","userSigExpired":1504403756000}
     */

    private int code;
    private String msg;
    /**
     * accountType : 1
     * createTime : 1488423641000
     * headImg : http://up.qqya.com/allimg/201710-t/17-101803_106599.jpg
     * id : 3
     * nickName : user3
     * updateTime : 1488423643000
     * userAccount : user3
     * userSig : eJxljs1Og0AYRfc8BWFbY2cGZqQmLrAtUrEqmUKLG0Jgil8rPxmG1sb47io2kcS7Pefm3g9N13Vj9cAv0yyru0ol6tQIQ7-WDWRc-MGmgTxJVWLK-B8U7w1IkaRbJWQPMaWUIDR0IBeVgi2cja4V0hzgNt8n-cZv3-ouE2uC7aECRQ*X83C6CKZdLEGkVRF43vo0u6uxn7FZtLPKjmK*GwFjHnOcx-16UyyKjRsSxuOX5VvAfct9nkTH2-vSjUckfRofIzt45f48JGNYOTeDSQWlOB*ybZviK8oG9CBkC3XVCwRhiomJfmJon9oXeI1bcw__
     * userSigExpired : 1504403756000
     */

    private DataEntity data;

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

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable{
        private String accountType;
        private String createTime;
        private String headImg;
        private int id;
        private String nickName;
        private long updateTime;
        private String userAccount;
        private String userSig;
        private long userSigExpired;

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getUserSig() {
            return userSig;
        }

        public void setUserSig(String userSig) {
            this.userSig = userSig;
        }

        public long getUserSigExpired() {
            return userSigExpired;
        }

        public void setUserSigExpired(long userSigExpired) {
            this.userSigExpired = userSigExpired;
        }
    }
}
