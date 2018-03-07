package com.bizaia.zhongyin.module.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.login.api.LoginAPI;
import com.bizaia.zhongyin.module.login.data.PlaceBean;
import com.bizaia.zhongyin.module.login.data.UserBean;
import com.bizaia.zhongyin.module.login.iml.ILoginView;
import com.bizaia.zhongyin.repository.ShareSDKConfig;
import com.bizaia.zhongyin.util.ErrorUtil;
import com.bizaia.zhongyin.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
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
import io.reactivex.subscribers.DisposableSubscriber;

public class FindPhoneActivity extends BaseActivity implements ILoginView {

    @BindView(R.id.back_img)
    ImageView mBackImg;
    @BindView(R.id.title_text)
    TextView mTitleText;
    @BindView(R.id.activity_register_phone_edit)
    EditText mPhoneEdit;
    @BindView(R.id.activity_register_get_code_btn)
    Button mGetCodeBtn;
    @BindView(R.id.activity_register_code_edit)
    EditText mCodeEdit;
    @BindView(R.id.activity_register_register_btn)
    Button mRegisterBtn;
    @BindView(R.id.activity_register_place_layout)
    LinearLayout mActivityRegisterPlaceLayout;
    @BindView(R.id.activity_register_place_text)
    TextView mPlaceText;

    @BindView(R.id.activity_find_pwd)
    EditText activity_find_pwd;
    @BindView(R.id.activity_pwdT)
    EditText activity_pwdT;

    private Disposable mCodeDisposable;
    private boolean isGetCode;

    private PlaceBean mPlaceBean;

    private Handler mHandler;

    private final int CODE_TRUE = 1;
    private final int ERROR = 3;

    private LoginAPI loginAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_phone);
        ButterKnife.bind(this);
        setViewParent(mBackImg);
        loginAPI = new LoginAPI(this);
        init();
    }

    @Override
    protected void onDestroy() {
        isGetCode = false;
        if (mCodeDisposable != null) mCodeDisposable.dispose();
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }

    private void init() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case CODE_TRUE:
                        startActivity(new Intent(FindPhoneActivity.this, RegisterTwoActivity.class)
                                .putExtra("phone", mPhoneEdit.getText().toString())
                                .putExtra(PlaceBean.class.getName(), mPlaceBean));
                        finish();
                        break;
                    case ERROR:
                        int code = msg.getData().getInt("code");
                        ToastUtils.show(FindPhoneActivity.this, ErrorUtil.getMsg(code));
                        if (code / 100 == 6) isGetCode = false;
                        break;
                }
            }
        };

        SMSSDK.initSDK(FindPhoneActivity.this,
                ShareSDKConfig.APP_KEY_FOR_SMS,
                ShareSDKConfig.APP_SECRET_FOR_SMS);
        SMSSDK.registerEventHandler(new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    switch (event) {
                        case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                            //提交验证码成功
                            for (Object o : ((HashMap<String, Object>) data).entrySet()) {
                                Map.Entry entry = (Map.Entry) o;
                                Log.d("TAG", "key : " + entry.getKey());
                                Log.d("TAG", "val : " + entry.getValue());
                            }
                            //phone country
                            String phone = (String) ((HashMap<String, Object>) data).get("phone");
                            String country = (String) ((HashMap<String, Object>) data).get("country");
                            if (phone.equals(mPhoneEdit.getText().toString())
                                    && mPlaceBean != null && country.equals(mPlaceBean.getNumber())) {
                                mHandler.sendEmptyMessage(1);
                            }
                            break;
                        case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                            //获取验证码成功
                            Log.d("TAG", "EVENT_GET_VERIFICATION_CODE");
                            break;
                        case SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES:
                            //返回支持发送验证码的国家列表
//                            for (HashMap<String, Object> hashMap : (ArrayList<HashMap<String, Object>>) data) {
//                                for (Object o : hashMap.entrySet()) {
//                                    Map.Entry entry = (Map.Entry) o;
//                                    Log.d("TAG", "key : " + entry.getKey());
//                                    Log.d("TAG", "val : " + entry.getValue());
//                                }
//                                //zone rule
//                            }
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
                            bundle.putInt("code", status);
                            msg.setData(bundle);
                            mHandler.sendMessage(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        mTitleText.setText(getString(R.string.login_pop_forget));
    }

    @OnClick({R.id.back_img, R.id.activity_register_place_layout
            , R.id.activity_register_get_code_btn, R.id.activity_register_register_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.activity_register_place_layout:
                startActivityForResult(new Intent(FindPhoneActivity.this, SelectPlaceActivity.class), 101);
                break;
            case R.id.activity_register_get_code_btn:
                if (mPlaceBean == null) {
                    ToastUtils.show(FindPhoneActivity.this, getString(R.string.register_activity_please_select_country));
                    return;
                }
                String phone = mPhoneEdit.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.show(FindPhoneActivity.this, getString(R.string.register_activity_please_input_phone));
                    return;
                }
                getCode(phone, mPlaceBean.getNumber());
                break;
            case R.id.activity_register_register_btn:
                String code = mCodeEdit.getText().toString();
                String phonet = mPhoneEdit.getText().toString();
                String pwd = activity_find_pwd.getText().toString().trim();
                String pwdt = activity_pwdT.getText().toString().trim();
                if (mPlaceBean == null) {
                    ToastUtils.show(FindPhoneActivity.this, getString(R.string.register_activity_please_select_country));
                    return;
                }
                if (TextUtils.isEmpty(phonet)) {
                    ToastUtils.show(FindPhoneActivity.this, getString(R.string.register_activity_please_input_phone));
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.show(FindPhoneActivity.this, getString(R.string.register_activity_please_input_code));
                    return;
                }

                if (TextUtils.isEmpty(pwd) || pwd.length() < 6) {
                    ToastUtils.show(FindPhoneActivity.this, getString(R.string.login_pwd_lenth_err));
                    return;
                }

                if (TextUtils.isEmpty(pwdt) || pwdt.length() < 6) {
                    ToastUtils.show(FindPhoneActivity.this, getString(R.string.login_pwd_lenth_err));
                    return;
                }

                if (!pwd.equals(pwdt)) {
                    ToastUtils.show(FindPhoneActivity.this, getString(R.string.pasword_is_not_same));
                    return;
                }

                loginAPI.find(phonet, pwd, "1", code);

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
                        mGetCodeBtn.setEnabled(false);
                        SMSSDK.getVerificationCode(number, phone);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (isGetCode) {
                            request(1);
                            String codeText = aLong + " S";
                            mGetCodeBtn.setText(codeText);
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
                        mGetCodeBtn.setEnabled(true);
                        mGetCodeBtn.setText(getString(R.string.register_activity_get_code_again));
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
                if (data != null) {
                    mPlaceBean = (PlaceBean) data.getSerializableExtra(PlaceBean.class.getName());
                    if (mPlaceBean != null) {
                        mPlaceText.setText(mPlaceBean.getName() + "  +" + mPlaceBean.getNumber());
                        mPlaceText.setTextColor(ContextCompat.getColor(FindPhoneActivity.this, R.color.black2));
                    }
                }
            }
        }
    }

    @Override
    public void showLogin(UserBean userBean) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showError() {
    }

    @Override
    public void showFindSuccess() {
        ToastUtils.show(FindPhoneActivity.this, getString(R.string.find_pwd_success));
        finish();
    }
}
