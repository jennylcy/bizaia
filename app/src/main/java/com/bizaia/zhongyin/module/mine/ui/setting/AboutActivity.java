package com.bizaia.zhongyin.module.mine.ui.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yan on 2017/3/20.
 */

public class AboutActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    ImageView btn_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_about);
        ButterKnife.bind(this);
        setViewParent(btn_back);
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        finish();
    }
}
