package com.bizaia.zhongyin.module.live.api;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.live.data.LiveState;
import com.bizaia.zhongyin.module.live.iml.IDataLiveState;
import com.bizaia.zhongyin.module.live.iml.ILiveStateView;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/4/11.
 */

public class LiveStateAPI {

    private ILiveStateView view;
    private IDataLiveState api;
    private DisposableObserver<LiveState> recom;


    public LiveStateAPI(ILiveStateView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataLiveState.class);
    }

    public DisposableObserver<LiveState> getDetail(long id) {
        cancel();
        return recom = api.getState(id,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LiveState>() {
                    @Override
                    public void onNext(LiveState data) {
                        if (data == null) {
                            view.showLiveStateError(-1,"数据为空");
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData() == null) {
                                    view.showLiveStateError(data.getCode(),data.getMsg());
                                } else {
                                    view.showLiveState(data);
                                }
                            } else if(data.getCode()==3000){
                                view.onRelogin();
                            }else {
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


    public void cancel() {
        if (recom != null)
            recom.dispose();

    }

}
