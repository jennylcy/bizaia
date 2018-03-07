package com.bizaia.zhongyin.module.login.presenter;

import io.reactivex.disposables.Disposable;

/**
 * Created by wujiaquan on 2017/4/17.
 */

public interface RegisterPresenter {
    Disposable register(String phone, String password, String nickName, String areaCode);
}
