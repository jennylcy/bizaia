package com.bizaia.zhongyin.module.pay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.mine.api.UserInfoAPI;
import com.bizaia.zhongyin.module.mine.data.UserInfoBean;
import com.bizaia.zhongyin.module.mine.iml.IUserInfoView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zyz on 2017/5/19.
 */

public class AddMoneyResultActivity extends BaseActivity implements IUserInfoView {

    private static final String TAG = "AddMoneyResultActivity";
    @BindView(R.id.tv_pay_title)
    TextView tvTitle;
    @BindView(R.id.btn_back)
    ImageView ivBack;
    @BindView(R.id.ivCall)
    ImageView ivCall;
    @BindView(R.id.ivResult)
    ImageView ivResult;
    @BindView(R.id.tvResult)
    TextView tvResult;
    @BindView(R.id.tvResultText)
    TextView tvResultText;
    @BindView(R.id.tvEnter)
    TextView tvEnter;

    private boolean resultType;
    private String payType;
    private UserInfoAPI userInfoAPI;
    private double unExchange;
    private String jpyValue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money_result);
        initContent();
    }


    protected void initContent() {
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        resultType = getIntent().getBooleanExtra("type",false);
        payType = getIntent().getStringExtra("moneyType");
        unExchange = getIntent().getDoubleExtra("unExchange",0);
        jpyValue = getIntent().getStringExtra("jpyValue");
        userInfoAPI = new UserInfoAPI(this);
        userInfoAPI.getInfo();
        initData();
    }

    private void initData(){
        ivBack.setOnClickListener(onClickListener);
        tvEnter.setOnClickListener(onClickListener);
        ivCall.setOnClickListener(onClickListener);

        Log.e(TAG, "initData: --------------->show this" );
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvEnter:
                     finish();
                    break;
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.ivCall:
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL
                            , Uri.parse("tel:" + getResources().getString(R.string.tel)));
                    startActivity(dialIntent);
                    break;
            }

        }
    };

    @Override
    public void showUserInfo(UserInfoBean userInfoBean) {
        String textValue="";
        if(resultType) {
            String res = getString(R.string.pay_add_result).replace("#","%s");
            if(jpyValue==null)
            textValue = String.format(res,"<font color='#009944'>JPY"+unExchange+"</font>","<font color='#009944'>JPY"+userInfoBean.getData().getBalance()+"</font>");
            else
                textValue = String.format(res,"<font color='#009944'>JPY"+jpyValue+"</font>","<font color='#009944'>JPY"+userInfoBean.getData().getBalance()+"</font>");
            tvResult.setText(R.string.pay_add_success);
            ivResult.setImageResource(R.drawable.icon_chongzhi01);
        }else {
            String res = getString(R.string.pay_add_fail_result).replace("#","%s");
            if(jpyValue==null)
            textValue = String.format(res,"<font color='#009944'>JPY"+unExchange+"</font>");
            else
                textValue = String.format(res,"<font color='#009944'>JPY"+jpyValue+"</font>");
            tvResult.setText(R.string.pay_add_fail);
            ivResult.setImageResource(R.drawable.icon_chongzhi02);
        }

        tvResultText.setText(Html.fromHtml(textValue));
    }

    @Override
    public void showIcon(String path) {

    }

    @Override
    public void showUserInfoError() {

    }
}
