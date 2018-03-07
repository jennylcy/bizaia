package com.bizaia.zhongyin.module.monthly.ui.all;

import android.content.Context;

import com.bizaia.zhongyin.module.common.mvp.CommonContract;
import com.bizaia.zhongyin.module.common.mvp.CommonPresent;
import com.bizaia.zhongyin.module.monthly.api.Monthly;
import com.bizaia.zhongyin.module.monthly.data.AllMonthlyData;
import com.bizaia.zhongyin.module.monthly.data.MonthlyJSData;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public final class AllMonthlyPresenter extends CommonPresent {
    private CommonContract.View view;
    private Context context;

    AllMonthlyPresenter(Context context, CommonContract.View view) {
        super(context, view);
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
    }

    @Override
    public void requireData() {
        Monthly monthly = AppRetrofit.getAppRetrofit().retrofit().create(Monthly.class);
       view.addSubscription(monthly.getAllMonthlyList(page, size)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeWith(new DisposableObserver<AllMonthlyData>() {
                   @Override
                   public void onNext(AllMonthlyData value) {
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
               }));
    }
}
