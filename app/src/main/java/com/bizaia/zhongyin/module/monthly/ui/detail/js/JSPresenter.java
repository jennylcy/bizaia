package com.bizaia.zhongyin.module.monthly.ui.detail.js;

import com.bizaia.zhongyin.module.monthly.api.Monthly;
import com.bizaia.zhongyin.module.monthly.data.AllJSData;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/4/19.
 */

public class JSPresenter implements JSContract.Presenter {
    private JSContract.View view;

    public JSPresenter(JSContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }


    @Override
    public void isNeedToBuy(long monthlyId) {

    }

    @Override
    public void getChapterList(long monthlyId, int pageNum, int pageSize) {
        Monthly monthly = AppRetrofit.getAppRetrofit().retrofit().create(Monthly.class);
        view.addSubscription(monthly.getChapterList(monthlyId, pageNum, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<AllJSData>() {
                    @Override
                    public void onNext(AllJSData value) {
                        view.setJSData(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.error();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
