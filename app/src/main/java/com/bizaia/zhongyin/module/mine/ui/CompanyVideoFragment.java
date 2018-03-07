package com.bizaia.zhongyin.module.mine.ui;

import android.annotation.SuppressLint;
import android.util.Log;

import com.bizaia.zhongyin.module.common.mvp.CommonContract;
import com.bizaia.zhongyin.module.mine.adapter.DataLectureresAdapterHelper;
import com.bizaia.zhongyin.module.video.data.RecentlyVideoData;
import com.bizaia.zhongyin.module.video.ui.recently.RecentlyPresenter;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;

@SuppressLint("ValidFragment")
public class CompanyVideoFragment extends DataNormalFragment<RecentlyVideoData, CommonContract.Presenter>
        implements CommonContract.View<RecentlyVideoData,CommonContract.Presenter> {

    private final String TAG="CompanyVideoFragment";

    public CompanyVideoFragment(long orgId){
        this.orgId = orgId;
    }

    @Override
    protected CustomAdapter adapter() {
        return new DataLectureresAdapterHelper().getAdapter(getContext(), dataList, null);
    }

    @Override
    protected void requireData(int pageNo, int pageSize, Long orgId, Long areaId, Long cateId) {
        Log.e(TAG, "requireData: ---->"+orgId );
        presenter.setOrgId(orgId);
        presenter.setPageSize(pageSize)
                .setPageNo(pageNo)
//                .setOrgId(orgId)
                .requireData();
    }

    @Override
    public void instancePresenter() {
        new RecentlyPresenter(getContext(), this);
    }

    @Override
    public void dataSuccess(RecentlyVideoData news) {
        super.dataSuccess(news);
        adapter.hide("DATA_EMPTY")
                .hide("NET_ERROR")
                .hide("DATA_ERROR");
        if(news!=null&&news.getData()!=null&&news.getData().getListData()!=null&&news.getData().getListData().getDatas()!=null) {
            if (pageNo == 1) {
                dataList.clear();
                checkLoadMoreAble();
            }
            if (news.getData().getListData() == null
                    || news.getData().getListData().getDatas() == null
                    || news.getData().getListData().getDatas().isEmpty()) {
                moreWrapper.clearLoadMore(true);
                canLoadMore = false;
                return;
            }
            dataList.addAll(news.getData().getListData().getDatas());
            moreWrapper.notifyDataSetChanged();
        }else{
            adapter.show("DATA_EMPTY");
            moreWrapper.clearLoadMore(true);
        }
        moreWrapper.notifyDataSetChanged();
    }

    @Override
    public void onRelogin() {
        reLogin();
    }

    public void refresh(){
        this.pageNo=1;
        presenter.requireData();
    }
}
