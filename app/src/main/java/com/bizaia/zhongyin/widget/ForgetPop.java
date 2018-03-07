package com.bizaia.zhongyin.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bizaia.zhongyin.R;

import java.util.TimerTask;

/**
 * Created by zyz on 2017/3/6.
 */

public abstract class ForgetPop {

    PopupWindow popupWindow;//弹窗
    View popupWindowView;
    View parentView;
    Context context;
    OnDismissFinishListener onDismissFinishListener;

    public ForgetPop(Context context, View pView, int layoutRes) {
        parentView = pView;
        this.context = context;
        popupWindowView = LayoutInflater.from(this.context).inflate(layoutRes, null);
        viewInit();

        //内容，高度，宽度
        popupWindow = new PopupWindow(popupWindowView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                true
        );

        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setClippingEnabled(false);

        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onDismissFinishListener != null) {
                    parentView.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            onDismissFinishListener.OnDismissFinish();
                        }
                    }, 500);
                }
            }
        });
//        popupWindow.setHeight(DisplayUtils.getFullScreenSize(context)[1]);
    }

    public void show() {
        try {
            popupWindow.showAtLocation(parentView, Gravity.TOP, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isShowing(){
        return  popupWindow.isShowing();
    }

    public void show(int gravity, int x, int y) {
        try {
            popupWindow.showAtLocation(parentView, gravity, x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public View findViewById(int idView) {
        return popupWindowView.findViewById(idView);
    }

    public void setOnFocusListener(int idView, View.OnFocusChangeListener onFocusChangeListener) {
        View view = findViewById(idView);
        view.setFocusable(true);
        view.setOnFocusChangeListener(onFocusChangeListener);
    }

    /**
     * 控件的相关初始化设置
     */
    public abstract void viewInit();

    public void setOnClickListener(int idView, View.OnClickListener onClickListener) {
        findViewById(idView).setOnClickListener(onClickListener);
    }

    public void setText(int idView, String text) {
        if (popupWindowView.findViewById(idView) instanceof TextView) {
            ((TextView) findViewById(idView)).setText(text);
        }
    }

    public View getView(int idView) {
        return findViewById(idView);
    }

    public void setImage(int idView, int res) {
        ((ImageView) findViewById(idView)).setImageDrawable(ContextCompat.getDrawable(context, res));
    }

    public void setVisibility(int idView, int visibility) {
        findViewById(idView).setVisibility(visibility);
    }

    /**
     * 弹窗消失
     */
    public void dismiss() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 设置监听
     *
     * @param onDismissFinishListener dismiss完成监听
     */
    public void setOnDismissFinishListener(OnDismissFinishListener onDismissFinishListener) {
        this.onDismissFinishListener = onDismissFinishListener;
    }

    /**
     * dismiss结束监听
     */
    public interface OnDismissFinishListener {
        void OnDismissFinish();
    }

}