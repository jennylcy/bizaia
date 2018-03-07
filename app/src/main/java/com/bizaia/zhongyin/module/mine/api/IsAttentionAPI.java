package com.bizaia.zhongyin.module.mine.api;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.mine.data.IsAttentionBean;
import com.bizaia.zhongyin.module.mine.iml.IAttentionView;
import com.bizaia.zhongyin.module.mine.iml.IDataIsAttention;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/10.
 */

public class IsAttentionAPI {
    private IAttentionView view;
    private IDataIsAttention api;
    private DisposableObserver<IsAttentionBean> subscription;

    public IsAttentionAPI(IAttentionView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataIsAttention.class);
    }



    public DisposableObserver<IsAttentionBean> isAttention(long id) {
        cancel();
        return subscription = api.isAttention(id,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<IsAttentionBean>() {
                    @Override
                    public void onNext(IsAttentionBean data) {
                        if (data == null) {
                            view.showAttentionError();
                        } else {
                            if (data.getCode() == 200) {
                              view.showIsAttention(data.isData());
                            } else if(data.getCode()==3000){
                                view.onRelogin();
                            }else {
                                view.showAttentionError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showAttentionError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<IsAttentionBean> addAttention(long id, final int type) {
        cancel();
        return subscription = api.addAttention(id,type,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<IsAttentionBean>() {
                    @Override
                    public void onNext(IsAttentionBean data) {
                        if (data == null) {
                            if(type==1)
                                view.showAddAttention(false);
                            else if(type==2)
                                view.showDelAttention(false);
                        } else {
                            if (data.getCode() == 200) {
                                if(type==1)
                                    view.showAddAttention(true);
                                else if(type==2)
                                    view.showDelAttention(true);
                            } else {
                                if(type==1)
                                    view.showAddAttention(false);
                                else if(type==2)
                                    view.showDelAttention(false);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(type==1)
                            view.showAddAttention(false);
                        else if(type==2)
                            view.showDelAttention(false);
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
