package com.bizaia.zhongyin.module.login.data;

/**
 * Created by wujiaquan on 2017/4/17.
 */

public class RegisterBean {

    /**
     * code : 200
     * data : {"id":10000028,"mobile":"18684040127","nickname":"Misutesu","avatarUrl":"download/img/1490002382361eSffR.jpg","authToken":"api_token_931d098436b046f4a85303c53431140c","interAreaCode":"86"}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 10000028
         * mobile : 18684040127
         * nickname : Misutesu
         * avatarUrl : download/img/1490002382361eSffR.jpg
         * authToken : api_token_931d098436b046f4a85303c53431140c
         * interAreaCode : 86
         */

        private int id;
        private String mobile;
        private String nickname;
        private String avatarUrl;
        private String authToken;
        private String interAreaCode;

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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        public String getInterAreaCode() {
            return interAreaCode;
        }

        public void setInterAreaCode(String interAreaCode) {
            this.interAreaCode = interAreaCode;
        }
    }
}
