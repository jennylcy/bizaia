package com.bizaia.zhongyin.module.monthly.ui.detail.js;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.monthly.adapter.DataAdapterHelper;
import com.bizaia.zhongyin.module.monthly.data.AllJSData;
import com.bizaia.zhongyin.module.monthly.data.IsNeedToBuyData;
import com.bizaia.zhongyin.module.monthly.data.MonthlyJSData;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreHelper;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreWrapper;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by yan on 2017/3/9.
 */

public class JSActivity extends BaseActivity implements JSContract.View {
    private static final String TAG = "JSActivity";

    protected static final int STATE_DATA_EMPTY = 0x100;
    protected static final int STATE_DATA_ERROR = 0x101;
    protected static final int STATE_NET_ERROR = 0x102;
    protected static final int STATE_NORMAL = 0x103;

    @BindView(R.id.rv_js_list)
    RecyclerView rvJsList;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.refresh_layout)
    PtrClassicFrameLayout ptrClassicFrameLayout;

    private JSContract.Presenter presenter;

    private int pageNo = 1;
    private int pageSize = 5;
    private CustomAdapter adapter;
    private List<AllJSData.DataBean.DatasBean> commendDatas;

    protected LoadMoreWrapper moreWrapper;
    protected LoadMoreHelper loadMoreHelper;
    private boolean canLoadMore = false;

    private long id;
    private String title;
    private boolean isBuy;
    private String shareUrl;
    private String filePath;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_js_list);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        init();
    }

    private void init() {
        shareUrl = getIntent().getStringExtra("shareUrl");
        filePath = getIntent().getStringExtra("filePath");
        MonthlyJSData.DataBean.MonthlyNewestBean data =(MonthlyJSData.DataBean.MonthlyNewestBean)getIntent().getSerializableExtra("month");
        rvJsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        commendDatas = new ArrayList<>();
        adapter = new DataAdapterHelper(){
            @Override
            public String getMonthlyTitle() {
                return title;
            }
            @Override
            public long getMonthlyId() {
                return (id);
            }
        }.getAdapter(getApplicationContext(), commendDatas);
        adapter.setObject(data);
        moreWrapper = new LoadMoreWrapper(adapter);
        loadMoreHelper = new LoadMoreHelper(moreWrapper);
        moreWrapper.setLoadMoreView(loadMoreHelper.getLoadMoreView(getApplicationContext()));
        moreWrapper.clearLoadMore(true);
        moreWrapper.setOnLoadMoreListener(onLoadMoreListener);
        rvJsList.setAdapter(moreWrapper);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.setPtrHandler(defaultHandler);

        new JSPresenter(this);
        id = getIntent().getLongExtra("id", 0);
        title = getIntent().getStringExtra("title");
        tvTitle.setText(title);
        presenter.getChapterList(id, pageNo, pageSize);
//        presenter.isNeedToBuy(id);
    }

    private PtrClassicFrameLayout.PtrDefaultHandler defaultHandler = new PtrClassicFrameLayout.PtrDefaultHandler() {
        public void onRefreshBegin(PtrFrameLayout frame) {
            rvJsList.scrollToPosition(0);
            pageNo = 1;
            presenter.getChapterList(id, pageNo, pageSize);
        }
    };

    private LoadMoreWrapper.OnLoadMoreListener onLoadMoreListener = new LoadMoreWrapper.OnLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            if (canLoadMore) {
                presenter.getChapterList(id, ++pageNo, pageSize);
            }
        }
    };

    @OnClick({R.id.iv_btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_btn_back:
                finish();
                break;
        }
    }

    @Override
    public void setPresenter(JSContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void setJSData(AllJSData jsData) {
        if (jsData.getCode() != 200) {
            if (commendDatas.isEmpty()) {
                loadMoreHelper.setStateDataError();
                setShowState(STATE_DATA_ERROR, true);
            } else {
                ToastUtils.showInUIThead(getBaseContext().getApplicationContext(), getString(R.string.error_data));
            }
            ptrClassicFrameLayout.refreshComplete();
            return;
        } else {
            setShowState(STATE_NORMAL, false);
            if (jsData.getData() != null
                    && jsData.getData().getDatas() != null) {
                if (pageNo == 1) {
                    ptrClassicFrameLayout.refreshComplete();
                    commendDatas.clear();
                    for(AllJSData.DataBean.DatasBean datasBean:jsData.getData().getDatas()){
                        datasBean.setShareUrl(shareUrl);
                        datasBean.setFileUrl(filePath);
                    }
                    commendDatas.addAll(jsData.getData().getDatas());
                    moreWrapper.notifyDataSetChanged();
                    checkLoadMoreAble();
                } else {
                    if (!jsData.getData().getDatas().isEmpty()) {
                        for(AllJSData.DataBean.DatasBean datasBean:jsData.getData().getDatas()){
                            datasBean.setShareUrl(shareUrl);
                        }
                        commendDatas.addAll(jsData.getData().getDatas());
                    } else {
                        loadMoreHelper.setStateDataNoMore();
                    }
                    moreWrapper.notifyDataSetChanged();
                }
            } else {
                if (pageNo == 1) {
                    setShowState(STATE_DATA_EMPTY, true);
                    ptrClassicFrameLayout.refreshComplete();
                } else {
                    loadMoreHelper.setStateDataNoMore();
                }
            }
        }
    }

    @Override
    public void setIsNeedToBuy(IsNeedToBuyData isNeedToBuy) {
        isBuy = isNeedToBuy.getData().isIsBuy();
    }

    @Override
    public void error() {
        if (pageNo == 1) {
            ptrClassicFrameLayout.refreshComplete();
            if (commendDatas.isEmpty()) {
                setShowState(STATE_NET_ERROR, true);
            } else {
                ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.net_error));
            }
        } else {
            ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.net_error));
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
        if (getRvLastItemToTop() > rvJsList.getMeasuredHeight()) {
            return true;
        }
        return false;
    }

    protected int getRvLastItemToTop() {
        if (rvJsList.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) rvJsList.getLayoutManager();
            View view = rvJsList.getChildAt(layoutManager.findLastVisibleItemPosition());
            if (view != null) {
                return view.getTop() + view.getMeasuredHeight();
            }
        }
        return 0;
    }

    protected void setShowState(int state, boolean withCommit) {

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
}
