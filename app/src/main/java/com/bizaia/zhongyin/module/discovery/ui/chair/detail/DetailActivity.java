package com.bizaia.zhongyin.module.discovery.ui.chair.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.discovery.action.AttentionSuccessAction;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.monthly.action.PaySuccessAction;
import com.bizaia.zhongyin.module.pay.PayActivity;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.StringUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/4/17.
 */

public class DetailActivity extends com.bizaia.zhongyin.module.discovery.ui.chair.DetailActivity implements Contract.View{
    private Contract.Presenter presenter;
    private boolean isPay;
    private boolean isFree;
    private int type;
    private double price;
    private  final String TAG = "DetailActivity";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Presenter(this);
        type = getIntent().getIntExtra("type", 1);
        price = getIntent().getDoubleExtra("price",0);
        addRxAction();
        isFree = getIntent().getBooleanExtra("isPay",false);
    }

    private void addRxAction() {
        addSubscription(RxBus.getInstance().getEvent(PaySuccessAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PaySuccessAction>() {
                    @Override
                    public void onNext(PaySuccessAction value) {
                        loadJs("javascript:paySuccess()");
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));

        addSubscription(RxBus.getInstance().getEvent(AttentionSuccessAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<AttentionSuccessAction>() {
                    @Override
                    public void onNext(AttentionSuccessAction value) {
                        if(value.state) {
                            loadJs("javascript:attentionSuccess()");
                        }else{
                            loadJs("javascript:attentionError()");
                        }
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));
        addSubscription(RxBus.getInstance().getEvent(ForceOffLine.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ForceOffLine>() {
                    @Override
                    public void onNext(ForceOffLine value) {
                        reLogin();
                      finish();
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));

    }

    @Override
    protected void stateInit(WebView webView) {
        super.stateInit(webView);
//        if (isPay) {
//            web.loadUrl("javascript:paySuccess()");
//        }
        if(isBuy){
            web.loadUrl("javascript:paySuccess()");
        }
    }

    protected void onPay() {
        Log.e(TAG, "onPay: ---------------------->"+id );
                   if(!isBuy) {
                       if(!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                           startActivity(new Intent(getBaseContext(), PayActivity.class)
                                   .putExtra("id", id)
                                   .putExtra("price", price)
                                   .putExtra("type", 5)
                                   .putExtra("title",getString(R.string.information_buy_title))
                                   .putExtra("isFree", isFree));
                       }else{
                           startActivity(new Intent(getBaseContext(), LoginActivity.class));
                       }
                   }else{
                       web.loadUrl("javascript:paySuccess()");
                   }
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setData() {

    }

    @Override
    public void error(String msg) {

    }


    @Override
    public void setOrder(OrderData order) {
//        orderData = order;
//        if (orderData.getData() != null
//                && orderData.getData().getOrder() != null) {
//            isPay = orderData.getData().getOrder().getStatus() != 0;
//            if (isPay){
//                web.loadUrl("javascript:paySuccess()");
//            }else{
//                if (orderData != null) {
//                    startActivity(new Intent(getBaseContext(), PayActivity.class)
//                            .putExtra("order", orderData)
//                            .putExtra("price",price)
//                            .putExtra("isFree",isFree));
//                }
//            }
//        }
    }



}