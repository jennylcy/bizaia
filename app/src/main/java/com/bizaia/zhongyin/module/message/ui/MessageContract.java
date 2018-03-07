package com.bizaia.zhongyin.module.message.ui;

import com.bizaia.zhongyin.base.BasePresenter;
import com.bizaia.zhongyin.base.BaseView;
import com.bizaia.zhongyin.module.message.data.MessageData;

/**
 * Created by yan on 2017/4/17.
 */

public interface MessageContract {
    interface View extends BaseView<Presenter> {
        void setData(MessageData messageData);

        void error(String msg);

        void reLogin();

        void delData(long id);

        void hasRead(boolean read);
    }

    interface Presenter extends BasePresenter {
        void getMessage(
                int type
                , int pageNum
                , int pageSize
        );

        void delMessage(
                long id
        );

        void delMessage();

        void readMessage(long id);

    }
}
