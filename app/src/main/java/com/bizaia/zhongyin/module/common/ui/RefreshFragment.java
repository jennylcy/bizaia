package com.bizaia.zhongyin.module.common.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.ResumeFragment;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreHelper;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreWrapper;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by yan on 2017/3/8.
 *
 * @param <T> adapter
 */
public abstract class RefreshFragment<T extends RecyclerView.Adapter> extends ResumeFragment {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh_layout)
    public PtrClassicFrameLayout refreshLayout;

    protected LoadMoreWrapper moreWrapper;
    protected LoadMoreHelper loadMoreHelper;

    protected int pageNo = 1;
    protected int pageSize = 5;

    protected boolean canLoadMore;

    protected T adapter;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_sub, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    protected void initView() {
        refreshLayout.setLastUpdateTimeRelateObject(this);
        refreshLayout.setPtrHandler(defaultHandler);
        recycler.setLayoutManager(layoutManager());
        adapter = adapter();
        moreWrapper = new LoadMoreWrapper(adapter);
        loadMoreHelper = new LoadMoreHelper(moreWrapper);
        moreWrapper.setLoadMoreView(loadMoreHelper.getLoadMoreView(getContext()));
        moreWrapper.clearLoadMore(true);
        moreWrapper.setOnLoadMoreListener(onLoadMoreListener);

        recycler.setAdapter(moreWrapper);
    }

    private PtrClassicFrameLayout.PtrDefaultHandler defaultHandler = new PtrClassicFrameLayout.PtrDefaultHandler() {
        public void onRefreshBegin(PtrFrameLayout frame) {
            recycler.scrollToPosition(0);
            onRefresh();
        }
    };

    private LoadMoreWrapper.OnLoadMoreListener onLoadMoreListener = new LoadMoreWrapper.OnLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            if (canLoadMore) {
                onLoadMore();
            }
        }
    };

    protected abstract T adapter();

    protected abstract RecyclerView.LayoutManager layoutManager();

    protected abstract void onLoadMore();

    protected abstract void onRefresh();

}
