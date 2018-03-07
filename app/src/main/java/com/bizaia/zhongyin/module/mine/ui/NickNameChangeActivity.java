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
import com.bizaia.zhongyin.module.mine.api.ChangeNickNameAPI;
import com.bizaia.zhongyin.module.mine.iml.INickNameChangeView;
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

public class NickNameChangeActivity extends BaseActivity implements INickNameChangeView {

    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.etNickname)
    EditText etNickname;
    @BindView(R.id.btnChange)
    Button btnChange;
    @BindView(R.id.ivCall)
    ImageView ivCall;

    private UserBean.DataEntity userBean;
    private ChangeNickNameAPI mAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname_change);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        userBean = BIZAIAApplication.getInstance().getUserBean();
        if (userBean == null) {
            reLogin();
            finish();
            return;
        }

        mAPI = new ChangeNickNameAPI(this);
        if (TextUtils.isEmpty(userBean.getNickname())) {
            etNickname.setText(userBean.getNickname());
        }
        tvTitle.setText(R.string.mine_nick_name);
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

    @OnClick({R.id.back_img, R.id.btnChange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.btnChange:
                String nickName = etNickname.getText().toString();
                if (TextUtils.isEmpty(nickName)) {
                    ToastUtils.show(NickNameChangeActivity.this, getString(R.string.please_input_new_nick_name));
                    return;
                }
                if (userBean != null
                        && !TextUtils.isEmpty(userBean.getNickname())
                        && userBean.getNickname().equals(nickName)) {
                    ToastUtils.show(NickNameChangeActivity.this, getString(R.string.not_need_change_nick_name));
                    return;
                }

                if (nickName.length() > 15) {
                    ToastUtils.show(NickNameChangeActivity.this, getString(R.string.change_nick_name_length));
                    return;
                }

                mAPI.changeNickName(nickName);
                break;
            default:
                break;
        }
    }

    @Override
    public void changeNickNameSuccess(String nickName) {
        ToastUtils.show(this, getString(R.string.password_change_success));
        BIZAIAApplication application = BIZAIAApplication.getInstance();
        userBean.setNickname(nickName);
        application.setUserBean(userBean);
        finish();
    }

    @Override
    public void changeNickNameError() {
        ToastUtils.show(this, getString(R.string.change_nick_name_error));
    }

    @Override
    public void changeNickNameError(String msg) {
        ToastUtils.show(this, msg);
    }

    @Override
    public void loginOutTime() {
        reLogin();
        finish();
    }
}
