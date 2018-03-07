package com.bizaia.zhongyin.module.live.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2017/3/7.
 */

public class LiveRecomBean implements Serializable {


    /**
     * code : 200
     * data : {"liveFocusList":[{"id":4,"title":"推荐了推荐了推荐了","coverUrl":"http://pic62.nipic.com/file/20150319/12632424_132215178296_2.jpg","liveId":22}],"liveRecommendList":{"pageSize":10,"pageNo":1,"totalCount":2,"totalPage":1,"datas":[{"id":3,"title":"直播课程推荐3","coverUrl":"http://pic62.nipic.com/file/20150319/12632424_132215178296_2.jpg","position":2,"liveId":3,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","liveStartTime":1491574970000,"price":200,"lecturer":"田中","maxAudience":60,"status":"2","cateName":"外语","createTime":1488771582000,"coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET"},{"id":2,"title":"直播课程推荐2","coverUrl":"http://pic62.nipic.com/file/20150319/12632424_132215178296_2.jpg","position":2,"liveId":2,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","liveStartTime":1491554144370,"price":80,"lecturer":"田中","maxAudience":50,"status":"2","cateName":"外语","createTime":1488771582000,"coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET"}],"offset":0}}
     */

    private int code;
    private String msg;
    /**
     * liveFocusList : [{"id":4,"title":"推荐了推荐了推荐了","coverUrl":"http://pic62.nipic.com/file/20150319/12632424_132215178296_2.jpg","liveId":22}]
     * liveRecommendList : {"pageSize":10,"pageNo":1,"totalCount":2,"totalPage":1,"datas":[{"id":3,"title":"直播课程推荐3","coverUrl":"http://pic62.nipic.com/file/20150319/12632424_132215178296_2.jpg","position":2,"liveId":3,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","liveStartTime":1491574970000,"price":200,"lecturer":"田中","maxAudience":60,"status":"2","cateName":"外语","createTime":1488771582000,"coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET"},{"id":2,"title":"直播课程推荐2","coverUrl":"http://pic62.nipic.com/file/20150319/12632424_132215178296_2.jpg","position":2,"liveId":2,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","liveStartTime":1491554144370,"price":80,"lecturer":"田中","maxAudience":50,"status":"2","cateName":"外语","createTime":1488771582000,"coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET"}],"offset":0}
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
         * pageSize : 10
         * pageNo : 1
         * totalCount : 2
         * totalPage : 1
         * datas : [{"id":3,"title":"直播课程推荐3","coverUrl":"http://pic62.nipic.com/file/20150319/12632424_132215178296_2.jpg","position":2,"liveId":3,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","liveStartTime":1491574970000,"price":200,"lecturer":"田中","maxAudience":60,"status":"2","cateName":"外语","createTime":1488771582000,"coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET"},{"id":2,"title":"直播课程推荐2","coverUrl":"http://pic62.nipic.com/file/20150319/12632424_132215178296_2.jpg","position":2,"liveId":2,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","liveStartTime":1491554144370,"price":80,"lecturer":"田中","maxAudience":50,"status":"2","cateName":"外语","createTime":1488771582000,"coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET"}]
         * offset : 0
         */

        private LiveRecommendListEntity liveRecommendList;
        /**
         * id : 4
         * title : 推荐了推荐了推荐了
         * coverUrl : http://pic62.nipic.com/file/20150319/12632424_132215178296_2.jpg
         * liveId : 22
         */

        private List<LiveFocusListEntity> liveFocusList;

        public LiveRecommendListEntity getLiveRecommendList() {
            return liveRecommendList;
        }

        public void setLiveRecommendList(LiveRecommendListEntity liveRecommendList) {
            this.liveRecommendList = liveRecommendList;
        }

        public List<LiveFocusListEntity> getLiveFocusList() {
            return liveFocusList;
        }

        public void setLiveFocusList(List<LiveFocusListEntity> liveFocusList) {
            this.liveFocusList = liveFocusList;
        }

        public static class LiveRecommendListEntity implements Serializable{
            private int pageSize;
            private int pageNo;
            private int totalCount;
            private int totalPage;
            private int offset;
            /**
             * id : 3
             * title : 直播课程推荐3
             * coverUrl : http://pic62.nipic.com/file/20150319/12632424_132215178296_2.jpg
             * position : 2
             * liveId : 3
             * organizationName : a中樱
             * organizationLogo : http://up.qqjia.com/z/01/tu4488_33.jpg
             * liveStartTime : 1491574970000
             * price : 200
             * lecturer : 田中
             * maxAudience : 60
             * status : 2
             * cateName : 外语
             * createTime : 1488771582000
             * coursewareStreamAddress : {"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"}
             * cameraStreamAddress : {"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"}
             * chatroomId : @TGS#35VYPJOET
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

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
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
                private int position;
                private long liveId;
                private String organizationName;
                private String organizationLogo;
                private String startTime;
                private double price;
                private String lecturer;
                private int maxAudience;
                private String status;
                private String cateName;
                private String createTime;
                private String buyNum;
                /**
                 * cid : ca90b935227e429493c5dc706e42cd58
                 * pushUrl : rtmp://scympush.forcetech.net:1935/lscym/lei
                 * pullFlvUrl : http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e
                 * pullM3u8Url : http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e
                 */

                private CoursewareStreamAddressEntity coursewareStreamAddress;
                /**
                 * cid : 7427c4fe15764217b3804899064f2720
                 * pushUrl : rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720
                 * pullFlvUrl : http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca
                 * pullM3u8Url : http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca
                 */

                private CameraStreamAddressEntity cameraStreamAddress;
                private String chatroomId;
                private String pageViewNum;

                public String getPageViewNum() {
                    return pageViewNum;
                }

                public void setPageViewNum(String pageViewNum) {
                    this.pageViewNum = pageViewNum;
                }

                public String getBuyNum() {
                    return buyNum;
                }

                public void setBuyNum(String buyNum) {
                    this.buyNum = buyNum;
                }

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

                public int getPosition() {
                    return position;
                }

                public void setPosition(int position) {
                    this.position = position;
                }

                public long getLiveId() {
                    return liveId;
                }

                public void setLiveId(long liveId) {
                    this.liveId = liveId;
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

                public String getStartTime() {
                    return startTime;
                }

                public void setStartTime(String startTime) {
                    this.startTime = startTime;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public String getLecturer() {
                    return lecturer;
                }

                public void setLecturer(String lecturer) {
                    this.lecturer = lecturer;
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

                public String getCateName() {
                    return cateName;
                }

                public void setCateName(String cateName) {
                    this.cateName = cateName;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
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

            }
        }

    }
}
