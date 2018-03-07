package com.bizaia.zhongyin.module.live.iml;

import com.bizaia.zhongyin.module.live.data.LiveState;

/**
 * Created by zyz on 2017/3/8.
 */

public interface ILiveStateView {

    void showLiveState(LiveState data);
    void showLiveStateError(int code,String msg);
    void onRelogin();
}
