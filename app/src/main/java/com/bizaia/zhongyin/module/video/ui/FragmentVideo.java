package com.bizaia.zhongyin.module.video.ui;

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
import com.bizaia.zhongyin.module.video.ui.recently.RecentlyFragment;
import com.bizaia.zhongyin.module.video.ui.recommend.RecommendFragment;
import com.bizaia.zhongyin.module.video.ui.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentVideo extends ResumeFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabs;
     @BindView(R.id.ivMsgTips)
    ImageView ivMsgTips;
    //    List<String> titles;
    TabHelper tabHelper;

    public static FragmentVideo newInstance() {
        return new FragmentVideo();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        viewInit();

    }


    private void viewInit() {

//        titles = new ArrayList<>();
//        titles.add("推荐");
//        titles.add("最新");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RecommendFragment());
        fragments.add(new RecentlyFragment());

        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager(), fragments));
        tabs.setupWithViewPager(viewPager);

        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabHelper = new TabHelper(tabs);
        tabHelper.modify(getResources().getString(R.string.live_recommend)
                , getResources().getString(R.string.live_new));
    }

    @OnClick({R.id.btn_search, R.id.btn_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("type",3);
                startActivity(intent );
                break;
            case R.id.btn_message:
                startActivity(new Intent(getContext(), MessageActivity.class)
                        .putExtra("type",2));
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
