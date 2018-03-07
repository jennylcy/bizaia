package com.bizaia.zhongyin.module.common.ui;

import com.bizaia.zhongyin.base.BasePresenter;
import com.bizaia.zhongyin.base.BaseView;

/**
 * Created by yan on 2017/3/8.
 */

public interface DataView<T,V extends BasePresenter> extends BaseView<V> {
    void dataSuccess(T news);

    void dataError();

    void onRelogin();

}