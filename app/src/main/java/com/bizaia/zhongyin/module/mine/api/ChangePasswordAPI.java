package com.bizaia.zhongyin.module.mine.api;

import com.bizaia.zhongyin.base.BasePresenter;
import com.bizaia.zhongyin.module.mine.iml.IPasswordChange;
import com.bizaia.zhongyin.module.mine.iml.IPasswordChangeView;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.ErrorUtil;
import com.bizaia.zhongyin.util.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by yan on 2017/5/3 .
 */

public class ChangePasswordAPI implements BasePresenter {
    private IPasswordChangeView mView;

    public ChangePasswordAPI(IPasswordChangeView view) {
        mView = view;
        mView.setPresenter(this);
    }

    public void changePassWord(final String oldPwd, final String newPwd) {
        IPasswordChange iPasswordChange = AppRetrofit.getAppRetrofit().retrofit().create(IPasswordChange.class);
        String oldp = MD5.md5(oldPwd);
        String newp = MD5.md5(newPwd);
        mView.addSubscription(iPasswordChange.changePassword(oldp, newp)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody bean) {
                        try {
                            JSONObject jsonObject = new JSONObject(bean.string());
                            if (jsonObject.getInt("code") != 200) {
                                mView.changePasswordError(ErrorUtil.getMsg(jsonObject.getInt("code")));
                                onComplete();
                                return;
                            }
                            mView.changePasswordSuccess();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        onComplete();
                        mView.changePasswordError();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }

}
