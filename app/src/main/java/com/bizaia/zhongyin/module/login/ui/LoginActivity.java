package com.bizaia.zhongyin.module.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.live.action.LoginSuccessAction;
import com.bizaia.zhongyin.module.live.imhelper.IMHelper;
import com.bizaia.zhongyin.module.login.api.LoginAPI;
import com.bizaia.zhongyin.module.login.data.UserBean;
import com.bizaia.zhongyin.module.login.iml.ILoginView;
import com.bizaia.zhongyin.repository.ShareSDKConfig;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.ForgetPop;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.line.Line;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends BaseActivity implements ILoginView {

    @BindView(R.id.back_img)
    ImageView mBackImg;
    @BindView(R.id.activity_login_name_edit)
    EditText mNameEdit;
    @BindView(R.id.activity_login_password_edit)
    EditText mPasswordEdit;
    @BindView(R.id.activity_login_forget_password_text)
    TextView mForgetPasswordText;
    @BindView(R.id.activity_login_login_btn)
    Button mLoginBtn;
    @BindView(R.id.activity_login_register_btn)
    Button mRegisterBtn;
    @BindView(R.id.activity_login_as_guest_text)
    TextView mAsGuestText;
    @BindView(R.id.activity_login_line_img)
    ImageView mLineImg;
    @BindView(R.id.activity_login_face_book_img)
    ImageView mFaceBookImg;
    @BindView(R.id.activity_login_wexin_img)
    ImageView mWexinImg;
    @BindView(R.id.title_text)
    TextView mTitleText;
    @BindView(R.id.linParent)
    View linParent;

    private LoginAPI loginAPI;
    private ForgetPop forgetPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setViewParent(mBackImg);
        initView();
    }

    @Override
    protected void onResume() {
        if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
            finish();
        }
        super.onResume();
    }

    private void initView() {
        mTitleText.setText(getString(R.string.login_activity_login_title));
        loginAPI = new LoginAPI(this);
    }

    private void showPop() {
        forgetPop = new ForgetPop(getApplicationContext(), linParent, R.layout.pop_forget_pwd) {
            @Override
            public void viewInit() {
                findViewById(R.id.ivPopClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forgetPop.dismiss();
                    }
                });
                findViewById(R.id.tvPhone).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forgetPop.dismiss();
                        startActivity(new Intent(LoginActivity.this, FindPhoneActivity.class));
                    }
                });
                findViewById(R.id.tvPopEmail).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forgetPop.dismiss();
                        startActivity(new Intent(LoginActivity.this, EmailFindActivity.class));
                    }
                });
            }
        };
        forgetPop.show();
    }

    @OnClick({R.id.back_img, R.id.activity_login_forget_password_text
            , R.id.activity_login_login_btn, R.id.activity_login_register_btn, R.id.activity_login_as_guest_text
            , R.id.activity_login_line_img, R.id.activity_login_face_book_img, R.id.activity_login_wexin_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.activity_login_forget_password_text:
                showPop();
                break;
            case R.id.activity_login_login_btn:
                String account = mNameEdit.getText().toString().trim();
                String pwd = mPasswordEdit.getText().toString().trim();
                if (StringUtils.isEmpty(account)) {
                    ToastUtils.show(LoginActivity.this, R.string.login_name_null);
                    return;
                }
                if (StringUtils.isEmpty(pwd)) {
                    ToastUtils.show(LoginActivity.this, R.string.login_pwd_null);
                    return;
                }
                if (pwd.length() < 6) {
                    ToastUtils.show(LoginActivity.this, R.string.login_pwd_lenth_err);
                    return;
                }
                loginAPI.login(account, pwd, FirebaseInstanceId.getInstance().getToken());
                break;
            case R.id.activity_login_register_btn:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.activity_login_as_guest_text:
                finish();
                break;
            case R.id.activity_login_line_img:
                otherLogin(Line.NAME);
                break;
            case R.id.activity_login_face_book_img:
//                otherLogin(Facebook.NAME);
                break;
            case R.id.activity_login_wexin_img:
                otherLogin(Wechat.NAME);
                break;
        }
    }

    private void otherLogin(final String loginMode) {
        ShareSDK.initSDK(LoginActivity.this, ShareSDKConfig.APP_KEY_FOR_LOGIN);
        Platform platform = ShareSDK.getPlatform(loginMode);
        if (platform.isAuthValid()) platform.removeAccount(true);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                String openID = platform.getDb().getUserId();
                String token = platform.getDb().getToken();
                int otherLoginType = -1;
//                if (loginMode.equals(Facebook.NAME)) {
//                    otherLoginType = 1;
//                } else
                if (loginMode.equals(Line.NAME)) {
                    otherLoginType = 2;
//                    openID = ShareSDKConfig.LINE_OPEN_ID;
                } else if (loginMode.equals(Wechat.NAME)) {
                    otherLoginType = 3;
//                    openID = ShareSDKConfig.WEIXIN_OPEN_ID;
                }
                if (otherLoginType != -1) {
                    loginAPI.otherLogin(openID, token, otherLoginType);
                } else {
                    ToastUtils.show(LoginActivity.this, R.string.login_activity_other_login_error);
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable t) {
                t.printStackTrace();
                ToastUtils.show(LoginActivity.this, R.string.login_activity_other_login_error);
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        platform.showUser(null);
    }

    @Override
    public void showLogin(UserBean userBean) {
        ToastUtils.show(LoginActivity.this, R.string.login_success);
        BIZAIAApplication.getInstance().setToken(userBean.getData().getAuthToken());
        BIZAIAApplication.getInstance().setUserBean(userBean.getData());
        BIZAIAApplication.getInstance().setEmail(userBean.getData().getEmail());
        IMHelper.getInstance().init(getApplicationContext());
        if (userBean.getData().getUserType() == 1) {
            RxBus.getInstance().post(new LoginSuccessAction(true));
        } else {
            RxBus.getInstance().post(new LoginSuccessAction(false));
        }
        finish();
    }


    @Override
    public void showError(String msg) {
        ToastUtils.show(LoginActivity.this, msg);
    }

    @Override
    public void showError() {
        ToastUtils.show(LoginActivity.this, R.string.login_error);
    }

    @Override
    public void showFindSuccess() {

    }
}
