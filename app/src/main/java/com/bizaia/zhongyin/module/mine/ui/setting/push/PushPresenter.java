package com.bizaia.zhongyin.module.mine.ui.setting.push;

import android.content.Context;
import android.util.Log;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.mine.ui.setting.SettingAPI;
import com.bizaia.zhongyin.repository.SPConfiguration;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.SPUtils;
import com.bizaia.zhongyin.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by yan on 2017/3/22.
 */

public class PushPresenter implements PushContract.Presenter {
    private PushContract.View view;
    private Context context;

    public PushPresenter(Context context, PushContract.View view) {
        this.view = view;
        this.context = context;
    }

    @Override
    public boolean getPushState() {
        return SPUtils.getBoolean(context, Context.MODE_PRIVATE
                , SPConfiguration.APP_NAME
                , SPConfiguration.SETTING_PUSH_SWITCH
                , true);
    }

    @Override
    public void setPushState(boolean isPush) {
        SPUtils.putBoolean(context
                , Context.MODE_PRIVATE
                , SPConfiguration.APP_NAME
                , SPConfiguration.SETTING_PUSH_SWITCH
                , isPush);
    }

    @Override
    public void requireSwitchState() {
        view.setSwitchState(true);
    }

    @Override
    public void setRemoteSwitchState(final boolean isPush) {
        SettingAPI settingAPI = AppRetrofit.getAppRetrofit().retrofit().create(SettingAPI.class);
        view.addSubscription(settingAPI.pushSwitch(isPush)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody value) {
                        try {
                            JSONObject jsonObject = new JSONObject(value.string());
                            int code = jsonObject.getInt("code");
                            if (code == 200) {
                                view.setSwitchState(isPush);
                                Log.e("remoteSwitch", "remoteSwitch： " + value.string());
                            } else {
                                ToastUtils.showInUIThead(context, context.getString(R.string.remote_setting_error));
                                view.setSwitchState(!isPush);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtils.showInUIThead(context, context.getString(R.string.remote_setting_error));
                        view.setSwitchState(!isPush);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
