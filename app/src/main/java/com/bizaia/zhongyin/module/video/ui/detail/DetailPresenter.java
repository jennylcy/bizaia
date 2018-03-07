package com.bizaia.zhongyin.module.video.ui.detail;

import android.content.Context;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.discovery.api.Discovery;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.module.video.api.Video;
import com.bizaia.zhongyin.module.video.data.VideoDetailData;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.ErrorUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public final class DetailPresenter implements VideoDetailContract.Presenter {
    private VideoDetailContract.View<VideoDetailData> view;
    private Context context;

    DetailPresenter(Context context, VideoDetailContract.View<VideoDetailData> view) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
    }

    @Override
    public void requireData(long videoId, String token) {
        Video video = AppRetrofit.getAppRetrofit().retrofit().create(Video.class);
        view.addSubscription(video.getVideoDetail(videoId,token)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<VideoDetailData>() {
            @Override
            public void onNext(VideoDetailData value) {
                if(value.getCode()==200) {
                    view.dataSuccess(value);
                }else if(value.getCode()==3000){
                    view.onRelogin();
                }else {
                    view.dataError();
                }
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
                        } else {
                            view.error(ErrorUtil.getMsg(value.getCode()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.toString();
                        view.error(context.getString(R.string.net_error));
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
