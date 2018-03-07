package com.bizaia.zhongyin.module.discovery.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/3/8.
 */

public class BannerLectureData implements Serializable {
    List<LectureData.DataBean.FocusDataBean> focusDatasBeen;

    public BannerLectureData(List<LectureData.DataBean.FocusDataBean> focusDatasBeen) {
        this.focusDatasBeen = focusDatasBeen;
    }

    public List<LectureData.DataBean.FocusDataBean> getFocusDataBeen() {
        return focusDatasBeen;
    }

    public void setFocusDatasBeen(List<LectureData.DataBean.FocusDataBean> focusDatasBeen) {
        this.focusDatasBeen = focusDatasBeen;
    }
}
