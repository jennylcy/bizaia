package com.bizaia.zhongyin.module.video.ui.recommend;

import android.content.Context;

import com.bizaia.zhongyin.module.common.mvp.CommonContract;
import com.bizaia.zhongyin.module.common.mvp.CommonPresent;
import com.bizaia.zhongyin.module.video.api.Video;
import com.bizaia.zhongyin.module.video.data.RecommendVideoData;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public final class RecommendPresenter extends CommonPresent {
    private CommonContract.View view;
    private Context context;

    RecommendPresenter(Context context, CommonContract.View view) {
        super(context, view);
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
    }

    @Override
    public void requireData() {
        Video video = AppRetrofit.getAppRetrofit().retrofit().create(Video.class);
        Observable<RecommendVideoData> dataObservable;
        if (isAreaIdEmpty()) {
            dataObservable = video.getRecommendVideoList(page, size);
        } else {
            dataObservable = video.getRecommendVideoList(page, size,  areaId);
        }

        view.addSubscription(dataObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<RecommendVideoData>() {
                    @Override
                    public void onNext(RecommendVideoData value) {
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
