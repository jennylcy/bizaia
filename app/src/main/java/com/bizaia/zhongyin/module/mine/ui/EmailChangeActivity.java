package com.bizaia.zhongyin.module.mine.ui;

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
import com.bizaia.zhongyin.module.login.data.UserBean;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.mine.api.ChangeMailAPI;
import com.bizaia.zhongyin.module.mine.iml.IMailChangeView;
import com.bizaia.zhongyin.util.RxBus;
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
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by zyz on 2017/3/13.
 */

public class EmailChangeActivity extends BaseActivity implements IMailChangeView {

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

    private UserBean.DataEntity userBean;

    private Disposable mCodeDisposable;
    private boolean isGetCode;

    private ChangeMailAPI mAPI;

    private String mMailTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_change);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        userBean = BIZAIAApplication.getInstance().getUserBean();
        if (userBean == null) {
            reLogin();
            finish();
            return;
        }

        mAPI = new ChangeMailAPI(this);
        tvTitle.setText(R.string.mine_email_change);
        ivCall.setVisibility(View.GONE);
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

    @OnClick({R.id.back_img, R.id.tvCode, R.id.btnChange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.tvCode:
                String mail = etMail.getText().toString();
                if (mail.equals(BIZAIAApplication.getInstance().getUserBean().getEmail())) {
                    ToastUtils.show(EmailChangeActivity.this, getString(R.string.not_need_change_mail));
                    return;
                }
                if (!StringUtils.isMail(mail)) {
                    ToastUtils.show(EmailChangeActivity.this, getString(R.string.please_input_sure_mail));
                    return;
                }
                getCode(mail);
                break;
            case R.id.btnChange:
                String mail1 = etMail.getText().toString();
                String code = etCode.getText().toString();
                if (!mail1.equals(mMailTemp)) {
                    ToastUtils.show(EmailChangeActivity.this, getString(R.string.please_get_code_first));
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.show(EmailChangeActivity.this, getString(R.string.please_input_code));
                    return;
                }
                mAPI.changeMail(mail1, code);
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
                        mAPI.getCode(mail,1);
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
        ToastUtils.show(EmailChangeActivity.this, getString(R.string.get_code_success));
    }

    @Override
    public void getCodeError() {
        ToastUtils.show(EmailChangeActivity.this, getString(R.string.get_code_error));
        isGetCode = false;
    }

    @Override
    public void getCodeError(String msg) {
        ToastUtils.show(EmailChangeActivity.this, msg);
        isGetCode = false;
    }

    @Override
    public void changeMailSuccess(String mail) {
        ToastUtils.show(EmailChangeActivity.this, getString(R.string.password_change_success));
        BIZAIAApplication application = BIZAIAApplication.getInstance();
        userBean.setEmail(mail);
        application.setUserBean(userBean);
        finish();
    }

    @Override
    public void changeMailError() {
        ToastUtils.show(EmailChangeActivity.this, getString(R.string.change_mail_error));
    }

    @Override
    public void changeMailError(String msg) {
        ToastUtils.show(EmailChangeActivity.this, msg);
    }

    @Override
    public void loginOutTime() {
        reLogin();
        finish();
    }

    @Override
    public void checkCode() {

    }
}
