package com.bizaia.zhongyin.module.monthly.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/4/19.
 */

public class AllJSData implements Serializable{

    /**
     * code : 200
     * data : {"pageSize":5,"pageNo":1,"totalCount":3,"totalPage":1,"pageUrl":"false","datas":[{"id":26,"title":"121","coverUrl":"download/img/1491901606132gxpVS.jpg","area":"123","author":"213","advertiseUrl":"","hrefUrl":"","createTime":1491898005524,"monthlyId":12,"content":"<p>2121<\/p>","h5Url":"http://192.168.4.205:8090/dist/html/notepad.html?id=26"},{"id":16,"title":"葡萄酒就是红酒？","coverUrl":"https://images-cn.ssl-images-amazon.com/images/I/41%2BP0t9GK-L.jpg","area":"法国","author":"路易斯","advertiseUrl":"\u2018\u2019","hrefUrl":"\u2018\u2019","createTime":1491645421440,"monthlyId":12,"browseNum":100,"commentNum":50,"content":"曾经有亲朋好友问这样的问题：\u201c为什么把红酒叫成葡萄酒？\u201d，也有不少人有过这样的感慨：\u201c原来这里的葡萄酒就是红酒啊！\u201d认识的一个懂中文的法国小伙儿，还特别强调，\u201c我知道中国人把葡萄酒叫做红酒\u201d，以表示自己对中国葡萄酒文化的了解。可以看得出来两国的广大群众对这两个词有多纠结。","h5Url":"http://192.168.4.205:8090/dist/html/notepad.html?id=16"},{"id":15,"title":"买酒要认准大师和机构认证","coverUrl":"https://images-cn.ssl-images-amazon.com/images/I/41%2BP0t9GK-L.jpg","area":"法国","author":"路易斯","advertiseUrl":"\u2018\u2019","hrefUrl":"\u2018\u2019","createTime":1491620127404,"monthlyId":12,"browseNum":100,"commentNum":50,"content":"在葡萄酒的世界里，有几个概念是总让人摸不着头脑的：葡萄酒就是红酒吗？那白葡萄酒怎么办？干红、干白里的\u201c干\u201d又是什么？香槟居然也是葡萄酒？","h5Url":"http://192.168.4.205:8090/dist/html/notepad.html?id=15"}]}
     */

    private int code;
    private DataBean data;

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
         * totalCount : 3
         * totalPage : 1
         * pageUrl : false
         * datas : [{"id":26,"title":"121","coverUrl":"download/img/1491901606132gxpVS.jpg","area":"123","author":"213","advertiseUrl":"","hrefUrl":"","createTime":1491898005524,"monthlyId":12,"content":"<p>2121<\/p>","h5Url":"http://192.168.4.205:8090/dist/html/notepad.html?id=26"},{"id":16,"title":"葡萄酒就是红酒？","coverUrl":"https://images-cn.ssl-images-amazon.com/images/I/41%2BP0t9GK-L.jpg","area":"法国","author":"路易斯","advertiseUrl":"\u2018\u2019","hrefUrl":"\u2018\u2019","createTime":1491645421440,"monthlyId":12,"browseNum":100,"commentNum":50,"content":"曾经有亲朋好友问这样的问题：\u201c为什么把红酒叫成葡萄酒？\u201d，也有不少人有过这样的感慨：\u201c原来这里的葡萄酒就是红酒啊！\u201d认识的一个懂中文的法国小伙儿，还特别强调，\u201c我知道中国人把葡萄酒叫做红酒\u201d，以表示自己对中国葡萄酒文化的了解。可以看得出来两国的广大群众对这两个词有多纠结。","h5Url":"http://192.168.4.205:8090/dist/html/notepad.html?id=16"},{"id":15,"title":"买酒要认准大师和机构认证","coverUrl":"https://images-cn.ssl-images-amazon.com/images/I/41%2BP0t9GK-L.jpg","area":"法国","author":"路易斯","advertiseUrl":"\u2018\u2019","hrefUrl":"\u2018\u2019","createTime":1491620127404,"monthlyId":12,"browseNum":100,"commentNum":50,"content":"在葡萄酒的世界里，有几个概念是总让人摸不着头脑的：葡萄酒就是红酒吗？那白葡萄酒怎么办？干红、干白里的\u201c干\u201d又是什么？香槟居然也是葡萄酒？","h5Url":"http://192.168.4.205:8090/dist/html/notepad.html?id=15"}]
         */

        private int pageSize;
        private int pageNo;
        private int totalCount;
        private int totalPage;
        private String pageUrl;
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

        public String getPageUrl() {
            return pageUrl;
        }

        public void setPageUrl(String pageUrl) {
            this.pageUrl = pageUrl;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean implements Serializable{
            /**
             * id : 26
             * title : 121
             * coverUrl : download/img/1491901606132gxpVS.jpg
             * area : 123
             * author : 213
             * advertiseUrl :
             * hrefUrl :
             * createTime : 1491898005524
             * monthlyId : 12
             * content : <p>2121</p>
             * h5Url : http://192.168.4.205:8090/dist/html/notepad.html?id=26
             * browseNum : 100
             * commentNum : 50
             */

            private long id;
            private String title;
            private String coverUrl;
            private String area;
            private String author;
            private String advertiseUrl;
            private String hrefUrl;
            private String createTime;
            private int monthlyId;
            private String content;
            private String h5Url;
            private int browseNum;
            private int commentNum;
            private String pageViewNum;
            private String subhead;
            private String digest;
            private String shareUrl;
            private String fileUrl;

            public String getFileUrl() {
                return fileUrl;
            }

            public void setFileUrl(String fileUrl) {
                this.fileUrl = fileUrl;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public String getDigest() {
                return digest;
            }

            public void setDigest(String digest) {
                this.digest = digest;
            }

            public String getSubhead() {
                return subhead;
            }

            public void setSubhead(String subhead) {
                this.subhead = subhead;
            }


            public String getPageViewNum() {
                return pageViewNum;
            }

            public void setPageViewNum(String pageViewNum) {
                this.pageViewNum = pageViewNum;
            }

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

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getAdvertiseUrl() {
                return advertiseUrl;
            }

            public void setAdvertiseUrl(String advertiseUrl) {
                this.advertiseUrl = advertiseUrl;
            }

            public String getHrefUrl() {
                return hrefUrl;
            }

            public void setHrefUrl(String hrefUrl) {
                this.hrefUrl = hrefUrl;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getMonthlyId() {
                return monthlyId;
            }

            public void setMonthlyId(int monthlyId) {
                this.monthlyId = monthlyId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getH5Url() {
                return h5Url;
            }

            public void setH5Url(String h5Url) {
                this.h5Url = h5Url;
            }

            public int getBrowseNum() {
                return browseNum;
            }

            public void setBrowseNum(int browseNum) {
                this.browseNum = browseNum;
            }

            public int getCommentNum() {
                return commentNum;
            }

            public void setCommentNum(int commentNum) {
                this.commentNum = commentNum;
            }
        }
    }
}
