package com.bizaia.zhongyin.module.mine.api;

import com.bizaia.zhongyin.module.mine.data.ChangeInfoBean;
import com.bizaia.zhongyin.module.mine.iml.IDataChangeInfo;
import com.bizaia.zhongyin.module.mine.iml.IMailChangeView;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.ErrorUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by wujiaquan on 2017/4/24.
 */

public class ChangeMailAPI {
    private IMailChangeView mView;
    private IDataChangeInfo api;
    private Disposable mCodeDisposable;
    private Disposable mChangeMailDisposable;

    public ChangeMailAPI(IMailChangeView view) {
        mView = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataChangeInfo.class);
    }

    public void getCode(final String mail, int type) {
        if (mCodeDisposable != null) mCodeDisposable.dispose();
        mCodeDisposable = api.getMailCode(mail, type)
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
                                mView.getCodeSuccess(mail);
                            } else if (bean.getCode() == 3000) {
                                mView.loginOutTime();
                            } else {
                                mView.getCodeError(ErrorUtil.getMsg(bean.getCode()));
                            }
                        } else {
                            mView.getCodeError();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        onComplete();
                        mView.getCodeError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void codeCheck(final String mail, int type, String code) {
        if (mCodeDisposable != null) mCodeDisposable.dispose();
        mCodeDisposable = api.codeCheck(mail, type, code)
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
                                mView.checkCode();
                            } else if (bean.getCode() == 3000) {
                                mView.loginOutTime();
                            } else {
                                mView.getCodeError(ErrorUtil.getMsg(bean.getCode()));
                            }
                        } else {
                            mView.getCodeError();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        onComplete();
                        mView.getCodeError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void changeMail(final String mail, String code) {
        if (mChangeMailDisposable != null) mChangeMailDisposable.dispose();
        mChangeMailDisposable = api.changeMail(mail, code)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<ChangeInfoBean>() {
                    @Override
                    public void onNext(ChangeInfoBean bean) {
                        if (bean != null) {
                            if (bean.getCode() == 200) {
                                mView.changeMailSuccess(mail);
                            } else {
                                mView.changeMailError(ErrorUtil.getMsg(bean.getCode()));
                            }
                        } else {
                            mView.changeMailError();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        onComplete();
                        mView.getCodeError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void cancel() {
        if (mCodeDisposable != null) mCodeDisposable.dispose();
        if (mChangeMailDisposable != null) mChangeMailDisposable.dispose();
    }
}
