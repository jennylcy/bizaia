package com.bizaia.zhongyin.module.monthly.ui.pdfdetail;

import com.bizaia.zhongyin.base.BasePresenter;
import com.bizaia.zhongyin.base.BaseView;
import com.bizaia.zhongyin.module.monthly.data.IsNeedToBuyData;
import com.bizaia.zhongyin.module.monthly.data.MonthlyDetail;
import com.bizaia.zhongyin.module.pay.data.OrderData;

/**
 * Created by yan on 2017/4/19.
 */

public interface PDFContract {
    interface View extends BaseView<Presenter> {
        void setIsNeedToBuy(IsNeedToBuyData isNeedToBuy);

        void setOrderData(OrderData orderData);

        void error(String msg);

        void setJSDetail();

        void setMonthlyDetail(MonthlyDetail detail);

        void reLogin();

        void netError();
    }

    interface Presenter extends BasePresenter {
        void isNeedToBuy(long monthlyId);

        void getDetail(long chapterId);

        void getOrderNum(
                long productId
                , int productType
        );

        void getMonthlyDetail(long id);

    }
}
