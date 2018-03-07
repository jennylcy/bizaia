package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.BillBean;

/**
 * Created by zyz on 2017/3/6.
 */

public interface IBillView {
    void showBill(BillBean data);
    void showBillError();
    void onRelogin();
}
