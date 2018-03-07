package com.bizaia.zhongyin.module.monthly.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/4/20.
 */

public class CommendData implements Serializable {

    /**
     * code : 200
     * data : {"pageSize":5,"pageNo":1,"totalCount":11,"totalPage":3,"datas":[{"id":12,"chapterId":16,"content":"考虑考虑","createTime":1491991558715,"uid":10000024,"nickname":"和大人","avatarUrl":"download/img/1491991135718jadvP.png"},{"id":13,"chapterId":16,"content":"考虑考虑","createTime":1491991616390,"uid":10000024,"nickname":"和大人","avatarUrl":"download/img/1491991135718jadvP.png"},{"id":14,"chapterId":16,"content":"啦啦啦啦","createTime":1491991625718,"uid":10000024,"nickname":"和大人","avatarUrl":"download/img/1491991135718jadvP.png"},{"id":15,"chapterId":16,"content":"你过来","createTime":1491991656397,"uid":10000024,"nickname":"和大人","avatarUrl":"download/img/1491991135718jadvP.png"},{"id":16,"chapterId":16,"content":"看几个很刚哈冷咯啦咯啦JJ路KKKKKK天咯啦咯啦咯啦咯啦咯啦咯啦i个咯啦咯啦","createTime":1491991763081,"uid":10000024,"nickname":"和大人","avatarUrl":"download/img/1491991135718jadvP.png"}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * pageSize : 5
         * pageNo : 1
         * totalCount : 11
         * totalPage : 3
         * datas : [{"id":12,"chapterId":16,"content":"考虑考虑","createTime":1491991558715,"uid":10000024,"nickname":"和大人","avatarUrl":"download/img/1491991135718jadvP.png"},{"id":13,"chapterId":16,"content":"考虑考虑","createTime":1491991616390,"uid":10000024,"nickname":"和大人","avatarUrl":"download/img/1491991135718jadvP.png"},{"id":14,"chapterId":16,"content":"啦啦啦啦","createTime":1491991625718,"uid":10000024,"nickname":"和大人","avatarUrl":"download/img/1491991135718jadvP.png"},{"id":15,"chapterId":16,"content":"你过来","createTime":1491991656397,"uid":10000024,"nickname":"和大人","avatarUrl":"download/img/1491991135718jadvP.png"},{"id":16,"chapterId":16,"content":"看几个很刚哈冷咯啦咯啦JJ路KKKKKK天咯啦咯啦咯啦咯啦咯啦咯啦i个咯啦咯啦","createTime":1491991763081,"uid":10000024,"nickname":"和大人","avatarUrl":"download/img/1491991135718jadvP.png"}]
         */

        private int pageSize;
        private int pageNo;
        private int totalCount;
        private int totalPage;
        private List<DatasBean> datas;

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean implements Serializable{
            /**
             * id : 12
             * chapterId : 16
             * content : 考虑考虑
             * createTime : 1491991558715
             * uid : 10000024
             * nickname : 和大人
             * avatarUrl : download/img/1491991135718jadvP.png
             */

            private int id;
            private int chapterId;
            private String content;
            private String createTime;
            private int uid;
            private String nickname;
            private String avatarUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getChapterId() {
                return chapterId;
            }

            public void setChapterId(int chapterId) {
                this.chapterId = chapterId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
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
        }
    }
}
