package com.bizaia.zhongyin.module.mine.ui.setting.advice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by yan on 2017/3/20.
 */

public class AdviceActivity extends BaseActivity implements AdviceContract.View<ResponseBody> {
    @BindView(R.id.et_advice)
    EditText etAdvice;
    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    private AdviceContract.Presenter presenter;
    private boolean adviceEmpty = true;
    private boolean contentEmpty = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_advice);
        ButterKnife.bind(this);
//        setViewParent(etAdvice);
//        etAdvice.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            if(!StringUtils.isEmpty(etAdvice.getText().toString().trim())){
//                tv_submit.setBackgroundResource(R.drawable.round_1156bf_radius_4);
//                adviceEmpty = false;
//                if(etAdvice.getText().toString().trim().length()>200){
//                  ToastUtils.show(AdviceActivity.this,getString(R.string.advice_lenth_limit));
//                }
//            }else{
//                adviceEmpty = true;
//            }
//                if(contentEmpty&&adviceEmpty){
//                    tv_submit.setBackgroundResource(R.drawable.round_535353_radius_4);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        etContact.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(!StringUtils.isEmpty(etContact.getText().toString().trim())){
//                    tv_submit.setBackgroundResource(R.drawable.round_1156bf_radius_4);
//                    contentEmpty = false;
//                }else{
//                    contentEmpty = true;
//                }
//                if(contentEmpty&&adviceEmpty){
//                    tv_submit.setBackgroundResource(R.drawable.round_535353_radius_4);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        new AdvicePresent(this);
    }

    @OnClick({R.id.tv_submit, R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                String contact = etContact.getText().toString().trim();
                if(!StringUtils.isEmpty(etAdvice.getText().toString().trim())&&
                        !StringUtils.isEmpty(etContact.getText().toString().trim())) {
                    if(etAdvice.getText().toString().trim().length()>200){
                        ToastUtils.show(AdviceActivity.this,getString(R.string.advice_lenth_limit));
                        return;
                    }

                    if(StringUtils.isMail(contact)) {
                        addAdvice();
                    }else{
                        ToastUtils.show(AdviceActivity.this,getString(R.string.please_input_sure_mail));
                        return;
                    }
                }else{
                    if (TextUtils.isEmpty(etContact.getText())) {
                        ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.enter_yout_advice));
                        return;
                    }

                    if (TextUtils.isEmpty(etAdvice.getText())) {
                        ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.enter_you_advice));
                        return;
                    }
                }

                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }

    private void addAdvice() {


        presenter.addAdvice(etAdvice.getText().toString(),etContact.getText().toString());
    }

    @Override
    public void dataSuccess(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            int code = jsonObject.getInt("code");
            if (code == 200) {
                ToastUtils.showInUIThead(AdviceActivity.this, getString(R.string.thanks_for_your_advice));
                etContact.setText("");
                etAdvice.setText("");
            } else {
                ToastUtils.showInUIThead(AdviceActivity.this, jsonObject.getString("msg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dataError() {
        ToastUtils.showInUIThead(this.getApplicationContext(), getString(R.string.commit_error));
    }

    @Override
    public void onRelogin() {
        reLogin();
    }

    @Override
    public void setPresenter(AdviceContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
