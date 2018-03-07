package com.bizaia.zhongyin.module.live.iml;

import com.bizaia.zhongyin.module.live.data.ThumbState;

/**
 * Created by zyz on 2017/3/8.
 */

public interface ILiveThumbUpView {

    void showThumbUp(ThumbState data);
    void showThumbDown(ThumbState data);
    void showThumbState(ThumbState data);
    void showThumbUpError(int code, String msg);
    void onRelogin();
}
