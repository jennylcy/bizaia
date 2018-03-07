package com.bizaia.zhongyin.module.pay.api;

import com.bizaia.zhongyin.module.pay.data.ExchangeRates;
import com.bizaia.zhongyin.module.pay.iml.IExchangeView;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/6.
 */

public class ExchangeAPI {

    private IExchangeView view;
    private Pay api;
    private DisposableObserver<ExchangeRates> rate;
    private String areaId="";

    public ExchangeAPI(IExchangeView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(Pay.class);
    }

    public DisposableObserver<ExchangeRates> exchangeRates() {
        cancel();
        return rate = api.getWxchangeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ExchangeRates>() {
                    @Override
                    public void onNext(ExchangeRates data) {
                        if (data == null) {
                            view.showExchangeError(-1,"数据为空");
                        } else {
                            if (data.getCode() == 200) {
                                view.showLiveDetail(data);
                            } else {
                                view.showExchangeError(data.getCode(),data.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showExchangeError(-1,"数据为空");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void cancel() {
        if (rate != null)
            rate.dispose();
    }

}
