package com.bizaia.zhongyin.module.video.ui.search;

import android.content.Context;
import android.text.TextUtils;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.common.data.SearchNavData;
import com.bizaia.zhongyin.module.video.api.Video;
import com.bizaia.zhongyin.module.video.data.RecentlySearchVideoData;
import com.bizaia.zhongyin.module.video.data.RecentlyVideoData;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public final class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View view;
    private Context context;

    SearchPresenter(Context context, SearchContract.View view) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
    }

    @Override
    public void getSearchNav() {
        Video monthly = AppRetrofit.getAppRetrofit().retrofit().create(Video.class);
        view.addSubscription(monthly.getCateList(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SearchNavData>() {
                    @Override
                    public void onNext(SearchNavData value) {
                        if (value.getCode() == 200) {
                            view.setSearchNav(value);
                        } else if (value.getCode() == 3000) {
                            view.reLogin();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.netError();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    @Override
    public void getSearchResult(int pageNum, int pageSize, String queryStr,int type,String label,String sid) {
        Video monthly = AppRetrofit.getAppRetrofit().retrofit().create(Video.class);
        view.addSubscription(monthly.getVideo(pageNum, pageSize, queryStr,type,label,sid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<RecentlySearchVideoData>() {
                    @Override
                    public void onNext(RecentlySearchVideoData value) {
                        if (value.getCode() == 200&&value.getData().getDatas()!=null)
                            view.setData(value);
                        else if (value.getCode() == 3000)
                            view.reLogin();
                        else if(value.getCode()==200)
                            view.dataError("");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.netError();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    @Override
    public void getVideoData(int pageNum, int pageSize, long cateId) {
        Video news = AppRetrofit.getAppRetrofit().retrofit().create(Video.class);
        Observable<RecentlyVideoData> dataObservable;
        if (TextUtils.isEmpty(BIZAIAApplication.getInstance().getAreaId())) {
            dataObservable = news.getVideoList(pageNum, pageSize, cateId);
        } else {
            dataObservable = news.getVideoList(pageNum, pageSize, cateId, BIZAIAApplication.getInstance().getAreaId());
        }
        view.addSubscription(dataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<RecentlyVideoData>() {
                    @Override
                    public void onNext(RecentlyVideoData value) {
                        if (value.getCode() == 200) {
                            view.setData(value);
                        }else if (value.getCode() == 3000)
                            view.reLogin();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.netError();
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

}
