package com.bizaia.zhongyin.module.pay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.pay.api.ExchangeAPI;
import com.bizaia.zhongyin.module.pay.api.Pay;
import com.bizaia.zhongyin.module.pay.data.ExchangeRates;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.module.pay.iml.IExchangeView;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.ExchangeRatePop;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/6/15.
 */

public class RechargeActivity extends BaseActivity implements IExchangeView {

    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.tv_pay_title)
    TextView title;
    @BindView(R.id.ivCall)
    ImageView ivCall;
    @BindView(R.id.linPayType)
    LinearLayout linPayType;
    @BindView(R.id.etType)
    EditText etType;
    @BindView(R.id.etValue)
    EditText etValue;
    @BindView(R.id.tv_btn_commit)
    TextView btnCommit;
    @BindView(R.id.linInput)
    LinearLayout linInput;
    @BindView(R.id.linPay)
    LinearLayout linPay;
    @BindView(R.id.tvExchangePrice)
    TextView tvExchangePrice;
    @BindView(R.id.clParent)
    View clParent;
    @BindView(R.id.tvShow)
    TextView tvShow;

    private ExchangeAPI exchangeAPI;
    private ExchangeRates data;
    protected OrderData orderData;
    private int buyType;
    private String monthly;
    private ExchangeRates.DataEntity dataEntity;
    private double price;
    private long id;
    private double payValue;
    private String payJpyValue;
    private String payType;
    private int actionType;
    private String titleText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_money);
        ButterKnife.bind(this);
        setViewParent(title);
        buyType = getIntent().getIntExtra("buyType", -1);
        monthly = getIntent().getStringExtra("monthly");
        price = getIntent().getDoubleExtra("price", 0);
        id = getIntent().getLongExtra("id", 0);
        actionType = getIntent().getIntExtra("type", 0);
        titleText = getIntent().getStringExtra("title");
        if (buyType == 6) {
            linPay.setVisibility(View.GONE);
            linInput.setVisibility(View.VISIBLE);
        } else {
            linInput.setVisibility(View.GONE);
            linPay.setVisibility(View.VISIBLE);
        }
        exchangeAPI = new ExchangeAPI(this);
        exchangeAPI.exchangeRates();
        if (titleText != null)
            title.setText(titleText);

        etValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = etValue.getText().toString().trim();
                if (!StringUtils.isEmpty(value)) {
                    getAddValue(Double.parseDouble(value));
                } else {
                    tvShow.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

    @OnClick({R.id.btn_back, R.id.ivCall, R.id.etType, R.id.tv_btn_commit})
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
            case R.id.etType:
                if (data != null && data.getData() != null && !data.getData().isEmpty()) {
                    initExchangePop(data);
                }
                break;
            case R.id.tv_btn_commit:
                String value = etValue.getText().toString().trim();
                String type = etType.getText().toString().trim();

                if (StringUtils.isEmpty(type)) {
                    ToastUtils.show(RechargeActivity.this, getString(R.string.recharge_remind));
                    return;
                }

                if (buyType == 6 && StringUtils.isEmpty(value)) {
                    ToastUtils.show(RechargeActivity.this, getString(R.string.pay_value_null));
                    return;
                }

                if (buyType == 6 && Double.parseDouble(payJpyValue) < 5) {
                    ToastUtils.show(RechargeActivity.this, getString(R.string.pay_value_min));
                    return;
                }

                if (buyType == 6 && Double.parseDouble(value) > 5000) {
                    ToastUtils.show(RechargeActivity.this, getString(R.string.pay_value_max));
                    return;
                }
                if (buyType != 6) {
                    recharge(id, payValue, payType);
                } else {
                    payValue = Double.parseDouble(value);
                    recharge(0, Double.parseDouble(value), payType);
                }
                break;
            default:
                break;
        }
    }


    private void recharge(long id, final double price, String type) {
        Log.e("RechargeActivity", "recharge: ------------------->" + price + "---------->" + actionType);
        Pay pay = AppRetrofit.getAppRetrofit().retrofit().create(Pay.class);
        addSubscription(pay.getOrder(id, actionType, price, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<OrderData>() {
                    @Override
                    public void onNext(OrderData value) {
                        orderData = value;
                        if (orderData.getCode() == 200) {
                            Intent intent = new Intent(RechargeActivity.this, PaymentActivity.class);
                            intent.putExtra("order", value);
                            intent.putExtra("money", payValue);
                            intent.putExtra("typelist", dataEntity.getPayMethodId());
                            intent.putExtra("payType", payType);
                            if (titleText != null)
                                intent.putExtra("title", titleText);
                            if (buyType == 6) {
                                intent.putExtra("isAdd", true);
                                intent.putExtra("unExchange", price);
                                intent.putExtra("jpy", payJpyValue);
                            }
                            startActivity(intent);
//                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.net_error));
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }


    private void initExchangePop(ExchangeRates data) {
        ExchangeRatePop pop = new ExchangeRatePop(getBaseContext(), data.getData(), clParent, new ExchangeRatePop.OnSelectTimeDialogListener() {
            @Override
            public void onOkBtnClick(ExchangeRates.DataEntity data) {
                Log.e("initExchangePop", "onOkBtnClick: -------->" + data);
                dataEntity = data;
                if (dataEntity != null)
                    setPrice();
            }
        });
        pop.show();
    }


    private void getAddValue(double price) {
        if (dataEntity != null) {
            if (price > 5000) {
                ToastUtils.show(this, R.string.pay_value_max);
                return;
            }
            double ex_price = price * dataEntity.getExchangeRate();
            DecimalFormat dfs = new DecimalFormat("##0.00");
            double ex_small = Double.parseDouble(dfs.format(ex_price)) * 1000;
            DecimalFormat dfb = new DecimalFormat("##0.000");
            double ex_big = Double.parseDouble(dfb.format(ex_price)) * 1000;
            if (ex_big - ex_small > 0) {
                payJpyValue = dfs.format((Double.parseDouble(dfs.format(ex_price)) + 0.01));
            } else {
                payJpyValue = dfs.format((Double.parseDouble(dfs.format(ex_price))));
            }
            Log.e("RechargeActivity", "setPrice: ------------->" + dataEntity.getExchangeRate() + "----->" + payJpyValue);
            tvShow.setText(String.valueOf(payJpyValue));
        }
    }

    private void setPrice() {
        payType = dataEntity.getCurrencySymbol();
        if (buyType == 6) {

        } else {
            double ex_price = price / dataEntity.getExchangeRate();
            DecimalFormat dfs = new DecimalFormat("##0.00");
            double ex_small = Double.parseDouble(dfs.format(ex_price)) * 1000;
            DecimalFormat dfb = new DecimalFormat("##0.000");
            double ex_big = Double.parseDouble(dfb.format(ex_price)) * 1000;
            if (ex_big - ex_small > 0) {
                payValue = Double.parseDouble(dfs.format(ex_price)) + 0.01;
                tvExchangePrice.setText(dataEntity.getCurrencySymbol() + " " + payValue);
            } else {
                payValue = Double.parseDouble(dfs.format(ex_price));
                tvExchangePrice.setText(dataEntity.getCurrencySymbol() + " " + payValue);
            }
            Log.e("RechargeActivity", "setPrice: ------------->" + dataEntity.getExchangeRate() + "----->" + payValue);
        }
        etType.setText(dataEntity.getCurrencyName() + " 1" + dataEntity.getCurrencySymbol() + " = " + dataEntity.getExchangeRate() + "JPY");

        String value = etValue.getText().toString().trim();
        if (!StringUtils.isEmpty(value)) {
            getAddValue(Double.parseDouble(value));
        } else {
            tvShow.setText("");
        }
    }

    @Override
    public void showLiveDetail(ExchangeRates data) {
        this.data = data;
    }

    @Override
    public void onRelogin() {

    }

    @Override
    public void showExchangeError(int code, String msg) {

    }
}
