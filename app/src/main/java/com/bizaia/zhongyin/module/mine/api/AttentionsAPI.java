package com.bizaia.zhongyin.module.mine.api;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.mine.data.AttentionsBean;
import com.bizaia.zhongyin.module.mine.iml.IDataAttentions;
import com.bizaia.zhongyin.module.mine.iml.IMyAttentionsView;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/10.
 */

public class AttentionsAPI {
    private IMyAttentionsView view;
    private IDataAttentions api;
    private DisposableObserver<AttentionsBean> subscription;

    public AttentionsAPI(IMyAttentionsView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataAttentions.class);
    }


    public DisposableObserver<AttentionsBean> getAttentions(int pageNum,int pageSize) {
        cancel();
        return subscription = api.getAttentions(pageNum,pageSize,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<AttentionsBean>() {
                    @Override
                    public void onNext(AttentionsBean data) {
                        if (data == null) {
                            view.showAttentionsError();
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData() == null) {
                                    view.showAttentionsError();
                                } else {
                                    view.showAttions(data);
                                }
                            }else if(data.getCode()==3000) {
                                view.onRelogin();
                            } else {
                                view.showAttentionsError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showAttentionsError();
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
