package com.bizaia.zhongyin.module.message.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.message.aciton.DeleteMsgAction;
import com.bizaia.zhongyin.module.message.adpter.DataAdapterHelper;
import com.bizaia.zhongyin.module.message.data.MessageData;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.ForgetPop;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreHelper;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreWrapper;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/6/5.
 */

public class MessageActivity extends BaseActivity implements MessageContract.View {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh_layout)
    PtrClassicFrameLayout refreshLayout;
    @BindView(R.id.tv_modify)
    TextView tvModify;
    @BindView(R.id.tv_btn_delete_all)
    TextView tvBtnDeleteAll;

    protected CustomAdapter adapter;
    protected LoadMoreWrapper moreWrapper;
    protected LoadMoreHelper loadMoreHelper;
    private boolean canLoadMore;

    protected int pageNo = 1;
    protected int pageSize = 10;

    private int type = 0;
    private List<MessageData.DataBean.DatasBean> messageDatas;

    private MessageContract.Presenter presenter;
    private DataAdapterHelper adapterHelper;

    private ForgetPop popDelete;
    private long delId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        setViewParent(recycler);
        new MessagePresenter(this, getBaseContext());
        type = getIntent().getIntExtra("type", 0);
        initView();
        presenter.getMessage(type, pageNo, pageSize);
        addSubscription(RxBus.getInstance().getEvent(ForceOffLine.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ForceOffLine>() {
                    @Override
                    public void onNext(ForceOffLine value) {
                        reLogin();
                        finish();
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));
    }

    protected void initView() {
        initPop();
        refreshLayout.setLastUpdateTimeRelateObject(this);
        refreshLayout.setPtrHandler(defaultHandler);
        recycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        messageDatas = new ArrayList<>();
        adapterHelper = new DataAdapterHelper();
        adapter = adapterHelper.getAdapter(getBaseContext(), messageDatas, new DataAdapterHelper.onReadListener() {
            @Override
            public void read(long id,int pos) {
                presenter.readMessage(id);
                messageDatas.get(pos).setRead(true);
                moreWrapper.notifyDataSetChanged();
            }
        });
        adapterHelper.setOnDelClickListener(onDelClickListener);
        moreWrapper = new LoadMoreWrapper(adapter);
        loadMoreHelper = new LoadMoreHelper(moreWrapper);
        moreWrapper.setLoadMoreView(loadMoreHelper.getLoadMoreView(getBaseContext()));
        moreWrapper.clearLoadMore(true);
        moreWrapper.setOnLoadMoreListener(onLoadMoreListener);

        recycler.setAdapter(moreWrapper);
    }

    private void initPop() {
        popDelete = new ForgetPop(getApplicationContext(), recycler, R.layout.pop_im) {
            @Override
            public void viewInit() {
                getView(R.id.tv_sure).setOnClickListener(onClickListener);
                TextView tvSure = (TextView)  getView(R.id.tv_sure);
                tvSure.setText("はい");
                getView(R.id.tv_cancel).setOnClickListener(onClickListener);
                TextView tvCancel = (TextView)  getView(R.id.tv_cancel);
                tvCancel.setText("いいえ");
                getView(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
                getView(R.id.content).setOnClickListener(onClickListener);
                TextView textView = (TextView)getView(R.id.content);
                textView.setText(R.string.msg_delete_tips);
            }
        };
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_sure:
                    if (delId == -1) {
                        presenter.delMessage();
                    } else {
                        presenter.delMessage(delId);
                    }
                case R.id.container:
                case R.id.tv_cancel:
                    popDelete.dismiss();
                    break;
            }
        }
    };

    private View.OnClickListener onDelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MessageData.DataBean.DatasBean datasBean = (MessageData.DataBean.DatasBean) v.getTag();
            delId = datasBean.getId();
            popDelete.show();
        }
    };

    private void onRefresh() {
        pageNo = 1;
        presenter.getMessage(type, pageNo, pageSize);
    }

    private void onLoadMore() {
        presenter.getMessage(type, ++pageNo, pageSize);
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

    @Override
    public void setPresenter(MessageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setData(MessageData messageData) {
        refreshLayout.refreshComplete();
        adapter.hide("DATA_EMPTY")
                .hide("NET_ERROR")
                .hide("DATA_ERROR");

        if (messageData.getData() != null
                && messageData.getData().getDatas() != null
                && !messageData.getData().getDatas().isEmpty()) {
            if (pageNo == 1) {
                messageDatas.clear();
                canLoadMore = true;
                loadMoreHelper.setStateDataLoading();
            }
            messageDatas.addAll(messageData.getData().getDatas());
            for(int i=0;i<messageDatas.size();i++){
                MessageData.DataBean.DatasBean  datasBean =messageDatas.get(i);
                datasBean.setPosition(i);
            }
            moreWrapper.notifyDataSetChanged();
        } else {
            if (messageDatas.isEmpty()) {
                adapter.show("DATA_EMPTY");
                moreWrapper.notifyDataSetChanged();
            }
            if (pageNo > 1) {
                canLoadMore = false;
                loadMoreHelper.setStateDataNoMore();
            }
        }

        if(messageDatas.isEmpty()&&pageNo==1){
            tvModify.setVisibility(View.GONE);
        }else{
            tvModify.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void error(String msg) {
        refreshLayout.refreshComplete();
        canLoadMore = false;

        adapter.hide("DATA_EMPTY")
                .hide("NET_ERROR")
                .hide("DATA_ERROR");
        if (messageDatas.isEmpty()) {
            adapter.show("DATA_ERROR");
        } else {
            ToastUtils.showInUIThead(getBaseContext(), msg);
        }
        if (pageNo > 1) {
            loadMoreHelper.setStateDataNoMore();
            moreWrapper.notifyDataSetChanged();
        }

        if(messageDatas.isEmpty()&&pageNo==1){
            tvModify.setVisibility(View.GONE);
        }else{
            tvModify.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void delData(long id) {
        if (id == -1) {
            messageDatas.clear();
            moreWrapper.notifyDataSetChanged();
            ToastUtils.show(MessageActivity.this,"削除成功");
            return;
        }

        for (int i = 0; i < messageDatas.size(); i++) {
            if (messageDatas.get(i).getId() == id) {
                messageDatas.remove(i);
                moreWrapper.notifyItemRemoved(i);
                ToastUtils.show(MessageActivity.this,"削除成功");
                break;
            }
        }
        if(messageDatas.isEmpty()){
            tvModify.setVisibility(View.GONE);
        }else{
            tvModify.setVisibility(View.VISIBLE);
        }

        RxBus.getInstance().post(new DeleteMsgAction());

    }

    @Override
    public void hasRead(boolean read) {
          RxBus.getInstance().post(new DeleteMsgAction());
    }


    @OnClick({R.id.btn_back, R.id.tv_modify, R.id.tv_btn_delete_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();

                break;
            case R.id.tv_modify:
                adapterHelper.triggerModify();
                if (adapterHelper.isModify()) {
                    tvModify.setText(getString(R.string.complete));
                    tvBtnDeleteAll.setVisibility(View.VISIBLE);
                } else {
                    tvModify.setText(getString(R.string.edit));
                    tvBtnDeleteAll.setVisibility(View.GONE);
                }
                moreWrapper.notifyDataSetChanged();
                break;
            case R.id.tv_btn_delete_all:
                popDelete.show();
                delId = -1;
                break;
        }
    }

    @Override
    protected void onResume() {
        if(adapter!=null&&messageDatas!=null&&!messageDatas.isEmpty())
            adapter.notifyDataSetChanged();
        super.onResume();
    }
}
