package com.bizaia.zhongyin.module.login.view;

import com.bizaia.zhongyin.base.BaseView;
import com.bizaia.zhongyin.module.login.presenter.LoginPresenter;

/**
 * Created by Administrator on 2017/2/28.
 */

public interface LoginView extends BaseView<LoginPresenter> {
    void setCodeText(String text);

    void getCodeError();

    void codeTimeEnd();
}
