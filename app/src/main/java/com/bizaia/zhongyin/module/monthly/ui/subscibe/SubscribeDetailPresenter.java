package com.bizaia.zhongyin.module.monthly.ui.subscibe;

import android.content.Context;

import com.bizaia.zhongyin.module.monthly.api.Monthly;
import com.bizaia.zhongyin.module.monthly.data.SubDetailData;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public final class SubscribeDetailPresenter implements SubscribeDetailContract.Presenter {
    private SubscribeDetailContract.View<SubDetailData> view;
    private Context context;

    public SubscribeDetailPresenter(Context context, SubscribeDetailContract.View<SubDetailData> view) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
    }


    @Override
    public void requestSubDetail() {
        Monthly monthly = AppRetrofit.getAppRetrofit().retrofit().create(Monthly.class);
        monthly.getSubDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SubDetailData>() {
                    @Override
                    public void onNext(SubDetailData value) {
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
