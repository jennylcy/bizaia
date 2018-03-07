package com.bizaia.zhongyin.module.monthly.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2017/6/16.
 */

public class MonthlyJSSearchData implements Serializable{

    /**
     * code : 200
     * data : {"pageSize":10,"pageNo":1,"totalCount":0,"totalPage":0,"datas":[{"id":10000037,"title":"2017年2月号","coverUrl":"download/img/1496389885843xGBbf.png","fileUrl":"1496389932117DTIXN.pdf","price":0.01,"createTime":"2017-06-02 07:53:58","description":"アジアから日本を見る　日本からアジアを見る　アジア現地発の最新ビジネス情報を提供","chapterNum":0}]}
     */

    private int code;
    private DataEntity data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * pageSize : 10
         * pageNo : 1
         * totalCount : 0
         * totalPage : 0
         * datas : [{"id":10000037,"title":"2017年2月号","coverUrl":"download/img/1496389885843xGBbf.png","fileUrl":"1496389932117DTIXN.pdf","price":0.01,"createTime":"2017-06-02 07:53:58","description":"アジアから日本を見る　日本からアジアを見る　アジア現地発の最新ビジネス情報を提供","chapterNum":0}]
         */

        private int pageSize;
        private int pageNo;
        private int totalCount;
        private int totalPage;
        private List<DatasEntity> datas;

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

        public List<DatasEntity> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasEntity> datas) {
            this.datas = datas;
        }

        public static class DatasEntity implements Serializable{
            /**
             * id : 10000037
             * title : 2017年2月号
             * coverUrl : download/img/1496389885843xGBbf.png
             * fileUrl : 1496389932117DTIXN.pdf
             * price : 0.01
             * createTime : 2017-06-02 07:53:58
             * description : アジアから日本を見る　日本からアジアを見る　アジア現地発の最新ビジネス情報を提供
             * chapterNum : 0
             */

            private long id;
            private String title;
            private String coverUrl;
            private String fileUrl;
            private double price;
            private String createTime;
            private String description;
            private int chapterNum;
            private String shareUrl;
            private List<MonthlyJSData.DataBean.MonthlyNewestBean.ChapterTitlesBean> chapterTitles;

            public List<MonthlyJSData.DataBean.MonthlyNewestBean.ChapterTitlesBean> getChapterTitles() {
                return chapterTitles;
            }

            public void setChapterTitles(List<MonthlyJSData.DataBean.MonthlyNewestBean.ChapterTitlesBean> chapterTitles) {
                this.chapterTitles = chapterTitles;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
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
        }
    }
}
