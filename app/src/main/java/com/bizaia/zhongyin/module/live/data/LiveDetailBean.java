package com.bizaia.zhongyin.module.live.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2017/3/8.
 */

public class LiveDetailBean implements Serializable{

    /**
     * code : 200
     * data : {"lecturerList":[{"id":10000002,"name":"田蔓莎","orgName":"新希望","job":"讲师","avatarUrl":"download/img/1493780900393XRoJf.jpg","introduction":"田蔓莎同学是一个优秀的讲师","orgId":10000001,"createTime":1493780934434,"lock":false}],"live":{"id":10000013,"title":"宋老师小课题开课啦","coverUrl":"download/img/1493805973773tpPGo.jpg","cateId":10000007,"startTime":1493849838000,"areaId":10000001,"price":0.01,"introduction":"测试而已","coursewareUrl":"download/pdf/1493806290462dNUZH.pdf","coursewareCoverUrl":"download/img/1493806290462uFLPY.jpg","coursewareStreamAddress":{"cid":"76edd997d3374accaa9d2d351c5142a4","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/76edd997d3374accaa9d2d351c5142a4","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/76edd997d3374accaa9d2d351c5142a4.flv?md5=4e9071ad2403dde0842bf1993acbb47c&time=5909ad13","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/76edd997d3374accaa9d2d351c5142a4.m3u8?md5=59e7598c3811cd6260e8fac0e73f68b2&time=5909ad13"},"cameraStreamAddress":{"cid":"750043a427eb4007972ac06f173ebb3c","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/750043a427eb4007972ac06f173ebb3c","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/750043a427eb4007972ac06f173ebb3c.flv?md5=3b85fe5d5224e7c1a95d7c74dc8dcc61&time=5909ad13","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/750043a427eb4007972ac06f173ebb3c.m3u8?md5=f85ba842601de64c9b9937197a6790ab&time=5909ad13"},"chatroomId":"@TGS#3MN3YXYE6","maxAudience":0,"status":"0","createTime":1493806356287,"reviewStatus":1,"organizationId":10000001,"organizationName":"中樱","organizationLogo":"download/img/1493025704883EbGww.png","lecturerIds":"10000002","lecturerAccount":"1471033731@qq.com","h5Url":"http://192.168.4.205:8090/H5/html/LiveShare.html?id=10000013","shareUrl":"http://192.168.4.205:8090/share/html/LiveShare.html?id=10000013","lock":0,"lecturerIdentifier":"8D8789ECDDB5F8BFBAD4AD30C97F86A4","pay":false,"del":false}}
     */

    private int code;
    private String msg;
    /**
     * lecturerList : [{"id":10000002,"name":"田蔓莎","orgName":"新希望","job":"讲师","avatarUrl":"download/img/1493780900393XRoJf.jpg","introduction":"田蔓莎同学是一个优秀的讲师","orgId":10000001,"createTime":1493780934434,"lock":false}]
     * live : {"id":10000013,"title":"宋老师小课题开课啦","coverUrl":"download/img/1493805973773tpPGo.jpg","cateId":10000007,"startTime":1493849838000,"areaId":10000001,"price":0.01,"introduction":"测试而已","coursewareUrl":"download/pdf/1493806290462dNUZH.pdf","coursewareCoverUrl":"download/img/1493806290462uFLPY.jpg","coursewareStreamAddress":{"cid":"76edd997d3374accaa9d2d351c5142a4","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/76edd997d3374accaa9d2d351c5142a4","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/76edd997d3374accaa9d2d351c5142a4.flv?md5=4e9071ad2403dde0842bf1993acbb47c&time=5909ad13","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/76edd997d3374accaa9d2d351c5142a4.m3u8?md5=59e7598c3811cd6260e8fac0e73f68b2&time=5909ad13"},"cameraStreamAddress":{"cid":"750043a427eb4007972ac06f173ebb3c","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/750043a427eb4007972ac06f173ebb3c","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/750043a427eb4007972ac06f173ebb3c.flv?md5=3b85fe5d5224e7c1a95d7c74dc8dcc61&time=5909ad13","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/750043a427eb4007972ac06f173ebb3c.m3u8?md5=f85ba842601de64c9b9937197a6790ab&time=5909ad13"},"chatroomId":"@TGS#3MN3YXYE6","maxAudience":0,"status":"0","createTime":1493806356287,"reviewStatus":1,"organizationId":10000001,"organizationName":"中樱","organizationLogo":"download/img/1493025704883EbGww.png","lecturerIds":"10000002","lecturerAccount":"1471033731@qq.com","h5Url":"http://192.168.4.205:8090/H5/html/LiveShare.html?id=10000013","shareUrl":"http://192.168.4.205:8090/share/html/LiveShare.html?id=10000013","lock":0,"lecturerIdentifier":"8D8789ECDDB5F8BFBAD4AD30C97F86A4","pay":false,"del":false}
     */

    private DataEntity data;

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

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable{
        /**
         * id : 10000013
         * title : 宋老师小课题开课啦
         * coverUrl : download/img/1493805973773tpPGo.jpg
         * cateId : 10000007
         * startTime : 1493849838000
         * areaId : 10000001
         * price : 0.01
         * introduction : 测试而已
         * coursewareUrl : download/pdf/1493806290462dNUZH.pdf
         * coursewareCoverUrl : download/img/1493806290462uFLPY.jpg
         * coursewareStreamAddress : {"cid":"76edd997d3374accaa9d2d351c5142a4","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/76edd997d3374accaa9d2d351c5142a4","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/76edd997d3374accaa9d2d351c5142a4.flv?md5=4e9071ad2403dde0842bf1993acbb47c&time=5909ad13","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/76edd997d3374accaa9d2d351c5142a4.m3u8?md5=59e7598c3811cd6260e8fac0e73f68b2&time=5909ad13"}
         * cameraStreamAddress : {"cid":"750043a427eb4007972ac06f173ebb3c","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/750043a427eb4007972ac06f173ebb3c","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/750043a427eb4007972ac06f173ebb3c.flv?md5=3b85fe5d5224e7c1a95d7c74dc8dcc61&time=5909ad13","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/750043a427eb4007972ac06f173ebb3c.m3u8?md5=f85ba842601de64c9b9937197a6790ab&time=5909ad13"}
         * chatroomId : @TGS#3MN3YXYE6
         * maxAudience : 0
         * status : 0
         * createTime : 1493806356287
         * reviewStatus : 1
         * organizationId : 10000001
         * organizationName : 中樱
         * organizationLogo : download/img/1493025704883EbGww.png
         * lecturerIds : 10000002
         * lecturerAccount : 1471033731@qq.com
         * h5Url : http://192.168.4.205:8090/H5/html/LiveShare.html?id=10000013
         * shareUrl : http://192.168.4.205:8090/share/html/LiveShare.html?id=10000013
         * lock : 0
         * lecturerIdentifier : 8D8789ECDDB5F8BFBAD4AD30C97F86A4
         * pay : false
         * del : false
         */

        private LiveEntity live;
        /**
         * id : 10000002
         * name : 田蔓莎
         * orgName : 新希望
         * job : 讲师
         * avatarUrl : download/img/1493780900393XRoJf.jpg
         * introduction : 田蔓莎同学是一个优秀的讲师
         * orgId : 10000001
         * createTime : 1493780934434
         * lock : false
         */

        private List<LecturerListEntity> lecturerList;

        public LiveEntity getLive() {
            return live;
        }

        public void setLive(LiveEntity live) {
            this.live = live;
        }

        public List<LecturerListEntity> getLecturerList() {
            return lecturerList;
        }

        public void setLecturerList(List<LecturerListEntity> lecturerList) {
            this.lecturerList = lecturerList;
        }

        public static class LiveEntity implements Serializable{
            private long id;
            private String title;
            private String coverUrl;
            private int cateId;
            private String startTime;
            private int areaId;
            private double price;
            private String introduction;
            private String coursewareUrl;
            private String coursewareCoverUrl;
            /**
             * cid : 76edd997d3374accaa9d2d351c5142a4
             * pushUrl : rtmp://zyph.forcetechcloud.com/lzy/76edd997d3374accaa9d2d351c5142a4
             * pullFlvUrl : http://zypl.forcetechcloud.com:38080/lzy/76edd997d3374accaa9d2d351c5142a4.flv?md5=4e9071ad2403dde0842bf1993acbb47c&time=5909ad13
             * pullM3u8Url : http://zypl.forcetechcloud.com:38080/lzy/76edd997d3374accaa9d2d351c5142a4.m3u8?md5=59e7598c3811cd6260e8fac0e73f68b2&time=5909ad13
             */

            private CoursewareStreamAddressEntity coursewareStreamAddress;
            /**
             * cid : 750043a427eb4007972ac06f173ebb3c
             * pushUrl : rtmp://zyph.forcetechcloud.com/lzy/750043a427eb4007972ac06f173ebb3c
             * pullFlvUrl : http://zypl.forcetechcloud.com:38080/lzy/750043a427eb4007972ac06f173ebb3c.flv?md5=3b85fe5d5224e7c1a95d7c74dc8dcc61&time=5909ad13
             * pullM3u8Url : http://zypl.forcetechcloud.com:38080/lzy/750043a427eb4007972ac06f173ebb3c.m3u8?md5=f85ba842601de64c9b9937197a6790ab&time=5909ad13
             */

            private CameraStreamAddressEntity cameraStreamAddress;
            private String chatroomId;
            private int maxAudience;
            private String status;
            private String createTime;
            private int reviewStatus;
            private int organizationId;
            private String organizationName;
            private String organizationLogo;
            private String lecturerIds;
            private String lecturerAccount;
            private String h5Url;
            private String shareUrl;
            private int lock;
            private String lecturerIdentifier;
            private boolean pay;
            private boolean del;
            private boolean notifi;
            private String lecturer;
            private String pushPlatformType;
            private String buyNum;
            private String realStartTime;
            private long liveId;

            public long getLiveId() {
                return liveId;
            }

            public void setLiveId(long liveId) {
                this.liveId = liveId;
            }

            public String getRealStartTime() {
                return realStartTime;
            }

            public void setRealStartTime(String realStartTime) {
                this.realStartTime = realStartTime;
            }

            public String getBuyNum() {
                return buyNum;
            }

            public void setBuyNum(String buyNum) {
                this.buyNum = buyNum;
            }

            public String getPushPlatformType() {
                return pushPlatformType;
            }

            public void setPushPlatformType(String pushPlatformType) {
                this.pushPlatformType = pushPlatformType;
            }

            public String getLecturer() {
                return lecturer;
            }

            public void setLecturer(String lecturer) {
                this.lecturer = lecturer;
            }

            public boolean isNotifi() {
                return notifi;
            }

            public void setNotifi(boolean notifi) {
                this.notifi = notifi;
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

            public int getCateId() {
                return cateId;
            }

            public void setCateId(int cateId) {
                this.cateId = cateId;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public int getAreaId() {
                return areaId;
            }

            public void setAreaId(int areaId) {
                this.areaId = areaId;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public String getCoursewareUrl() {
                return coursewareUrl;
            }

            public void setCoursewareUrl(String coursewareUrl) {
                this.coursewareUrl = coursewareUrl;
            }

            public String getCoursewareCoverUrl() {
                return coursewareCoverUrl;
            }

            public void setCoursewareCoverUrl(String coursewareCoverUrl) {
                this.coursewareCoverUrl = coursewareCoverUrl;
            }

            public CoursewareStreamAddressEntity getCoursewareStreamAddress() {
                return coursewareStreamAddress;
            }

            public void setCoursewareStreamAddress(CoursewareStreamAddressEntity coursewareStreamAddress) {
                this.coursewareStreamAddress = coursewareStreamAddress;
            }

            public CameraStreamAddressEntity getCameraStreamAddress() {
                return cameraStreamAddress;
            }

            public void setCameraStreamAddress(CameraStreamAddressEntity cameraStreamAddress) {
                this.cameraStreamAddress = cameraStreamAddress;
            }

            public String getChatroomId() {
                return chatroomId;
            }

            public void setChatroomId(String chatroomId) {
                this.chatroomId = chatroomId;
            }

            public int getMaxAudience() {
                return maxAudience;
            }

            public void setMaxAudience(int maxAudience) {
                this.maxAudience = maxAudience;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getReviewStatus() {
                return reviewStatus;
            }

            public void setReviewStatus(int reviewStatus) {
                this.reviewStatus = reviewStatus;
            }

            public int getOrganizationId() {
                return organizationId;
            }

            public void setOrganizationId(int organizationId) {
                this.organizationId = organizationId;
            }

            public String getOrganizationName() {
                return organizationName;
            }

            public void setOrganizationName(String organizationName) {
                this.organizationName = organizationName;
            }

            public String getOrganizationLogo() {
                return organizationLogo;
            }

            public void setOrganizationLogo(String organizationLogo) {
                this.organizationLogo = organizationLogo;
            }

            public String getLecturerIds() {
                return lecturerIds;
            }

            public void setLecturerIds(String lecturerIds) {
                this.lecturerIds = lecturerIds;
            }

            public String getLecturerAccount() {
                return lecturerAccount;
            }

            public void setLecturerAccount(String lecturerAccount) {
                this.lecturerAccount = lecturerAccount;
            }

            public String getH5Url() {
                return h5Url;
            }

            public void setH5Url(String h5Url) {
                this.h5Url = h5Url;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public int getLock() {
                return lock;
            }

            public void setLock(int lock) {
                this.lock = lock;
            }

            public String getLecturerIdentifier() {
                return lecturerIdentifier;
            }

            public void setLecturerIdentifier(String lecturerIdentifier) {
                this.lecturerIdentifier = lecturerIdentifier;
            }

            public boolean isPay() {
                return pay;
            }

            public void setPay(boolean pay) {
                this.pay = pay;
            }

            public boolean isDel() {
                return del;
            }

            public void setDel(boolean del) {
                this.del = del;
            }


        }

    }
}
