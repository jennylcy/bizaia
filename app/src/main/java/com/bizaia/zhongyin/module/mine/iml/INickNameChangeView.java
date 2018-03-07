package com.bizaia.zhongyin.module.mine.iml;

/**
 * Created by wujiaquan on 2017/4/24.
 */

public interface INickNameChangeView {
    void changeNickNameSuccess(String nickName);

    void changeNickNameError();

    void changeNickNameError(String msg);

    void loginOutTime();
}
