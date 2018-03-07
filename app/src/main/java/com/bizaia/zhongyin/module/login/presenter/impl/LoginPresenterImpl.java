package com.bizaia.zhongyin.module.login.presenter.impl;

import com.bizaia.zhongyin.module.login.presenter.LoginPresenter;
import com.bizaia.zhongyin.module.login.view.LoginView;

/**
 * Created by wujiaquan on 2017/2/28.
 */

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView mView;

    public LoginPresenterImpl(LoginView view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getCode(String phone) {

    }
}
