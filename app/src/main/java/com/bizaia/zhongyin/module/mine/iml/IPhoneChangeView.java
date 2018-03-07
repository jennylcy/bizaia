package com.bizaia.zhongyin.module.mine.iml;

/**
 * Created by wujiaquan on 2017/4/18.
 */

public interface IPhoneChangeView {
    void changePhoneSuccess(String phone);

    void changePhoneError();

    void changePhoneError(String msg);

    void loginOutTime();
}
