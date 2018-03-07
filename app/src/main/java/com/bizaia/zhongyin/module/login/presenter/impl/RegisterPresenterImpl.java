package com.bizaia.zhongyin.module.login.presenter.impl;

import com.bizaia.zhongyin.module.login.data.RegisterBean;
import com.bizaia.zhongyin.module.login.iml.IDataRegister;
import com.bizaia.zhongyin.module.login.iml.IRegisterView;
import com.bizaia.zhongyin.module.login.presenter.RegisterPresenter;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.ErrorUtil;
import com.bizaia.zhongyin.util.MD5;
import com.bizaia.zhongyin.util.StringUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by wujiaquan on 2017/4/17.
 */

public class RegisterPresenterImpl implements RegisterPresenter {

    private IRegisterView mView;
    private IDataRegister api;

    public RegisterPresenterImpl(IRegisterView view) {
        mView = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataRegister.class);
    }

    @Override
    public Disposable register(String phone, String password, String nickName, String areaCode) {
        int type = 1;
        if (StringUtils.isMail(phone))
            type = 2;
        return api.login(phone, MD5.md5(password), type, nickName, areaCode)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<RegisterBean>() {
                    @Override
                    protected void onStart() {
                        request(1);
                    }

                    @Override
                    public void onNext(RegisterBean bean) {
                        if (bean == null) {
                            mView.registerError();
                        } else {
                            if (bean.getCode() != 200) {
                                mView.registerError(ErrorUtil.getMsg(bean.getCode()));
                            } else {
                                mView.registerSuccess(bean);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        onComplete();
                        mView.registerError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
