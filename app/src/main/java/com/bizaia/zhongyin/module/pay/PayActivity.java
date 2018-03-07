package com.bizaia.zhongyin.module.pay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.live.action.LoginSuccessAction;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.monthly.action.PaySuccessAction;
import com.bizaia.zhongyin.module.pay.api.Pay;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.module.pay.remainingpay.RemainingPayManager;
import com.bizaia.zhongyin.util.ACache;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.NumUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/2/24.
 */

public class PayActivity extends BaseActivity {
    @BindView(R.id.tv_remain_money)
    TextView tvRemainMoney;
    @BindView(R.id.tv_pay_title)
    TextView tvPayTitle;
    @BindView(R.id.ivCall)
    ImageView ivCall;

    @BindView(R.id.tv_add_money)
    TextView tv_add_money;
    @BindView(R.id.tv_remain_pay)
    TextView tv_remain_pay;
    @BindView(R.id.tv_other_pay)
    TextView tv_other_pay;

    protected PayPalResult payPalResult;
    protected RemainingPayManager remainingPayManager;

    protected int clickedIndex = -1;
    protected OrderData orderData;

    protected int payType;
    private boolean isFree = false;
    private String monthly;
    private int buyType;
    private double price;
    private long id;
    private int actionType;
    private String titleText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContent();
    }

    protected void initContent() {
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        setViewParent(tvPayTitle);
        isFree = getIntent().getBooleanExtra("isFree", false);
        monthly = getIntent().getStringExtra("monthly");
        buyType = getIntent().getIntExtra("buyType", -1);
        price = getIntent().getDoubleExtra("price", 0);
        id = getIntent().getLongExtra("id", 0);
        actionType = getIntent().getIntExtra("type", 0);
        titleText = getIntent().getStringExtra("title");
        init();


        if (remainingPayManager == null) {
            remainingPayManager = new RemainingPayManager(this, payPalResult);
        }

        if (isFree) {
            recharge(price, "JPY");
            ACache.get(getBaseContext()).put("payType", "-1");
            payType = -1;
        }

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
        if (remainingPayManager != null) {
            remainingPayManager.onDestroy();
        }
        super.onDestroy();
    }

    protected void init() {
        if (BIZAIAApplication.getInstance().getUserInfo() != null)
            tvRemainMoney.setText(NumUtils.getRoundTwoPlaceStr(BIZAIAApplication.getInstance().getUserInfo().getBalance()));
        tvPayTitle.setText(titleText);
        payPalResult = new PayPalResult();

    }



    @OnClick({R.id.btn_back, R.id.tv_other_pay, R.id.tv_remain_pay, R.id.tv_add_money, R.id.ivCall})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_pay_btn:
                btnPayCall();
                break;
            case R.id.tv_remain_pay:
                recharge(price, "JPY");
//                if (remainingPayManager == null) {
//                    remainingPayManager = new RemainingPayManager(this, payPalResult);
//                }
//                remainingPayManager.RemainingPay(orderData);
                ACache.get(getBaseContext()).put("payType", "-1");
                payType = -1;
                break;
            case R.id.tv_add_money:
                intent = new Intent(PayActivity.this, RechargeActivity.class);
                intent.putExtra("monthly", monthly);
                intent.putExtra("buyType", 6);
                intent.putExtra("price", price);
                intent.putExtra("id", id);
                intent.putExtra("type", 6);
                startActivity(intent);
                break;
            case R.id.tv_other_pay:
                intent = new Intent(PayActivity.this, RechargeActivity.class);
                intent.putExtra("monthly", monthly);
                intent.putExtra("buyType", buyType);
                intent.putExtra("price", price);
                intent.putExtra("id", id);
                intent.putExtra("type", actionType);
                intent.putExtra("title", titleText);
                startActivity(intent);
                break;
            case R.id.ivCall:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL
                        , Uri.parse("tel:" + getResources().getString(R.string.tel)));
                startActivity(dialIntent);
                break;

        }
    }

    protected void btnPayCall() {
    }

    public class PayPalResult implements DoResult {
        @Override
        public void confirmSuccess() {
            ToastUtils.show(getApplicationContext(), R.string.puy_success);
            startActivity(new Intent(PayActivity.this, PaySuccessActivity.class)
                    .putExtra("orderData", orderData)
                    .putExtra("payType", payType)
                    .putExtra("title", titleText)
            );
            RxBus.getInstance().post(new PaySuccessAction(orderData));
            if (!StringUtils.isEmpty(monthly))
                RxBus.getInstance().post(new LoginSuccessAction(false));

            tvRemainMoney.setText(NumUtils.getRoundTwoPlaceStr(orderData.getData().getWallet().getBalance()
                    - orderData.getData().getOrder().getPrice()));
            finish();
        }

        @Override
        public void confirmNetWorkError() {
            ToastUtils.show(getApplicationContext(), R.string.pay_error);
        }

        @Override
        public void customerCanceled() {
            ToastUtils.show(getApplicationContext(), R.string.cancel_pay);
        }

        @Override
        public void confirmFuturePayment() {
            ToastUtils.show(getApplicationContext(), "");
        }

        @Override
        public void invalidPaymentConfiguration() {
            ToastUtils.show(getApplicationContext(), R.string.pay_msg_error);
        }
    }

    private void recharge(double price, String type) {
        Log.e("createOrder: ", "createOrder()------------------》"+id);
        Pay pay = AppRetrofit.getAppRetrofit().retrofit().create(Pay.class);
        addSubscription(pay.getOrder(id, actionType, price, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<OrderData>() {
                    @Override
                    public void onNext(OrderData value) {
                        orderData = value;
                        if (orderData.getCode() == 200) {
                            remainingPayManager.RemainingPay(value);
                        } else {
                            ToastUtils.show(PayActivity.this, R.string.pay_balance_error);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.net_error));
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

}
