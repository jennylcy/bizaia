package com.bizaia.zhongyin.module.discovery.api;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.discovery.iml.IDataNewsDetail;
import com.bizaia.zhongyin.module.discovery.data.NewsDetailBean;
import com.bizaia.zhongyin.module.discovery.iml.INewsDetailView;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/14.
 */

public class INewsDetailAPI{
    private INewsDetailView view;
    private IDataNewsDetail api;
    private DisposableObserver<NewsDetailBean> subscription;

    public INewsDetailAPI(INewsDetailView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataNewsDetail.class);
    }



    public DisposableObserver<NewsDetailBean> getNewsDetail(long id) {
        cancel();
        return subscription = api.newsDetail(id,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<NewsDetailBean>() {
                    @Override
                    public void onNext(NewsDetailBean data) {
                        if (data == null) {
                            view.showNewsDetailError();
                        } else {
                            if (data.getCode() == 200) {
                                view.showNewsDetail(data);
                            } else {
                                view.showNewsDetailError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showNewsDetailError();
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