package com.bizaia.zhongyin.module.live.ui.recently;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.ResumeFragment;
import com.bizaia.zhongyin.module.common.data.CacheData;
import com.bizaia.zhongyin.module.live.action.LiveEndAction;
import com.bizaia.zhongyin.module.live.adapter.DataLiveAdapterHelper;
import com.bizaia.zhongyin.module.live.api.LiveAPI;
import com.bizaia.zhongyin.module.live.imhelper.IMHelper;
import com.bizaia.zhongyin.module.live.iml.ILiveRecomView;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.util.ACache;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.TimeUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreHelper;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreWrapper;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("ValidFragment")
public class LiveRecentlyFragment extends ResumeFragment implements ILiveRecomView {
    @BindView(R.id.rvLive)
    RecyclerView rvLive;

    List<Object> dataList;
    LoadMoreWrapper moreWrapper;
    CustomAdapter adapter;
    @BindView(R.id.pfLive)
    PtrClassicFrameLayout pfLive;

    private LiveAPI liveAPI;
    private String type;
    private int pageNum = 0;
    private int pageSize = 5;
    protected LoadMoreHelper loadMoreHelper;

    protected boolean canLoadMore;

    public LiveRecentlyFragment(String type) {
        this.type = type;
    }

    public LiveRecentlyFragment() {
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_recently, container, false);
        ButterKnife.bind(this, view);

        initView();

        liveAPI = new LiveAPI(this);

        return view;
    }


    private void initView() {
        pfLive.setLastUpdateTimeRelateObject(this);
        pfLive.setPtrHandler(defaultHandler);

        rvLive.setLayoutManager(new LinearLayoutManager(getContext()));
        dataList = new ArrayList<>();
        adapter = new DataLiveAdapterHelper().getAdapter(getContext(), dataList, pfLive);
        moreWrapper = new LoadMoreWrapper(adapter);
        loadMoreHelper = new LoadMoreHelper(moreWrapper);
        moreWrapper.setLoadMoreView(loadMoreHelper.getLoadMoreView(getContext()));
        moreWrapper.clearLoadMore(true);
        moreWrapper.setOnLoadMoreListener(onLoadMoreListener);

        rvLive.setAdapter(moreWrapper);
        addSubscription(RxBus.getInstance().getEvent(LiveEndAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LiveEndAction>() {
                    @Override
                    public void onNext(LiveEndAction value) {
                        pageNum =1;
                        if ("0".equals(type)) {
                            liveAPI.getRecom(pageNum, pageSize);
                        } else if ("1".equals(type)) {
                            liveAPI.getNew(pageNum, pageSize);
                        }
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));
    }

    PtrClassicFrameLayout.PtrDefaultHandler defaultHandler = new PtrClassicFrameLayout.PtrDefaultHandler() {
        public void onRefreshBegin(PtrFrameLayout frame) {
            pageNum = 1;
            rvLive.scrollToPosition(0);
            pfLive.refreshComplete();
            if ("0".equals(type)) {
                liveAPI.getRecom(pageNum, pageSize);
            } else if ("1".equals(type)) {
                liveAPI.getNew(pageNum, pageSize);
            } else if ("2".equals(type)) {
                Calendar c = Calendar.getInstance();
                String date = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE);
                liveAPI.getDailyLive(date, BIZAIAApplication.getInstance().getUserBean().getEmail());
            }
        }
    };

    LoadMoreWrapper.OnLoadMoreListener onLoadMoreListener = new LoadMoreWrapper.OnLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            if (canLoadMore) {
                if ("0".equals(type)) {
                    liveAPI.getRecom(++pageNum, pageSize);
                } else if ("1".equals(type)) {
                    liveAPI.getNew(++pageNum, pageSize);
                } else if ("2".equals(type)) {
                    liveAPI.getDailyLive(TimeUtils.getTodayDateA(), BIZAIAApplication.getInstance().getUserBean().getEmail());
                }
            }
        }
    };


    private boolean canLoadMore() {
        if (getRvLastItemToTop() > rvLive.getMeasuredHeight()) {
            return true;
        }
        return false;
    }

    private int getRvLastItemToTop() {
        if (rvLive.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) rvLive.getLayoutManager();
            View view = rvLive.getChildAt(layoutManager.findLastVisibleItemPosition());
            if (view != null) {
                return view.getTop() + view.getMeasuredHeight();
            }
        }
        return 0;
    }


    @Override
    public void showLiveRecom(List<Object> list, int pageTotle) {
        adapter.hide("DATA_EMPTY")
                .hide("NET_ERROR")
                .hide("DATA_ERROR");
        if (list != null && !list.isEmpty()) {
            if (pageNum == 1) {
                dataList.clear();
                dataList.addAll(list);
                checkLoadMoreAble();
            } else {
                dataList.addAll(list);
            }
            moreWrapper.notifyDataSetChanged();
        } else {
            if (pageNum == 1) {
                if (dataList.isEmpty()) {
                    adapter.hide("DATA_EMPTY")
                            .hide("NET_ERROR")
                            .show("DATA_ERROR");
                }else {
                    ToastUtils.showInUIThead(getContext(),getString(R.string.update_date_error));
                }
            } else {
                canLoadMore = false;
                loadMoreHelper.setStateDataNoMore();
            }
            moreWrapper.notifyDataSetChanged();
        }
    }

    @Override
    public void showLiveNew(List<Object> list, int pageTotle) {
        adapter.hide("DATA_EMPTY")
                .hide("NET_ERROR")
                .hide("DATA_ERROR");

        if (list != null && !list.isEmpty()) {
            if (pageNum == 1) {
                dataList.clear();
                dataList.addAll(list);
                checkLoadMoreAble();
            } else {
                dataList.addAll(list);
            }
            moreWrapper.notifyDataSetChanged();
        } else {
            if (pageNum == 1) {
                if (dataList.isEmpty()) {
                    adapter.hide("DATA_EMPTY")
                            .hide("NET_ERROR")
                            .show("DATA_ERROR");
                }else {
                    ToastUtils.showInUIThead(getContext(),getString(R.string.update_date_error));
                }
            } else {
                canLoadMore = false;
                loadMoreHelper.setStateDataNoMore();
            }
            moreWrapper.notifyDataSetChanged();
        }
    }

    @Override
    public void showLiveRecomError(int code, String msg) {
        if (!dataList.isEmpty()) {
            ToastUtils.showInUIThead(getContext().getApplicationContext(), getString(R.string.update_date_error));
        } else {
            adapter.hide("DATA_EMPTY")
                    .hide("NET_ERROR")
                    .show("DATA_ERROR");
            moreWrapper.clearLoadMore(true, true);
            moreWrapper.notifyDataSetChanged();
        }
        pfLive.refreshComplete();
        if (code == 3000) {
            ToastUtils.show(getActivity(), R.string.app_logout_other);
            BIZAIAApplication.getInstance().setToken("");
            BIZAIAApplication.getInstance().setUserBean(null);
            IMHelper.getInstance().imLoginout();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    /*
     * ---------------------------------- 缓存 -------------------------------------
     */
    @Override
    protected void onSaveArguments(Bundle bundle) {
        super.onSaveArguments(bundle);
        bundle.putString("type", type);
//        if (!dataList.isEmpty()) {
//            CacheData<Object> objectCacheData = new CacheData<>(dataList);
//            ACache.get(getContext().getApplicationContext()
//                    , getContext().getApplicationContext().getCacheDir().getAbsolutePath())
//                    .put(type + "LiveRecentlyData", objectCacheData, ACache.TIME_DAY * 7);
//        }
    }

    @Override
    protected void onReloadArguments(Bundle bundle) {
        super.onReloadArguments(bundle);
        type = bundle.getString("type");

        if (isLazyLoad) {
//            getCacheData();
        }
    }

    private boolean getCacheData() {
        CacheData cacheData = (CacheData) ACache.get(getContext().getApplicationContext()
                , getContext().getApplicationContext().getCacheDir().getAbsolutePath())
                .getAsObject(type + "LiveRecentlyData");
        if (cacheData != null) {

            dataList.clear();
            dataList.addAll(cacheData.getDataList());
            moreWrapper.notifyDataSetChanged();

            return true;
        }
        return false;
    }

    @Override
    protected void onLoadLazy(Bundle reLoadBundle) {
        super.onLoadLazy(reLoadBundle);
//        getCacheData();

        pfLive.autoRefresh();
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

}
