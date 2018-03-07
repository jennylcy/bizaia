package com.bizaia.zhongyin.module.pay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.util.ACache;
import com.bizaia.zhongyin.util.TimeUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yan on 2017/4/21.
 */

public class PaySuccessActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_sub_title)
    TextView tvSubTitle;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_notice_title)
    TextView tvNoticeTitle;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.tvTitle)
    TextView tvTopTitle;
    @BindView(R.id.tv_code_notice)
    TextView tvCodeNotice;
    @BindView(R.id.iv_code)
    ImageView ivCode;

    private OrderData orderData;
    private int payType;
    private String moneyType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        init();
    }


    private void init() {
        orderData = (OrderData) getIntent().getSerializableExtra("orderData");
        moneyType = getIntent().getStringExtra("moneyType");
        if (orderData != null
                && orderData.getData() != null
                && orderData.getData().getOrder() != null) {
            tvSubTitle.setText(String.valueOf(getResources().getText(R.string.pay_success_title)
                    + orderData.getData().getOrder().getProductTitle()));
            tvOrderNumber.setText(String.valueOf(getResources().getText(R.string.pay_success_order_num)
                    + orderData.getData().getOrder().getOrderNum()));
            if(payType==1) {
                tvPayMoney.setText(String.valueOf(getResources().getText(R.string.pay_success_money).toString()
                        + moneyType + orderData.getData().getOrder().getPrice()));
            }else{
                tvPayMoney.setText(String.valueOf(getResources().getText(R.string.pay_success_money).toString()
                        + "JPY" + orderData.getData().getOrder().getPrice()));
            }
            if(orderData.getData().getOrder().getStatus()==0) {
                tvNoticeTitle.setText(String.valueOf(getResources().getText(R.string.pay_success_notice).toString()+"JPY"
                        + orderData.getData().getOrder().getPrice()));
            }else if(orderData.getData().getOrder().getStatus()==1){
                if(payType==1) {
                    tvNoticeTitle.setText(String.valueOf(getResources().getText(R.string.pay_has_pay).toString()
                            + "JPY" + orderData.getData().getOrder().getPrice()));
                }else {
                    tvNoticeTitle.setText(String.valueOf(getResources().getText(R.string.pay_has_pay).toString()
                            + "JPY" + orderData.getData().getOrder().getPrice()));
                }
            }
            tvPayTime.setText(String.valueOf(getResources().getText(R.string.pay_success_time).toString()
                    + TimeUtils.getStandardTime(System.currentTimeMillis())));

            tvTitle.setText(TextUtils.isEmpty(getIntent().getStringExtra("title"))
                    ? getString(R.string.pay_success)
                    : getIntent().getStringExtra("title"));

//            类型1.paypel 2.银联 3.微信 4.余额
//            1月刊订阅 2直播 3 点播 4 月刊 5 讲座  6 充值
            if (orderData.getData().getOrder().getProductType() == 5) {
                tvCodeNotice.setVisibility(View.VISIBLE);

                ivCode.setVisibility(View.VISIBLE);
                ivCode.setImageBitmap(CodeUtils.createImage(orderData.getData().getOrder().getOrderNum(), 400, 400, null));
            }
            payType = getIntent().getIntExtra("payType", -2);
            if (payType == -2 && ACache.get(getBaseContext()).getAsString("payType") != null) {
                payType = Integer.valueOf(ACache.get(getBaseContext()).getAsString("payType"));
            }
            String payStr = "";
            Log.e("PaySuccessActivity", "init: -------->"+payType );
            if (payType != -2) {
                if (payType == -1) {
                    payStr = getString(R.string.remain_pay);
                } else if (payType == 1) {
                    payStr = getString(R.string.option_paypal);
                } else if (payType == 3) {
                    payStr = "WeChat";
                } else if (payType == 4) {
                    payStr = "Master Card";
                }else if(payType==6){
                    payStr="Unionpay";
                }else if(payType==7){
                    payStr="Visa";
                }
                tvPayType.setText(String.valueOf(getResources().getText(R.string.pay_success_method).toString()
                        + payStr));
            }

            if (getIntent().getBooleanExtra("justShow", false)) {
                tvPayType.setVisibility(View.GONE);
            }
        }
    }

    @OnClick({R.id.back_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }
}
