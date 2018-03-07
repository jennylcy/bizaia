package com.bizaia.zhongyin.module.mine.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2017/3/11.
 */

public class AttentionsBean implements Serializable{


    /**
     * code : 200
     * data : {"pageSize":5,"pageNo":1,"totalCount":1,"totalPage":1,"datas":[{"orgId":1,"orgName":"中樱","orgAvatarUrl":"http://up.qqjia.com/z/01/tu4488_33.jpg"}]}
     */

    private int code;
    /**
     * pageSize : 5
     * pageNo : 1
     * totalCount : 1
     * totalPage : 1
     * datas : [{"orgId":1,"orgName":"中樱","orgAvatarUrl":"http://up.qqjia.com/z/01/tu4488_33.jpg"}]
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
         * orgId : 1
         * orgName : 中樱
         * orgAvatarUrl : http://up.qqjia.com/z/01/tu4488_33.jpg
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
            private long orgId;
            private String orgName;
            private String orgAvatarUrl;

            public long getOrgId() {
                return orgId;
            }

            public void setOrgId(long orgId) {
                this.orgId = orgId;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public String getOrgAvatarUrl() {
                return orgAvatarUrl;
            }

            public void setOrgAvatarUrl(String orgAvatarUrl) {
                this.orgAvatarUrl = orgAvatarUrl;
            }
        }
    }
}
