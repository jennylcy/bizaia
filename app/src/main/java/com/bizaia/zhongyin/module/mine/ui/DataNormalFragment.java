package com.bizaia.zhongyin.module.mine.ui;

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
import com.bizaia.zhongyin.module.common.ui.DataView;
import com.bizaia.zhongyin.module.live.imhelper.IMHelper;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/3/8.
 * the fragment for request data, use better in viewpager
 *
 * @param <T> data type
 * @param <V> presenter
 */
public abstract class DataNormalFragment<T, V extends BasePresenter> extends NormalFragment<CustomAdapter> implements DataView<T, V> {
    protected List<Object> dataList;
    protected V presenter;
    protected Long orgId;
    protected Long areaId;
    protected Long cateId;
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        instancePresenter();
        dataList = new ArrayList<>();
        return super.createView(inflater, container, savedInstanceState);
    }

    private void getDisposable() {
        addSubscription(Observable.timer(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        pageNo = 1;
                        requireData(pageNo, pageSize,orgId,areaId,cateId);
                    }
                }));
    }

    @Override
    protected void onLoadLazy(Bundle reLoadBundle) {
        super.onLoadLazy(reLoadBundle);
        getDisposable();
    }

    @Override
    protected RecyclerView.LayoutManager layoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected void onRefresh() {
        pageNo = 1;
        requireData(pageNo, pageSize,orgId,areaId,cateId);
    }

    protected abstract void requireData(int pageNo, int pageSize, Long orgId, Long areaId, Long cateId);

    @Override
    protected void onLoadMore() {
        requireData(++pageNo, pageSize,orgId,areaId,cateId);
    }

    public abstract void instancePresenter();

    @Override
    public void dataSuccess(T news) {
        canLoadMore = true;
        moreWrapper.clearLoadMore(false);

        adapter.hide("DATA_EMPTY")
                .hide("NET_ERROR")
                .hide("DATA_ERROR");
    }

    public void dataError() {
        dataList.clear();
        adapter.hide("DATA_EMPTY")
                .hide("NET_ERROR")
                .show("DATA_ERROR");

        moreWrapper.clearLoadMore(true);
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
}
