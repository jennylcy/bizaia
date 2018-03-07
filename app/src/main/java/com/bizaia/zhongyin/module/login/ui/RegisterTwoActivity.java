package com.bizaia.zhongyin.module.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.bizaia.zhongyin.module.login.data.PlaceBean;
import com.bizaia.zhongyin.module.login.data.RegisterBean;
import com.bizaia.zhongyin.module.login.data.UserBean;
import com.bizaia.zhongyin.module.login.iml.ILoginView;
import com.bizaia.zhongyin.module.login.iml.IRegisterView;
import com.bizaia.zhongyin.module.login.presenter.RegisterPresenter;
import com.bizaia.zhongyin.module.login.presenter.impl.RegisterPresenterImpl;
import com.bizaia.zhongyin.module.mine.ui.PerfectInfoActivity;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.ToastUtils;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterTwoActivity extends BaseActivity implements IRegisterView, ILoginView {

    @BindView(R.id.back_img)
    ImageView mBackImg;
    @BindView(R.id.title_text)
    TextView mTitleText;
    @BindView(R.id.nick_name_edit)
    EditText mNickNameEdit;
    @BindView(R.id.password_one_edit)
    EditText mPasswordOneEdit;
    @BindView(R.id.password_two_edit)
    EditText mPasswordTwoEdit;
    @BindView(R.id.activity_register_register_btn)
    Button mRegisterBtn;

    private String phone;
    private PlaceBean mPlaceBean;

    private RegisterPresenter mPresenter;
    private LoginAPI loginAPI;

    public static void show(Context context, String phone, PlaceBean placeBean) {
        context.startActivity(new Intent(context, RegisterTwoActivity.class)
                .putExtra("phone", phone)
                .putExtra(PlaceBean.class.getName(), placeBean));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);
        ButterKnife.bind(this);
        setViewParent(mBackImg);
        init();
    }

    private void init() {
        mPresenter = new RegisterPresenterImpl(this);
        loginAPI = new LoginAPI(this);

        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        mPlaceBean = (PlaceBean) intent.getSerializableExtra(PlaceBean.class.getName());
        mTitleText.setText(getString(R.string.register_activity_register_title));
    }

    @OnClick({R.id.back_img, R.id.activity_register_register_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.activity_register_register_btn:
                String nickName = mNickNameEdit.getText().toString();
                String passwordOne = mPasswordOneEdit.getText().toString();
                String passwordTwo = mPasswordTwoEdit.getText().toString();
//                if (TextUtils.isEmpty(nickName)) {
//                    ToastUtils.show(RegisterTwoActivity.this, getString(R.string.please_input_nick_name));
//                    return;
//                }
                if (TextUtils.isEmpty(passwordOne)) {
                    ToastUtils.show(RegisterTwoActivity.this, R.string.please_input_password);
                    return;
                }
                if (TextUtils.isEmpty(passwordTwo)) {
                    ToastUtils.show(RegisterTwoActivity.this, R.string.please_true_password);
                    return;
                }
                if (!passwordOne.equals(passwordTwo)) {
                    ToastUtils.show(RegisterTwoActivity.this, R.string.two_password_is_not_same);
                    return;
                }
                if (passwordOne.length() < 6 || passwordTwo.length() < 6) {
                    ToastUtils.show(RegisterTwoActivity.this, R.string.login_pwd_lenth_err);
                    return;
                }

                addSubscription(mPresenter.register(this.phone, passwordOne, nickName
                        , mPlaceBean == null ? "0" : mPlaceBean.getNumber()));
                break;
        }
    }

    @Override
    public void registerSuccess(RegisterBean userBean) {
        ToastUtils.show(this, R.string.register_success);
        loginAPI.login(phone, mPasswordOneEdit.getText().toString(), FirebaseInstanceId.getInstance().getToken());
    }

    @Override
    public void registerError(String msg) {
        ToastUtils.show(this, msg);
    }

    @Override
    public void registerError() {
        ToastUtils.show(this, R.string.register_error);
    }

    @Override
    public void showLogin(UserBean userBean) {
        BIZAIAApplication.getInstance().setToken(userBean.getData().getAuthToken());
        BIZAIAApplication.getInstance().setUserBean(userBean.getData());
        BIZAIAApplication.getInstance().setEmail(userBean.getData().getEmail());
        IMHelper.getInstance().init(getApplicationContext());
        if (userBean.getData().getUserType() == 1) {
            RxBus.getInstance().post(new LoginSuccessAction(true));
        } else {
            RxBus.getInstance().post(new LoginSuccessAction(false));
        }
        PerfectInfoActivity.showForRegister(this, mPlaceBean == null ? "" : mPlaceBean.getName(), phone);
        finish();
    }

    @Override
    public void showError(String msg) {
        ToastUtils.show(this, msg);
        finish();
    }

    @Override
    public void showError() {
        finish();
    }

    @Override
    public void showFindSuccess() {

    }
}
