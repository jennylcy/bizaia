package com.bizaia.zhongyin.module.discovery.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/3/8.
 */

public class BannerRecentlyData implements Serializable {
    List<RecentlyNewsData.DataBean.FocusDataBean> focusDatasBeen;

    public BannerRecentlyData(List<RecentlyNewsData.DataBean.FocusDataBean> focusDatasBeen) {
        this.focusDatasBeen = focusDatasBeen;
    }

    public List<RecentlyNewsData.DataBean.FocusDataBean> getFocusDataBeen() {
        return focusDatasBeen;
    }

    public void setFocusDatasBeen(List<RecentlyNewsData.DataBean.FocusDataBean> focusDatasBeen) {
        this.focusDatasBeen = focusDatasBeen;
    }
}
