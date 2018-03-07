package com.bizaia.zhongyin.module.video.ui.recently;

import android.content.Context;

import com.bizaia.zhongyin.module.common.mvp.CommonContract;
import com.bizaia.zhongyin.module.common.mvp.CommonPresent;
import com.bizaia.zhongyin.module.video.api.Video;
import com.bizaia.zhongyin.module.video.data.RecentlyVideoData;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public final class RecentlyPresenter extends CommonPresent {
    private CommonContract.View view;
    private Context context;

    public RecentlyPresenter(Context context, CommonContract.View view) {
        super(context, view);
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
    }

    @Override
    public void requireData() {
        Video video = AppRetrofit.getAppRetrofit().retrofit().create(Video.class);
        Observable<RecentlyVideoData> dataObservable;
        if (isAreaIdEmpty()) {
            dataObservable = video.getCompanyVideoList(page, size,orgId);
        } else {
            dataObservable = video.getCompanyVideoList(page, size,  orgId,areaId);
        }
       view.addSubscription(dataObservable
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeWith(new DisposableObserver<RecentlyVideoData>() {
                   @Override
                   public void onNext(RecentlyVideoData value) {
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
