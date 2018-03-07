package com.bizaia.zhongyin.module.login.iml;

import com.bizaia.zhongyin.module.login.data.UserBean;

/**
 * Created by zyz on 2017/3/6.
 */

public interface ILoginView {
    void showLogin(UserBean userBean);

    void showError(String msg);

    void showError();

    void showFindSuccess();
}
