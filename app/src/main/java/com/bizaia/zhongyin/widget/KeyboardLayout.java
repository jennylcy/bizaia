package com.bizaia.zhongyin.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Administrator
 * Created on 2017/9/7 14:27
 */

public class KeyboardLayout extends RelativeLayout {

    private int[] mInsets = new int[4];

    public KeyboardLayout(Context context) {
        super(context);
    }

    public KeyboardLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public KeyboardLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public final int[] getInsets() {
        return mInsets;
    }

    /**
     * 此方法以过期，当应用最低API支持为20后，可以重写以下方法
     *
     * @Override public final WindowInsets onApplyWindowInsets(WindowInsets insets) {
     * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
     * mInsets[0] = insets.getSystemWindowInsetLeft();
     * mInsets[1] = insets.getSystemWindowInsetTop();
     * mInsets[2] = insets.getSystemWindowInsetRight();
     * return super.onApplyWindowInsets(insets.replaceSystemWindowInsets(0, 0, 0,
     * insets.getSystemWindowInsetBottom()));
     * } else {
     * return insets;
     * }
     * }
     * 未测试……
     */
    @Override
    protected final boolean fitSystemWindows(Rect insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            mInsets[0] = insets.left;
            mInsets[1] = insets.top;
            mInsets[2] = insets.right;
            return super.fitSystemWindows(insets);
        } else {
            return super.fitSystemWindows(insets);
        }
    }
}

