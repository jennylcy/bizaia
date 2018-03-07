package com.bizaia.zhongyin.module.monthly.ui.subscibe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.login.data.UserBean;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.module.monthly.data.SubscribeData;
import com.bizaia.zhongyin.module.pay.PayActivity;
import com.bizaia.zhongyin.module.pay.PaySuccessActivity;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.TimeUtils;
import com.bizaia.zhongyin.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yan on 2017/5/8.
 */

public class MonthlyBuyActivity extends BaseActivity implements MonthlyBuyContract.View<OrderData> {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_title)
    TextView tvName;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.end_time)
    TextView endTime;
    @BindView(R.id.during_day)
    TextView duringDay;
    @BindView(R.id.puy_price)
    TextView puyPrice;
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    private SubscribeData.DataBean.DatasBean datasBean;
    private MonthlyBuyContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_buy);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        new MonthlyBuyPresenter(this, this);
        init();
    }

    private void init() {
        tvTitle.setText(R.string.subs_title);
        tv_submit.setText(R.string.mine_enter);
        datasBean = (SubscribeData.DataBean.DatasBean) getIntent().getSerializableExtra("subscribeData");
        if (datasBean == null) {
            finish();
            return;
        }
        UserBean.DataEntity userBean = BIZAIAApplication.getInstance().getUserBean();
        if (userBean != null && !TextUtils.isEmpty(userBean.getNickname())) {
            tvNotice.setText(Html.fromHtml("<font color='#1156bf'>"+userBean.getNickname()+"</font>"+getString(R.string.monthly_buy_besure)));
        }
        tvName.setText(String.valueOf(datasBean.getTitle()+"購入JPY"+datasBean.getPrice()));
        startTime.setText(TimeUtils.getTodayDateA());
//        endTime.setText(TimeUtils.getStandardDay(datasBean.getCreateTime()+  (datasBean.getMonthNum() * 30*24*3600*1000)));
        endTime.setText(TimeUtils.getMonthToToday(datasBean.getMonthNum()));
        duringDay.setText("    "+String.valueOf(datasBean.getMonthNum()));
        puyPrice.setText(String.valueOf("JPY" + datasBean.getPrice()));
    }

    @OnClick({R.id.back_img, R.id.ivCall, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.ivCall:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL
                        , Uri.parse("tel:" + getResources().getString(R.string.tel)));
                startActivity(dialIntent);
                break;

            case R.id.tv_submit:
                if(!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    startActivity(new Intent(getBaseContext(), PayActivity.class)
                            .putExtra("price", datasBean.getPrice())
                            .putExtra("id", datasBean.getId())
                            .putExtra("type", 1)
                    );
                }else{
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                }
                break;
        }
    }

    @Override
    public void setPresenter(MonthlyBuyContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void dataSuccess(OrderData orderData) {
        if (orderData.getData().getOrder().getStatus() == 0) {
            startActivityForResult(new Intent(getBaseContext(), PayActivity.class)
                    .putExtra("order", orderData)
                    .putExtra("price",datasBean.getPrice())
                    .putExtra("title",getString(R.string.monthly_buy_title))
                    .putExtra("monthly","1"), 99);
        } else if (orderData.getData().getOrder().getStatus() == 1) {
            startActivity(new Intent(getBaseContext(), PaySuccessActivity.class)
                    .putExtra("orderData", orderData)
                    .putExtra("title", getString(R.string.has_subsribe))
                    .putExtra("title",getString(R.string.monthly_buy_title))
                    .putExtra("justShow", true)
            );
        }
    }

    @Override
    public void dataError() {
        ToastUtils.showInUIThead(getBaseContext(), getString(R.string.error_data));
    }

    @Override
    public void onRelogin() {
        reLogin();
    }

    @Override
    public void netError() {
        ToastUtils.showInUIThead(getBaseContext(), getString(R.string.net_error));
    }
}
