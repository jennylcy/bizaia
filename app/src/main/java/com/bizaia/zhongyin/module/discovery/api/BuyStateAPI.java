package com.bizaia.zhongyin.module.discovery.api;

import android.util.Log;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.discovery.data.BuyStateBean;
import com.bizaia.zhongyin.module.discovery.iml.IsBuyView;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/10.
 */

public class BuyStateAPI {
    private IsBuyView view;
    private Discovery api;
    private DisposableObserver<BuyStateBean> subscription;

    public BuyStateAPI(IsBuyView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(Discovery.class);
    }



    public DisposableObserver<BuyStateBean> isBuy(long id,int type) {
        cancel();
        Log.e("BuyStateAPI", "isBuy----------------------> "+id+"------>"+type );
        return subscription = api.checkBuy(id,type,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BuyStateBean>() {
                    @Override
                    public void onNext(BuyStateBean data) {
                        if (data == null) {
                            view.showStateError();
                        } else {
                            if (data.getCode() == 200) {
                              view.showIsBuy(data.getData().isIsBuy());
                            } else {
                                view.showStateError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showStateError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    public void cancel() {
        if (subscription != null)
            subscription.dispose();
    }
}
