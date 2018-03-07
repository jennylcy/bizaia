package com.bizaia.zhongyin.module.live.api;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.live.data.ThumbState;
import com.bizaia.zhongyin.module.live.iml.IDataLiveState;
import com.bizaia.zhongyin.module.live.iml.ILiveThumbUpView;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/4/11.
 */

public class LiveThumbUpAPI {

    private ILiveThumbUpView view;
    private IDataLiveState api;
    private DisposableObserver<ThumbState> up;
    private DisposableObserver<ThumbState>down;
    private DisposableObserver<ThumbState> isCheck;


    public LiveThumbUpAPI(ILiveThumbUpView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataLiveState.class);
    }

    public DisposableObserver<ThumbState> thumbUp(long id) {
        cancel();
        return up = api.thumbUp(id,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ThumbState>() {
                    @Override
                    public void onNext(ThumbState data) {
                        if (data == null) {
                            view.showThumbUpError(-1,"数据为空");
                        } else {
                            if (data.getCode() == 200) {
                                view.showThumbUp(data);
                            } else if(data.getCode()==3000){
                                view.onRelogin();
                            } else {
                                view.showThumbUpError(data.getCode(),data.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showThumbUpError(-1,"数据为空");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<ThumbState> thumbDown(long id) {
        cancelDown();
        return down = api.thumbDown(id,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ThumbState>() {
                    @Override
                    public void onNext(ThumbState data) {
                        if (data == null) {
                            view.showThumbUpError(-1,"数据为空");
                        } else {
                            if (data.getCode() == 200) {
                                    view.showThumbDown(data);

                            } else if(data.getCode()==3000){
                                view.onRelogin();
                            } else {
                                view.showThumbUpError(data.getCode(),data.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showThumbUpError(-1,"数据为空");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<ThumbState> isThumb(long id) {
        cancelIsCheck();
        return isCheck = api.getThumbState(id,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ThumbState>() {
                    @Override
                    public void onNext(ThumbState data) {
                        if (data == null) {
                            view.showThumbUpError(-1,"数据为空");
                        } else {
                            if (data.getCode() == 200) {
                                    view.showThumbState(data);
                            } else if(data.getCode()==3000){
                                 view.onRelogin();
                            }else {
                                view.showThumbUpError(data.getCode(),data.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showThumbUpError(-1,"数据为空");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void cancel() {
        if (up != null)
            up.dispose();
    }
    public void cancelDown() {
        if (down != null)
            down.dispose();
    }
    public void cancelIsCheck() {
        if (isCheck != null)
            isCheck.dispose();
    }

}
