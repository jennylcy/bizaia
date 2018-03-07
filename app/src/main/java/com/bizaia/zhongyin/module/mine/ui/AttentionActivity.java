package com.bizaia.zhongyin.module.mine.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.mine.adapter.DataAttentionsAdapterHelper;
import com.bizaia.zhongyin.module.mine.api.AttentionsAPI;
import com.bizaia.zhongyin.module.mine.api.IsAttentionAPI;
import com.bizaia.zhongyin.module.mine.data.AttentionsBean;
import com.bizaia.zhongyin.module.mine.iml.IAttentionView;
import com.bizaia.zhongyin.module.mine.iml.IMyAttentionsView;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.widget.ForgetPop;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
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
 * Created by zyz on 2017/3/11.
 */

public class AttentionActivity extends BaseActivity implements IMyAttentionsView, IAttentionView {

    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivCall)
    ImageView ivCall;
    @BindView(R.id.rvAttention)
    RecyclerView rvAttention;

    List<Object> dataList;
    LoadMoreWrapper moreWrapper;
    CustomAdapter adapter;
    @BindView(R.id.pfLecturer)
    PtrClassicFrameLayout pfLecturer;

    private int  pageNum=1;
    private int pageSize=10;

    private AttentionsAPI attentionsAPI;
    private int posi;
    private IsAttentionAPI isAttentionAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attentions);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        tvTitle.setText(R.string.mine_attention);
        ivCall.setVisibility(View.GONE);
        initView();

        attentionsAPI = new AttentionsAPI(this);
        attentionsAPI.getAttentions(pageNum,pageSize);


        isAttentionAPI = new IsAttentionAPI(this);
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


    private void initView() {
        pfLecturer.setLastUpdateTimeRelateObject(this);
        pfLecturer.setPtrHandler(defaultHandler);

        rvAttention.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        dataList = new ArrayList<>();
        adapter = new DataAttentionsAdapterHelper().getAdapter(getApplicationContext(), dataList, pfLecturer, new DataAttentionsAdapterHelper.CancelAttion() {
            @Override
            public void cancel(int pos) {
                posi  = pos;
                AttentionsBean.DataEntity.DatasEntity datasEntity = (AttentionsBean.DataEntity.DatasEntity)dataList.get(posi);
                showRemindPop(datasEntity.getOrgName());
            }
        });
        moreWrapper = new LoadMoreWrapper(adapter);
        moreWrapper.setLoadMoreView(getLoadMoreView());
        moreWrapper.setOnLoadMoreListener(onLoadMoreListener);
        rvAttention.setAdapter(moreWrapper);
    }

    private ForgetPop forgetPop;
    private void showRemindPop(final String name){
        if(forgetPop!=null)
            forgetPop.dismiss();
        forgetPop = new ForgetPop(getApplicationContext(), back_img, R.layout.pop_im) {
            @Override
            public void viewInit() {
                ((TextView)findViewById(R.id.content)).setText(name+getString(R.string.mine_cancel_attention));
                TextView start =(TextView)findViewById(R.id.tv_cancel);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forgetPop.dismiss();
                    }
                });
                TextView cancel =(TextView)findViewById(R.id.tv_sure);
                cancel.setText(R.string.mine_cancel_cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AttentionsBean.DataEntity.DatasEntity datasEntity=   (AttentionsBean.DataEntity.DatasEntity)dataList.get(posi);

                        isAttentionAPI.addAttention(datasEntity.getOrgId(),2);
                        forgetPop.dismiss();
                    }
                });
                ImageView ivClose = (ImageView)findViewById(R.id.ivClose);
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        forgetPop.dismiss();
                    }
                });
            }
        };
        forgetPop.show();
    }

    PtrClassicFrameLayout.PtrDefaultHandler defaultHandler = new PtrClassicFrameLayout.PtrDefaultHandler() {
        public void onRefreshBegin(PtrFrameLayout frame) {
            pageNum=1;
            rvAttention.scrollToPosition(0);
            pfLecturer.refreshComplete();
        }
    };

    LoadMoreWrapper.OnLoadMoreListener onLoadMoreListener = new LoadMoreWrapper.OnLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            if(canLoadMore()){
                attentionsAPI.getAttentions(++pageNum,pageSize);
            }
        }
    };


    private View getLoadMoreView() {
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
              getBaseContext().getResources().getDimensionPixelSize(R.dimen.load_more_height));
        View loadMore = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_loadmore, null);
        loadMore.setLayoutParams(layoutParams);
        return loadMore;
    }

    @Override
    public void showAttions(AttentionsBean attentionsBean) {
        adapter.hide("DATA_EMPTY")
                .hide("NET_ERROR")
                .hide("DATA_ERROR");
        if(pageNum==1)
            dataList.clear();

        if(pageNum>=attentionsBean.getData().getTotalPage())
            moreWrapper.clearLoadMore(true);
        else
            moreWrapper.clearLoadMore(false);
        if(attentionsBean.getData().getDatas()!=null&&!attentionsBean.getData().getDatas().isEmpty()) {
            dataList.addAll(attentionsBean.getData().getDatas());
        }else{
            adapter.show("DATA_EMPTY");
            moreWrapper.clearLoadMore(true);
        }
        moreWrapper.notifyDataSetChanged();
    }

    @Override
    public void showAttentionsError() {
        adapter.hide("DATA_EMPTY")
                .hide("NET_ERROR")
                .show("DATA_ERROR");
        moreWrapper.clearLoadMore(true);
        moreWrapper.notifyDataSetChanged();
    }


    private boolean canLoadMore() {
        if (getRvLastItemToTop() > rvAttention.getMeasuredHeight()) {
            return true;
        }
        return false;
    }

    private int getRvLastItemToTop() {
        if (rvAttention.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) rvAttention.getLayoutManager();
            View view = rvAttention.getChildAt(layoutManager.findLastVisibleItemPosition());
            if (view != null) {
                return view.getTop() + view.getMeasuredHeight();
            }
        }
        return 0;
    }

    @Override
    public void showIsAttention(boolean isAttention) {

    }

    @Override
    public void showAddAttention(boolean isAttention) {

    }

    @Override
    public void showDelAttention(boolean isAttention) {
        if(isAttention&&!dataList.isEmpty())
            dataList.remove(posi);
        if(dataList.isEmpty()){
            adapter.show("DATA_EMPTY");
        }
        moreWrapper.notifyDataSetChanged();
    }

    @Override
    public void onRelogin() {
        reLogin();
    }

    @Override
    public void showAttentionError() {

    }


    @OnClick({R.id.back_img, R.id.ivCall})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_img:
                finish();
                break;
        }
    }

    @OnClick(R.id.ivCall)
    public void onViewClicked() {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL
                , Uri.parse("tel:" + getResources().getString(R.string.tel)));
        startActivity(dialIntent);
    }
}
