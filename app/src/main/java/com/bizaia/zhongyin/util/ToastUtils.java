package com.bizaia.zhongyin.util;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bizaia.zhongyin.R;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 消息提示工具
 * Created by jensen on 11/18/15.
 */
public class ToastUtils {

    private static Toast mToast;
    private static Toast mCustomToast;
    private static Toast mMineToast;

    private ToastUtils() {
    }

    /**
     * 系统自带Toast样式
     *
     * @param mContext
     * @param resId
     */
    public static void show(Context mContext, @StringRes int resId) {
        show(mContext, resId, Toast.LENGTH_SHORT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
    }

    /**
     * 系统自带Toast样式
     *
     * @param mContext
     * @param text
     */
    public static void show(Context mContext, CharSequence text) {
        show(mContext,text,Toast.LENGTH_SHORT);

//        View view =  LayoutInflater.from(mContext).inflate(R.layout.tost_show,null);
//        ((TextView) view.findViewById(R.id.tvTost)).setText(text);
//        showCustom(mContext, view, Toast.LENGTH_SHORT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
    }
    public static void showInUIThead(final Context mContext, final CharSequence text) {
        Observable.just(text)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        show(mContext,text,Toast.LENGTH_SHORT);
                    }
                });


//        View view =  LayoutInflater.from(mContext).inflate(R.layout.tost_show,null);
//        ((TextView) view.findViewById(R.id.tvTost)).setText(text);
//        showCustom(mContext, view, Toast.LENGTH_SHORT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
    }

    /**
     * 系统自带Toast样式
     *
     * @param mContext
     * @param resId
     * @param duration
     */
    public static void show(Context mContext, @StringRes int resId, int duration) {
        show(mContext, resId, duration, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
    }

    /**
     * 系统自带Toast样式
     *
     * @param mContext
     * @param text
     * @param duration
     */
    public static void show(Context mContext, CharSequence text, int duration) {
        show(mContext, text, duration, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
    }

    /**
     * 系统自带Toast样式
     *
     * @param mContext
     * @param resId
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void show(Context mContext, @StringRes int resId, int gravity, int xOffset, int yOffset) {
        show(mContext, resId, Toast.LENGTH_SHORT, gravity, xOffset, yOffset);
    }

    /**
     * 系统自带Toast样式
     *
     * @param mContext
     * @param text
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void show(Context mContext, CharSequence text, int gravity, int xOffset, int yOffset) {
        show(mContext, text, Toast.LENGTH_SHORT, gravity, xOffset, yOffset);
    }

    /**
     * @param mContext
     * @param resId
     * @param duration
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void show(Context mContext, @StringRes int resId, int duration, int gravity, int xOffset, int yOffset) {
        String res = "";
        try {
            res = mContext.getResources().getString(resId);
        } catch (Exception e) {
        }
        show(mContext, res, duration, gravity, xOffset, yOffset);
    }

    /**
     * 系统自带Toast样式
     *
     * @param mContext
     * @param text
     * @param duration
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void show(Context mContext, CharSequence text, int duration, int gravity, int xOffset, int yOffset) {
        if (mContext == null || TextUtils.isEmpty(text))
            return;
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, duration);
        } else {
            mToast.setDuration(duration);
            mToast.setText(text);
        }
        mToast.setGravity(gravity, xOffset, yOffset);
        mToast.show();
    }

    /******************************************/

    /**
     * 自定义Toast样式
     *
     * @param mContext
     * @param resource
     */
    public static void showCustom(Context mContext, @LayoutRes int resource) {
        showCustom(mContext, resource, Toast.LENGTH_SHORT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
    }

    /**
     * 自定义Toast样式
     *
     * @param mContext
     * @param view
     */
    public static void showCustom(Context mContext, View view) {
        showCustom(mContext, view, Toast.LENGTH_SHORT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
    }

    /**
     * 自定义Toast样式
     *
     * @param mContext
     * @param resource
     * @param duration
     */
    public static void showCustom(Context mContext, @LayoutRes int resource, int duration) {
        showCustom(mContext, resource, duration, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
    }

    /**
     * 自定义Toast样式
     *
     * @param mContext
     * @param view
     * @param duration
     */
    public static void showCustom(Context mContext, View view, int duration) {
        showCustom(mContext, view, duration, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
    }

    /**
     * 自定义Toast样式
     *
     * @param mContext
     * @param resource
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void showCustom(Context mContext, @LayoutRes int resource, int gravity, int xOffset, int yOffset) {
        showCustom(mContext, resource, Toast.LENGTH_SHORT, gravity, xOffset, yOffset);
    }

    /**
     * 自定义Toast样式
     *
     * @param mContext
     * @param view
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void showCustom(Context mContext, View view, int gravity, int xOffset, int yOffset) {
        showCustom(mContext, view, Toast.LENGTH_SHORT, gravity, xOffset, yOffset);
    }

    /**
     * @param mContext
     * @param resource
     * @param duration
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void showCustom(Context mContext, @LayoutRes int resource, int duration, int gravity, int xOffset, int yOffset) {
        View view = null;
        try {
            view = LayoutInflater.from(mContext).inflate(resource, null);
        } catch (Exception e) {
        }
        showCustom(mContext, view, duration, gravity, xOffset, yOffset);
    }

    /**
     * 自定义Toast样式
     *
     * @param mContext
     * @param view
     * @param duration
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void showCustom(Context mContext, View view, int duration, int gravity, int xOffset, int yOffset) {
        if (mContext == null || view == null)
            return;
        if (mCustomToast == null) {
            mCustomToast = new Toast(mContext);
        }
        mCustomToast.setView(view);
        mCustomToast.setDuration(duration);
        mCustomToast.setGravity(gravity, xOffset, yOffset);
        mCustomToast.show();
    }
}
