package com.bizaia.zhongyin.module.mine.ui;

import android.annotation.SuppressLint;
import android.util.Log;

import com.bizaia.zhongyin.module.mine.adapter.DataLectureresAdapterHelper;
import com.bizaia.zhongyin.module.mine.api.CompanyNewsPresenter;
import com.bizaia.zhongyin.module.discovery.data.RecentlyNewsData;
import com.bizaia.zhongyin.module.common.mvp.CommonContract;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class CompanyNewsFragment extends DataNormalFragment<RecentlyNewsData, CommonContract.Presenter>
        implements CommonContract.View<RecentlyNewsData, CommonContract.Presenter> {

    private final String TAG="CompanyNewsFragment";

    public CompanyNewsFragment(long orgId){
        this.orgId = orgId;
    }

    @Override
    protected void requireData(int pageNo, int pageSize, Long orgId, Long areaId, Long cateId) {

        Log.e(TAG, "requireData: ---->"+orgId );
        presenter.setOrgId(orgId);
        presenter.setPageNo(pageNo);
        presenter.setPageSize(pageSize);
        presenter.requireData();
    }

    @Override
    public void instancePresenter() {
        new CompanyNewsPresenter(getContext(), this);
    }

    @Override
    public void dataSuccess(RecentlyNewsData news) {
        super.dataSuccess(news);
        adapter.hide("DATA_EMPTY")
                .hide("NET_ERROR")
                .hide("DATA_ERROR");
        if(news!=null&&news.getData()!=null&&news.getData().getListData()!=null&&news.getData().getListData().getDatas()!=null) {
            if (this.dataList == null)
                this.dataList = new ArrayList<>();
            if (pageNo == 1) {
                this.dataList.clear();
            }
            this.dataList.addAll(news.getData().getListData().getDatas());
            moreWrapper.notifyDataSetChanged();
            if (pageNo > news.getData().getListData().getTotalPage()
                    || news.getData() == null) {
                moreWrapper.clearLoadMore(true);
                canLoadMore = false;
            }
        }else{
            adapter.show("DATA_EMPTY");
            moreWrapper.clearLoadMore(true);
        }
    }

    public void refresh(){
        this.pageNo=1;
        presenter.requireData();
    }

    @Override
    public void onRelogin() {
        reLogin();
    }

    @Override
    protected CustomAdapter adapter() {
        return new DataLectureresAdapterHelper().getAdapter(getContext(), this.dataList, null);
    }
}
