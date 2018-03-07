package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.UserInfoBean;

/**
 * Created by zyz on 2017/3/6.
 */

public interface IUserInfoView {
    void showUserInfo(UserInfoBean userInfoBean);
    void showIcon(String path);
    void showUserInfoError();
}
