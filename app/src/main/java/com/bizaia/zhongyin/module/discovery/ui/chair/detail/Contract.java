package com.bizaia.zhongyin.module.discovery.ui.chair.detail;

import com.bizaia.zhongyin.base.BasePresenter;
import com.bizaia.zhongyin.base.BaseView;
import com.bizaia.zhongyin.module.pay.data.OrderData;

/**
 * Created by yan on 2017/4/17.
 */

public interface Contract {
    interface View extends BaseView<Presenter> {
        void setData();
         void error(String msg);
        void setOrder(OrderData order);
        void onRelogin();
    }

    interface Presenter extends BasePresenter {
        void getDetail(long lectureId);

        void getOrderNum(
                long productId
                , int productType
        );
    }
}
