package com.bizaia.zhongyin.module.discovery.ui.jobs;

import android.content.Context;

import com.bizaia.zhongyin.module.common.mvp.CommonContract;
import com.bizaia.zhongyin.module.common.mvp.CommonPresent;
import com.bizaia.zhongyin.module.discovery.api.Discovery;
import com.bizaia.zhongyin.module.discovery.data.RecruitData;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public final class JobsPresenter extends CommonPresent {


    public JobsPresenter(Context context, CommonContract.View view) {
        super(context, view);
    }


    @Override
    public void requireData() {
        Discovery news = AppRetrofit.getAppRetrofit().retrofit().create(Discovery.class);
        Observable<RecruitData> dataObservable;
        if (isAreaIdEmpty()) {
            dataObservable = news.getRecruitList(page, size);
        } else {
            dataObservable = news.getRecruitList(page, size, areaId);
        }
        view.addSubscription(dataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<RecruitData>() {
                    @Override
                    public void onNext(RecruitData value) {
                        if (value.getCode() == 200) {
                            view.dataSuccess(value);
                        } else if (value.getCode() == 3000) {
                            view.onRelogin();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
//                        view.dataError("");
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }
}
