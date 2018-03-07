package com.bizaia.zhongyin.module.monthly.ui.subscibe;

import android.content.Context;

import com.bizaia.zhongyin.module.monthly.api.Monthly;
import com.bizaia.zhongyin.module.monthly.data.SubscribeData;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public final class SubscribePresenter implements SubscribeContract.Presenter {
    private SubscribeContract.View<SubscribeData> view;
    private Context context;

    SubscribePresenter(Context context, SubscribeContract.View<SubscribeData> view) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
    }

    @Override
    public void requireData() {
        Monthly monthly = AppRetrofit.getAppRetrofit().retrofit().create(Monthly.class);
        monthly.getSubscribeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SubscribeData>() {
                    @Override
                    public void onNext(SubscribeData value) {
                        view.dataSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.dataError();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
