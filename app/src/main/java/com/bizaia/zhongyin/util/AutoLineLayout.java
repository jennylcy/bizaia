package com.bizaia.zhongyin.util;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

/**
 * 线性自动换行布局
 */
public class AutoLineLayout extends ViewGroup {

    public AutoLineLayout(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public AutoLineLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        measure(widthMeasureSpec, heightMeasureSpec, widthMode, widthSize, heightMode, heightSize);
    }

    private void measure(int widthMeasureSpec, int heightMeasureSpec, int widthMode, int widthSize, int heightMode, int heightSize){
        int availableWidth = widthSize - getPaddingLeft() - getPaddingRight();   // 可用宽度
        int parentViewHeight = 0; // 实际需要的高度
        int rowWidth = 0;  // 记录行宽
        int rowHeight = 0;  // 记录行高
        int childViewWidth; // 记录子View宽度
        int childViewHeight;    // 记录子View高度
        View childView;
        LinkedList<View> rowViews = new LinkedList<View>();
        boolean widthSizeUnspecified = widthMode == MeasureSpec.UNSPECIFIED;
        for (int position = 0; position < getChildCount(); position++) {
            childView = getChildAt(position);
            if(childView.getVisibility() == View.GONE) continue;

            // 测量并计算当前View需要的宽高
            measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0);
            LayoutParams lp = (LayoutParams) childView.getLayoutParams();
            childViewWidth = lp.leftMargin+childView.getMeasuredWidth()+lp.rightMargin;
            childViewHeight = lp.topMargin+childView.getMeasuredHeight()+lp.bottomMargin;

            // 如果宽度方面是包括，那么就记录总宽度，否则就换行并调整子Vie的宽度
            if(widthSizeUnspecified){
                rowWidth += childViewWidth;
                // 更新行高
                if(childViewHeight > parentViewHeight){
                    parentViewHeight = childViewHeight;
                }
            }else{
                // 如果宽度方面加上当前View就超过了可用宽度
                if(rowWidth + childViewWidth > availableWidth){
                    // 就调整之前的View的宽度以充满

                    rowViews.clear();
                    rowWidth = 0;   //  清空行宽
                    parentViewHeight += rowHeight;    // 增加View高度
                    rowHeight = 0;  // 清空行高
                }

                rowViews.add(childView);
                rowWidth += childViewWidth;  // 增加行宽

                // 更新行高
                if(childViewHeight > rowHeight){
                    rowHeight = childViewHeight;
                }
            }
        }

        if(!widthSizeUnspecified && !rowViews.isEmpty()){

            rowViews.clear();
            parentViewHeight += rowHeight;    // 增加View高度
        }

        int finalWidth = 0;
        int finalHeight = 0;
        switch (widthMode){
            case MeasureSpec.EXACTLY :
            case MeasureSpec.AT_MOST:
                finalWidth = widthSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                finalWidth = rowWidth + getPaddingLeft() + getPaddingRight();
                break;
        }
        switch (heightMode){
            case MeasureSpec.EXACTLY :
                finalHeight = heightSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                finalHeight = parentViewHeight + getPaddingTop() + getPaddingBottom();
                break;
        }
        setMeasuredDimension(finalWidth, finalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int viewWidth = r - l;
        int leftOffset = getPaddingLeft();
        int topOffset = getPaddingTop();
        int rowMaxHeight = 0;
        View childView;
        for( int w = 0, count = getChildCount(); w < count; w++ ){
            childView = getChildAt(w);
            LayoutParams lp = (LayoutParams) childView.getLayoutParams();

            // 如果加上当前子View的宽度后超过了ViewGroup的宽度，就换行
            int occupyWidth = lp.leftMargin + childView.getMeasuredWidth() + lp.rightMargin;
            if(leftOffset + occupyWidth + getPaddingRight() > viewWidth){
                leftOffset = getPaddingLeft();  // 回到最左边
                topOffset += rowMaxHeight;  // 换行
                rowMaxHeight = 0;
            }

            int left = leftOffset + lp.leftMargin;
            int top = topOffset + lp.topMargin;
            int right = leftOffset+ lp.leftMargin + childView.getMeasuredWidth();
            int bottom =  topOffset + lp.topMargin + childView.getMeasuredHeight();
            childView.layout(left, top, right, bottom);

            // 横向偏移
            leftOffset += occupyWidth;

            // 试图更新本行最高View的高度
            int occupyHeight = lp.topMargin + childView.getMeasuredHeight() + lp.bottomMargin;
            if(occupyHeight > rowMaxHeight){
                rowMaxHeight = occupyHeight;
            }
        }
    }

    /**
     * Returns a set of layout parameters with a width of
     * {@link ViewGroup.LayoutParams#MATCH_PARENT},
     * and a height of {@link ViewGroup.LayoutParams#MATCH_PARENT}.
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public static class LayoutParams extends MarginLayoutParams {

        /**
         * {@inheritDoc}
         */
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(int width, int height) {
            super(width, height);
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        /**
         * Copy constructor. Clones the width, height, margin values, and
         * gravity of the source.
         *
         * @param source The layout params to copy from.
         */
        public LayoutParams(LayoutParams source) {
            super(source);
        }
    }
}