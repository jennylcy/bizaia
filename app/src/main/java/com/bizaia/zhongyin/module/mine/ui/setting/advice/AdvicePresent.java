package com.bizaia.zhongyin.module.mine.ui.setting.advice;

import com.bizaia.zhongyin.module.mine.ui.setting.SettingAPI;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by yan on 2017/3/20.
 */

public class AdvicePresent implements AdviceContract.Presenter {
    private AdviceContract.View<ResponseBody> view;

    public AdvicePresent(AdviceContract.View<ResponseBody> view) {
        view.setPresenter(this);
        this.view = view;
    }

    @Override
    public void addAdvice(String content ,String method) {

        view.addSubscription(AppRetrofit.getAppRetrofit().retrofit()
                .create(SettingAPI.class)
                .addProposal(content,method)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody value) {
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
                })
        );
    }
}
