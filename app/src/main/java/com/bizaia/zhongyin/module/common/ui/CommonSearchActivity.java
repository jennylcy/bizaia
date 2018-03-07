package com.bizaia.zhongyin.module.common.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.common.data.SearchFixedData;
import com.bizaia.zhongyin.module.common.data.SendSearchDataToSearchAct;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreHelper;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/3/7.
 */

public abstract class CommonSearchActivity extends BaseActivity implements LoadMoreWrapper.OnLoadMoreListener {
    protected static final int STATE_DATA_EMPTY = 0x100;
    protected static final int STATE_DATA_ERROR = 0x101;
    protected static final int STATE_NET_ERROR = 0x102;
    protected static final int STATE_NORMAL = 0x103;

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.content_view)
    View content_view;

    protected LinearLayoutManager layoutManager;
    protected List<Object> objectDisplayList;
    protected CustomAdapter adapter;
    protected List<SearchFixedData> fixedData;
    protected LoadMoreWrapper moreWrapper;
    protected LoadMoreHelper loadMoreHelper;

    protected SendSearchDataToSearchAct dataToSearchAct;

    protected boolean canLoadMore;

    protected int pageSize = 10;
    protected int pageNo = 1;
    public int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setViewParent(content_view);
        type = getIntent().getIntExtra("type",0);
        init();
    }

    private void init() {
        etSearch.setOnEditorActionListener(onEditorActionListener);
        etSearch.addTextChangedListener(watcher);
        layoutManager = new LinearLayoutManager(getBaseContext());
        objectDisplayList = new ArrayList<>();
        fixedData = new ArrayList<>();
        adapter = adapter(objectDisplayList);
        moreWrapper = new LoadMoreWrapper(adapter);
        loadMoreHelper = new LoadMoreHelper(moreWrapper);
        moreWrapper.setLoadMoreView(loadMoreHelper.getLoadMoreView(getBaseContext()));
        moreWrapper.setOnLoadMoreListener(this);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(moreWrapper);
        showSearchFixedData();
        rxActionInit();
    }

    private void rxActionInit() {
        addSubscription(
                RxBus.getInstance().getEvent(SendSearchDataToSearchAct.class)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<SendSearchDataToSearchAct>() {
                            @Override
                            public void onNext(SendSearchDataToSearchAct value) {

                                dataToSearchAct = value;
                                pageNo = 1;
                                etSearch.setText(dataToSearchAct.strData);
//                                requestData(dataToSearchAct,"1");
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
    }

    protected abstract CustomAdapter adapter(List<Object> objectDisplayList);

    @OnClick({R.id.btn_back, R.id.btn_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_search:
                searchGo();
                break;
        }
    }

    public void searchGo() {
        if (etSearch.getText() != null && !etSearch.getText().toString().trim().equals("")) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
            pageNo = 1;
            if(dataToSearchAct!=null){
                requestData(dataToSearchAct,"1");
            }else{
                dataToSearchAct = null;
                requestData(etSearch.getText().toString(),null);
            }
        } else {
            ToastUtils.showInUIThead(getBaseContext(), getString(R.string.Input_empty));
        }
    }

    protected abstract void requestData(String query,String type);

    protected void requestData(SendSearchDataToSearchAct query,String type) {
    }

    protected void showSearchFixedData() {
        objectDisplayList.clear();
        objectDisplayList.addAll(fixedData);
        moreWrapper.clearLoadMore(true);
        moreWrapper.notifyDataSetChanged();
    }

    private TextView.OnEditorActionListener onEditorActionListener =
            new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        searchGo();
                    }
                    return false;
                }
            };

    @Override
    public void onLoadMoreRequested() {
        pageNo++;
        if (dataToSearchAct != null) {
            requestData(dataToSearchAct,"1");
        } else {
            requestData(etSearch.getText().toString(),null);
        }
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(etSearch.getText())) {
                if( dataToSearchAct != null)
                    dataToSearchAct = null;
                setShowState(STATE_NORMAL, false);
                showSearchFixedData();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


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
