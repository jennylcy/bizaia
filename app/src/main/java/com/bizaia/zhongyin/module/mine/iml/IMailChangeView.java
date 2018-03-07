package com.bizaia.zhongyin.module.mine.iml;

/**
 * Created by wujiaquan on 2017/4/24.
 */

public interface IMailChangeView {
    void getCodeSuccess(String mail);

    void getCodeError();

    void getCodeError(String msg);

    void changeMailSuccess(String mail);

    void changeMailError();

    void changeMailError(String msg);

    void loginOutTime();

    void checkCode();


}
