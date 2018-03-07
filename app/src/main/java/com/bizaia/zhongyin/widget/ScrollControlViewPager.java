package com.bizaia.zhongyin.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by yan on 2017/4/19.
 */

public class ScrollControlViewPager extends ViewPager {
    private boolean isScrollable=true;

    public void setScrollable(boolean scrollable) {
        isScrollable = scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isScrollable) {
            return false;
        } else {
            return super.onTouchEvent(ev);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isScrollable) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }

    }

    public ScrollControlViewPager(Context context) {
        super(context);
        init(context);

    }

    private void init(Context context) {
//        try {
//            Field mTouchSlop =ViewPager.class.getDeclaredField("mTouchSlop");
//            mTouchSlop.setAccessible(true);
//            try {
//                mTouchSlop.set(this, 0);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
    }

    public ScrollControlViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }
}
