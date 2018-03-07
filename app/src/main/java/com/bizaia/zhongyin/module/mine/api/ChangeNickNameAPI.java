package com.bizaia.zhongyin.module.mine.api;

import com.bizaia.zhongyin.module.mine.data.ChangeInfoBean;
import com.bizaia.zhongyin.module.mine.iml.IDataChangeInfo;
import com.bizaia.zhongyin.module.mine.iml.INickNameChangeView;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.ErrorUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by wujiaquan on 2017/4/24.
 */

public class ChangeNickNameAPI {
    private INickNameChangeView mView;
    private IDataChangeInfo api;
    private Disposable mDisposable;

    public ChangeNickNameAPI(INickNameChangeView view) {
        mView = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataChangeInfo.class);
    }

    public void changeNickName(final String nickName) {
        cancel();
        mDisposable = api.changeNickName(nickName)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<ChangeInfoBean>() {
                    @Override
                    protected void onStart() {
                        request(1);
                    }

                    @Override
                    public void onNext(ChangeInfoBean bean) {
                        if (bean != null) {
                            if (bean.getCode() == 200) {
                                mView.changeNickNameSuccess(nickName);
                            } else if (bean.getCode() == 3000) {
                                mView.loginOutTime();
                            } else {
                                mView.changeNickNameError(ErrorUtil.getMsg(bean.getCode()));
                            }
                        } else {
                            mView.changeNickNameError();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        onComplete();
                        mView.changeNickNameError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void cancel() {
        if (mDisposable != null) mDisposable.dispose();
    }
}
