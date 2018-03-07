package com.bizaia.zhongyin.module.monthly.ui.detail.jscomment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.module.monthly.adapter.DataAdapterHelper;
import com.bizaia.zhongyin.module.monthly.data.CommendData;
import com.bizaia.zhongyin.util.SizeUtils;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.TimeUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreHelper;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreWrapper;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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

public class JSDetailCommentActivity extends BaseActivity implements JSCommentContract.View {
    private static final String TAG = "JSDetailCommentActivity";

    protected static final int STATE_DATA_EMPTY = 0x100;
    protected static final int STATE_DATA_ERROR = 0x101;
    protected static final int STATE_NET_ERROR = 0x102;
    protected static final int STATE_NORMAL = 0x103;

    @BindView(R.id.rv_js_list)
    RecyclerView rvJsList;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.refresh_layout)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.content_view)
    LinearLayout content;
    @BindView(R.id.ll_publish_layout)
    View publishLayout;
    @BindView(R.id.ll_trigger_layout)
    View triggerLayout;
    @BindView(R.id.tv_title)
    TextView tvTile;

    @BindView(R.id.tv_title_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_name)
    TextView tvAuthor;
    @BindView(R.id.tv_comment_size)
    TextView commentSize;
    @BindView(R.id.et_nick_name)
    EditText nickName;
    @BindView(R.id.tvCommentNum)
    TextView tvCommentNum;
    @BindView(R.id.relInput)
    RelativeLayout relInput;

    private JSCommentContract.Presenter presenter;
    private int pageNo = 1;
    private int pageSize = 10;
    private CustomAdapter adapter;
    private List<CommendData.DataBean.DatasBean> commendDatas;

    protected LoadMoreWrapper moreWrapper;
    protected LoadMoreHelper loadMoreHelper;
    private boolean canLoadMore = false;

    private long id;
    private String title;
    private String author;
    private String time;
    private String subTile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_monthly_commend_list);
        ButterKnife.bind(this);
        setViewParent(rvJsList);
        init();
    }

    private void init() {
        rvJsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        commendDatas = new ArrayList<>();
        adapter = new DataAdapterHelper().getAdapter(getApplicationContext(), commendDatas);
        moreWrapper = new LoadMoreWrapper(adapter);
        loadMoreHelper = new LoadMoreHelper(moreWrapper);
        moreWrapper.setLoadMoreView(loadMoreHelper.getLoadMoreView(getApplicationContext()));
        moreWrapper.clearLoadMore(true);
        moreWrapper.setOnLoadMoreListener(onLoadMoreListener);
        rvJsList.setAdapter(moreWrapper);

        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.setPtrHandler(defaultHandler);
        content.addOnLayoutChangeListener(onLayoutChangeListener);

        new JSCommentPresenter(this);
        id = getIntent().getLongExtra("id", 0);
        title = getIntent().getStringExtra("title");
        author = getIntent().getStringExtra("author");
        time = getIntent().getStringExtra("time");
        subTile = getIntent().getStringExtra("subTile");
        tvName.setText(subTile);
        tvAuthor.setText(author);
        tvTile.setText(subTile);
        tvTime.setText(TimeUtils.timeTransGBToCN(time));
        nickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInputOpen();
            }
        });

        if (BIZAIAApplication.getInstance().getUserBean() != null) {
//            if (!TextUtils.isEmpty(BIZAIAApplication.getInstance().getUserBean().getNickname()))
//                nickName.setText(BIZAIAApplication.getInstance().getUserBean().getNickname() + ":");
//            else {
//                nickName.setText(BIZAIAApplication.getInstance().getUserBean().getId() + ":");
//            }
        }

        presenter.getCommentList(id, pageNo, pageSize);

        addLayoutListener(relInput,publishLayout);
    }

    public void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                main.getWindowVisibleDisplayFrame(rect);
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                if (mainInvisibleHeight > 100) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                    main.scrollTo(0, srollHeight);
                } else {
                    main.scrollTo(0, 0);
                }
            }
        });
    }

    private PtrClassicFrameLayout.PtrDefaultHandler defaultHandler = new PtrClassicFrameLayout.PtrDefaultHandler() {
        public void onRefreshBegin(PtrFrameLayout frame) {
            rvJsList.scrollToPosition(0);
            pageNo = 1;
            presenter.getCommentList(id, pageNo, pageSize);
        }
    };
    private View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom
                , int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if ((bottom - oldBottom) > SizeUtils.getFullScreenSize(getApplicationContext())[1] / 4) {
                onInputClose();
            }
        }
    };

    private void onInputClose() {
        Log.e(TAG, "onInputClose: ");
        relInput.setVisibility(View.INVISIBLE);
        triggerLayout.setVisibility(View.VISIBLE);
    }

    private void onInputOpen() {
        Log.e(TAG, "onInputOpen: ");
        relInput.setVisibility(View.VISIBLE);
        triggerLayout.setVisibility(View.INVISIBLE);
        etContent.requestFocusFromTouch();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }

        }, 200);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(relInput.getVisibility()!=View.INVISIBLE){
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                onInputClose();
                return true;
            }else{
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private LoadMoreWrapper.OnLoadMoreListener onLoadMoreListener = new LoadMoreWrapper.OnLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            if (canLoadMore) {
                presenter.getCommentList(id, ++pageNo, pageSize);
            }
        }
    };

    @OnClick({R.id.iv_back_btn, R.id.tv_btn_publish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_btn:
                finish();
                break;
            case R.id.tv_btn_publish:
                if(!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    publish();
                }else{
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                }
                break;
        }
    }

    private void publish() {
        if (!TextUtils.isEmpty(etContent.getText().toString().trim())) {
            presenter.publishComment(id, etContent.getText().toString());
            etContent.setText("");
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            onInputClose();
        }else{
            ToastUtils.show(getBaseContext().getApplicationContext(), getString(R.string.live_empty_error));
        }
    }

    @Override
    public void setPresenter(JSCommentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setCommendData(CommendData commendData) {
        if (commendData.getCode() != 200) {
            if (commendDatas.isEmpty()) {
                loadMoreHelper.setStateDataError();
                setShowState(STATE_DATA_ERROR, true);
            } else {
                ToastUtils.showInUIThead(getBaseContext().getApplicationContext(), getString(R.string.error_data));
            }
            ptrClassicFrameLayout.refreshComplete();
            return;
        } else {
//            ToastUtils.show(getBaseContext().getApplicationContext(), getString(R.string.comment_publish));
            setShowState(STATE_NORMAL, false);
            if (commendData.getData() != null
                    && commendData.getData().getDatas() != null) {
                if (pageNo == 1) {
                    commentSize.setText(String.valueOf(commendData.getData().getTotalCount()));
                    tvCommentNum.setText(getString(R.string.reply_count)+String.valueOf(commendData.getData().getTotalCount()));
                    ptrClassicFrameLayout.refreshComplete();
                    commendDatas.clear();
                    commendDatas.addAll(commendData.getData().getDatas());
                    moreWrapper.notifyDataSetChanged();
                    checkLoadMoreAble();
                } else {
                    if (!commendData.getData().getDatas().isEmpty()) {
                        commendDatas.addAll(commendData.getData().getDatas());
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
    public void publishSuccess() {
        ToastUtils.show(JSDetailCommentActivity.this, "配信が成功しました");
        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh();
            }
        }, 100);
    }

    @Override
    public void error() {
        if (pageNo == 1) {
            ptrClassicFrameLayout.refreshComplete();
            if (commendDatas.isEmpty()) {
                setShowState(STATE_NET_ERROR, true);
            } else {
                ToastUtils.showInUIThead(getApplicationContext(),getString(R.string.net_error));
            }
        } else {
            ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.net_error));
        }

        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh();
            }
        }, 100);
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
