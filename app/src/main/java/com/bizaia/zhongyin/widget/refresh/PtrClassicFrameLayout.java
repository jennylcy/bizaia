package com.bizaia.zhongyin.widget.refresh;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class PtrClassicFrameLayout extends PtrFrameLayout {
    private int mPagingTouchSlop;
    private PullToRefreshHeader mPtrClassicHeader;
     private PtrClassicDefaultFooter mPtrClassicFooter;

    public PtrClassicFrameLayout(Context context) {
        super(context);
        initViews();
    }


    public PtrClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    public void setRefreshBarMarginTop(int marginTop) {
//        mPtrClassicHeader.setRefreshBarMarginTop(marginTop);
    }

    private void initViews() {
//        mPtrClassicHeader = new PullToRefreshHeader(getContext());
        mPtrClassicHeader = new PullToRefreshHeader(getContext());
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);
     /*   mPtrClassicFooter = new PtrClassicDefaultFooter(getContext());
        setFooterView(mPtrClassicFooter);
        addPtrUIHandler(mPtrClassicFooter);*/
    }

    public void setCanLoadMore(boolean canLoadMore){
        if(canLoadMore){
            mPtrClassicFooter = new PtrClassicDefaultFooter(getContext());
            setFooterView(mPtrClassicFooter);
            addPtrUIHandler(mPtrClassicFooter);
        }
    }


//    public PullToRefreshHeader getHeader() {
//        return mPtrClassicHeader;
//    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        setLastUpdateTimeHeaderKey(key);
        setLastUpdateTimeFooterKey(key);
    }

    public void setLastUpdateTimeHeaderKey(String key) {
        if (mPtrClassicHeader != null) {
        }
    }

    public void setLastUpdateTimeFooterKey(String key) {
        if (mPtrClassicFooter != null) {
            mPtrClassicFooter.setLastUpdateTimeKey(key);
        }
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        setLastUpdateTimeHeaderRelateObject(object);
        setLastUpdateTimeFooterRelateObject(object);
    }

    public void setLastUpdateTimeHeaderRelateObject(Object object) {
        if (mPtrClassicHeader != null) {
        }
    }

    public void setLastUpdateTimeFooterRelateObject(Object object) {
        if (mPtrClassicFooter != null) {
            mPtrClassicFooter.setLastUpdateTimeRelateObject(object);
        }
    }

    public static abstract class PtrDefaultHandler extends PtrDefaultHandler2 {

        @Override
        public void onLoadMoreBegin(in.srain.cube.views.ptr.PtrFrameLayout frame) {

        }

    }
    private boolean canRefresh = true;

    public void setCanRefresh(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }

    private ViewPager mViewPager;
    private int mTouchSlop;
    private float startY;
    private float startX;
    // 记录viewPager是否拖拽的标记
    private boolean mIsHorizontalMove;
    // 记录事件是否已被分发
    private boolean isDeal;
    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
//        if (e.getActionMasked() == MotionEvent.ACTION_UP ||
//                e.getActionMasked() == MotionEvent.ACTION_CANCEL) {
//            canRefresh = true;
//            Log.e("dispatchTouchEvent","----------------->"+canRefresh);
//        }
        if (mViewPager == null) {
            if (canRefresh)
                return super.dispatchTouchEvent(e);
            else {
                return super.dispatchTouchEventSupper(e);
            }
        }else{
            int action = e.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // 记录手指按下的位置
                    startY = e.getY();
                    startX = e.getX();
                    // 初始化标记
                    mIsHorizontalMove = false;
                    isDeal = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    // 如果已经判断出是否由横向还是纵向处理，则跳出
                    if (isDeal) {
                        break;
                    }
                    mIsHorizontalMove = true;
                    // 获取当前手指位置
                    float endY = e.getY();
                    float endX = e.getX();
                    float distanceX = Math.abs(endX - startX);
                    float distanceY = Math.abs(endY - startY);
                    if (distanceX != distanceY) {
                        // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
                        if (distanceX > mTouchSlop && distanceX > distanceY) {
                            mIsHorizontalMove = true;
                            isDeal = true;
                        } else if (distanceY > mTouchSlop) {
                            mIsHorizontalMove = false;
                            isDeal = true;
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mIsHorizontalMove = false;
                    isDeal = false;
                    break;
            }
            if (mIsHorizontalMove) {
                return dispatchTouchEventSupper(e);
            }else{
                if (canRefresh)
                    return super.dispatchTouchEvent(e);
                else {
                    return super.dispatchTouchEventSupper(e);
                }
            }
        }
    }
}
