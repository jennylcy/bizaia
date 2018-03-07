package com.bizaia.zhongyin.module.login.presenter;

import com.bizaia.zhongyin.base.BasePresenter;

/**
 * Created by wujiaquan on 2017/2/28.
 */

public interface LoginPresenter extends BasePresenter {
    void getCode(String phone);
}
