package com.bizaia.zhongyin.module.mine.data;

import com.bizaia.zhongyin.module.live.data.CameraStreamAddressEntity;
import com.bizaia.zhongyin.module.live.data.CoursewareStreamAddressEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2017/3/14.
 */

public class BuyLiveBean implements Serializable{


    /**
     * code : 200
     * data : {"pageSize":10,"pageNo":1,"totalCount":1,"totalPage":1,"datas":[{"id":10000123,"title":"预告","coverUrl":"download/img/1496730076512BCdXE.jpg","cateId":10000073,"cateName":"测试","startTime":"2017-06-07 06:20:32","areaId":10000007,"price":0.01,"lecturer":"阿甘","introduction":"ad","coursewareUrl":"1496730100700xBLOK.pdf","coursewareCoverUrl":"download/img/1496730100700GqtlN.jpg","coursewareStreamAddress":{"cid":"c94478b8e60848a6bb9cdd4f683ee39f","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/c94478b8e60848a6bb9cdd4f683ee39f","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/c94478b8e60848a6bb9cdd4f683ee39f.flv?md5=0e2463d142942859825e50e69c96a635&time=593649f8","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/c94478b8e60848a6bb9cdd4f683ee39f.m3u8?md5=69e962135aa2986108c56cdf804ce771&time=593649f8"},"cameraStreamAddress":{"cid":"0b5689d10c144da38de1e4f920d90423","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/0b5689d10c144da38de1e4f920d90423","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/0b5689d10c144da38de1e4f920d90423.flv?md5=16ff8ab6754a72bc507ec5c363a89f2b&time=593649f8","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/0b5689d10c144da38de1e4f920d90423.m3u8?md5=9d020910416799a3a275eb72f9085bb9&time=593649f8"},"chatroomId":"@TGS#3S3PG7ZET","maxAudience":0,"status":"0","createTime":"2017-06-06 06:21:44.383487","reviewStatus":1,"organizationId":10000009,"organizationName":"1905","organizationLogo":"download/img/1494226655282MAnUb.png","lecturerIds":"10000005","lecturerAccount":"gan@qq.com","shareUrl":"http://api.forcetechcloud.com:8090/api/share/html/LiveShare.html?id=10000123","lock":0,"lecturerIdentifier":"238108932FD12EAF70F5CB3E3FE4AB44","pageViewNum":33,"pay":false,"buyNum":2,"del":false}]}
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

    public static class DataEntity implements Serializable{
        /**
         * pageSize : 10
         * pageNo : 1
         * totalCount : 1
         * totalPage : 1
         * datas : [{"id":10000123,"title":"预告","coverUrl":"download/img/1496730076512BCdXE.jpg","cateId":10000073,"cateName":"测试","startTime":"2017-06-07 06:20:32","areaId":10000007,"price":0.01,"lecturer":"阿甘","introduction":"ad","coursewareUrl":"1496730100700xBLOK.pdf","coursewareCoverUrl":"download/img/1496730100700GqtlN.jpg","coursewareStreamAddress":{"cid":"c94478b8e60848a6bb9cdd4f683ee39f","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/c94478b8e60848a6bb9cdd4f683ee39f","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/c94478b8e60848a6bb9cdd4f683ee39f.flv?md5=0e2463d142942859825e50e69c96a635&time=593649f8","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/c94478b8e60848a6bb9cdd4f683ee39f.m3u8?md5=69e962135aa2986108c56cdf804ce771&time=593649f8"},"cameraStreamAddress":{"cid":"0b5689d10c144da38de1e4f920d90423","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/0b5689d10c144da38de1e4f920d90423","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/0b5689d10c144da38de1e4f920d90423.flv?md5=16ff8ab6754a72bc507ec5c363a89f2b&time=593649f8","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/0b5689d10c144da38de1e4f920d90423.m3u8?md5=9d020910416799a3a275eb72f9085bb9&time=593649f8"},"chatroomId":"@TGS#3S3PG7ZET","maxAudience":0,"status":"0","createTime":"2017-06-06 06:21:44.383487","reviewStatus":1,"organizationId":10000009,"organizationName":"1905","organizationLogo":"download/img/1494226655282MAnUb.png","lecturerIds":"10000005","lecturerAccount":"gan@qq.com","shareUrl":"http://api.forcetechcloud.com:8090/api/share/html/LiveShare.html?id=10000123","lock":0,"lecturerIdentifier":"238108932FD12EAF70F5CB3E3FE4AB44","pageViewNum":33,"pay":false,"buyNum":2,"del":false}]
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
             * id : 10000123
             * title : 预告
             * coverUrl : download/img/1496730076512BCdXE.jpg
             * cateId : 10000073
             * cateName : 测试
             * startTime : 2017-06-07 06:20:32
             * areaId : 10000007
             * price : 0.01
             * lecturer : 阿甘
             * introduction : ad
             * coursewareUrl : 1496730100700xBLOK.pdf
             * coursewareCoverUrl : download/img/1496730100700GqtlN.jpg
             * coursewareStreamAddress : {"cid":"c94478b8e60848a6bb9cdd4f683ee39f","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/c94478b8e60848a6bb9cdd4f683ee39f","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/c94478b8e60848a6bb9cdd4f683ee39f.flv?md5=0e2463d142942859825e50e69c96a635&time=593649f8","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/c94478b8e60848a6bb9cdd4f683ee39f.m3u8?md5=69e962135aa2986108c56cdf804ce771&time=593649f8"}
             * cameraStreamAddress : {"cid":"0b5689d10c144da38de1e4f920d90423","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/0b5689d10c144da38de1e4f920d90423","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/0b5689d10c144da38de1e4f920d90423.flv?md5=16ff8ab6754a72bc507ec5c363a89f2b&time=593649f8","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/0b5689d10c144da38de1e4f920d90423.m3u8?md5=9d020910416799a3a275eb72f9085bb9&time=593649f8"}
             * chatroomId : @TGS#3S3PG7ZET
             * maxAudience : 0
             * status : 0
             * createTime : 2017-06-06 06:21:44.383487
             * reviewStatus : 1
             * organizationId : 10000009
             * organizationName : 1905
             * organizationLogo : download/img/1494226655282MAnUb.png
             * lecturerIds : 10000005
             * lecturerAccount : gan@qq.com
             * shareUrl : http://api.forcetechcloud.com:8090/api/share/html/LiveShare.html?id=10000123
             * lock : 0
             * lecturerIdentifier : 238108932FD12EAF70F5CB3E3FE4AB44
             * pageViewNum : 33
             * pay : false
             * buyNum : 2
             * del : false
             */

            private int id;
            private String title;
            private String coverUrl;
            private int cateId;
            private String cateName;
            private String startTime;
            private int areaId;
            private double price;
            private String lecturer;
            private String introduction;
            private String coursewareUrl;
            private String coursewareCoverUrl;
            private CoursewareStreamAddressEntity coursewareStreamAddress;
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
            private String shareUrl;
            private int lock;
            private String lecturerIdentifier;
            private int pageViewNum;
            private boolean pay;
            private int buyNum;
            private boolean del;

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

            public int getCateId() {
                return cateId;
            }

            public void setCateId(int cateId) {
                this.cateId = cateId;
            }

            public String getCateName() {
                return cateName;
            }

            public void setCateName(String cateName) {
                this.cateName = cateName;
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

            public String getLecturer() {
                return lecturer;
            }

            public void setLecturer(String lecturer) {
                this.lecturer = lecturer;
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

            public int getPageViewNum() {
                return pageViewNum;
            }

            public void setPageViewNum(int pageViewNum) {
                this.pageViewNum = pageViewNum;
            }

            public boolean isPay() {
                return pay;
            }

            public void setPay(boolean pay) {
                this.pay = pay;
            }

            public int getBuyNum() {
                return buyNum;
            }

            public void setBuyNum(int buyNum) {
                this.buyNum = buyNum;
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
