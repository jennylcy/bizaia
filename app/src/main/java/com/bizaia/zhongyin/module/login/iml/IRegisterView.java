package com.bizaia.zhongyin.module.login.iml;

import com.bizaia.zhongyin.module.login.data.RegisterBean;

/**
 * Created by wujiaquan on 2017/4/17.
 */

public interface IRegisterView {
    void registerSuccess(RegisterBean userBean);

    void registerError(String msg);

    void registerError();
}
