package com.bizaia.zhongyin.module.mine.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.ResumeFragment;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreHelper;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yan on 2017/3/8.
 *
 * @param <T> adapter
 */
public abstract class NormalFragment<T extends RecyclerView.Adapter> extends ResumeFragment {
    @BindView(R.id.recycler)
    RecyclerView recycler;

    protected LoadMoreWrapper moreWrapper;
    protected LoadMoreHelper loadMoreHelper;

    protected int pageNo = 1;
    protected int pageSize = 99;

    protected boolean canLoadMore;

    protected T adapter;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_sub_normal, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    protected void initView() {
        recycler.setLayoutManager(layoutManager());
        adapter = adapter();
        moreWrapper = new LoadMoreWrapper(adapter);
        loadMoreHelper = new LoadMoreHelper(moreWrapper);
        moreWrapper.setLoadMoreView(loadMoreHelper.getLoadMoreView(getContext()));
        moreWrapper.clearLoadMore(true);
        moreWrapper.setOnLoadMoreListener(onLoadMoreListener);
        recycler.setAdapter(moreWrapper);
    }


    private LoadMoreWrapper.OnLoadMoreListener onLoadMoreListener = new LoadMoreWrapper.OnLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            Log.e("NormalFragment", "onLoadMoreRequested: ------------>" +canLoadMore);
            if (canLoadMore) {
                onLoadMore();
            }
        }
    };

    private View getLoadMoreView() {
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
             getContext().getResources().getDimensionPixelSize(R.dimen.load_more_height));
        View loadMore = LayoutInflater.from(getContext()).inflate(R.layout.item_loadmore, null);
        loadMore.setLayoutParams(layoutParams);
        return loadMore;
    }

    protected abstract T adapter();

    protected abstract RecyclerView.LayoutManager layoutManager();

    protected abstract void onLoadMore();

    protected abstract void onRefresh();

}
