package com.bizaia.zhongyin.module.monthly.ui;

import android.content.Intent;
import android.os.Bundle;
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
import com.bizaia.zhongyin.module.message.ui.MessageActivity;
import com.bizaia.zhongyin.module.monthly.ui.all.AllMonthlyFragment;
import com.bizaia.zhongyin.module.monthly.ui.recently.RecentlyFragment;
import com.bizaia.zhongyin.module.monthly.ui.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentMonthly extends ResumeFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.btn_search)
    ImageView btn_search;
    @BindView(R.id.ivMsgTips)
    ImageView ivMsgTips;
    private TabHelper tabHelper;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    public static FragmentMonthly newInstance() {
        return new FragmentMonthly();
    }

    private void init() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RecentlyFragment());
        fragments.add(new AllMonthlyFragment());
//        fragments.add(new SubscribeFragment());
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager(), fragments));
        tabs.setupWithViewPager(viewPager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabHelper = new TabHelper(tabs);
        tabHelper.modify(getResources().getString(R.string.live_new)
                , getResources().getString(R.string.monthly_before)
                );
    }

    @OnClick({R.id.btn_search, R.id.btn_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("type",4);
                startActivity(intent );
                break;
            case R.id.btn_message:
                startActivity(new Intent(getContext(), MessageActivity.class)
                        .putExtra("type",3));
                break;
        }
    }


    @Override
    public void showNum() {
        super.showNum();
        if(ivMsgTips!=null&&BIZAIAApplication.getInstance().getMsgNum()!=0){
            ivMsgTips.setVisibility(View.VISIBLE);
        }else if(ivMsgTips!=null){
            ivMsgTips.setVisibility(View.GONE);
        }
    }
}
