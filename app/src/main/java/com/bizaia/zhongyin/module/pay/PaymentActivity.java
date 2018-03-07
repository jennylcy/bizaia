package com.bizaia.zhongyin.module.pay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.live.action.LoginSuccessAction;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.monthly.action.PaySuccessAction;
import com.bizaia.zhongyin.module.pay.adapter.DataAdapterHelper;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.module.pay.paypel.PayPalManager;
import com.bizaia.zhongyin.module.pay.remainingpay.RemainingPayManager;
import com.bizaia.zhongyin.module.pay.unionpay.UnionPayManager;
import com.bizaia.zhongyin.module.pay.wx.WXPayManager;
import com.bizaia.zhongyin.util.ACache;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.ForgetPop;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/6/15.
 */


public class PaymentActivity extends BaseActivity {


    private final static String TAG="PaymentActivity";
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.tv_pay_title)
    TextView tvTitle;
    @BindView(R.id.ivCall)
    ImageView ivCall;
    @BindView(R.id.rvPayment)
    RecyclerView rvPayment;
    @BindView(R.id.tv_pay_btn)
    TextView tv_pay_btn;

    CustomAdapter adapter;
    private List<String> dataList;
    private List<String> typeList;
    private int payType;
    private int position;

    protected PayPalManager payPalManager;
    protected PayPalResult payPalResult;
    protected UnionPayManager unionPayManager;
    protected RemainingPayManager remainingPayManager;
    protected WXPayManager wxPayManager;

    protected OrderData orderData;
    private String monthly;
    private String moneyType;
    private double payValue;
    private boolean isAdd;
    private double unExchange;
    private String jpyValue;

    private String titleText;


    private ForgetPop forgetPop;



    /**
     * Paypal(1, "Paypal"),
     * ITunes(2, "ITunes"),
     * Wechat(3, "WeChat"),
     * Master(4, "Master Card"),
     * Balance(5, "余额"),
     * CUP(6, "Unionpay"),
     * VISA(7, "Visa");
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        titleText = getIntent().getStringExtra("title");
        if (titleText != null)
            tvTitle.setText(titleText);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void init() {
        String[] types = getIntent().getStringExtra("typelist").split(",");
        orderData = (OrderData) getIntent().getSerializableExtra("order");
        dataList = new ArrayList<>();
        typeList = new ArrayList<>();
        payPalResult = new PayPalResult();
        monthly = getIntent().getStringExtra("monthly");
        moneyType = getIntent().getStringExtra("payType");
        payValue = getIntent().getDoubleExtra("money", 0.0);
        isAdd = getIntent().getBooleanExtra("isAdd",false);
        unExchange = getIntent().getDoubleExtra("unExchange",0);
        jpyValue = getIntent().getStringExtra("jpy");

        Log.e("PaymentActivity", "init: -------------->" + moneyType + "------->" + payValue);
        for (int i = 0; i < types.length; i++) {
            payType = Integer.parseInt(types[i]);
            dataList.add(getTypeName(Integer.parseInt(types[i])));
            typeList.add(types[i]);
        }
        rvPayment.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapter = new DataAdapterHelper().getAdapter(getBaseContext(), dataList, null, new PayTypeSelect() {
            @Override
            public void select(int pos) {
                position = pos;
                adapter.notifyDataSetChanged();
            }
        });
        rvPayment.setAdapter(adapter);

        addSubscription(RxBus.getInstance().getEvent(PaySuccessAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PaySuccessAction>() {
                    @Override
                    public void onNext(PaySuccessAction value) {
                        if (!isSuccess) {
                            isSuccess = true;
                            Log.e("PaymentActivity", "confirmSuccess: ------weixin-------->paysuccess------"+String.valueOf(value.payData));
                            if (value != null && value.payData instanceof String && "weixin".equals(value.payData)) {
                                if (!isAdd) {
                                    if (titleText != null) {
                                        startActivity(new Intent(PaymentActivity.this, PaySuccessActivity.class)
                                                .putExtra("orderData", orderData)
                                                .putExtra("title", titleText)
                                                .putExtra("payType", payType)
                                        );
                                    } else {
                                        startActivity(new Intent(PaymentActivity.this, PaySuccessActivity.class)
                                                .putExtra("orderData", orderData)
                                                .putExtra("payType", payType)
                                        );
                                    }
                                } else {
                                    toOther();
//                                    Log.e(TAG, "confirmSuccess: ------------>"+unExchange+"-------"+String.valueOf(value.payData) );
//                                    Intent intent = new Intent(PaymentActivity.this, AddMoneyResultActivity.class);
//                                    intent.putExtra("type", true);
//                                    intent.putExtra("unExchange",unExchange);
//                                    startActivity(intent);
                                }
                                RxBus.getInstance().post(new PaySuccessAction(orderData));
                                if (!StringUtils.isEmpty(monthly))
                                    RxBus.getInstance().post(new LoginSuccessAction(false));
                                finish();
                            }
                        }
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));

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

    private  void toOther(){
        Log.e(TAG, "confirmSuccess: ------------>"+unExchange+"-------weixin");
        Intent intent = new Intent(PaymentActivity.this, AddMoneyResultActivity.class);
        intent.putExtra("type", true);
        intent.putExtra("unExchange",unExchange);
        intent.putExtra("jpyValue",jpyValue);
        startActivity(intent);
    }

    private String getTypeName(int type) {
        if (type == 1) {
            return "Paypal";
        } else if (type == 3) {
            return "WeChat";
        } else if (type == 4) {
            return "Master Card";
        } else if (type == 5) {
            return getString(R.string.mine_account_balance);
        } else if (type == 6) {
            return "Unionpay";
        } else if (type == 7) {
            return "Visa";
        }
        return "";
    }

    @OnClick({R.id.btn_back, R.id.ivCall, R.id.tv_pay_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.ivCall:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL
                        , Uri.parse("tel:" + getResources().getString(R.string.tel)));
                startActivity(dialIntent);
                break;
            case R.id.tv_pay_btn:
                payType = Integer.parseInt(typeList.get(position));
                if(isAdd){
                    showBuy("JPY" + jpyValue, getTypeName(payType));
                }else {
                    showBuy(moneyType + payValue, getTypeName(payType));
                }
//                payStart(payType);
                break;
        }
    }

    protected void payStart(int type) {
        ACache.get(getBaseContext()).put("payType", String.valueOf(type));
        payType = type;
        switch (type) {
            case 1:
                if (payPalManager == null) {
                    payPalManager = new PayPalManager();
                }
                payPalManager.startPayPalService(this);
                orderData.getData().getOrder().setPrice(payValue);
                payPalManager.doPayPalPay(this, orderData, moneyType);
                break;

            case 6:
                if (unionPayManager == null) {
                    unionPayManager = new UnionPayManager(this);
                }
                unionPayManager.startPay();
                break;
            case 3:
                if (wxPayManager == null) {
                    wxPayManager = new WXPayManager(this);
//                    wxPayManager.setOnPayListener(payPalResult);
                }
//                orderData.getData().getOrder().setPrice(orderData.getData().getOrder().getPrice());
                wxPayManager.startPay(orderData, moneyType);

                break;
            case 4:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (payType) {
            case 1:
                payPalManager.confirmPayResult(this, requestCode, resultCode, data, payPalResult);
                break;
            case 6:
                unionPayManager.confirmPayResult(requestCode, resultCode, data, payPalResult);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }
    private boolean isSuccess;
    public class PayPalResult implements DoResult {
        @Override
        public void confirmSuccess() {
            if(!isSuccess) {
                isSuccess = true;
                ToastUtils.show(getApplicationContext(), R.string.puy_success);
                Log.e("PaymentActivity", "confirmSuccess: ------PayPal-------->paysuccess");
                if (!isAdd) {
                    if (titleText != null) {
                        startActivity(new Intent(PaymentActivity.this, PaySuccessActivity.class)
                                .putExtra("orderData", orderData)
                                .putExtra("title", titleText)
                                .putExtra("payType", payType)
                        );
                    } else {
                        startActivity(new Intent(PaymentActivity.this, PaySuccessActivity.class)
                                .putExtra("orderData", orderData)
                                .putExtra("payType", payType)
                        );
                    }
                } else {
                    Log.e(TAG, "confirmSuccess: ------------>"+unExchange );
                    Intent intent = new Intent(PaymentActivity.this, AddMoneyResultActivity.class);
                    intent.putExtra("type", true);
                    intent.putExtra("unExchange",unExchange);
                    intent.putExtra("jpyValue",jpyValue);
                    startActivity(intent);
                }
                RxBus.getInstance().post(new PaySuccessAction(orderData));
                if (!StringUtils.isEmpty(monthly))
                    RxBus.getInstance().post(new LoginSuccessAction(false));
                finish();
            }
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

    private void showBuy(final String value, final String type) {
        if (forgetPop != null)
            forgetPop.dismiss();
        forgetPop = new ForgetPop(getApplicationContext(), rvPayment, R.layout.pop_buy_enter) {
            @Override
            public void viewInit() {
                ((TextView) findViewById(R.id.tvPrice)).setText(value);
                    ((TextView) findViewById(R.id.content)).setText(Html
                            .fromHtml("これから<font color='#1156bf'>" + type + "</font>を使って、アカウントに<font color='#1156bf'>"
                                    + value + "</font>をチャージします。続行しますか。"));
                TextView start = (TextView) findViewById(R.id.tv_cancel);
                start.setText(R.string.live_pay_enter);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //  确定
                        forgetPop.dismiss();
                        payStart(payType);
                    }
                });
                TextView cancel = (TextView) findViewById(R.id.tv_sure);
                cancel.setText(R.string.live_im_cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forgetPop.dismiss();
                        //  取消
                    }
                });
                ImageView ivClose = (ImageView) findViewById(R.id.ivClose);
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        forgetPop.dismiss();

                    }
                });
            }
        };
        forgetPop.show();
    }


    public interface PayTypeSelect {
        void select(int pos);
    }
}
