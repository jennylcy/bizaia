package com.bizaia.zhongyin.module.live.iml;

import com.bizaia.zhongyin.module.login.data.UserBean;

/**
 * Created by zyz on 2017/4/12.
 */

public interface IStartStreamView {
    void showLiveState(UserBean data);
    void showLiveStateError(int code,String msg);
}
