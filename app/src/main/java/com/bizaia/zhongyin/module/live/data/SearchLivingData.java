package com.bizaia.zhongyin.module.live.data;

import java.util.List;

/**
 * Created by yan on 2017/5/10.
 */

public class SearchLivingData {

    /**
     * code : 200
     * data : {"pageSize":10,"pageNo":1,"totalCount":0,"totalPage":0,"datas":[{"id":10000061,"title":"甘立辉的直播间","coverUrl":"download/img/1494314790378qDnpb.png","cateId":10000007,"cateName":"绘画","startTime":1494314700000,"areaId":10000021,"country":"中国","province":"成都","price":0.01,"lecturer":"阿甘","introduction":"去委屈委屈与偶请问欧请我IE脾气哦我委屈哟西文图脾气哦我IE脾气哦我我怕抗裂砂浆发了卡号发给","coursewareUrl":"1494314768991dDdSo.pdf","coursewareCoverUrl":"download/img/1494314768991mJtZY.jpg","coursewareStreamAddress":{"cid":"ad64187ec0734a07b9bd80f83b553a6b","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/ad64187ec0734a07b9bd80f83b553a6b","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ad64187ec0734a07b9bd80f83b553a6b.flv?md5=9f34c96c2cf6de00d2415e20e6dad39a&time=59116f28","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ad64187ec0734a07b9bd80f83b553a6b.m3u8?md5=b022f1f497fb4eddc7810ec03f42e5e8&time=59116f28"},"cameraStreamAddress":{"cid":"84eb6c0e962846cbbc4273db8f6ba8a2","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/84eb6c0e962846cbbc4273db8f6ba8a2","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/84eb6c0e962846cbbc4273db8f6ba8a2.flv?md5=9b493489c2307258982bc9b1dbe8c8e6&time=59116f28","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/84eb6c0e962846cbbc4273db8f6ba8a2.m3u8?md5=4ed7c0a2d2347e06403a6e71a474128d&time=59116f28"},"chatroomId":"@TGS#3EDME6YEP","maxAudience":0,"status":"0","createTime":1494314792584,"reviewStatus":1,"organizationId":10000009,"organizationName":"1905","lecturerIds":"10000005","lecturerAccount":"gan@qq.com","shareUrl":"http://api.forcetechcloud.com:8090/api/share/html/LiveShare.html?id=10000061","lock":0,"lecturerIdentifier":"238108932FD12EAF70F5CB3E3FE4AB44","pay":false,"del":false}]}
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

    public static class DataBean {
        /**
         * pageSize : 10
         * pageNo : 1
         * totalCount : 0
         * totalPage : 0
         * datas : [{"id":10000061,"title":"甘立辉的直播间","coverUrl":"download/img/1494314790378qDnpb.png","cateId":10000007,"cateName":"绘画","startTime":1494314700000,"areaId":10000021,"country":"中国","province":"成都","price":0.01,"lecturer":"阿甘","introduction":"去委屈委屈与偶请问欧请我IE脾气哦我委屈哟西文图脾气哦我IE脾气哦我我怕抗裂砂浆发了卡号发给","coursewareUrl":"1494314768991dDdSo.pdf","coursewareCoverUrl":"download/img/1494314768991mJtZY.jpg","coursewareStreamAddress":{"cid":"ad64187ec0734a07b9bd80f83b553a6b","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/ad64187ec0734a07b9bd80f83b553a6b","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ad64187ec0734a07b9bd80f83b553a6b.flv?md5=9f34c96c2cf6de00d2415e20e6dad39a&time=59116f28","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ad64187ec0734a07b9bd80f83b553a6b.m3u8?md5=b022f1f497fb4eddc7810ec03f42e5e8&time=59116f28"},"cameraStreamAddress":{"cid":"84eb6c0e962846cbbc4273db8f6ba8a2","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/84eb6c0e962846cbbc4273db8f6ba8a2","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/84eb6c0e962846cbbc4273db8f6ba8a2.flv?md5=9b493489c2307258982bc9b1dbe8c8e6&time=59116f28","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/84eb6c0e962846cbbc4273db8f6ba8a2.m3u8?md5=4ed7c0a2d2347e06403a6e71a474128d&time=59116f28"},"chatroomId":"@TGS#3EDME6YEP","maxAudience":0,"status":"0","createTime":1494314792584,"reviewStatus":1,"organizationId":10000009,"organizationName":"1905","lecturerIds":"10000005","lecturerAccount":"gan@qq.com","shareUrl":"http://api.forcetechcloud.com:8090/api/share/html/LiveShare.html?id=10000061","lock":0,"lecturerIdentifier":"238108932FD12EAF70F5CB3E3FE4AB44","pay":false,"del":false}]
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

        public static class DatasBean {
            /**
             * id : 10000061
             * title : 甘立辉的直播间
             * coverUrl : download/img/1494314790378qDnpb.png
             * cateId : 10000007
             * cateName : 绘画
             * startTime : 1494314700000
             * areaId : 10000021
             * country : 中国
             * province : 成都
             * price : 0.01
             * lecturer : 阿甘
             * introduction : 去委屈委屈与偶请问欧请我IE脾气哦我委屈哟西文图脾气哦我IE脾气哦我我怕抗裂砂浆发了卡号发给
             * coursewareUrl : 1494314768991dDdSo.pdf
             * coursewareCoverUrl : download/img/1494314768991mJtZY.jpg
             * coursewareStreamAddress : {"cid":"ad64187ec0734a07b9bd80f83b553a6b","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/ad64187ec0734a07b9bd80f83b553a6b","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ad64187ec0734a07b9bd80f83b553a6b.flv?md5=9f34c96c2cf6de00d2415e20e6dad39a&time=59116f28","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ad64187ec0734a07b9bd80f83b553a6b.m3u8?md5=b022f1f497fb4eddc7810ec03f42e5e8&time=59116f28"}
             * cameraStreamAddress : {"cid":"84eb6c0e962846cbbc4273db8f6ba8a2","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/84eb6c0e962846cbbc4273db8f6ba8a2","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/84eb6c0e962846cbbc4273db8f6ba8a2.flv?md5=9b493489c2307258982bc9b1dbe8c8e6&time=59116f28","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/84eb6c0e962846cbbc4273db8f6ba8a2.m3u8?md5=4ed7c0a2d2347e06403a6e71a474128d&time=59116f28"}
             * chatroomId : @TGS#3EDME6YEP
             * maxAudience : 0
             * status : 0
             * createTime : 1494314792584
             * reviewStatus : 1
             * organizationId : 10000009
             * organizationName : 1905
             * lecturerIds : 10000005
             * lecturerAccount : gan@qq.com
             * shareUrl : http://api.forcetechcloud.com:8090/api/share/html/LiveShare.html?id=10000061
             * lock : 0
             * lecturerIdentifier : 238108932FD12EAF70F5CB3E3FE4AB44
             * pay : false
             * del : false
             */

            private long id;
            private String title;
            private String coverUrl;
            private int cateId;
            private String cateName;
            private String startTime;
            private int areaId;
            private String country;
            private String province;
            private double price;
            private String lecturer;
            private String introduction;
            private String coursewareUrl;
            private String coursewareCoverUrl;
            private CoursewareStreamAddressBean coursewareStreamAddress;
            private CameraStreamAddressBean cameraStreamAddress;
            private String chatroomId;
            private int maxAudience;
            private String status;
            private String createTime;
            private int reviewStatus;
            private int organizationId;
            private String organizationName;
            private String lecturerIds;
            private String lecturerAccount;
            private String shareUrl;
            private int lock;
            private String lecturerIdentifier;
            private boolean pay;
            private String organizationLogo;
            private String h5Url;
            private boolean del;
            private String pageViewNum;

            public String getPageViewNum() {
                return pageViewNum;
            }

            public void setPageViewNum(String pageViewNum) {
                this.pageViewNum = pageViewNum;
            }

            public String getOrganizationLogo() {
                return organizationLogo;
            }

            public void setOrganizationLogo(String organizationLogo) {
                this.organizationLogo = organizationLogo;
            }

            public String getH5Url() {
                return h5Url;
            }

            public void setH5Url(String h5Url) {
                this.h5Url = h5Url;
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

            public CoursewareStreamAddressBean getCoursewareStreamAddress() {
                return coursewareStreamAddress;
            }

            public void setCoursewareStreamAddress(CoursewareStreamAddressBean coursewareStreamAddress) {
                this.coursewareStreamAddress = coursewareStreamAddress;
            }

            public CameraStreamAddressBean getCameraStreamAddress() {
                return cameraStreamAddress;
            }

            public void setCameraStreamAddress(CameraStreamAddressBean cameraStreamAddress) {
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

            public static class CoursewareStreamAddressBean {
                /**
                 * cid : ad64187ec0734a07b9bd80f83b553a6b
                 * pushUrl : rtmp://zyph.forcetechcloud.com/lzy/ad64187ec0734a07b9bd80f83b553a6b
                 * pullFlvUrl : http://zypl.forcetechcloud.com:38080/lzy/ad64187ec0734a07b9bd80f83b553a6b.flv?md5=9f34c96c2cf6de00d2415e20e6dad39a&time=59116f28
                 * pullM3u8Url : http://zypl.forcetechcloud.com:38080/lzy/ad64187ec0734a07b9bd80f83b553a6b.m3u8?md5=b022f1f497fb4eddc7810ec03f42e5e8&time=59116f28
                 */

                private String cid;
                private String pushUrl;
                private String pullFlvUrl;
                private String pullM3u8Url;

                public String getCid() {
                    return cid;
                }

                public void setCid(String cid) {
                    this.cid = cid;
                }

                public String getPushUrl() {
                    return pushUrl;
                }

                public void setPushUrl(String pushUrl) {
                    this.pushUrl = pushUrl;
                }

                public String getPullFlvUrl() {
                    return pullFlvUrl;
                }

                public void setPullFlvUrl(String pullFlvUrl) {
                    this.pullFlvUrl = pullFlvUrl;
                }

                public String getPullM3u8Url() {
                    return pullM3u8Url;
                }

                public void setPullM3u8Url(String pullM3u8Url) {
                    this.pullM3u8Url = pullM3u8Url;
                }
            }

            public static class CameraStreamAddressBean {
                /**
                 * cid : 84eb6c0e962846cbbc4273db8f6ba8a2
                 * pushUrl : rtmp://zyph.forcetechcloud.com/lzy/84eb6c0e962846cbbc4273db8f6ba8a2
                 * pullFlvUrl : http://zypl.forcetechcloud.com:38080/lzy/84eb6c0e962846cbbc4273db8f6ba8a2.flv?md5=9b493489c2307258982bc9b1dbe8c8e6&time=59116f28
                 * pullM3u8Url : http://zypl.forcetechcloud.com:38080/lzy/84eb6c0e962846cbbc4273db8f6ba8a2.m3u8?md5=4ed7c0a2d2347e06403a6e71a474128d&time=59116f28
                 */

                private String cid;
                private String pushUrl;
                private String pullFlvUrl;
                private String pullM3u8Url;

                public String getCid() {
                    return cid;
                }

                public void setCid(String cid) {
                    this.cid = cid;
                }

                public String getPushUrl() {
                    return pushUrl;
                }

                public void setPushUrl(String pushUrl) {
                    this.pushUrl = pushUrl;
                }

                public String getPullFlvUrl() {
                    return pullFlvUrl;
                }

                public void setPullFlvUrl(String pullFlvUrl) {
                    this.pullFlvUrl = pullFlvUrl;
                }

                public String getPullM3u8Url() {
                    return pullM3u8Url;
                }

                public void setPullM3u8Url(String pullM3u8Url) {
                    this.pullM3u8Url = pullM3u8Url;
                }
            }
        }
    }
}
