package com.bizaia.zhongyin.module.live.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2017/3/27.
 */

public class LiveDailyBean implements Serializable{


    /**
     * code : 200
     * data : [{"startTime":"2017-04-27 16:00:00","endTime":"2017-04-27 20:00:00","title":"testing","coursewareStreamAddress":{"cid":"8d00d4aec90f47848a16b3edf51ab280","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/8d00d4aec90f47848a16b3edf51ab280","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/8d00d4aec90f47848a16b3edf51ab280.flv?md5=177d88f097907d6f6d51b83a987967b4&time=59003bf0","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/8d00d4aec90f47848a16b3edf51ab280.m3u8?md5=f983295268d06e1f6cb2867615896b79&time=59003bf0"},"cameraStreamAddress":{"cid":"d0ce2c34c19a4afdaa1c92855c641323","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/d0ce2c34c19a4afdaa1c92855c641323","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/d0ce2c34c19a4afdaa1c92855c641323.flv?md5=f94c4c0c106353f5b1b71f85364cd92b&time=59003bf1","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/d0ce2c34c19a4afdaa1c92855c641323.m3u8?md5=c335b6da1a7c24ba5f1c08c497e38e3f&time=59003bf1"},"liveId":112,"status":1,"filePath":"1111111","chatroomId":"@TGS#3VHLCQYE5"}]
     */

    private int code;
    private String msg;
    /**
     * startTime : 2017-04-27 16:00:00
     * endTime : 2017-04-27 20:00:00
     * title : testing
     * coursewareStreamAddress : {"cid":"8d00d4aec90f47848a16b3edf51ab280","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/8d00d4aec90f47848a16b3edf51ab280","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/8d00d4aec90f47848a16b3edf51ab280.flv?md5=177d88f097907d6f6d51b83a987967b4&time=59003bf0","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/8d00d4aec90f47848a16b3edf51ab280.m3u8?md5=f983295268d06e1f6cb2867615896b79&time=59003bf0"}
     * cameraStreamAddress : {"cid":"d0ce2c34c19a4afdaa1c92855c641323","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/d0ce2c34c19a4afdaa1c92855c641323","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/d0ce2c34c19a4afdaa1c92855c641323.flv?md5=f94c4c0c106353f5b1b71f85364cd92b&time=59003bf1","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/d0ce2c34c19a4afdaa1c92855c641323.m3u8?md5=c335b6da1a7c24ba5f1c08c497e38e3f&time=59003bf1"}
     * liveId : 112
     * status : 1
     * filePath : 1111111
     * chatroomId : @TGS#3VHLCQYE5
     */

    private List<DataEntity> data;

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

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable{
        private String startTime;
        private String endTime;
        private String title;
        /**
         * cid : 8d00d4aec90f47848a16b3edf51ab280
         * pushUrl : rtmp://zyph.forcetechcloud.com/lzy/8d00d4aec90f47848a16b3edf51ab280
         * pullFlvUrl : http://zypl.forcetechcloud.com:38080/lzy/8d00d4aec90f47848a16b3edf51ab280.flv?md5=177d88f097907d6f6d51b83a987967b4&time=59003bf0
         * pullM3u8Url : http://zypl.forcetechcloud.com:38080/lzy/8d00d4aec90f47848a16b3edf51ab280.m3u8?md5=f983295268d06e1f6cb2867615896b79&time=59003bf0
         */

        private CoursewareStreamAddressEntity coursewareStreamAddress;
        /**
         * cid : d0ce2c34c19a4afdaa1c92855c641323
         * pushUrl : rtmp://zyph.forcetechcloud.com/lzy/d0ce2c34c19a4afdaa1c92855c641323
         * pullFlvUrl : http://zypl.forcetechcloud.com:38080/lzy/d0ce2c34c19a4afdaa1c92855c641323.flv?md5=f94c4c0c106353f5b1b71f85364cd92b&time=59003bf1
         * pullM3u8Url : http://zypl.forcetechcloud.com:38080/lzy/d0ce2c34c19a4afdaa1c92855c641323.m3u8?md5=c335b6da1a7c24ba5f1c08c497e38e3f&time=59003bf1
         */

        private CameraStreamAddressEntity cameraStreamAddress;
        private long liveId;
        private int status;
        private String filePath;
        private String chatroomId;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public long getLiveId() {
            return liveId;
        }

        public void setLiveId(long liveId) {
            this.liveId = liveId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getChatroomId() {
            return chatroomId;
        }

        public void setChatroomId(String chatroomId) {
            this.chatroomId = chatroomId;
        }

    }
}
