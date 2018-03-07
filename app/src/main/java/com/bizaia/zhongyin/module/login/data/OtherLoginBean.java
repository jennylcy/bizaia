package com.bizaia.zhongyin.module.login.data;

/**
 * Created by wujiaquan on 2017/4/17.
 */

public class OtherLoginBean {

    /**
     * code : 200
     * msg : 账号不存在
     * data : {"id":10000069,"nickname":"伍加全","avatarUrl":"/upload/img/1490002382361eSffR.jpg","authToken":"api_token_30cade632905448c922ec4657414a054"}
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
         * id : 10000069
         * nickname : 伍加全
         * avatarUrl : /upload/img/1490002382361eSffR.jpg
         * authToken : api_token_30cade632905448c922ec4657414a054
         */

        private int id;
        private String nickname;
        private String avatarUrl;
        private String authToken;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    }
}
