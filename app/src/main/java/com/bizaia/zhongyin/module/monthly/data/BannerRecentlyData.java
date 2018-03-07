package com.bizaia.zhongyin.module.monthly.data;
 


import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/3/8.
 */

public class BannerRecentlyData  implements Serializable {
    List<MonthlyJSData.DataBean.MonthlyNewestBean> focusDatasBeen;

    public BannerRecentlyData(List<MonthlyJSData.DataBean.MonthlyNewestBean> focusDatasBeen) {
        this.focusDatasBeen = focusDatasBeen;
    }

    public List<MonthlyJSData.DataBean.MonthlyNewestBean> getFocusDataBeen() {
        return focusDatasBeen;
    }

    public void setFocusDatasBeen(List<MonthlyJSData.DataBean.MonthlyNewestBean> focusDatasBeen) {
        this.focusDatasBeen = focusDatasBeen;
    }
}
