package com.bizaia.zhongyin.module.discovery.ui;

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
import com.bizaia.zhongyin.module.discovery.ui.chair.ChairFragment;
import com.bizaia.zhongyin.module.discovery.ui.organization.OrganizationFragment;
import com.bizaia.zhongyin.module.discovery.ui.search.SearchActivity;
import com.bizaia.zhongyin.module.message.ui.MessageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentDiscovery extends ResumeFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.ivMsgTips)
    ImageView ivMsgTips;

    private TabHelper tabHelper;

    public static FragmentDiscovery newInstance() {
        return new FragmentDiscovery();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OrganizationFragment());
        fragments.add(new ChairFragment());
//        fragments.add(new JobsFragment());
//        fragments.add(new NewsFragment());
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager(), fragments));
        tabs.setupWithViewPager(viewPager);
//        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabHelper = new TabHelper(tabs);
        tabHelper.modify(getString(R.string.live_recommend)
                ,getString(R.string.live_new)
             );
    }

    @OnClick({R.id.btn_search, R.id.btn_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("type",1);
                startActivity(intent );
                break;
            case R.id.btn_message:
                startActivity(new Intent(getContext(), MessageActivity.class)
                .putExtra("type",1));
                break;
        }
    }

    @Override
    public void showNum() {
        super.showNum();
        if(BIZAIAApplication.getInstance().getMsgNum()!=0&&ivMsgTips!=null){
            ivMsgTips.setVisibility(View.VISIBLE);
        }else if(ivMsgTips!=null){
            ivMsgTips.setVisibility(View.GONE);
        }
    }
}
