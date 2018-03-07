package com.bizaia.zhongyin.module.video.data;

import com.bizaia.zhongyin.module.live.data.LecturerListEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/3/11.
 */

public class VideoDetailData implements Serializable {


    /**
     * code : 200
     * data : {"vod":{"id":7,"title":"共享经济改变生活","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","adminId":1,"orgId":1,"cateId":10003,"category":"点播","subcategory":"英语","areaId":10004,"country":"日本","province":"","price":10,"lecturers":"史珍香，史浩驰","introduction":"共享经济成为新的投资方向","createTime":1489484605000,"status":1,"playUrl":"","buyNum":100,"type":1,"orgName":"a中樱","avatarUrl":"http://up.qqjia.com/z/01/tu4488_33.jpg","isDel":false,"liveId":102,"isPaid":false},"lecturerList":[{"id":1,"name":"李老师","orgName":"三和株式会社","job":"数学讲师","avatarUrl":"http://img0.imgtn.bdimg.com/it/u=2756228202944364039&fm=214&gp=0.jpg","introduction":"10年教学经验","orgId":1,"createTime":1488879902000},{"id":2,"name":"田中","orgName":"三和株式会社","job":"英语讲师","avatarUrl":"http://v1.qzone.cc/avatar/201508/05/11/05/55c17d7dce37c723.jpg%21200x200.jpg","introduction":"8年英语教学经验，耶鲁英语硕士专业毕业","orgId":1,"createTime":1488881991000}]}
     */

    private int code;
    /**
     * vod : {"id":7,"title":"共享经济改变生活","coverUrl":"http://img02.tooopen.com/images/20140504/sy_60294738471.jpg","adminId":1,"orgId":1,"cateId":10003,"category":"点播","subcategory":"英语","areaId":10004,"country":"日本","province":"","price":10,"lecturers":"史珍香，史浩驰","introduction":"共享经济成为新的投资方向","createTime":1489484605000,"status":1,"playUrl":"","buyNum":100,"type":1,"orgName":"a中樱","avatarUrl":"http://up.qqjia.com/z/01/tu4488_33.jpg","isDel":false,"liveId":102,"isPaid":false}
     * lecturerList : [{"id":1,"name":"李老师","orgName":"三和株式会社","job":"数学讲师","avatarUrl":"http://img0.imgtn.bdimg.com/it/u=2756228202944364039&fm=214&gp=0.jpg","introduction":"10年教学经验","orgId":1,"createTime":1488879902000},{"id":2,"name":"田中","orgName":"三和株式会社","job":"英语讲师","avatarUrl":"http://v1.qzone.cc/avatar/201508/05/11/05/55c17d7dce37c723.jpg%21200x200.jpg","introduction":"8年英语教学经验，耶鲁英语硕士专业毕业","orgId":1,"createTime":1488881991000}]
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
        /**
         * id : 7
         * title : 共享经济改变生活
         * coverUrl : http://img02.tooopen.com/images/20140504/sy_60294738471.jpg
         * adminId : 1
         * orgId : 1
         * cateId : 10003
         * category : 点播
         * subcategory : 英语
         * areaId : 10004
         * country : 日本
         * province :
         * price : 10
         * lecturers : 史珍香，史浩驰
         * introduction : 共享经济成为新的投资方向
         * createTime : 1489484605000
         * status : 1
         * playUrl :
         * buyNum : 100
         * type : 1
         * orgName : a中樱
         * avatarUrl : http://up.qqjia.com/z/01/tu4488_33.jpg
         * isDel : false
         * liveId : 102
         * isPaid : false
         */

        private VodEntity vod;
        /**
         * id : 1
         * name : 李老师
         * orgName : 三和株式会社
         * job : 数学讲师
         * avatarUrl : http://img0.imgtn.bdimg.com/it/u=2756228202944364039&fm=214&gp=0.jpg
         * introduction : 10年教学经验
         * orgId : 1
         * createTime : 1488879902000
         */

        private List<LecturerListEntity> lecturerList;

        public VodEntity getVod() {
            return vod;
        }

        public void setVod(VodEntity vod) {
            this.vod = vod;
        }

        public List<LecturerListEntity> getLecturerList() {
            return lecturerList;
        }

        public void setLecturerList(List<LecturerListEntity> lecturerList) {
            this.lecturerList = lecturerList;
        }

        public static class VodEntity implements Serializable{
            private long id;
            private String title;
            private String coverUrl;
            private int adminId;
            private long orgId;
            private int cateId;
            private String category;
            private String subcategory;
            private int areaId;
            private String country;
            private String province;
            private double price;
            private String lecturers;
            private String introduction;
            private String createTime;
            private int status;
            private String playUrl;
            private int buyNum;
            private int type;
            private String orgName;
            private String avatarUrl;
            private boolean isDel;
            private int liveId;
            private boolean isPaid;
            private  String shareUrl;
            private String coursewareUrl;
            private String pageViewNum;

            public String getPageViewNum() {
                return pageViewNum;
            }

            public void setPageViewNum(String pageViewNum) {
                this.pageViewNum = pageViewNum;
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

            public int getAdminId() {
                return adminId;
            }

            public void setAdminId(int adminId) {
                this.adminId = adminId;
            }

            public long getOrgId() {
                return orgId;
            }

            public void setOrgId(long orgId) {
                this.orgId = orgId;
            }

            public int getCateId() {
                return cateId;
            }

            public void setCateId(int cateId) {
                this.cateId = cateId;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getSubcategory() {
                return subcategory;
            }

            public void setSubcategory(String subcategory) {
                this.subcategory = subcategory;
            }

            public int getAreaId() {
                return areaId;
            }

            public void setAreaId(int areaId) {
                this.areaId = areaId;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getLecturers() {
                return lecturers;
            }

            public void setLecturers(String lecturers) {
                this.lecturers = lecturers;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getPlayUrl() {
                return playUrl;
            }

            public void setPlayUrl(String playUrl) {
                this.playUrl = playUrl;
            }

            public int getBuyNum() {
                return buyNum;
            }

            public void setBuyNum(int buyNum) {
                this.buyNum = buyNum;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public boolean isIsDel() {
                return isDel;
            }

            public void setIsDel(boolean isDel) {
                this.isDel = isDel;
            }

            public int getLiveId() {
                return liveId;
            }

            public void setLiveId(int liveId) {
                this.liveId = liveId;
            }

            public boolean isIsPaid() {
                return isPaid;
            }

            public void setIsPaid(boolean isPaid) {
                this.isPaid = isPaid;
            }

            public boolean isPaid() {
                return isPaid;
            }

            public String getCoursewareUrl() {
                return coursewareUrl;
            }

            public void setCoursewareUrl(String coursewareUrl) {
                this.coursewareUrl = coursewareUrl;
            }
        }

    }
}
