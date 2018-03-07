package com.bizaia.zhongyin.module.live.iml;

import java.util.List;

/**
 * Created by zyz on 2017/3/6.
 */

public interface ILiveRecomView {
    void showLiveRecom(List<Object> list,int pageTotal);
    void showLiveNew(List<Object> list,int pageTotal);
    void showLiveRecomError(int code ,String msg);
}
