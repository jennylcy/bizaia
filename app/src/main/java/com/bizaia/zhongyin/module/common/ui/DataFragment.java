package com.bizaia.zhongyin.module.common.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BasePresenter;
import com.bizaia.zhongyin.module.common.data.CacheData;
import com.bizaia.zhongyin.module.live.imhelper.IMHelper;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.util.ACache;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by yan on 2017/3/8.
 * the fragment for request data, use better in viewpager
 *
 * @param <T> data type
 * @param <V> presenter
 */
public abstract class DataFragment<T, V extends BasePresenter> extends RefreshFragment<CustomAdapter> implements DataView<T, V> {
    protected static final int STATE_DATA_EMPTY = 0x100;
    protected static final int STATE_DATA_ERROR = 0x101;
    protected static final int STATE_NET_ERROR = 0x102;
    protected static final int STATE_NORMAL = 0x103;
    protected int currentState = 0;
    protected Long orgId;
    protected String areaId;
    protected Long cateId;
    protected List<Object> dataList;
    protected V presenter;
    private String cacheName;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        instancePresenter();
        cacheName = cacheName();
        dataList = new ArrayList<>();
        return super.createView(inflater, container, savedInstanceState);
    }

    @Override
    protected void onLoadLazy(Bundle reLoadBundle) {
        super.onLoadLazy(reLoadBundle);
        getCacheData();

        refreshLayout.autoRefresh();
    }

    @Override
    protected RecyclerView.LayoutManager layoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected void onRefresh() {
        pageNo = 1;
        requireData(pageNo, pageSize, orgId, areaId, cateId);
    }

    @Override
    protected void onLoadMore() {
        requireData(++pageNo, pageSize, orgId, areaId, cateId);
    }

    @Override
    public void dataSuccess(T news) {
        setShowState(STATE_NORMAL, false);

        canLoadMore = true;
        moreWrapper.clearLoadMore(false);
    }

    public void dataError() {
        if (pageNo > 1) {
            loadMoreHelper.setStateDataError();
        } else {
            if (!dataList.isEmpty()) {
                ToastUtils.showInUIThead(getContext(), getString(R.string.update_date_error));
            } else {
                dataList.clear();
                setShowState(STATE_DATA_ERROR, false);

                moreWrapper.clearLoadMore(true);
                moreWrapper.notifyDataSetChanged();
            }
        }

        refreshLayout.refreshComplete();
    }

    protected void setShowState(int state, boolean withCommit) {
        currentState = state;

        adapter.hide("DATA_EMPTY")
                .hide("NET_ERROR")
                .hide("DATA_ERROR");

        switch (state) {
            case STATE_DATA_EMPTY:
                adapter.show("DATA_EMPTY");
                break;
            case STATE_DATA_ERROR:
                adapter.show("DATA_ERROR");

                break;
            case STATE_NET_ERROR:
                adapter.show("NET_ERROR");
                break;
            case STATE_NORMAL:
                break;
        }
        if (withCommit) {
            moreWrapper.notifyDataSetChanged();
        }

    }

    protected void checkLoadMoreAble() {
        addSubscription(Observable.timer(150, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long value) {
                        if (canLoadMore()) {
                            loadMoreHelper.setStateDataLoading();
                            canLoadMore = true;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }

    protected boolean canLoadMore() {
        if (getRvLastItemToTop() > recycler.getMeasuredHeight()) {
            return true;
        }
        return false;
    }

    protected int getRvLastItemToTop() {
        if (recycler.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recycler.getLayoutManager();
            View view = recycler.getChildAt(layoutManager.findLastVisibleItemPosition());
            if (view != null) {
                return view.getTop() + view.getMeasuredHeight();
            }
        }
        return 0;
    }

    @Override
    protected void onSaveArguments(Bundle bundle) {
        super.onSaveArguments(bundle);
        if (!dataList.isEmpty()) {
            CacheData<Object> objectCacheData = new CacheData<>(dataList);
            ACache.get(getContext().getApplicationContext()
                    , getContext().getApplicationContext().getCacheDir().getAbsolutePath())
                    .put(cacheName, objectCacheData, ACache.TIME_DAY * 7);
        }
    }

    @Override
    protected void onReloadArguments(Bundle bundle) {
        super.onReloadArguments(bundle);
        if (isLazyLoad) {
            getCacheData();
        }
    }

    private boolean getCacheData() {
//        if (getContext() == null) return false;
//        CacheData cacheData = (CacheData) ACache.get(getContext()
//                , getContext().getApplicationContext().getCacheDir().getAbsolutePath())
//                .getAsObject(cacheName);
//        if (cacheData != null) {
//
//            dataList.clear();
//            dataList.addAll(cacheData.getDataList());
//            moreWrapper.notifyDataSetChanged();
//
//            return true;
//        }
        return false;
    }

    public void reLogin(){
        ToastUtils.show(getActivity(), R.string.app_logout_other);
        BIZAIAApplication.getInstance().setToken("");
        BIZAIAApplication.getInstance().setUserBean(null);
        IMHelper.getInstance().imLoginout();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Override
    public void setPresenter(V presenter) {
        this.presenter = presenter;
    }

    public abstract void instancePresenter();

    protected abstract String cacheName();

    protected abstract void requireData(int pageNo, int pageSize, Long orgId, String areaId, Long cateId);

}
