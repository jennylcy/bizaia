package com.bizaia.zhongyin.widget.adapter.LoadMore;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.util.AnimationGroupManager;
import com.bizaia.zhongyin.widget.adapter.ViewHolder;

public class LoadMoreWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private RecyclerView.Adapter mInnerAdapter;
    private View mLoadMoreView;

    private AnimationGroupManager containerAnimation;
    private long animationDuring = 200;

    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            setLoadMoreHeight((int) ((float) animation.getAnimatedValue()));
        }
    };
    private AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            if (getLoadMoreView().findViewById(R.id.iv_loading) != null) {
                ImageView ivLoading = (ImageView) getLoadMoreView().findViewById(R.id.iv_loading);
                if (ivLoading.getDrawable() != null && ivLoading.getDrawable() instanceof AnimationDrawable) {
                    AnimationDrawable loadingAnimation = (AnimationDrawable) ivLoading.getDrawable();
                    loadingAnimation.stop();
                }
            }
        }
    };

    private void setLoadMoreHeight(int height) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) mLoadMoreView.getLayoutParams();
        layoutParams.height = height;
        mLoadMoreView.setLayoutParams(layoutParams);
    }

    public View getLoadMoreView() {
        return mLoadMoreView;
    }

    public LoadMoreWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    private boolean isShowLoadMore(int position) {
        return (position >= mInnerAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            ViewHolder holder = null;
            if (mLoadMoreView != null) {
                holder = ViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    private boolean clearLoadMore = false;

    public void clearLoadMore(boolean clearLoadMore) {
        clearLoadMore(clearLoadMore, false);
    }

    public void clearLoadMore(boolean clearLoadMore, boolean withAnimation) {
        if (clearLoadMore == this.clearLoadMore) return;
        this.clearLoadMore = clearLoadMore;
        if (withAnimation) {
            initAnimation();
            if (clearLoadMore) {
                containerAnimation.reHide();
            } else {
                containerAnimation.show();
            }
        } else {
            if (clearLoadMore) {
                setLoadMoreHeight(0);
            } else {
                setLoadMoreHeight(mLoadMoreView.getContext()
                        .getResources().getDimensionPixelSize(R.dimen.load_more_height));
            }
        }
    }

    private void initAnimation() {
        if (containerAnimation == null) {
            containerAnimation = new AnimationGroupManager(
                    animationDuring
                    , animatorListenerAdapter
                    , new AccelerateInterpolator()
                    , animatorUpdateListener
                    , 0
                    , mLoadMoreView.getContext().getResources().getDimensionPixelSize(R.dimen.load_more_height)
            );
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isShowLoadMore(position)) {
            if (mOnLoadMoreListener != null && !this.clearLoadMore) {
                mOnLoadMoreListener.onLoadMoreRequested();
            }
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isShowLoadMore(position)) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (isShowLoadMore(holder.getLayoutPosition())) {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + 1;
    }

    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
        return this;
    }
}
