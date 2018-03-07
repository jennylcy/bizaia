package com.bizaia.zhongyin.module.live.api;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.live.data.LiveDetailBean;
import com.bizaia.zhongyin.module.live.data.ThumbState;
import com.bizaia.zhongyin.module.live.iml.IDataLiveDetail;
import com.bizaia.zhongyin.module.live.iml.ILiveDetailView;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/6.
 */

public class LiveDetailAPI {

    private ILiveDetailView view;
    private IDataLiveDetail api;
    private DisposableObserver<LiveDetailBean> recom;
    private DisposableObserver<OrderData> order;
    private DisposableObserver<ThumbState> notifi;


    public LiveDetailAPI(ILiveDetailView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataLiveDetail.class);
    }

    public DisposableObserver<LiveDetailBean> getDetail(long id) {
        cancel();
        return recom = api.getDetail(id,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LiveDetailBean>() {
                    @Override
                    public void onNext(LiveDetailBean data) {
                        if (data == null) {
                            view.showLiveDetailError(-1,"数据为空");
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData() == null) {
                                    view.showLiveDetailError(data.getCode(),data.getMsg());
                                } else {
                                    view.showLiveDetail(data);
                                }
                            } else if(data.getCode()==3000){
                                view.onRelogin();
                            }else {
                                view.showLiveDetailError(data.getCode(),data.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLiveDetailError(-1,"数据为空");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public DisposableObserver<OrderData> getOrder(long id,int type) {
        cancelOrd();
        return order = api.getOrderNum(id,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<OrderData>() {
                    @Override
                    public void onNext(OrderData data) {
                        if (data == null) {
                            view.showLiveDetailError(-1,"数据为空");
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData() == null) {
                                    view.showLiveDetailError(data.getCode(),data.getMsg());
                                } else {
                                    view.showOrder(data);
                                }
                            } else {
                                view.showLiveDetailError(data.getCode(),data.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLiveDetailError(-1,"数据为空");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<ThumbState> setNotifi(long id, final boolean type) {
        cancelOrd();
        return notifi = api.setNotif(id,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ThumbState>() {
                    @Override
                    public void onNext(ThumbState data) {
                        if (data == null) {
                            view.showLiveDetailError(-1,"数据为空");
                        } else {
                            if (data.getCode() == 200) {
                               if(type){
                                   view.showNotifiSuccess();
                               }else{
                                   view.showUnnotifiSuccess();
                               }
                            } else {
                                view.showLiveDetailError(data.getCode(),data.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLiveDetailError(-1,"数据为空");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void cancel() {
        if (recom != null)
            recom.dispose();

    }

    public void cancelOrd() {
        if (order != null)
            order.dispose();

    }
}
