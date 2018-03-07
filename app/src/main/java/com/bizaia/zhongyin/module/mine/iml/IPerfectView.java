package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.InfoBean;

/**
 * Created by Administrator
 * Created on 2017/7/7 15:37
 */

public interface IPerfectView {
    void getInfoSuccess(InfoBean bean);

    void getInfoError(int errorCode);

    void changeInfoSuccess();

    void changeInfoError(int errorCode);
}
