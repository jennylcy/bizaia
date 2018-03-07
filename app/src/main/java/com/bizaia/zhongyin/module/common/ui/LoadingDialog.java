package com.bizaia.zhongyin.module.common.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bizaia.zhongyin.R;

/**
 * Created by yan on 2017/4/28.
 */

public class LoadingDialog extends Dialog {
    AnimationDrawable loadingAnimation;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.DialogProgress);
        View container = LayoutInflater.from(getContext()).inflate(R.layout.loading_dialog, null);
        ImageView ivLoading = (ImageView) container.findViewById(R.id.pb_loading);
        loadingAnimation = (AnimationDrawable) ivLoading.getDrawable();
        setContentView(container);

        setCanceledOnTouchOutside(true);
    }

    @Override
    public void show() {
        super.show();
        loadingAnimation.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        loadingAnimation.stop();
    }

    @Override
    public void hide() {
        super.hide();
        dismiss();
    }
}
