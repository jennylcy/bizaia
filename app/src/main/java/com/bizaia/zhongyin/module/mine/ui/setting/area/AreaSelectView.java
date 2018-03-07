package com.bizaia.zhongyin.module.mine.ui.setting.area;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.mine.data.AreaData;
import com.bizaia.zhongyin.util.AnimationGroupManager;
import com.bizaia.zhongyin.util.AnimationUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.SizeUtils;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by yan on 2017/3/21.
 */

public class AreaSelectView extends FrameLayout implements View.OnClickListener {
    private static final String TAG = "AreaSelectView";

    private TextView tvTitle;
    private ImageView ivMore;
    private FlexboxLayout flexboxLayout;
    private FrameLayout container;
    private View flTitleBar;
    private AreaData.DataBean areaData;

    private AnimationGroupManager moreAnimation;
    private AnimationGroupManager planeAnimation;
    private AnimationGroupManager containerAnimation;
    private boolean isAnimationShow = false;
    private boolean isSureSize = false;
    private int animationDuring = 150;

    private OnItemSelect onItemSelect;
    private CompositeDisposable compositeDisposable;

    private int flexLayoutHeight;
    private int allNumbs;


    public void init(Context context) {

        compositeDisposable = new CompositeDisposable();
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.area_select_view, this, false);
        addView(frameLayout);
        tvTitle = (TextView) frameLayout.findViewById(R.id.tv_title);
        flexboxLayout = (FlexboxLayout) frameLayout.findViewById(R.id.tl_box_layout);
        flexboxLayout.addOnLayoutChangeListener(onLayoutChangeListener);
        container = (FrameLayout) frameLayout.findViewById(R.id.container);
        flTitleBar = frameLayout.findViewById(R.id.fl_title_bar);
        flTitleBar.setOnClickListener(this);
        ivMore = (ImageView) frameLayout.findViewById(R.id.iv_push_down);

        RxBus.getInstance().getEvent(AreaActivity.ClearAll.class)
                .subscribe(new Consumer<AreaActivity.ClearAll>() {
                    @Override
                    public void accept(AreaActivity.ClearAll clearAll) throws Exception {
                        try {
                            clearView();
                        }catch (Exception e){}
                    }
                });
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        compositeDisposable.clear();
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void hide() {
        if (isAnimationShow) {
            isAnimationShow = false;
            if (flexboxLayout.getChildCount() != 0) {
                planeAnimation.hide();
                containerAnimation.hide();
                moreAnimation.hide();
            }
        }
    }

    public void setAreaData(AreaData.DataBean areaData) {
        this.areaData = areaData;
        Log.e(TAG, "setAreaData: " + flexboxLayout.getChildCount() + "    " + areaData.getProvinceList().size());

        for (int i = 0; i < areaData.getProvinceList().size(); i++) {
            Log.e(TAG, "setAreaData: " + areaData.getProvinceList().get(i).isSelect());
        }
        if (flexboxLayout != null) {
            for (int i = 0; i < flexboxLayout.getChildCount(); i++) {
                if (areaData.getProvinceList().size() > i) {
                    flexboxLayout.getChildAt(i).setSelected(areaData.getProvinceList().get(i).isSelect());
                }
            }
        }
    }

    public boolean isAnimationShow() {
        return isAnimationShow;
    }

    private OnLayoutChangeListener onLayoutChangeListener = new OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom
                , int oldLeft, int oldTop, int oldRight, int oldBottom) {
            Log.e(TAG, "onLayoutChange: " + bottom + "   " + top);
            flexLayoutHeight = bottom - top;
            if (isAnimationShow && !isSureSize) {
                isSureSize = true;
                animationInit();
                planeAnimation.show();
                containerAnimation.show();
                moreAnimation.show();
            }
        }
    };

    public void clearView() {
        planeAnimation.hide();
        containerAnimation.hide();
        moreAnimation.hide();
        flexboxLayout.removeAllViews();
    }

    private OnClickListener onBarClickListener;


    public void setOnBarClickListener(OnClickListener onBarClickListener) {
        this.onBarClickListener = onBarClickListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_title_bar:
                triggerAnimation();
                if (onBarClickListener != null) {
                    onBarClickListener.onClick(this);
                }
                break;
            case R.id.tv_area:
                int position = (int) v.getTag();
                areaData.getProvinceList().get(position).setSelect(!areaData.getProvinceList().get(position).isSelect());
                v.setSelected(areaData.getProvinceList().get(position).isSelect());
                if (onItemSelect != null) {
                    List<AreaData.DataBean.ProvinceListBean> provinceListBeen = new ArrayList<>();
                    provinceListBeen.add(areaData.getProvinceList().get(position));
                    onItemSelect.onSelect(areaData, provinceListBeen
                            , areaData.getProvinceList().get(position).isSelect());
                }
                break;
        }
    }

    private void animationInit() {
        if (moreAnimation == null)
            moreAnimation = new AnimationGroupManager(
                    ivMore
                    , animationDuring
                    , AnimationUtils.AnimationType.ROTATION
                    , null
                    , new AccelerateInterpolator()
                    , 90
                    , 270
            );


        if (planeAnimation == null) {
            planeAnimation = new AnimationGroupManager(
                    flexboxLayout
                    , animationDuring
                    , AnimationUtils.AnimationType.TRANSLATEY
                    , null
                    , new AccelerateInterpolator()
                    , -flexLayoutHeight
                    , 0
            );

            if (containerAnimation == null) {
                containerAnimation = new AnimationGroupManager(
                        animationDuring
                        , null
                        , new AccelerateInterpolator()
                        , animatorUpdateListener
                        , flTitleBar.getMeasuredHeight() + SizeUtils.sp2px(getContext(), 1)
                        , flexLayoutHeight
                        + flTitleBar.getMeasuredHeight()
                        + SizeUtils.sp2px(getContext(), 1)
                );
            }
        }
    }

    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) getLayoutParams();
            layoutParams.height = (int) ((float) animation.getAnimatedValue());
            setLayoutParams(layoutParams);
        }
    };

    private void triggerAnimation() {
        if (flexboxLayout.getVisibility() != VISIBLE) {
            flexboxLayout.setVisibility(VISIBLE);
        }

        if (flexboxLayout.getChildCount() == 0) {
            if (isAnimationShow = !isAnimationShow) {
                for (int i = 0; i < areaData.getProvinceList().size(); i++) {
                    TextView view = (TextView) LayoutInflater.from(getContext())
                            .inflate(R.layout.activity_mine_setting_area_item, null);
                    view.setText(areaData.getProvinceList().get(i).getProvince());
                    view.setTag(i);
                    view.setSelected(areaData.getProvinceList().get(i).isSelect());
                    view.setOnClickListener(this);
                    flexboxLayout.addView(view);
                }
            }
        } else {
            if (isAnimationShow = !isAnimationShow) {
                planeAnimation.show();
                containerAnimation.show();
                moreAnimation.show();
            } else {
                planeAnimation.hide();
                containerAnimation.hide();
                moreAnimation.hide();
            }
        }
    }


    public void setOnItemSelect(OnItemSelect onItemSelect) {
        this.onItemSelect = onItemSelect;
    }

    public interface OnItemSelect {
        void onSelect(AreaData.DataBean areaData, List<AreaData.DataBean.ProvinceListBean> itemCurrentData, boolean selected);
    }

    public AreaSelectView(Context context) {
        super(context);
        init(context);
    }

    public AreaSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AreaSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


}
