package com.bizaia.zhongyin.module.common.util;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.TextView;

import com.bizaia.zhongyin.R;

/**
 * Created by yan on 2017/3/16.
 */

public class TabHelper {
    private TabLayout tabLayout;
    private boolean show;

    public TabHelper(TabLayout tableLayout) {
        this.tabLayout = tableLayout;
    }

    public TabHelper(TabLayout tableLayout ,boolean  isShow) {
        this.tabLayout = tableLayout;
        this.show = isShow;
    }

    public void modify(String... titles) {
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(R.layout.main_tab_style);
            }

            if(show){
                tab.getCustomView().findViewById(R.id.tab_indicator).setVisibility(View.GONE);
            }

            if (i == 0 && tab != null && tab.getCustomView() != null) {
                tab.getCustomView().findViewById(R.id.tv_title).setSelected(true);
            }

            if (tab != null && tab.getCustomView() != null) {
                TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tv_title);
                textView.setText(titles[i]);
            }
        }
        onClickInit();

    }

    private void onClickInit() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab != null && tab.getCustomView() != null) {
                    tab.getCustomView().findViewById(R.id.tv_title).setSelected(true);
                    tab.getCustomView().findViewById(R.id.tab_line).setSelected(true);
                    tab.getCustomView().findViewById(R.id.tab_indicator).setSelected(true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab != null && tab.getCustomView() != null) {
                    tab.getCustomView().findViewById(R.id.tv_title).setSelected(false);
                    tab.getCustomView().findViewById(R.id.tab_line).setSelected(false);
                    tab.getCustomView().findViewById(R.id.tab_indicator).setSelected(false);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}