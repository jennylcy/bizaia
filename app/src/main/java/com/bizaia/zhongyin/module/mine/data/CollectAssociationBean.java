package com.bizaia.zhongyin.module.mine.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2017/4/25.
 */

public class CollectAssociationBean implements Serializable{


    /**
     * code : 200
     * data : {"pageSize":15,"pageNo":1,"totalCount":1,"totalPage":1,"datas":[{"id":21,"title":"手作|一剪成思","coverUrl":"http://yy.getop.cc/upload/20170321150425.png","createTime":1491533818000,"favorite":false,"h5Url":"http://192.168.4.205:8090/dist/html/communityDetail.html?id=21"}]}
     */

    private int code;
    /**
     * pageSize : 15
     * pageNo : 1
     * totalCount : 1
     * totalPage : 1
     * datas : [{"id":21,"title":"手作|一剪成思","coverUrl":"http://yy.getop.cc/upload/20170321150425.png","createTime":1491533818000,"favorite":false,"h5Url":"http://192.168.4.205:8090/dist/html/communityDetail.html?id=21"}]
     */

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

    public static class DataEntity implements Serializable{
        private int pageSize;
        private int pageNo;
        private int totalCount;
        private int totalPage;
        /**
         * id : 21
         * title : 手作|一剪成思
         * coverUrl : http://yy.getop.cc/upload/20170321150425.png
         * createTime : 1491533818000
         * favorite : false
         * h5Url : http://192.168.4.205:8090/dist/html/communityDetail.html?id=21
         */

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
            private int id;
            private String title;
            private String coverUrl;
            private String createTime;
            private boolean favorite;
            private String h5Url;

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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public boolean isFavorite() {
                return favorite;
            }

            public void setFavorite(boolean favorite) {
                this.favorite = favorite;
            }

            public String getH5Url() {
                return h5Url;
            }

            public void setH5Url(String h5Url) {
                this.h5Url = h5Url;
            }
        }
    }
}
