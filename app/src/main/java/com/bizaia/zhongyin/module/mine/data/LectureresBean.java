package com.bizaia.zhongyin.module.mine.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2017/3/10.
 */

public class LectureresBean implements Serializable{


    /**
     * code : 200
     * data : {"pageSize":10,"pageNo":1,"totalCount":2,"totalPage":1,"datas":[{"id":1,"name":"李老师","orgName":"三和株式会社","job":"数学讲师","avatarUrl":"http://img0.imgtn.bdimg.com/it/u=275622820,2944364039&fm=214&gp=0.jpg","introduction":"10年教学经验","orgId":1,"createTime":1488879902000},{"id":2,"name":"田中","orgName":"三和株式会社","job":"英语讲师","avatarUrl":"http://v1.qzone.cc/avatar/201508/05/11/05/55c17d7dce37c723.jpg%21200x200.jpg","introduction":"8年英语教学经验，耶鲁英语硕士专业毕业","orgId":1,"createTime":1488881991000}]}
     */

    private int code;
    /**
     * pageSize : 10
     * pageNo : 1
     * totalCount : 2
     * totalPage : 1
     * datas : [{"id":1,"name":"李老师","orgName":"三和株式会社","job":"数学讲师","avatarUrl":"http://img0.imgtn.bdimg.com/it/u=275622820,2944364039&fm=214&gp=0.jpg","introduction":"10年教学经验","orgId":1,"createTime":1488879902000},{"id":2,"name":"田中","orgName":"三和株式会社","job":"英语讲师","avatarUrl":"http://v1.qzone.cc/avatar/201508/05/11/05/55c17d7dce37c723.jpg%21200x200.jpg","introduction":"8年英语教学经验，耶鲁英语硕士专业毕业","orgId":1,"createTime":1488881991000}]
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
         * id : 1
         * name : 李老师
         * orgName : 三和株式会社
         * job : 数学讲师
         * avatarUrl : http://img0.imgtn.bdimg.com/it/u=275622820,2944364039&fm=214&gp=0.jpg
         * introduction : 10年教学经验
         * orgId : 1
         * createTime : 1488879902000
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
            private String name;
            private String orgName;
            private String job;
            private String avatarUrl;
            private String introduction;
            private int orgId;
            private String createTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public String getJob() {
                return job;
            }

            public void setJob(String job) {
                this.job = job;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public int getOrgId() {
                return orgId;
            }

            public void setOrgId(int orgId) {
                this.orgId = orgId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
