package com.bizaia.zhongyin.module.live.iml;

import com.bizaia.zhongyin.module.live.data.LiveDetailBean;
import com.bizaia.zhongyin.module.pay.data.OrderData;

/**
 * Created by zyz on 2017/3/8.
 */

public interface ILiveDetailView {

    void showLiveDetail(LiveDetailBean data);
    void showOrder(OrderData data);
    void showNotifiSuccess();
    void showUnnotifiSuccess();
    void onRelogin();
    void showLiveDetailError(int code,String msg);
}
