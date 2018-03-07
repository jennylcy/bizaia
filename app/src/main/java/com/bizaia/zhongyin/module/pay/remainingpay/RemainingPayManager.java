package com.bizaia.zhongyin.module.pay.remainingpay;


import android.content.Context;
import android.util.Log;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.pay.PayActivity;
import com.bizaia.zhongyin.module.pay.api.Pay;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.module.pay.data.RemainPayData;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.ErrorUtil;
import com.bizaia.zhongyin.util.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/3/20.
 */

public class RemainingPayManager {
    private static final String TAG = "RemainingPayManager";

    private CompositeDisposable compositeDisposable;
    private RemainingPayService payService;
    private Context context;
    private PayActivity.PayPalResult payPalResult;

    public RemainingPayManager(PayActivity payActivity, PayActivity.PayPalResult payPalResult) {
        this.context = payActivity.getBaseContext();
        this.payPalResult = payPalResult;
        compositeDisposable = new CompositeDisposable();
        payService = new RemainingPayService(this);

    }

    public void RemainingPay(OrderData orderData) {
        payService.pay(orderData.getData().getOrder().getProductId()
                , orderData.getData().getOrder().getProductType()
                , orderData.getData().getOrder().getOrderNum()
        );
    }

    private void success(RemainPayData.DataBean value) {

    }

    private void error(String msg) {
        Log.e(TAG, "error: " + msg);
        ToastUtils.showInUIThead(context, msg);
    }

    private void netError() {
        ToastUtils.showInUIThead(context, context.getString(R.string.net_error));
    }

    public void onDestroy() {
        compositeDisposable.clear();
    }

    private static class RemainingPayService {
        private RemainingPayManager payManager;

        private RemainingPayService(RemainingPayManager payManager) {
            this.payManager = payManager;
        }

        private void pay(long productId, int type, String orderNum) {
            Pay pay = AppRetrofit.getAppRetrofit().retrofit().create(Pay.class);
            payManager.compositeDisposable.add(pay.remainingPay(productId, type, orderNum)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<RemainPayData>() {
                        @Override
                        public void onNext(RemainPayData value) {
                            if (value.getCode() != 200) {
                                payManager.error(ErrorUtil.getMsg(value.getCode()));
                            } else if (value.getData() != null) {
                                payManager.success(value.getData());
                                payManager.payPalResult.confirmSuccess();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            payManager.netError();
                        }

                        @Override
                        public void onComplete() {

                        }
                    })
            );
        }
    }

}
