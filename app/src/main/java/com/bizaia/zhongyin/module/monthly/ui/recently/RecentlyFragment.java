package com.bizaia.zhongyin.module.monthly.ui.recently;

import android.util.Log;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.mvp.CommonContract;
import com.bizaia.zhongyin.module.common.ui.DataFragment;
import com.bizaia.zhongyin.module.monthly.adapter.DataAdapterHelper;
import com.bizaia.zhongyin.module.monthly.data.MonthlyJSData;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;

public class RecentlyFragment extends DataFragment<MonthlyJSData, CommonContract.Presenter>
        implements CommonContract.View<MonthlyJSData, CommonContract.Presenter> {

    MonthlyJSData.DataBean.MonthlyNewestBean monthlyNewestBean;
    private static final String TAG = "RecentlyFragment";
    @Override
    protected void requireData(int pageNo, int pageSize, Long orgId, String areaId, Long cateId) {
        presenter.setPageNo(pageNo)
                .setPageSize(pageSize)
                .requireData();
    }

    @Override
    protected CustomAdapter adapter() {
        return new DataAdapterHelper() {
            @Override
            public String getMonthlyTitle() {
                return (monthlyNewestBean != null ? monthlyNewestBean.getTitle() : "");
            }
            @Override
            public long getMonthlyId() {
                return (monthlyNewestBean != null ? monthlyNewestBean.getId() : 0);
            }
        }.getAdapter(getContext(), dataList);
    }

    @Override
    public void instancePresenter() {
        new RecentlyPresenter(getContext(), this);
    }

    @Override
    public void dataSuccess(MonthlyJSData news) {
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
            if (news.getData().getMonthlyNewest() != null) {
                isClear = true;
                dataList.clear();
                monthlyNewestBean = news.getData().getMonthlyNewest();
                adapter.setObject(monthlyNewestBean);
                dataList.add(news.getData().getMonthlyNewest());
            }

            if (news.getData().getChapterList() != null
                    && news.getData().getChapterList().getDatas() != null) {
                if (!isClear) {
                    isClear = true;
                    dataList.clear();
                }
                for(MonthlyJSData.DataBean.ChapterListBean.DatasBean datasBean:news.getData().getChapterList().getDatas()){
                    datasBean.setShareUrl(news.getData().getMonthlyNewest().getShareUrl());
                    datasBean.setFileUrl(news.getData().getMonthlyNewest().getFileUrl());
                    Log.e(TAG, "dataSuccess: ------------>"+news.getData().getMonthlyNewest().getShareUrl() );
                }
                dataList.addAll(news.getData().getChapterList().getDatas());
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
            if (news.getData().getChapterList() == null
                    || news.getData().getChapterList().getDatas() == null
                    || news.getData().getChapterList().getDatas().isEmpty()) {
                loadMoreHelper.setStateDataNoMore();
                canLoadMore = false;
                return;
            }
            for(MonthlyJSData.DataBean.ChapterListBean.DatasBean datasBean:news.getData().getChapterList().getDatas()){
                datasBean.setShareUrl(news.getData().getMonthlyNewest().getShareUrl());
            }
            dataList.addAll(news.getData().getChapterList().getDatas());
            moreWrapper.notifyDataSetChanged();
        }
    }

    @Override
    public void onRelogin() {
        reLogin();
    }


    @Override
    protected String cacheName() {
        return "monthlyRecentlyData";
    }

}
