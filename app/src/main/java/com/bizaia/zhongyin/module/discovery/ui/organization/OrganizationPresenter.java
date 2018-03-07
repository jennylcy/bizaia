package com.bizaia.zhongyin.module.discovery.ui.organization;

import android.content.Context;

import com.bizaia.zhongyin.module.discovery.api.Discovery;
import com.bizaia.zhongyin.module.discovery.data.AssociationData;
import com.bizaia.zhongyin.module.common.mvp.CommonContract;
import com.bizaia.zhongyin.module.common.mvp.CommonPresent;
import com.bizaia.zhongyin.module.discovery.data.LectureData;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public final class OrganizationPresenter extends CommonPresent {


    public OrganizationPresenter(Context context, CommonContract.View view) {
        super(context, view);
    }

    @Override
    public void requireData() {
        Discovery news = AppRetrofit.getAppRetrofit().retrofit().create(Discovery.class);
        Observable<LectureData> dataObservable;
        if (isAreaIdEmpty()) {
            dataObservable = news.getAssociationList(page, size);
        } else {
            dataObservable = news.getAssociationList(page, size, areaId);
        }
        view.addSubscription(dataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LectureData>() {
                    @Override
                    public void onNext(LectureData value) {
                        if (value.getCode() == 200) {
                            view.dataSuccess(value);
                        } else if (value.getCode() == 3000) {
                            view.onRelogin();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }
}
