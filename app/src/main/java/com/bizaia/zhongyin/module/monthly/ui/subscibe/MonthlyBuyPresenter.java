package com.bizaia.zhongyin.module.monthly.ui.subscibe;

import android.content.Context;

import com.bizaia.zhongyin.module.monthly.api.Monthly;
import com.bizaia.zhongyin.module.monthly.data.SubscribeData;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public final class MonthlyBuyPresenter implements MonthlyBuyContract.Presenter {
    private MonthlyBuyContract.View<OrderData> view;
    private Context context;

    MonthlyBuyPresenter(Context context, MonthlyBuyContract.View<OrderData> view) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
    }
    @Override
    public void getOrderNum(long productId, int productType) {
        Monthly monthly = AppRetrofit.getAppRetrofit().retrofit().create(Monthly.class);
        view.addSubscription(monthly.getOrderNum(productId, productType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<OrderData>() {
                    @Override
                    public void onNext(OrderData value) {
                        if (value.getCode() != 200) {
                            if (value.getCode() == 3000) {
                                view.onRelogin();
                                return;
                            }
                            view.dataError( );
                            return;
                        }
                        view.dataSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.netError();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }

}
