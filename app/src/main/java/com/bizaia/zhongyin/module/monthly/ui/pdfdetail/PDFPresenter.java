package com.bizaia.zhongyin.module.monthly.ui.pdfdetail;

import android.util.Log;

import com.bizaia.zhongyin.module.monthly.api.Monthly;
import com.bizaia.zhongyin.module.monthly.data.IsNeedToBuyData;
import com.bizaia.zhongyin.module.monthly.data.MonthlyDetail;
import com.bizaia.zhongyin.module.monthly.data.SubDetailData;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/4/19.
 */

public class PDFPresenter implements PDFContract.Presenter {
    private PDFContract.View view;

    public PDFPresenter(PDFContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void isNeedToBuy(long monthlyId) {
        Log.e("PDFPresenter", "isNeedToBuy: ---------------->"+monthlyId );
        Monthly monthly = AppRetrofit.getAppRetrofit().retrofit().create(Monthly.class);
        view.addSubscription(monthly.isWhetherToBuy(monthlyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<IsNeedToBuyData>() {
                    @Override
                    public void onNext(IsNeedToBuyData value) {
                        if (value.getCode() != 200) {
                            if (value.getCode() == 3000) {
                                view.reLogin();
                                return;
                            }
                            view.error(value.getMsg());
                            return;
                        }
                        view.setIsNeedToBuy(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.netError();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }

    @Override
    public void getDetail(long chapterId) {
        Monthly monthly = AppRetrofit.getAppRetrofit().retrofit().create(Monthly.class);
        view.addSubscription(monthly.getJSDetail(chapterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SubDetailData>() {
                    @Override
                    public void onNext(SubDetailData value) {
                        if (value.getCode() != 200) {
                            if (value.getCode() == 3000) {
                                view.reLogin();
                                return;
                            }
                            view.error(value.getMsg());
                            return;
                        }
                        view.setJSDetail();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.netError();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void getMonthlyDetail(long id) {
        Monthly monthly = AppRetrofit.getAppRetrofit().retrofit().create(Monthly.class);
        view.addSubscription(monthly.getMonthlyDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MonthlyDetail>() {
                    @Override
                    public void onNext(MonthlyDetail value) {
                        if (value.getCode() != 200) {
                            if (value.getCode() == 3000) {
                                view.reLogin();
                                return;
                            }
                            view.error(value.getMsg());
                            return;
                        }
                        view.setMonthlyDetail(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.netError();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void getOrderNum(long productId, int productType) {
        Monthly monthly = AppRetrofit.getAppRetrofit().retrofit().create(Monthly.class);
        view.addSubscription(monthly.getOrderNum(productId, productType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<OrderData>() {
                    @Override
                    public void onNext(OrderData value) {
                        if (value.getCode() != 200) {
                            if (value.getCode() == 3000) {
                                view.reLogin();
                                return;
                            }
                            view.error(value.getMsg());
                            return;
                        }
                        view.setOrderData(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.netError();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }
}
