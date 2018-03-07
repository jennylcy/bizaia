package com.bizaia.zhongyin.module.live.api;

import android.util.Log;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.live.iml.IDataStartStream;
import com.bizaia.zhongyin.module.live.iml.IStartStreamView;
import com.bizaia.zhongyin.module.login.data.UserBean;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/4/11.
 */

public class StartStreamAPI {

    private IStartStreamView view;
    private IDataStartStream api;
    private DisposableObserver<UserBean> recom;
    private DisposableObserver<UserBean> end;


    public StartStreamAPI(IStartStreamView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataStartStream.class);
    }

    public DisposableObserver<UserBean> start(long id) {
        cancel();
        Log.e("StartStreamAPI", "start: ----current_time->" +System.currentTimeMillis()+"-----"+id);
        return recom = api.start(id,
                2,System.currentTimeMillis(),
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserBean>() {
                    @Override
                    public void onNext(UserBean data) {
                        if (data == null) {
                            view.showLiveStateError(-1,"数据为空");
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData() == null) {
                                    view.showLiveStateError(200,data.getMsg());
                                } else {
                                    view.showLiveState(data);
                                }
                            } else {
                                view.showLiveStateError(data.getCode(),data.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLiveStateError(-1,"数据为空");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<UserBean> end(long id) {
        cancelEnd();
        return end = api.end(id,
               System.currentTimeMillis())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserBean>() {
                    @Override
                    public void onNext(UserBean data) {
                        if (data == null) {
                            view.showLiveStateError(-1,"数据为空");
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData() == null) {
                                    view.showLiveStateError(200,data.getMsg());
                                } else {
                                    view.showLiveState(data);
                                }
                            } else {
                                view.showLiveStateError(data.getCode(),data.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLiveStateError(-1,"数据为空");;
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void cancel() {
        if (recom != null)
            recom.dispose();

    }

    public void cancelEnd(){
        if (end != null)
            end.dispose();
    }

}
