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
import com.bizaia.zhongyin.module.mine.api.ChangePasswordAPI;
import com.bizaia.zhongyin.module.mine.iml.IPasswordChangeView;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/13.
 */

public class PwdChangeActivity extends BaseActivity implements IPasswordChangeView {

    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    //    @BindView(R.id.etMail)
//    EditText etPhone;
//    @BindView(R.id.tvCode)
//    TextView tvCode;
//    @BindView(R.id.etCode)
//    EditText etCode;
    @BindView(R.id.btnChange)
    Button btnChange;
    @BindView(R.id.ivCall)
    ImageView ivCall;
    @BindView(R.id.etOldPwd)
    EditText etOldPwd;
    @BindView(R.id.etNewPwd)
    EditText etNewPwd;
    @BindView(R.id.etNewPwdEnter)
    EditText etNewPwdEnter;

    private UserBean.DataEntity userBean;

    private boolean isGetCode;
    private ChangePasswordAPI changePasswordAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_change);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        new ChangePasswordAPI(this);
        userBean = BIZAIAApplication.getInstance().getUserBean();
        if (userBean == null) {
            reLogin();
            finish();
            return;
        }

        tvTitle.setText(R.string.mine_pass_change);
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

    @OnClick({R.id.back_img, R.id.tvCode, R.id.btnChange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.tvCode:
                break;
            case R.id.btnChange:
                if (TextUtils.isEmpty(etOldPwd.getText())) {
                    ToastUtils.showInUIThead(getBaseContext(), getString(R.string.old_password_not_empty_able));
                    return;
                }
                if (TextUtils.isEmpty(etNewPwd.getText())) {
                    ToastUtils.showInUIThead(getBaseContext(), getString(R.string.new_password_not_empty_able));
                    return;
                }
                if (!etNewPwd.getText().toString().equals(etNewPwdEnter.getText().toString())) {
                    ToastUtils.showInUIThead(getBaseContext(), getString(R.string.pasword_is_not_same));
                    return;
                }
                if (etNewPwd.length() < 6 || etOldPwd.length() < 6) {
                    ToastUtils.show(PwdChangeActivity.this, R.string.login_pwd_lenth_err);
                    return;
                }
                changePasswordAPI.changePassWord(etOldPwd.getText().toString(), etNewPwd.getText().toString());

                break;
            default:
                break;
        }
    }

    @Override
    public void setPresenter(ChangePasswordAPI presenter) {
        this.changePasswordAPI = presenter;
    }

    @Override
    public void changePasswordSuccess() {
        finish();
        ToastUtils.showInUIThead(getBaseContext(), getString(R.string.password_change_success));
    }

    @Override
    public void changePasswordError() {
        ToastUtils.showInUIThead(getBaseContext(), getString(R.string.net_error));
    }

    @Override
    public void changePasswordError(String msg) {
        ToastUtils.showInUIThead(getBaseContext(), msg);
    }
}
