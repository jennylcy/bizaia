package com.bizaia.zhongyin.module.login.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/3/6.
 */

public class UserBean implements Serializable {


    /**
     * code : 200
     * msg : "账号不存在"
     * data : {"id":10000011,"mobile":"15208376981","email":"15208376981@1905tech.com","nickname":"tangyuanze","userType":1,"isLocked":false,"avatarUrl":"/upload/img/1490002382361eSffR.jpg","orgId":1,"authToken":"api_token_51e98e5208b24749b8a6347936e4d189","remoteIp":"192.168.4.207","msgSwitch":true,"memberSig":"eJxljl1PgzAYhe-5FYRr40ppy9hdKTBx07lsS*SKIC1YDR*DgmPG-66yJZL43j7Pe8751HRdN-br3W2SplVXqlgNtTD0hW4A4*YP1rXkcaJiq*H-oDjVshFxkinRjNDEGEMApo7kolQyk1cDEYtYrgkpnvuuCx0EKAEOYdilGAVzd-LZ8vd4rL9Eo59cSLBjThWZj-DBj1i4ZTZ-jIoZSs8fw3HDVmev7Aa2DA4ocp5XHGzsenm6P3q0xXn4Sp-CneX1jK2HhlKGguIuof4LI32eD2jfZdVsa0dte6jexKRSyUJcBzmAEGST6aBeNK2sylGAwMQmtMDvGdqX9g1PzGJq","memberSigExpired":1506173561000,"areas":"","interAreaCode":"+86"}
     */

    private int code;
    private String msg;

    /**
     * id : 10000011
     * mobile : 15208376981
     * email : 15208376981@1905tech.com
     * nickname : tangyuanze
     * userType : 1
     * isLocked : false
     * avatarUrl : /upload/img/1490002382361eSffR.jpg
     * orgId : 1
     * authToken : api_token_51e98e5208b24749b8a6347936e4d189
     * remoteIp : 192.168.4.207
     * msgSwitch : true
     * memberSig : eJxljl1PgzAYhe-5FYRr40ppy9hdKTBx07lsS*SKIC1YDR*DgmPG-66yJZL43j7Pe8751HRdN-br3W2SplVXqlgNtTD0hW4A4*YP1rXkcaJiq*H-oDjVshFxkinRjNDEGEMApo7kolQyk1cDEYtYrgkpnvuuCx0EKAEOYdilGAVzd-LZ8vd4rL9Eo59cSLBjThWZj-DBj1i4ZTZ-jIoZSs8fw3HDVmev7Aa2DA4ocp5XHGzsenm6P3q0xXn4Sp-CneX1jK2HhlKGguIuof4LI32eD2jfZdVsa0dte6jexKRSyUJcBzmAEGST6aBeNK2sylGAwMQmtMDvGdqX9g1PzGJq
     * memberSigExpired : 1506173561000
     * areas :
     * interAreaCode : +86
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

    public static class DataEntity implements Serializable {
        private int id;
        private String mobile;
        private String email;
        private String nickname;
        private int userType;
        private boolean isLocked;
        private String avatarUrl;
        private int orgId;
        private String authToken;
        private String remoteIp;
        private boolean msgSwitch;
        private String memberSig;
        private long memberSigExpired;
        private String areas;
        private String interAreaCode;
        private String nid;
        private boolean isThirdparty = true;

        public String getNid() {
            return nid;
        }

        public void setNid(String nid) {
            this.nid = nid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public boolean isIsLocked() {
            return isLocked;
        }

        public void setIsLocked(boolean isLocked) {
            this.isLocked = isLocked;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        public String getRemoteIp() {
            return remoteIp;
        }

        public void setRemoteIp(String remoteIp) {
            this.remoteIp = remoteIp;
        }

        public boolean isMsgSwitch() {
            return msgSwitch;
        }

        public void setMsgSwitch(boolean msgSwitch) {
            this.msgSwitch = msgSwitch;
        }

        public String getMemberSig() {
            return memberSig;
        }

        public void setMemberSig(String memberSig) {
            this.memberSig = memberSig;
        }

        public long getMemberSigExpired() {
            return memberSigExpired;
        }

        public void setMemberSigExpired(long memberSigExpired) {
            this.memberSigExpired = memberSigExpired;
        }

        public String getAreas() {
            return areas;
        }

        public void setAreas(String areas) {
            this.areas = areas;
        }

        public String getInterAreaCode() {
            return interAreaCode;
        }

        public void setInterAreaCode(String interAreaCode) {
            this.interAreaCode = interAreaCode;
        }

        public boolean isThirdparty() {
            return isThirdparty;
        }

        public void setThirdparty(boolean thirdparty) {
            isThirdparty = thirdparty;
        }
    }
}
