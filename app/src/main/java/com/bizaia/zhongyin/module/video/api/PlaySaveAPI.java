package com.bizaia.zhongyin.module.video.api;

import com.bizaia.zhongyin.module.video.data.SaveResultBean;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/10.
 */

public class PlaySaveAPI {
    private ISavePlayView view;
    private IDataSavePlay api;
    private DisposableObserver<SaveResultBean> subscription;

    public PlaySaveAPI(ISavePlayView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataSavePlay.class);
    }



    public DisposableObserver<SaveResultBean> save(long id, int type,int time) {
        cancel();
        return subscription = api.save(id,
                type,time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SaveResultBean>() {
                    @Override
                    public void onNext(SaveResultBean data) {
                        if (data == null) {
                            view.showSaveError();
                        } else {
                            if (data.getCode() == 200) {
                              view.showSave(data);
                            } else if(data.getCode()==3000){
                                view.showSaveError();
                            }else {
                                view.showSaveError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showSaveError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<SaveResultBean> get(long id,  int type) {
        cancel();
        return subscription = api.get(id,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SaveResultBean>() {
                    @Override
                    public void onNext(SaveResultBean data) {
                        if (data == null) {
                          view.showSaveError();
                        } else {
                            if (data.getCode() == 200) {
                             view.showSaveResult(data);
                            } else {
                                view.showSaveError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showSaveError();
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
