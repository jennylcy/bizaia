package com.bizaia.zhongyin.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 2017/3/6.
 */

public class NavigationView extends LinearLayout {

    private List<ImageView> imageViews;
    private List<TextView> textViews;
    private LinearLayout container;
    private int selectIndex = -1;

    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        container = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.navigation_view, this, false);
        imageViews = new ArrayList<>();
        textViews = new ArrayList<>();
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) child;
                linearLayout.setTag(i);
                linearLayout.setOnClickListener(onClickListener);
                imageViews.add((ImageView) linearLayout.getChildAt(0));
                textViews.add((TextView) linearLayout.getChildAt(1));
            }
        }
        addView(container);
    }

    public void setSelect(int index) {
        if (selectIndex != -1) {
            imageViews.get(selectIndex).setSelected(false);
            textViews.get(selectIndex).setSelected(false);
        }
        imageViews.get(index).setSelected(true);
        textViews.get(index).setSelected(true);
        selectIndex = index;
    }

    private OnItemClickListener onItemClickListener;
    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (Integer) v.getTag();
            if (onItemClickListener != null) {
                if (index > 2)
                    index --;

                onItemClickListener.onItemClick(index);
                setSelect(index);
            }
        }
    };

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int index);
    }
}
