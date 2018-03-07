package com.bizaia.zhongyin.module.live.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.ResumeFragment;
import com.bizaia.zhongyin.module.live.adapter.DataImAdapterHelper;
import com.bizaia.zhongyin.module.live.data.IMDataBean;
import com.bizaia.zhongyin.module.live.iml.ImHelperView;
import com.bizaia.zhongyin.widget.ScrollSpeedLinearLayoutManger;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreWrapper;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by zyz on 2017/3/16.
 */

public class IMLiveFragment extends ResumeFragment {

    private static final String TAG="IMLiveFragment.this";
    List<Object> dataList;
    LoadMoreWrapper moreWrapper;
    CustomAdapter adapter;
    @BindView(R.id.pfLive)
    PtrClassicFrameLayout pfLive;
    @BindView(R.id.rvImList)
    RecyclerView rvImList;
    private ImHelperView imHelperView;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_im_list, container, false);
        ButterKnife.bind(this, view);
        initView();


        return view;
    }




    private void initView() {
        pfLive.setLastUpdateTimeRelateObject(this);
        pfLive.setPtrHandler(defaultHandler);
        ScrollSpeedLinearLayoutManger layoutManager = new ScrollSpeedLinearLayoutManger(getContext());
//        layoutManager.setStackFromEnd(true);
        rvImList.setLayoutManager(layoutManager);
        dataList = new ArrayList<>();
        adapter = new DataImAdapterHelper().getAdapter(getContext(), dataList,true,null);
        moreWrapper = new LoadMoreWrapper(adapter);
        moreWrapper.setLoadMoreView(getLoadMoreView());
        moreWrapper.setOnLoadMoreListener(onLoadMoreListener);
        rvImList.setAdapter(moreWrapper);
    }


    public void showLogo(){

    }

    public void setData(IMDataBean data){
        dataList.add(data);
        adapter.notifyDataSetChanged();
        moreWrapper.notifyDataSetChanged();
        rvImList.smoothScrollToPosition(dataList.size()-1);
        Log.e(TAG, "setData: ---->"+dataList.size() );
    };
    public void showZoom(List<Object> msgList){
        if(!dataList.isEmpty())
            dataList.clear();
        dataList.addAll(msgList);
        adapter.notifyDataSetChanged();
        moreWrapper.notifyDataSetChanged();
        rvImList.smoothScrollToPosition(dataList.size()-1);
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
//        if(isVisibleToUser&&moreWrapper!=null&&rvImList!=null&&dataList!=null){
//            moreWrapper.notifyDataSetChanged();
//            if(!dataList.isEmpty())
//            rvImList.scrollToPosition(dataList.size()-1);
//
//            Log.e(TAG, "setUserVisibleHint: ---->"+dataList.size() );
//        }
    }

    PtrClassicFrameLayout.PtrDefaultHandler defaultHandler = new PtrClassicFrameLayout.PtrDefaultHandler() {
        public void onRefreshBegin(PtrFrameLayout frame) {
//
//            rvImList.scrollToPosition(0);
//            pfLive.refreshComplete();

        }
    };

    LoadMoreWrapper.OnLoadMoreListener onLoadMoreListener = new LoadMoreWrapper.OnLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {


        }
    };

    public void setImHelperView(ImHelperView imHelperView) {
        this.imHelperView = imHelperView;
    }


    private View getLoadMoreView() {
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                100);
        View loadMore = LayoutInflater.from(getContext()).inflate(R.layout.item_loadmore, null);
        loadMore.setVisibility(View.INVISIBLE);
        loadMore.setLayoutParams(layoutParams);
        return loadMore;
    }



}
