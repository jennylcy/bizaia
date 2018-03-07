package com.bizaia.zhongyin.module.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.login.data.PlaceBean;
import com.bizaia.zhongyin.module.login.data.UserBean;
import com.bizaia.zhongyin.module.login.ui.SelectPlaceActivity;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.mine.api.ChangePhoneAPI;
import com.bizaia.zhongyin.module.mine.iml.IPhoneChangeView;
import com.bizaia.zhongyin.repository.ShareSDKConfig;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by zyz on 2017/3/13.
 */

public class PhoneChangeActivity extends BaseActivity implements IPhoneChangeView {

    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.etMail)
    EditText etPhone;
    @BindView(R.id.tvCode)
    TextView tvCode;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.btnChange)
    Button btnChange;
    @BindView(R.id.ivCall)
    ImageView ivCall;
    @BindView(R.id.activity_register_place_text)
    TextView mPlaceText;

    private UserBean.DataEntity userBean;

    private Disposable mCodeDisposable;
    private boolean isGetCode;

    private PlaceBean mPlaceBean;

    private Handler mHandler;

    private final int CODE_TRUE = 1;
    private final int ERROR = 3;

    private ChangePhoneAPI mAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_change);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        userBean = BIZAIAApplication.getInstance().getUserBean();
        if (userBean == null) {
            reLogin();
            finish();
            return;
        }

        mAPI = new ChangePhoneAPI(this);

        tvTitle.setText(R.string.mine_phone_change);
        ivCall.setVisibility(View.GONE);

        SMSSDK.initSDK(PhoneChangeActivity.this,
                ShareSDKConfig.APP_KEY_FOR_SMS,
                ShareSDKConfig.APP_SECRET_FOR_SMS);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case CODE_TRUE:
                        mAPI.changePhone(etPhone.getText().toString(), mPlaceBean.getNumber());
                        break;
                    case ERROR:
                        ToastUtils.show(PhoneChangeActivity.this, msg.getData().getString("detail"));
                        break;
                }
            }
        };

        SMSSDK.registerEventHandler(new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    switch (event) {
                        case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                            //提交验证码成功
                            String phone = (String) ((HashMap<String, Object>) data).get("phone");
                            String country = (String) ((HashMap<String, Object>) data).get("country");
                            if (phone.equals(etPhone.getText().toString())
                                    && mPlaceBean != null && country.equals(mPlaceBean.getNumber())) {
                                mHandler.sendEmptyMessage(CODE_TRUE);
                            }
                            break;
                        default:
                    }
                } else {
                    try {
                        Throwable throwable = ((Throwable) data);
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        if (status > 0 && !TextUtils.isEmpty(des)) {
                            Message msg = new Message();
                            msg.what = ERROR;
                            Bundle bundle = new Bundle();
                            bundle.putString("detail", des);
                            msg.setData(bundle);
                            mHandler.sendMessage(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        addSubscription(RxBus.getInstance().getEvent(ForceOffLine.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ForceOffLine>() {
                    @Override
                    public void onNext(ForceOffLine value) {
                        reLogin();
                        finish();
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        if (mCodeDisposable != null) {
            mCodeDisposable.dispose();
            isGetCode = false;
        }
        if(mAPI!=null)
        mAPI.cancel();
        super.onDestroy();
    }

    @OnClick({R.id.back_img, R.id.tvCode, R.id.btnChange, R.id.place_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.tvCode:
                if (mPlaceBean == null) {
                    ToastUtils.show(PhoneChangeActivity.this, getString(R.string.register_activity_please_select_country));
                    return;
                }
                String phone = etPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.show(PhoneChangeActivity.this, getString(R.string.register_activity_please_input_phone));
                    return;
                }
                getCode(phone, mPlaceBean.getNumber());
                break;
            case R.id.btnChange:
                String phones = etPhone.getText().toString();
                if (TextUtils.isEmpty(phones)) {
                    ToastUtils.show(PhoneChangeActivity.this, getString(R.string.register_activity_please_input_phone));
                    return;
                }
                String code = etCode.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.show(PhoneChangeActivity.this, getString(R.string.register_activity_please_input_code));
                } else {
                    SMSSDK.submitVerificationCode(mPlaceBean.getNumber(), etPhone.getText().toString(), code);
                }
                break;
            case R.id.place_layout:
                startActivityForResult(new Intent(PhoneChangeActivity.this, SelectPlaceActivity.class), 101);
                break;
            default:
                break;
        }
    }

    private void getCode(final String phone, final String number) {
        mCodeDisposable = Flowable.interval(0, 1, TimeUnit.SECONDS)
                .take(60)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return 60 - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<Long>() {
                    @Override
                    protected void onStart() {
                        request(1);
                        isGetCode = true;
                        tvCode.setEnabled(false);
                        SMSSDK.getVerificationCode(number, phone);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (isGetCode) {
                            request(1);
                            String codeText = aLong + " S";
                            tvCode.setText(codeText);
                        } else {
                            onComplete();
                            mCodeDisposable.dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        onComplete();
                    }

                    @Override
                    public void onComplete() {
                        tvCode.setEnabled(true);
                        tvCode.setText(getString(R.string.register_activity_get_code_again));
                        isGetCode = false;
                    }
                });
        addSubscription(mCodeDisposable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == 101) {
                mPlaceBean = (PlaceBean) data.getSerializableExtra(PlaceBean.class.getName());
                if (mPlaceBean != null) {
                    mPlaceText.setText(mPlaceBean.getName() + "  " + mPlaceBean.getNumber());
                    mPlaceText.setTextColor(ContextCompat.getColor(PhoneChangeActivity.this, R.color.cr595959));
                }
            }
        }
    }

    @Override
    public void changePhoneSuccess(String phone) {
        ToastUtils.show(PhoneChangeActivity.this, getString(R.string.password_change_success));
        BIZAIAApplication application = BIZAIAApplication.getInstance();
        userBean.setMobile(phone);
        application.setUserBean(userBean);
        finish();
    }

    @Override
    public void changePhoneError() {
        ToastUtils.show(PhoneChangeActivity.this, getString(R.string.phone_change_error));
    }

    @Override
    public void changePhoneError(String msg) {
        ToastUtils.show(PhoneChangeActivity.this, msg);
    }

    @Override
    public void loginOutTime() {
        reLogin();
        finish();
    }
}
