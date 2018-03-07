package com.bizaia.zhongyin.module.video.data;
 

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/3/8.
 */

public class BannerRecentlyData implements Serializable {
    List<RecentlyVideoData.DataBean.FocusDataBean> focusDatasBeen;

    public BannerRecentlyData(List<RecentlyVideoData.DataBean.FocusDataBean> focusDatasBeen) {
        this.focusDatasBeen = focusDatasBeen;
    }

    public List<RecentlyVideoData.DataBean.FocusDataBean> getFocusDataBeen() {
        return focusDatasBeen;
    }

    public void setFocusDatasBeen(List<RecentlyVideoData.DataBean.FocusDataBean> focusDatasBeen) {
        this.focusDatasBeen = focusDatasBeen;
    }
}
