package com.bizaia.zhongyin.module.discovery.ui.chair.detail;

import com.bizaia.zhongyin.module.discovery.api.Discovery;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by yan on 2017/4/17.
 */

public class Presenter implements Contract.Presenter {
    private Contract.View view;

    public Presenter(Contract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void getDetail(long lectureId) {
        Discovery discovery = AppRetrofit.getAppRetrofit().retrofit().create(Discovery.class);
        view.addSubscription(discovery.getLectureDetail(lectureId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody value) {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void getOrderNum(long productId, int productType) {
        Discovery discovery = AppRetrofit.getAppRetrofit().retrofit().create(Discovery.class);
        view.addSubscription(discovery.getOrderNum(productId, productType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<OrderData>() {
                    @Override
                    public void onNext(OrderData value) {
                        if (value.getCode() == 200) {
                            view.setOrder(value);
                        } else if(value.getCode()==3000){
                            view.onRelogin();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.toString();
                        view.error("网络出错");
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
