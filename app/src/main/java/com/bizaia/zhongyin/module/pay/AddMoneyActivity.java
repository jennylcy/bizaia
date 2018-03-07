package com.bizaia.zhongyin.module.pay;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.pay.api.Pay;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/2/24.
 */

public class AddMoneyActivity extends PayActivity {
    @BindView(R.id.ll_pay_type_container)
    LinearLayout llPayTypeContainer;
    @BindView(R.id.tv_remain_money)
    TextView tvRemainMoney;
    @BindView(R.id.tv_pay_title)
    TextView tvPayTitle;
    @BindView(R.id.tv_btn_commit)
    TextView tvBtnCommit;
    @BindView(R.id.ivCall)
    View ivCall;
    @BindView(R.id.etValue)
    EditText etValue;

    protected void initContent() {
        setContentView(R.layout.activity_add_money);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        Log.e("init", "init: ");
        payPalResult = new PayPalResult();
        tvBtnCommit.setOnClickListener(onClickListener);
        ivCall.setOnClickListener(onClickListener);
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

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_btn_commit:
                    String money = etValue.getText().toString().trim();
                    if(StringUtils.isEmpty(money)){
                        ToastUtils.show(AddMoneyActivity.this,getString(R.string.pay_value_null));
                        return;
                    }

                    if(Double.parseDouble(money)>5000){
                        ToastUtils.show(AddMoneyActivity.this,getString(R.string.pay_value_max));
                        return;
                    }

                    createOrder();
                    break;
                case R.id.ivCall:
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL
                            , Uri.parse("tel:" + getResources().getString(R.string.tel)));
                    startActivity(dialIntent);
                    break;
            }

        }
    };

    protected void btnPayCall() {
        Log.e("btnPayCall: ", "btnPayCall()");
    }

    private void createOrder() {
        Log.e("createOrder: ", "createOrder()");// 1月刊订阅 2直播 3 点播 4 月刊 5 讲座  6 充值
        Pay pay = AppRetrofit.getAppRetrofit().retrofit().create(Pay.class);
        addSubscription(pay.getOrder(0L, 6,0.0,"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<OrderData>() {
                    @Override
                    public void onNext(OrderData value) {
                        orderData = value;
                        Intent intent = new Intent(AddMoneyActivity.this,AddMoneyResultActivity.class);
                        if (orderData.getCode() != 200) {
//                            ToastUtils.showInUIThead(getApplicationContext(), orderData.getMsg());
                            intent.putExtra("type",true);
                            intent.putExtra("money",value.getData().getOrder().getPrice());
                            startActivity(intent);
                            finish();
                        } else {
                            intent.putExtra("type",false);
                            intent.putExtra("money",value.getData().getOrder().getPrice());
                            startActivity(intent);
//                            payStart();
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
