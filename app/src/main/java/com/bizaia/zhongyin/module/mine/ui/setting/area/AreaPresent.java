package com.bizaia.zhongyin.module.mine.ui.setting.area;

import android.util.Log;

import com.bizaia.zhongyin.module.mine.data.AreaData;
import com.bizaia.zhongyin.module.mine.ui.setting.SettingAPI;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/3/20.
 */

public class AreaPresent implements AreaContract.Presenter {
    private AreaContract.View<AreaData> view;

    public AreaPresent(AreaContract.View<AreaData> view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void getArea() {

        view.addSubscription(AppRetrofit.getAppRetrofit().retrofit()
                .create(SettingAPI.class)
                .getAreaList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<AreaData>() {
                    @Override
                    public void onNext(AreaData value) {
                        view.dataSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("AreaPresent", "dataSuccess: ------------->area null" );
                        e.printStackTrace();
                        view.dataError();
                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }
}
