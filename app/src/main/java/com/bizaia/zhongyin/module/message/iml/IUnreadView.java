package com.bizaia.zhongyin.module.message.iml;

import com.bizaia.zhongyin.module.message.data.UnreadMsgBean;

/**
 * Created by zyz on 2017/3/8.
 */

public interface IUnreadView {

    void showUnreadNum(UnreadMsgBean data);
    void showUnreadNumError(int code, String msg);
}
