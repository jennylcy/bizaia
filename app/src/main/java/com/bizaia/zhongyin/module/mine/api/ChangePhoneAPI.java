package com.bizaia.zhongyin.module.mine.api;

import com.bizaia.zhongyin.module.mine.data.ChangeInfoBean;
import com.bizaia.zhongyin.module.mine.iml.IDataChangeInfo;
import com.bizaia.zhongyin.module.mine.iml.IPhoneChangeView;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.ErrorUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by wujiaquan on 2017/4/18.
 */

public class ChangePhoneAPI {
    private IPhoneChangeView mView;
    private IDataChangeInfo api;
    private Disposable mDisposable;

    public ChangePhoneAPI(IPhoneChangeView view) {
        mView = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataChangeInfo.class);
    }

    public void changePhone(final String phone, String areaCode) {
        if (mDisposable != null) mDisposable.dispose();
        mDisposable = api.changePhone(phone, areaCode)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<ChangeInfoBean>() {
                    @Override
                    protected void onStart() {
                        request(1);
                    }

                    @Override
                    public void onNext(ChangeInfoBean bean) {
                        if (bean == null) {
                            mView.changePhoneError();
                        } else {
                            if (bean.getCode() == 200) {
                                mView.changePhoneSuccess(phone);
                            } else if (bean.getCode() == 3000) {
                                mView.loginOutTime();
                            } else {
                                mView.changePhoneError(ErrorUtil.getMsg(bean.getCode()));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        mView.changePhoneError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void cancel(){
        if (mDisposable != null) mDisposable.dispose();
    }
}
