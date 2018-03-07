package com.bizaia.zhongyin.module.monthly.ui.all;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.mvp.CommonContract;
import com.bizaia.zhongyin.module.common.ui.DataFragment;
import com.bizaia.zhongyin.module.monthly.adapter.DataAdapterHelper;
import com.bizaia.zhongyin.module.monthly.data.AllMonthlyData;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;

public class AllMonthlyFragment extends DataFragment<AllMonthlyData, CommonContract.Presenter>
        implements CommonContract.View<AllMonthlyData, CommonContract.Presenter> {
    @Override
    protected void requireData(int pageNo, int pageSize, Long orgId, String areaId, Long cateId) {
        presenter.setPageNo(pageNo);
        presenter.setPageSize(pageSize);
        presenter.requireData();
    }

    @Override
    protected CustomAdapter adapter() {
        return new DataAdapterHelper().getAdapter(getContext(), dataList);
    }

    @Override
    public void instancePresenter() {
        new AllMonthlyPresenter(getContext(), this);
    }

    @Override
    public void dataSuccess(AllMonthlyData news) {
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

            if (news.getData() != null && news.getData().getDatas() != null) {
                if (!isClear) {
                    isClear = true;
                    dataList.clear();
                }
                dataList.addAll(news.getData().getDatas());
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
            if (news.getData() == null
                    || news.getData().getDatas() == null
                    ||news.getData().getDatas().isEmpty()) {
                loadMoreHelper.setStateDataNoMore();
                canLoadMore = false;
                return;
            }
            dataList.addAll(news.getData().getDatas());
            moreWrapper.notifyDataSetChanged();
        }
    }

    @Override
    public void onRelogin() {
        reLogin();
    }

    @Override
    protected String cacheName() {
        return "allMonthlyRecentlyData";
    }

}
