package com.bizaia.zhongyin.module.video.data;

import java.io.Serializable;

/**
 * Created by zyz on 2017/3/10.
 */

public class SaveResultBean implements Serializable{



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
        private int point;

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }
    }
}
