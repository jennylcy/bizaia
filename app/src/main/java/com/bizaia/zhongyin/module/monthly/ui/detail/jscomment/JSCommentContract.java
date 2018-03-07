package com.bizaia.zhongyin.module.monthly.ui.detail.jscomment;

import com.bizaia.zhongyin.base.BasePresenter;
import com.bizaia.zhongyin.base.BaseView;
import com.bizaia.zhongyin.module.monthly.data.AllJSData;
import com.bizaia.zhongyin.module.monthly.data.CommendData;
import com.bizaia.zhongyin.module.monthly.data.IsNeedToBuyData;

import okhttp3.ResponseBody;
import retrofit2.http.Field;

/**
 * Created by yan on 2017/4/19.
 */

public interface JSCommentContract {
    interface View extends BaseView<Presenter> {
        void setCommendData(CommendData commendData);
        void publishSuccess();
        void error();
    }

    interface Presenter extends BasePresenter {
        void getCommentList(
                long monthlyId
                , int pageNum
                , int pageSize
        );

        void publishComment(
                long chapterId
                , String content
        );
    }
}
