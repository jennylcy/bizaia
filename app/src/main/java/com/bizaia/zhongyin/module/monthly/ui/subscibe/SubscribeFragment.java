package com.bizaia.zhongyin.module.monthly.ui.subscibe;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.ResumeFragment;
import com.bizaia.zhongyin.module.live.action.LoginSuccessAction;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.module.monthly.adapter.DataAdapterHelper;
import com.bizaia.zhongyin.module.monthly.data.SubscribeData;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.WrapContentManager;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SubscribeFragment extends ResumeFragment implements SubscribeContract.View<SubscribeData> {
    private static final String TAG = "SubscribeFragment";

    @BindView(R.id.tv_link)
    TextView tvLink;
    @BindView(R.id.rv_buy)
    RecyclerView rvBuy;
    @BindView(R.id.tv_info_1)
    TextView tvInfo1;
    @BindView(R.id.tv_info_2)
    TextView tvInfo2;

    private List<SubscribeData.DataBean.DatasBean> subscribeDatas;
    private CustomAdapter adapter;

    private SubscribeContract.Presenter presenter;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monthly_subscribe, container, false);
        ButterKnife.bind(this, view);
        new SubscribePresenter(getContext(), this);
        initView();
        initTextLink();
        initRxAction();
        presenter.requireData();
        return view;
    }

    private void initRxAction() {
        addSubscription(RxBus.getInstance().getEvent(LoginSuccessAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginSuccessAction>() {
                    @Override
                    public void onNext(LoginSuccessAction value) {
                        presenter.requireData();
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));
    }

    private void initTextLink() {
        StringBuilder stringBuilder = new StringBuilder(getResources().getString(R.string.monthly_info));
        SpannableString spanableInfo = new SpannableString(stringBuilder.toString());
        spanableInfo.setSpan(new ClickableSpan() {
                                 public void onClick(View widget) {
                                     Log.e(TAG, "onClick: " + getResources().getString(R.string.website));
                                 }

                                 public void updateDrawState(TextPaint ds) {
                                     ds.setUnderlineText(false);
                                 }
                             }
                , stringBuilder.indexOf(getResources().getString(R.string.website))
                , stringBuilder.indexOf(getResources().getString(R.string.website)) + getResources().getString(R.string.website).length()
                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanableInfo.setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(getContext(), R.color.cr_1156bf))
                , stringBuilder.indexOf(getResources().getString(R.string.website))
                , stringBuilder.indexOf(getResources().getString(R.string.website)) + getResources().getString(R.string.website).length()
                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spanableInfo.setSpan(new ClickableSpan() {
                                 public void onClick(View widget) {
                                     Log.e(TAG, "onClick: " + getString(R.string.tel));
                                     Intent dialIntent = new Intent(Intent.ACTION_DIAL
                                             , Uri.parse("tel:" + getString(R.string.tel)));
                                     startActivity(dialIntent);
                                 }

                                 public void updateDrawState(TextPaint ds) {
                                     ds.setUnderlineText(false);
                                 }
                             }
                , stringBuilder.indexOf(getString(R.string.tel))
                , stringBuilder.indexOf(getString(R.string.tel)) + getString(R.string.tel).length()
                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanableInfo.setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(getContext(), R.color.cr_1156bf))
                , stringBuilder.indexOf(getResources().getString(R.string.tel))
                , stringBuilder.indexOf(getResources().getString(R.string.tel)) + getResources().getString(R.string.tel).length()
                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spanableInfo.setSpan(new ClickableSpan() {
                                 public void onClick(View widget) {
                                     Log.e(TAG, "onClick: " + getResources().getString(R.string.email));
                                 }

                                 public void updateDrawState(TextPaint ds) {
                                     ds.setUnderlineText(false);
                                 }
                             }
                , stringBuilder.indexOf(getResources().getString(R.string.email))
                , stringBuilder.indexOf(getResources().getString(R.string.email)) + getResources().getString(R.string.email).length()
                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanableInfo.setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(getContext(), R.color.cr_1156bf))
                , stringBuilder.indexOf(getResources().getString(R.string.email))
                , stringBuilder.indexOf(getResources().getString(R.string.email)) + getResources().getString(R.string.email).length()
                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvInfo1.setText(spanableInfo);
        tvInfo1.setMovementMethod(LinkMovementMethod.getInstance());

        StringBuilder stringBuilder2 = new StringBuilder(getResources().getString(R.string.monthly_info_2));
        SpannableString spanableInfo2 = new SpannableString(stringBuilder2.toString());
        spanableInfo2.setSpan(new ClickableSpan() {
                                  public void onClick(View widget) {
                                      Log.e(TAG, "onClick: " + getResources().getString(R.string.tel));
                                      Intent dialIntent = new Intent(Intent.ACTION_DIAL
                                              , Uri.parse("tel:" + getResources().getString(R.string.tel)));
                                      startActivity(dialIntent);
                                  }

                                  public void updateDrawState(TextPaint ds) {
                                      ds.setUnderlineText(false);
                                  }
                              }
                , stringBuilder2.indexOf(getResources().getString(R.string.tel))
                , stringBuilder2.indexOf(getResources().getString(R.string.tel)) + getResources().getString(R.string.tel).length()
                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanableInfo2.setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(getContext(), R.color.cr_1156bf))
                , stringBuilder2.indexOf(getResources().getString(R.string.tel))
                , stringBuilder2.indexOf(getResources().getString(R.string.tel)) + getResources().getString(R.string.tel).length()
                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spanableInfo2.setSpan(new ClickableSpan() {
                                  public void onClick(View widget) {
                                      Log.e(TAG, "onClick: " + getResources().getString(R.string.email));
                                  }

                                  public void updateDrawState(TextPaint ds) {
                                      ds.setUnderlineText(false);
                                  }
                              }
                , stringBuilder2.indexOf(getResources().getString(R.string.email))
                , stringBuilder2.indexOf(getResources().getString(R.string.email)) + getResources().getString(R.string.email).length()
                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanableInfo2.setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(getContext(), R.color.cr_1156bf))
                , stringBuilder2.indexOf(getResources().getString(R.string.email))
                , stringBuilder2.indexOf(getResources().getString(R.string.email)) + getResources().getString(R.string.email).length()
                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvInfo2.setText(spanableInfo2);
        tvInfo2.setMovementMethod(LinkMovementMethod.getInstance());
    }

    protected void initView() {
        subscribeDatas = new ArrayList<>();
        tvLink.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvLink.getPaint().setAntiAlias(true);
        rvBuy.setLayoutManager(new WrapContentManager(getContext()));
        adapter = new DataAdapterHelper()
                .setOnClickListener(onClickListener)
                .getAdapter(getContext(), subscribeDatas)
        ;
        rvBuy.setAdapter(adapter);
    }

    private DataAdapterHelper.OnClickListener onClickListener = new DataAdapterHelper.OnClickListener() {
        @Override
        public void onClick(SubscribeData.DataBean.DatasBean dataEntity) {
            if(StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())){
                startActivity(new Intent(getContext(), LoginActivity.class));
                return;
            }
            startActivity(new Intent(getContext(), MonthlyBuyActivity.class)
                    .putExtra("subscribeData", dataEntity)
            );
        }
    };

    @Override
    public void dataSuccess(SubscribeData news) {
        if (news.getCode() != 200 || news.getData() == null || news.getData().getDatas() == null)
            return;
        subscribeDatas.clear();
        subscribeDatas.addAll(news.getData().getDatas());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void dataError() {

    }

    @Override
    public void onRelogin() {
        reLogin();
    }

    @Override
    public void setPresenter(SubscribeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void netError() {
        ToastUtils.showInUIThead(getContext(), getString(R.string.net_error));
    }
}
