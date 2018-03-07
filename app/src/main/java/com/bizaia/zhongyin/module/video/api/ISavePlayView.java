package com.bizaia.zhongyin.module.video.api;

import com.bizaia.zhongyin.module.video.data.SaveResultBean;

/**
 * Created by zyz on 2017/3/6.
 */

public interface ISavePlayView {
    void showSave(SaveResultBean resultBean);
    void showSaveResult(SaveResultBean resultBean);
    void showSaveError();
}
