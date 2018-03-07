package com.bizaia.zhongyin.module.mine.api;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.mine.data.BuyLiveBean;
import com.bizaia.zhongyin.module.mine.data.BuyMonthlyBean;
import com.bizaia.zhongyin.module.mine.data.BuyNewsBean;
import com.bizaia.zhongyin.module.mine.data.BuyVideoBean;
import com.bizaia.zhongyin.module.mine.iml.IBuyHisView;
import com.bizaia.zhongyin.module.mine.iml.IDataBuyHis;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/7.
 */

public class BuyHisAPI {
    private IBuyHisView view;
    private IDataBuyHis api;
    private DisposableObserver<BuyNewsBean> newsSub;
    private DisposableObserver<BuyLiveBean> liveSub;
    private DisposableObserver<BuyVideoBean> videoSub;
    private DisposableObserver<BuyMonthlyBean> monthlySub;

    public BuyHisAPI(IBuyHisView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataBuyHis.class);
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
                            } else if(data.getCode()==3000) {
                              view.onRelogin();
                            }else {
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

    public DisposableObserver<BuyLiveBean> getLive(int pageNum,int pageSize) {
        cancel();
        return liveSub = api.getLive(pageNum,pageSize,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BuyLiveBean>() {
                    @Override
                    public void onNext(BuyLiveBean data) {
                        if (data == null) {
                            view.showBuyHisError();
                        } else {
                            if (data.getCode() == 200) {
                                    view.showBuyLive(data);
                            }else if(data.getCode()==3000) {
                                view.onRelogin();
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
        cancel();
        return videoSub = api.getVideo(pageNum,pageSize,
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

                            } else if(data.getCode()==3000) {
                                view.onRelogin();
                            }else {
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

    public DisposableObserver<BuyMonthlyBean> getMonthly(int pageNum,int pageSize) {
        cancel();
        return monthlySub = api.getMonthly(pageNum,pageSize,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BuyMonthlyBean>() {
                    @Override
                    public void onNext(BuyMonthlyBean data) {
                        if (data == null) {
                            view.showBuyHisError();
                        } else {
                            if (data.getCode() == 200) {
                                    view.showBuyMonthly(data);
                            }else if(data.getCode()==3000) {
                                view.onRelogin();
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

        if (liveSub != null)
            liveSub.dispose();

        if (videoSub != null)
            videoSub.dispose();

        if (monthlySub != null)
            monthlySub.dispose();
    }
}
