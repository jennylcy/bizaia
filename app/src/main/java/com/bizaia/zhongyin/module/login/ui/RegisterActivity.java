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
import com.bizaia.zhongyin.module.login.data.PlaceBean;
import com.bizaia.zhongyin.module.mine.api.ChangeMailAPI;
import com.bizaia.zhongyin.module.mine.iml.IMailChangeView;
import com.bizaia.zhongyin.repository.ShareSDKConfig;
import com.bizaia.zhongyin.util.ErrorUtil;
import com.bizaia.zhongyin.util.StringUtils;
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

public class RegisterActivity extends BaseActivity implements IMailChangeView {

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

    private Disposable mCodeDisposable;
    private boolean isGetCode;

    private PlaceBean mPlaceBean;

    private Handler mHandler;

    private final int CODE_TRUE = 1;
    private final int ERROR = 3;

    private ChangeMailAPI mAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setViewParent(mBackImg);
        mAPI = new ChangeMailAPI(this);
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
                        RegisterTwoActivity.show(RegisterActivity.this, mPhoneEdit.getText().toString(), mPlaceBean);
                        finish();
                        break;
                    case ERROR:
                        int code = msg.getData().getInt("code");
                        ToastUtils.show(RegisterActivity.this, ErrorUtil.getMsg(code));
                        if (code / 100 == 6) isGetCode = false;
                        break;
                }
            }
        };

        SMSSDK.initSDK(RegisterActivity.this,
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
                            msg.what = 3;
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

        mTitleText.setText(getString(R.string.register_activity_register_title));
    }

    @OnClick({R.id.back_img, R.id.activity_register_place_layout
            , R.id.activity_register_get_code_btn, R.id.activity_register_register_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.activity_register_place_layout:
                startActivityForResult(new Intent(RegisterActivity.this, SelectPlaceActivity.class), 101);
                break;
            case R.id.activity_register_get_code_btn:
                String phone = mPhoneEdit.getText().toString();
                String type = mPlaceText.getText().toString().trim();
                if (!StringUtils.isEmpty(type)&&StringUtils.isEmpty(phone)) {
                    ToastUtils.show(RegisterActivity.this, getString(R.string.register_hint_user_name));
                    return;
                }
                if (StringUtils.isMail(phone)) {
                    getCode(phone, null, 2);
                } else {
                    if (mPlaceBean == null) {
                        ToastUtils.show(RegisterActivity.this, getString(R.string.register_activity_please_select_country));
                        return;
                    }
                    getCode(phone, mPlaceBean.getNumber(), 1);
                }
                break;
            case R.id.activity_register_register_btn:
                String code = mCodeEdit.getText().toString();
                String phone1 = mPhoneEdit.getText().toString();
                if (StringUtils.isEmpty(phone1)) {
                    ToastUtils.show(RegisterActivity.this, getString(R.string.register_hint_user_name));
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.show(RegisterActivity.this, getString(R.string.register_activity_please_input_code));
                } else {
                    if (StringUtils.isMail(phone1)) {
                        mAPI.codeCheck(phone1, 3, code);
                    } else {
                        if (mPlaceBean == null) {
                            return;
                        }
                        SMSSDK.submitVerificationCode(mPlaceBean.getNumber(), mPhoneEdit.getText().toString(), code);
                    }
                }
                break;
        }
    }

    private void getCode(final String phone, final String number, final int type) {
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
                        if (type == 1) {
                            SMSSDK.getVerificationCode(number, phone);
                        } else {
                            mAPI.getCode(phone, 3);
                        }
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
                        mPlaceText.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.black2));
                    }
                }
            }
        }
    }

    @Override
    public void getCodeSuccess(String mail) {
        ToastUtils.show(RegisterActivity.this, getString(R.string.get_code_success));
    }

    @Override
    public void getCodeError() {
        ToastUtils.show(RegisterActivity.this, getString(R.string.get_code_error));
        isGetCode = false;
    }

    @Override
    public void getCodeError(String msg) {
    }

    @Override
    public void changeMailSuccess(String mail) {

    }

    @Override
    public void changeMailError() {

    }

    @Override
    public void changeMailError(String msg) {

    }

    @Override
    public void loginOutTime() {

    }

    @Override
    public void checkCode() {
        RegisterTwoActivity.show(this, mPhoneEdit.getText().toString(), mPlaceBean);
        finish();
    }
}
