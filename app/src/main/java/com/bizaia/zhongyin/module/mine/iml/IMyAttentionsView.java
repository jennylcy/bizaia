package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.AttentionsBean;

/**
 * Created by zyz on 2017/3/11.
 */

public interface IMyAttentionsView {
    void showAttions(AttentionsBean attentionsBean);
    void showAttentionsError();
    void onRelogin();
}
