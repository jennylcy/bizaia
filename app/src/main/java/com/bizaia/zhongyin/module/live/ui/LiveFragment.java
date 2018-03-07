package com.bizaia.zhongyin.module.live.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.ResumeFragment;
import com.bizaia.zhongyin.module.common.adapter.PagerAdapter;
import com.bizaia.zhongyin.module.common.util.TabHelper;
import com.bizaia.zhongyin.module.live.action.LoginSuccessAction;
import com.bizaia.zhongyin.module.live.ui.recently.LiveRecentlyFragment;
import com.bizaia.zhongyin.module.live.ui.search.SearchActivity;
import com.bizaia.zhongyin.module.message.ui.MessageActivity;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/6.
 */

public class LiveFragment extends ResumeFragment {

    @BindView(R.id.vpLive)
    ViewPager vpLive;

    @BindView(R.id.tlLive)
    TabLayout tlLive;
    @BindView(R.id.ivMsgTips)
    ImageView ivMsgTips;

    private TabHelper tabHelper;

    public static LiveFragment newInstance() {
        return new LiveFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, container, false);
        ButterKnife.bind(this, view);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new LiveRecentlyFragment("0"));
        fragments.add(new LiveRecentlyFragment("1"));
        if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())
                && BIZAIAApplication.getInstance().getUserBean() != null
                && 1 == BIZAIAApplication.getInstance().getUserBean().getUserType())
            fragments.add(new LiveRecentlyFragment("2"));
        vpLive.setAdapter(new PagerAdapter(getChildFragmentManager(), fragments));
        tlLive.setupWithViewPager(vpLive);

        tabHelper = new TabHelper(tlLive);
        if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())
                && BIZAIAApplication.getInstance().getUserBean() != null
                && 1 == BIZAIAApplication.getInstance().getUserBean().getUserType()) {
            tabHelper.modify(getResources().getString(R.string.live_recommend),
                    getResources().getString(R.string.live_new),
                    getResources().getString(R.string.live_my_lecture));
//            tlLive.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabHelper.modify(getResources().getString(R.string.live_recommend),
                    getResources().getString(R.string.live_new));
        }

        addSubscription(RxBus.getInstance().getEvent(LoginSuccessAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginSuccessAction>() {
                    @Override
                    public void onNext(LoginSuccessAction value) {
                        loginChange(value.isTeacher());
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));


        return view;
    }

    private void loginChange(boolean isTeacher) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new LiveRecentlyFragment("0"));
        fragments.add(new LiveRecentlyFragment("1"));
        if (isTeacher)
            fragments.add(new LiveRecentlyFragment("2"));
        vpLive.setAdapter(new PagerAdapter(getChildFragmentManager(), fragments));
        tlLive.setupWithViewPager(vpLive);

        tabHelper = new TabHelper(tlLive);
        if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())
                && BIZAIAApplication.getInstance().getUserBean() != null
                && 1 == BIZAIAApplication.getInstance().getUserBean().getUserType()) {
            tabHelper.modify(getResources().getString(R.string.live_recommend),
                    getResources().getString(R.string.live_new),
                    getResources().getString(R.string.live_my_lecture));
        } else {
            tabHelper.modify(getResources().getString(R.string.live_recommend),
                    getResources().getString(R.string.live_new));
        }
    }

    @OnClick({R.id.btn_search, R.id.btn_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.btn_message:
                startActivity(new Intent(getContext(), MessageActivity.class)
                        .putExtra("type", 3));
                break;
        }
    }

    @Override
    public void showNum() {
        super.showNum();
        if (ivMsgTips != null && BIZAIAApplication.getInstance().getMsgNum() != 0) {
            ivMsgTips.setVisibility(View.VISIBLE);
        } else if (ivMsgTips != null) {
            ivMsgTips.setVisibility(View.GONE);
        }
    }
}