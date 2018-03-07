package com.bizaia.zhongyin.widget.refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bizaia.zhongyin.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


public class PullToRefreshHeader extends FrameLayout implements PtrUIHandler {
    private static final String TAG = "PullToRefreshHeader";

    private AnimationDrawable loadingAnimation;

    public PullToRefreshHeader(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        View container = LayoutInflater.from(getContext()).inflate(R.layout.pull_to_refresh_header, null);
        ImageView ivLoading = (ImageView) container.findViewById(R.id.iv_loading);
        loadingAnimation = (AnimationDrawable) ivLoading.getDrawable();
        addView(container);
    }

    public PullToRefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToRefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        Log.e(TAG, "onUIReset: "+loadingAnimation);
        loadingAnimation.stop();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        Log.e(TAG, "onUIRefreshPrepare: "+loadingAnimation);
        loadingAnimation.start();
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        Log.e(TAG, "onUIRefreshBegin: "+loadingAnimation);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        Log.e(TAG, "onUIRefreshComplete: "+loadingAnimation);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
    }
}
