package com.bizaia.zhongyin.module.monthly.ui.search;

import android.content.Context;

import com.bizaia.zhongyin.module.common.data.SearchNavData;
import com.bizaia.zhongyin.module.monthly.api.Monthly;
import com.bizaia.zhongyin.module.monthly.data.MonthlyJSSearchData;
import com.bizaia.zhongyin.module.video.api.Video;
import com.bizaia.zhongyin.util.AppRetrofit;

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
        view.addSubscription(monthly.getCateList(4)
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
    public void getSearchResult(int pageNum, int pageSize, String queryStr,int type,String label) {
        Monthly monthly =  AppRetrofit.getAppRetrofit().retrofit().create(Monthly.class);
        view.addSubscription(monthly.getSearch(pageNum, pageSize, queryStr,type,label)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MonthlyJSSearchData>() {
                    @Override
                    public void onNext(MonthlyJSSearchData value) {
                        if(value.getCode()==200&&value.getData().getDatas()!=null) {
                            view.setData(value);
                        }else if(value.getCode()==200){
                            view.dataError("");
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
}
