package com.bizaia.zhongyin.module.mine.api;

import android.content.Context;

import com.bizaia.zhongyin.module.discovery.api.Discovery;
import com.bizaia.zhongyin.module.discovery.data.RecentlyNewsData;
import com.bizaia.zhongyin.module.common.mvp.CommonContract;
import com.bizaia.zhongyin.module.common.mvp.CommonPresent;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public final class CompanyNewsPresenter extends CommonPresent {

    public CompanyNewsPresenter(Context context, CommonContract.View view) {
        super(context, view);
    }

    @Override
    public void requireData() {
        Discovery news = AppRetrofit.getAppRetrofit().retrofit().create(Discovery.class);
        news.getCompanyNewsList(page, size,orgId )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<RecentlyNewsData>() {
                    @Override
                    public void onNext(RecentlyNewsData value) {
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
