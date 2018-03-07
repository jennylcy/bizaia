package com.bizaia.zhongyin;

import android.content.Context;

import com.bizaia.zhongyin.module.mine.ui.setting.push.PushPresenter;

/**
 * Created by yan on 2017/3/20.
 */

public class MainPresent extends PushPresenter implements MainContract.Presenter {
    private MainContract.View view;

    public MainPresent(Context context, MainContract.View view) {
        super(context, view);
        view.setPresenter(this);
        this.view = view;
    }

}
