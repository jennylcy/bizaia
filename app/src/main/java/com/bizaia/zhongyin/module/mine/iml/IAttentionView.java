package com.bizaia.zhongyin.module.mine.iml;

/**
 * Created by zyz on 2017/3/6.
 */

public interface IAttentionView {
    void showIsAttention(boolean isAttention);
    void showAddAttention(boolean isAttention);
    void showDelAttention(boolean isAttention);
    void onRelogin();
    void showAttentionError();
}
