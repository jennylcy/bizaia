package com.bizaia.zhongyin.module.discovery.api;

import android.util.Log;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.mine.data.IsAttentionBean;
import com.bizaia.zhongyin.module.discovery.iml.ICollectionView;
import com.bizaia.zhongyin.module.discovery.iml.IDataIsCollection;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/10.
 */

public class IsCollectionAPI {
    private ICollectionView view;
    private IDataIsCollection api;
    private DisposableObserver<IsAttentionBean> subscription;

    public IsCollectionAPI(ICollectionView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataIsCollection.class);
    }



    public DisposableObserver<IsAttentionBean> isCollection(long id,int type) {
        cancel();
        return subscription = api.isCollection(type,id,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<IsAttentionBean>() {
                    @Override
                    public void onNext(IsAttentionBean data) {
                        if (data == null) {
                            view.showCollectionError();
                        } else {
                            if (data.getCode() == 200) {
                              view.showIsCollection(data.isData());
                            } else {
                                view.showCollectionError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showCollectionError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<IsAttentionBean> addCollection(long id, int type, final int operat) {
        Log.e("IsCollectionAPI", "addCollection: ---->"+id+"---"+type );
        cancel();
        return subscription = api.addCollection(id,type,operat,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<IsAttentionBean>() {
                    @Override
                    public void onNext(IsAttentionBean data) {
                        if (data == null) {
                            if(operat==1) {
                                view.showAddCollection(false);
                            }else{
                                view.showDelCollection(false);
                            }
                        } else {
                            if (data.getCode() == 200) {
                                if(operat==1) {
                                    view.showAddCollection(true);
                                }else{
                                    view.showDelCollection(true);
                                }
                            } else {
                                if(operat==1) {
                                    view.showAddCollection(false);
                                }else{
                                    view.showDelCollection(false);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(operat==1) {
                            view.showAddCollection(false);
                        }else{
                            view.showDelCollection(false);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<IsAttentionBean> deletAll(int type, final int operat) {
        cancel();
        return subscription = api.addCollection(type,operat,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<IsAttentionBean>() {
                    @Override
                    public void onNext(IsAttentionBean data) {
                        if (data == null) {
                            if(operat==1) {
                                view.showAddCollection(false);
                            }else{
                                view.showDelCollection(false);
                            }
                        } else {
                            if (data.getCode() == 200) {
                                if(operat==1) {
                                    view.showAddCollection(true);
                                }else{
                                    view.showDelCollection(true);
                                }
                            } else {
                                if(operat==1) {
                                    view.showAddCollection(false);
                                }else{
                                    view.showDelCollection(false);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(operat==1) {
                            view.showAddCollection(false);
                        }else{
                            view.showDelCollection(false);
                        }
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
