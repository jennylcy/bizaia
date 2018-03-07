package com.bizaia.zhongyin.module.mine.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.common.ui.LoadingDialog;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.mine.adapter.DataBillAdapterHelper;
import com.bizaia.zhongyin.module.mine.api.BillAPI;
import com.bizaia.zhongyin.module.mine.data.BillBean;
import com.bizaia.zhongyin.module.mine.iml.IBillView;
import com.bizaia.zhongyin.module.monthly.action.PaySuccessAction;
import com.bizaia.zhongyin.module.pay.RechargeActivity;
import com.bizaia.zhongyin.util.RxBus;
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
 * Created by zyz on 2017/3/7.
 */

public class AccountActivity extends BaseActivity implements IBillView {

    private static final String TAG="AccountActivity";
    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivCall)
    ImageView ivCall;
    @BindView(R.id.tvTotle)
    TextView tvTotle;
    @BindView(R.id.btnRecharge)
    Button btnRecharge;
    @BindView(R.id.rvBill)
    RecyclerView rvBill;
    @BindView(R.id.pfBill)
    PtrClassicFrameLayout pfBill;

    private BillAPI billAPI;

    private int pageSize =10;
    private int pageNum=1;
    private List<Object> dataList;
    private CustomAdapter adapter;
    private     LoadMoreWrapper moreWrapper;
    protected LoadMoreHelper loadMoreHelper;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        tvTitle.setText(R.string.mine_account);
        initView();

        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        billAPI = new BillAPI(this);
        billAPI.getBill(pageNum,pageSize);
    }


    private void initView() {
        pfBill.setLastUpdateTimeRelateObject(this);
        pfBill.setPtrHandler(defaultHandler);

        rvBill.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        dataList = new ArrayList<>();
        adapter = new DataBillAdapterHelper().getAdapter(AccountActivity.this, dataList, pfBill);
        moreWrapper = new LoadMoreWrapper(adapter);
        loadMoreHelper = new LoadMoreHelper(moreWrapper);
        moreWrapper.setLoadMoreView(loadMoreHelper.getLoadMoreView(getBaseContext()));
        moreWrapper.clearLoadMore(true);
        moreWrapper.setOnLoadMoreListener(onLoadMoreListener);
        rvBill.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //当前RecyclerView显示出来的最后一个的item的position
                int lastPosition = -1;
                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    lastPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    if(lastPosition !=0){
                        pfBill.setCanRefresh(false);
                    }else{
                        pfBill.setCanRefresh(true);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }

        });
        rvBill.setAdapter(moreWrapper);
        addSubscription(RxBus.getInstance().getEvent(PaySuccessAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PaySuccessAction>(){
                    @Override
                    public void onNext(PaySuccessAction value) {
                        pageNum =1;
                        billAPI.getBill(pageNum,pageSize);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
        tvTotle.setText(BIZAIAApplication.getInstance().getUserInfo().getBalance()+"");

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

    @OnClick({R.id.ivCall, R.id.back_img,R.id.btnRecharge})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivCall:
                break;
            case R.id.back_img:
                finish();
                break;
            case R.id.btnRecharge:
                Intent  intent = new Intent(AccountActivity.this, RechargeActivity.class);
                intent.putExtra("buyType",6);
                intent.putExtra("type",6);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void showBill(BillBean data) {
        loadingDialog.dismiss();
        initData(data);
    }

    @Override
    public void showBillError() {
        loadingDialog.dismiss();
        adapter.hide("DATA_EMPTY")
                .hide("NET_ERROR")
                .show("DATA_ERROR");
        moreWrapper.clearLoadMore(true);
        moreWrapper.notifyDataSetChanged();
    }

    @Override
    public void onRelogin() {
        loadingDialog.dismiss();
        reLogin();
    }

    private void initData(BillBean data){
        adapter.hide("DATA_EMPTY")
                .hide("NET_ERROR")
                .hide("DATA_ERROR");

        Log.e(TAG, "addData: -------------setData->");
        if(pageNum==1)
            dataList.clear();
        if(pageNum>=data.getData().getTradeRecordList().getTotalPage())
            moreWrapper.clearLoadMore(true);
        else
            moreWrapper.clearLoadMore(false);


        if(data.getData().getTradeRecordList()!=null&&data.getData().getTradeRecordList().getDatas()!=null) {
            dataList.addAll(data.getData().getTradeRecordList().getDatas());
        }else{
            adapter.show("DATA_EMPTY");
            moreWrapper.clearLoadMore(true);
        }
        moreWrapper.notifyDataSetChanged();

    }

    PtrClassicFrameLayout.PtrDefaultHandler defaultHandler = new PtrClassicFrameLayout.PtrDefaultHandler() {
        public void onRefreshBegin(PtrFrameLayout frame) {
            pageNum=1;
            rvBill.scrollToPosition(0);
            pfBill.refreshComplete();

        }
    };

    LoadMoreWrapper.OnLoadMoreListener onLoadMoreListener = new LoadMoreWrapper.OnLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
           if(canLoadMore()){
               billAPI.getBill(++pageNum,pageSize);
           }

        }
    };




    private boolean canLoadMore() {
        Log.e(TAG, "onLoadMoreRequested: -------------getMeasuredHeight-> "+rvBill.getMeasuredHeight());
        if (getRvLastItemToTop() > rvBill.getMeasuredHeight()) {
            return true;
        }
        return false;
    }

    private int getRvLastItemToTop() {
        if (rvBill.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) rvBill.getLayoutManager();
            View view = rvBill.getChildAt(rvBill.getChildCount()-1);
            Log.e(TAG, "getRvLastItemToTop: ---------------->"+ layoutManager.findLastVisibleItemPosition()+"-------"+rvBill.getChildCount());
            if (view != null) {
                return view.getTop() + view.getMeasuredHeight();
            }
        }
        return 0;
    }

    @OnClick(R.id.ivCall)
    public void onViewClicked() {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL
                , Uri.parse("tel:" + getResources().getString(R.string.tel)));
        startActivity(dialIntent);
    }
}
