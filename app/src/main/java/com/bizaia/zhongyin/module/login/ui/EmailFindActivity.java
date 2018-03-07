package com.bizaia.zhongyin.module.login.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.login.api.LoginAPI;
import com.bizaia.zhongyin.module.login.data.UserBean;
import com.bizaia.zhongyin.module.login.iml.ILoginView;
import com.bizaia.zhongyin.module.mine.api.ChangeMailAPI;
import com.bizaia.zhongyin.module.mine.iml.IMailChangeView;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by zyz on 2017/3/13.
 */

public class EmailFindActivity extends BaseActivity implements IMailChangeView, ILoginView {

    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.etMail)
    EditText etMail;
    @BindView(R.id.tvCode)
    TextView tvCode;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.btnChange)
    Button btnChange;
    @BindView(R.id.ivCall)
    ImageView ivCall;

    @BindView(R.id.activity_pwd)
    EditText activity_pwd;
    @BindView(R.id.activity_pwdT)
    EditText activity_pwdT;



    private Disposable mCodeDisposable;
    private boolean isGetCode;

    private ChangeMailAPI mAPI;

    private String mMailTemp;
    private LoginAPI loginAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_email);
        ButterKnife.bind(this);
        setViewParent(back_img);
        loginAPI= new LoginAPI(this);

        mAPI = new ChangeMailAPI(this);
        tvTitle.setText(R.string.login_pop_forget);
        ivCall.setVisibility(View.GONE);
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

    @OnClick({R.id.back_img, R.id.tvCode, R.id.btnChange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.tvCode:
                String mail = etMail.getText().toString();
                if (!StringUtils.isMail(mail)) {
                    ToastUtils.show(EmailFindActivity.this, getString(R.string.find_pwd_eamil_error));
                    return;
                }
                getCode(mail);
                break;
            case R.id.btnChange:
                String mail1 = etMail.getText().toString();
                String code = etCode.getText().toString();
                String pwd = activity_pwd.getText().toString().trim();
                String pwdt  = activity_pwdT.getText().toString().trim();
                if (!StringUtils.isMail(mail1)) {
                    ToastUtils.show(EmailFindActivity.this, getString(R.string.find_pwd_eamil_error));
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.show(EmailFindActivity.this, getString(R.string.please_input_code));
                    return;
                }
                if(TextUtils.isEmpty(pwd)||pwd.length()<6){
                    ToastUtils.show(EmailFindActivity.this, getString(R.string.login_pwd_lenth_err));
                    return;
                }

                if(TextUtils.isEmpty(pwdt)||pwdt.length()<6){
                    ToastUtils.show(EmailFindActivity.this, getString(R.string.login_pwd_lenth_err));
                    return;
                }

                if(!pwd.equals(pwdt)){
                    ToastUtils.show(EmailFindActivity.this, getString(R.string.pasword_is_not_same));
                    return;
                }
                loginAPI.find(mail1,pwd,"2",code);
                break;
            default:
                break;
        }
    }

    private void getCode(final String mail) {
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
                        mAPI.getCode(mail,2);
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
    public void getCodeSuccess(String mail) {
        mMailTemp = mail;
        ToastUtils.show(EmailFindActivity.this, getString(R.string.get_code_success));
    }

    @Override
    public void getCodeError() {
        ToastUtils.show(EmailFindActivity.this, getString(R.string.get_code_error));
        isGetCode = false;
    }

    @Override
    public void getCodeError(String msg) {
        ToastUtils.show(EmailFindActivity.this, msg);
        isGetCode = false;
    }

    @Override
    public void changeMailSuccess(String mail) {
        finish();
    }

    @Override
    public void changeMailError() {
    }

    @Override
    public void changeMailError(String msg) {
        ToastUtils.show(EmailFindActivity.this, msg);
    }

    @Override
    public void loginOutTime() {
        reLogin();
        finish();
    }

    @Override
    public void checkCode() {

    }

    @Override
    public void showLogin(UserBean userBean) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showError() {
//        ToastUtils.show(EmailFindActivity.this,getString(R.string.find_pwd_erro));
    }

    @Override
    public void showFindSuccess() {
        ToastUtils.show(EmailFindActivity.this,getString(R.string.find_pwd_success));
        finish();
    }
}
