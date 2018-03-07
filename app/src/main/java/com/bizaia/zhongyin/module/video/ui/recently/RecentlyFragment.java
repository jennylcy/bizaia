package com.bizaia.zhongyin.module.video.ui.recently;

import android.Manifest;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.mvp.CommonContract;
import com.bizaia.zhongyin.module.common.ui.DataFragment;
import com.bizaia.zhongyin.module.video.adapter.DataAdapterHelper;
import com.bizaia.zhongyin.module.video.data.BannerRecentlyData;
import com.bizaia.zhongyin.module.video.data.RecentlyVideoData;
import com.bizaia.zhongyin.module.video.ui.detail.DetailActivity;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.OnItemClickListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class RecentlyFragment extends DataFragment<RecentlyVideoData, CommonContract.Presenter>
        implements CommonContract.View<RecentlyVideoData, CommonContract.Presenter> {
    @Override
    protected void requireData(int pageNo, int pageSize, Long orgId, String areaId, Long cateId) {
        presenter.setPageNo(pageNo);
        presenter.setPageSize(pageSize);
        presenter.requireData();
    }

    @Override
    protected CustomAdapter adapter() {
        CustomAdapter adapter = new DataAdapterHelper().getAdapter(getContext(), dataList, refreshLayout);
        adapter.setOnDataItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, final int position) {
                RxPermissions rxPermissions = new RxPermissions(getActivity());
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(
                                new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean granted) throws Exception {
                                        Log.e("granted", granted + "");
                                        if (granted) {
                                            String s = ((RecentlyVideoData.DataBean.ListDataBean.DatasBean)
                                                    dataList.get(position)).getTitle();
                                            Long id = ((RecentlyVideoData.DataBean.ListDataBean.DatasBean)
                                                    dataList.get(position)).getId();

                                            startActivity(new Intent(getContext(),
                                                    DetailActivity.class)
                                                    .putExtra("title", ((RecentlyVideoData.DataBean.ListDataBean.DatasBean)
                                                            dataList.get(position)).getTitle())
                                                    .putExtra("videoId", ((RecentlyVideoData.DataBean.ListDataBean.DatasBean)
                                                            dataList.get(position)).getId())
                                                    .putExtra("cover", ((RecentlyVideoData.DataBean.ListDataBean.DatasBean)
                                                            dataList.get(position)).getCoverUrl()));
                                        } else {
                                        }
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        throwable.printStackTrace();
                                    }
                                });
            }
        });
        return adapter;
    }

    @Override
    public void instancePresenter() {
        new RecentlyPresenter(getContext(), this);
    }

    @Override
    public void dataSuccess(RecentlyVideoData news) {
        setShowState(STATE_NORMAL, false);
        refreshLayout.refreshComplete();

        if (news.getCode() != 200) {
            if (dataList.isEmpty()) {
                setShowState(STATE_DATA_ERROR, false);
            } else {
                ToastUtils.showInUIThead(getContext().getApplicationContext(), getString(R.string.error_data));
            }
            return;
        }

        //refresh
        if (pageNo == 1) {
            boolean isClear = false;
            if (news.getData().getFocusData() != null) {
                isClear = true;
                dataList.clear();
                dataList.add(new BannerRecentlyData(news.getData().getFocusData()));
            }
            if (news.getData().getListData() != null && news.getData().getListData().getDatas() != null) {
                if (!isClear) {
                    isClear = true;
                    dataList.clear();
                }
                dataList.addAll(news.getData().getListData().getDatas());
            }
            if (isClear) {
                moreWrapper.notifyDataSetChanged();
            } else {
                if (dataList.isEmpty()) {
                    setShowState(STATE_DATA_EMPTY, true);
                } else {
                    ToastUtils.showInUIThead(getContext(), getString(R.string.null_data));
                }
            }

            checkLoadMoreAble();
        }

        //load more
        else {
            if (news.getData().getListData() == null
                    || news.getData().getListData().getDatas() == null
                    || news.getData().getListData().getDatas().isEmpty()) {
                loadMoreHelper.setStateDataNoMore();
                canLoadMore = false;
                return;
            }
            dataList.addAll(news.getData().getListData().getDatas());
            moreWrapper.notifyDataSetChanged();
        }
    }

    @Override
    public void onRelogin() {
        reLogin();
    }

    @Override
    protected String cacheName() {
        return "videoRecentlyData";
    }

}
