package com.bizaia.zhongyin.module.login.api;


import com.bizaia.zhongyin.module.live.data.ThumbState;
import com.bizaia.zhongyin.module.login.data.OtherLoginBean;
import com.bizaia.zhongyin.module.login.data.UserBean;
import com.bizaia.zhongyin.module.login.iml.IDataLogin;
import com.bizaia.zhongyin.module.login.iml.ILoginView;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.ErrorUtil;
import com.bizaia.zhongyin.util.MD5;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by zyz on 2017/3/1.
 */

public class LoginAPI {
    private ILoginView view;
    private IDataLogin api;
    private DisposableObserver<UserBean> subscription;
    private DisposableObserver<ThumbState> find;

    public LoginAPI(ILoginView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataLogin.class);
    }

    public DisposableObserver<UserBean> login(String account, String pwd, String nid) {
        return subscription = api.login(account, MD5.md5(pwd), nid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserBean>() {
                    @Override
                    public void onNext(UserBean data) {
                        if (data == null) {
                            view.showError();
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData() == null) {
                                    view.showError();
                                } else {
                                    view.showLogin(data);
                                }
                            } else {
                                view.showError(ErrorUtil.getMsg(data.getCode()));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<ThumbState> find(String account, String pwd, String type, String code) {
        cancelFind();
        String transpwd = MD5.md5(pwd);
        return find = api.findPhone(account, transpwd, type, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ThumbState>() {
                    @Override
                    public void onNext(ThumbState data) {
                        if (data == null) {
                            view.showError();
                        } else {
                            if (data.getCode() == 200) {
                                view.showFindSuccess();
                            } else {
                                view.showError(ErrorUtil.getMsg(data.getCode()));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public Disposable otherLogin(String openId, String token, final int otherLoginType) {
        return api.otherLogin(openId, token, otherLoginType)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<OtherLoginBean>() {
                    @Override
                    protected void onStart() {
                        request(1);
                    }

                    @Override
                    public void onNext(OtherLoginBean data) {
                        if (data == null) {
                            view.showError();
                        } else {
                            if (data.getCode() == 200) {
                                UserBean bean = new UserBean();
                                bean.setCode(data.getCode());
                                UserBean.DataEntity entity = new UserBean.DataEntity();
                                entity.setId(data.getData().getId());
                                entity.setMobile("");
                                entity.setNickname(data.getData().getNickname());
                                entity.setUserType(otherLoginType);
                                entity.setIsLocked(false);
                                entity.setAvatarUrl(data.getData().getAvatarUrl());
                                entity.setOrgId(0);
                                entity.setAuthToken(data.getData().getAuthToken());
                                entity.setRemoteIp("");
                                entity.setMsgSwitch(true);
                                entity.setMemberSig("");
                                entity.setMemberSigExpired(0);
                                entity.setInterAreaCode("");
                                bean.setData(entity);
                                view.showLogin(bean);
                            } else {
                                view.showError(ErrorUtil.getMsg(data.getCode()));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        view.showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void cancel() {
        if (subscription != null) subscription.dispose();
    }

    public void cancelFind() {
        if (find != null) find.dispose();
    }
}
