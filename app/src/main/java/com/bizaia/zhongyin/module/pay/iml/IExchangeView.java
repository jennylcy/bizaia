package com.bizaia.zhongyin.module.pay.iml;

import com.bizaia.zhongyin.module.pay.data.ExchangeRates;

/**
 * Created by zyz on 2017/3/8.
 */

public interface IExchangeView {

    void showLiveDetail(ExchangeRates data);
    void onRelogin();
    void showExchangeError(int code, String msg);
}
