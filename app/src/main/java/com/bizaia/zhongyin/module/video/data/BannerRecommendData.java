package com.bizaia.zhongyin.module.video.data;
 

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/3/8.
 */

public class BannerRecommendData implements Serializable {
    List<RecommendVideoData.DataBean.FocusDataBean> focusDatasBeen;

    public BannerRecommendData(List<RecommendVideoData.DataBean.FocusDataBean> focusDatasBeen) {
        this.focusDatasBeen = focusDatasBeen;
    }

    public List<RecommendVideoData.DataBean.FocusDataBean> getFocusDataBeen() {
        return focusDatasBeen;
    }

    public void setFocusDatasBeen(List<RecommendVideoData.DataBean.FocusDataBean> focusDatasBeen) {
        this.focusDatasBeen = focusDatasBeen;
    }
}
