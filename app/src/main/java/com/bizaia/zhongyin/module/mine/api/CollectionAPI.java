package com.bizaia.zhongyin.module.mine.api;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.mine.data.BuyNewsBean;
import com.bizaia.zhongyin.module.mine.data.BuyVideoBean;
import com.bizaia.zhongyin.module.mine.data.CollectAssociationBean;
import com.bizaia.zhongyin.module.mine.data.CollectLecturesBean;
import com.bizaia.zhongyin.module.mine.data.CollectRecruitBean;
import com.bizaia.zhongyin.module.mine.iml.IBuyHisView;
import com.bizaia.zhongyin.module.mine.iml.IDataCollection;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/7.
 */

public class CollectionAPI {
    private IBuyHisView view;
    private IDataCollection api;
    private DisposableObserver<BuyNewsBean> newsSub;
    private DisposableObserver<BuyVideoBean> vodSub;
    private DisposableObserver<CollectLecturesBean> chairSub;
    private DisposableObserver<CollectAssociationBean> assoSub;
    private DisposableObserver<CollectRecruitBean> recrSub;

    public CollectionAPI(IBuyHisView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataCollection.class);
    }



    public DisposableObserver<BuyNewsBean> getNews(int pageNum, int pageSize) {
        cancel();
        return newsSub = api.getNews(pageNum,pageSize,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BuyNewsBean>() {
                    @Override
                    public void onNext(BuyNewsBean data) {
                        if (data == null) {
                            view.showBuyHisError();
                        } else {
                            if (data.getCode() == 200) {
                                    view.showBuyNews(data);
                            } else {
                                view.showBuyHisError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showBuyHisError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public DisposableObserver<BuyVideoBean> getVideo(int pageNum,int pageSize) {
        cancelVideo();
        return vodSub = api.getVideo(pageNum,pageSize,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BuyVideoBean>() {
                    @Override
                    public void onNext(BuyVideoBean data) {
                        if (data == null) {
                            view.showBuyHisError();
                        } else {
                            if (data.getCode() == 200) {
                                    view.showBuyVideo(data);
                            } else {
                                view.showBuyHisError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showBuyHisError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<CollectLecturesBean> getChair(int pageNum, int pageSize) {
        cancelLect();
        return chairSub = api.getLectures(pageNum,pageSize,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CollectLecturesBean>() {
                    @Override
                    public void onNext(CollectLecturesBean data) {
                        if (data == null) {
                            view.showBuyHisError();
                        } else {
                            if (data.getCode() == 200) {
                                view.showLectures(data);
                            } else {
                                view.showBuyHisError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showBuyHisError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<CollectAssociationBean> getAsso(int pageNum,int pageSize) {
        cancelAsso();
        return assoSub = api.getAsso(pageNum,pageSize,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CollectAssociationBean>() {
                    @Override
                    public void onNext(CollectAssociationBean data) {
                        if (data == null) {
                            view.showBuyHisError();
                        } else {
                            if (data.getCode() == 200) {
                                view.showAsso(data);
                            } else {
                                view.showBuyHisError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showBuyHisError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<CollectRecruitBean> getRecr(int pageNum,int pageSize) {
        cancelRecr();
        return recrSub = api.getRecr(pageNum,pageSize,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CollectRecruitBean>() {
                    @Override
                    public void onNext(CollectRecruitBean data) {
                        if (data == null) {
                            view.showBuyHisError();
                        } else {
                            if (data.getCode() == 200) {
                                view.showRecr(data);
                            } else {
                                view.showBuyHisError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showBuyHisError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }




    public void cancel() {
        if (newsSub != null)
            newsSub.dispose();
    }

    public void cancelVideo(){
        if (vodSub != null)
            vodSub.dispose();
    }

    public void cancelLect(){
        if (chairSub != null)
            chairSub.dispose();
    }

    public void cancelAsso(){
        if (assoSub != null)
            assoSub.dispose();
    }

    public void cancelRecr(){
        if (recrSub != null)
            recrSub.dispose();
    }
}
