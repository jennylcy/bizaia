package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.base.BaseView;
import com.bizaia.zhongyin.module.mine.api.ChangePasswordAPI;

/**
 * Created by wujiaquan on 2017/4/18 .
 */

public interface IPasswordChangeView extends BaseView<ChangePasswordAPI> {
    void changePasswordSuccess( );

    void changePasswordError();

    void changePasswordError(String msg);

}
