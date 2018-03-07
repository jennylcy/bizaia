package com.bizaia.zhongyin.module.monthly.ui.subscibe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.login.data.UserBean;
import com.bizaia.zhongyin.module.monthly.data.SubDetailData;
import com.bizaia.zhongyin.util.TimeUtils;
import com.bizaia.zhongyin.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yan on 2017/5/8.
 */

public class MonthlySubSuccessActivity extends BaseActivity implements SubscribeDetailContract.View<SubDetailData> {
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
    @BindView(R.id.tvSubStart)
    TextView tvSubStart;

    private SubDetailData datasBean;
    private SubscribeDetailContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_subscribe_success);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        SubscribeDetailPresenter sub =   new SubscribeDetailPresenter(this, this);
        sub.requestSubDetail();
        tvTitle.setText(R.string.monthly_sub_all);
    }

    private void init(SubDetailData datasBean) {
        this.datasBean = datasBean;
        UserBean.DataEntity userBean = BIZAIAApplication.getInstance().getUserBean();
        if (userBean != null && !TextUtils.isEmpty(userBean.getNickname())) {
            tvNotice.setText(userBean.getNickname());
        }
        if(datasBean.getData()!=null) {
            tvName.setText(String.valueOf(datasBean.getData().getMonthlyTitle() + "購入JPY" + datasBean.getData().getMonthlyPrice()));
            startTime.setText(TimeUtils.timeTransGBToCNDate(datasBean.getData().getServiceStartTime()));
            endTime.setText(TimeUtils.timeTransGBToCNDate(datasBean.getData().getServiceEndTime()));
            long dateStart = TimeUtils.timeTranstimestamp(datasBean.getData().getServiceStartTime());
            long dateEnd = TimeUtils.timeTranstimestamp(datasBean.getData().getServiceEndTime());
              int day = (int)((dateEnd-System.currentTimeMillis())/(1000*3600*24));
              if((dateEnd-System.currentTimeMillis())%(1000*3600*24)>0){
                  day=day+1;
              }
            duringDay.setText(String.valueOf(day));
            puyPrice.setText(String.valueOf("JPY" + datasBean.getData().getMonthlyPrice()));
            tvSubStart.setText(TimeUtils.timeTransGBToCNDate(datasBean.getData().getServiceStartTime()));
        }
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

        }
    }


    @Override
    public void dataSuccess(SubDetailData news) {
        init(news);
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

    @Override
    public void setPresenter(SubscribeDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
