package com.bizaia.zhongyin.module.discovery.ui.chair;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.ui.DataFragment;
import com.bizaia.zhongyin.module.discovery.adapter.DataAdapterHelper;
import com.bizaia.zhongyin.module.discovery.data.BannerLectureData;
import com.bizaia.zhongyin.module.discovery.data.LectureData;
import com.bizaia.zhongyin.module.common.mvp.CommonContract;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;

public class ChairFragment extends DataFragment<LectureData, CommonContract.Presenter>
        implements CommonContract.View<LectureData, CommonContract.Presenter> {
    private DataAdapterHelper dataAdapterHelper;

    @Override
    protected void requireData(int pageNo, int pageSize, Long orgId, String areaId, Long cateId) {
        presenter.setPageNo(pageNo);
        presenter.setPageSize(pageSize);
        presenter.requireData();
    }

    @Override
    public void instancePresenter() {
        new ChairPresenter(getContext(), this);
    }

    @Override
    public void dataSuccess(LectureData news) {
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
            if (news.getData().getFocusData() != null
                    &&!news.getData().getFocusData().isEmpty()) {
                isClear = true;
                dataList.clear();
                dataList.add(new BannerLectureData(news.getData().getFocusData()));
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

            if (dataList.isEmpty()) {
                setShowState(STATE_DATA_EMPTY, false);
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
    protected CustomAdapter adapter() {
        dataAdapterHelper = new DataAdapterHelper();
        return dataAdapterHelper.getAdapter(getContext(), dataList, refreshLayout);
    }

    @Override
    public void onResume() {
        super.onResume();
        dataAdapterHelper.bannerResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        dataAdapterHelper.bannerPause();
    }

    @Override
    protected String cacheName() {
        return "chairData";
    }

}
