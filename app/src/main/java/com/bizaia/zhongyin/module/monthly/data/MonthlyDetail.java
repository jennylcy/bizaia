package com.bizaia.zhongyin.module.monthly.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2017/9/14.
 */

public class MonthlyDetail implements Serializable{

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

    public static class DataBean implements Serializable {

       private Monthly monthly;

        public Monthly getMonthly() {
            return monthly;
        }

        public void setMonthly(Monthly monthly) {
            this.monthly = monthly;
        }

        private List<Chapter> chapterList;

        public List<Chapter> getChapterList() {
            return chapterList;
        }

        public void setChapterList(List<Chapter> chapterList) {
            this.chapterList = chapterList;
        }

        public static class Chapter implements Serializable {
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

        public static  class Monthly implements Serializable{

            /**
             * id : 10000004
             * title : 2017年8月号vol.2
             * coverUrl : download/img/1500269308791Gwivt.png
             * fileUrl : 1500269351451LeQQA.pdf
             * price : 0.0
             * createTime : 2017-07-1705: 30: 11
             * description : アジアから日本を見る、日本からアジアを見る、アジア現地発の最新ビジネス情報を提供
             * chapterNum : 0
             * isDel : null
             * chapterTitles : null
             * shareUrl : null
             */

            private int id;
            private String title;
            private String coverUrl;
            private String fileUrl;
            private double price;
            private String createTime;
            private String description;
            private int chapterNum;
            private Object isDel;
            private Object chapterTitles;
            private String shareUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
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

            public String getFileUrl() {
                return fileUrl;
            }

            public void setFileUrl(String fileUrl) {
                this.fileUrl = fileUrl;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getChapterNum() {
                return chapterNum;
            }

            public void setChapterNum(int chapterNum) {
                this.chapterNum = chapterNum;
            }

            public Object getIsDel() {
                return isDel;
            }

            public void setIsDel(Object isDel) {
                this.isDel = isDel;
            }

            public Object getChapterTitles() {
                return chapterTitles;
            }

            public void setChapterTitles(Object chapterTitles) {
                this.chapterTitles = chapterTitles;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }
        }
    }


}
