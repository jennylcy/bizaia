package com.bizaia.zhongyin.module.video.ui.detail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.ResumeFragment;
import com.bizaia.zhongyin.module.live.data.LecturerListEntity;
import com.bizaia.zhongyin.module.mine.adapter.DataLectureresAdapterHelper;
import com.bizaia.zhongyin.util.ImageLoaderUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreWrapper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragmentSub extends ResumeFragment {

    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_person_info)
    TextView tvPersonInfo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rvTeacher)
    RecyclerView rvTeacher;
    @BindView(R.id.tvDetailTitle)
    TextView tvDetailTitle;

    private List<Object> dataList;
    private CustomAdapter adapter;
    private LoadMoreWrapper moreWrapper;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_detail_sub, container, false);
        ButterKnife.bind(this, view);

        rvTeacher.setLayoutManager(new LinearLayoutManager(getContext()));
        dataList = new ArrayList<>();
        adapter = new DataLectureresAdapterHelper().getAdapter(getContext(), dataList, null);
        moreWrapper = new LoadMoreWrapper(adapter);
        moreWrapper.setLoadMoreView(getLoadMoreView());
        moreWrapper.setOnLoadMoreListener(onLoadMoreListener);
        rvTeacher.setAdapter(moreWrapper);

        return view;
    }

    private View getLoadMoreView() {
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0);
        View loadMore = LayoutInflater.from(getContext()).inflate(R.layout.item_loadmore, null);
        loadMore.setLayoutParams(layoutParams);
        loadMore.setVisibility(View.GONE);
        return loadMore;
    }
    LoadMoreWrapper.OnLoadMoreListener onLoadMoreListener = new LoadMoreWrapper.OnLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {

        }
    };

    public void setTvDetail(String detail) {
        tvDetail.setText(detail);
    }

    public void setTvInfo(String info) {
        tvInfo.setText(info);
    }

    public void setTvPersonInfo(String personInfo) {
        tvPersonInfo.setText(personInfo);
    }

    public void setTvName(String name) {
        tvName.setText(name);
    }

    public void setTitle(String title){
        tvDetailTitle.setText(title);
    }

    public void setIvHead(String url) {
        ImageLoader.getInstance().displayImage(url, ivHead, ImageLoaderUtils.getImageHighQualityOptions());
    }

    public void setLectureList(List<LecturerListEntity> lectureList){
        if(dataList.isEmpty()) {
            dataList.addAll(lectureList);
            moreWrapper.notifyDataSetChanged();
        }
    }

}
