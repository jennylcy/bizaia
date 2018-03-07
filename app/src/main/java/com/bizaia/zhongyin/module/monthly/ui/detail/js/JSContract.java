package com.bizaia.zhongyin.module.monthly.ui.detail.js;

import com.bizaia.zhongyin.base.BasePresenter;
import com.bizaia.zhongyin.base.BaseView;
import com.bizaia.zhongyin.module.monthly.data.AllJSData;
import com.bizaia.zhongyin.module.monthly.data.IsNeedToBuyData;

/**
 * Created by yan on 2017/4/19.
 */

public interface JSContract {
    interface View extends BaseView<Presenter> {
        void setJSData(AllJSData jsData);
        void setIsNeedToBuy(IsNeedToBuyData isNeedToBuy);
        void error();
    }

    interface Presenter extends BasePresenter {
        void isNeedToBuy(long monthlyId);
        void getChapterList(
                long monthlyId
                , int pageNum
                , int pageSize
        );
    }
}
