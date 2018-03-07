package com.bizaia.zhongyin.module.monthly.ui.search;

import android.os.Bundle;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.data.SearchFixedData;
import com.bizaia.zhongyin.module.common.data.SearchNavData;
import com.bizaia.zhongyin.module.common.data.SendSearchDataToSearchAct;
import com.bizaia.zhongyin.module.common.ui.CommonSearchActivity;
import com.bizaia.zhongyin.module.monthly.adapter.DataAdapterHelper;
import com.bizaia.zhongyin.module.monthly.data.MonthlyJSSearchData;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yan on 2017/3/7.
 */

public class SearchActivity extends CommonSearchActivity implements SearchContract.View {
    private SearchContract.Presenter presenter;
    private SearchNavData searchNav;
    @BindView(R.id.refresh_layout)
    public PtrClassicFrameLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SearchPresenter(getBaseContext(), this);
        presenter.getSearchNav();
    }

    @Override
    protected CustomAdapter adapter(List<Object> objectDisplayList) {
        return new DataAdapterHelper().getAdapter(getBaseContext(), objectDisplayList);
    }

    @Override
    protected void requestData(String query,String label) {
        refreshLayout.autoRefresh();
        presenter.getSearchResult(pageNo, pageSize, query,type,null);
    }
    @Override
    protected void requestData(SendSearchDataToSearchAct query, String lable) {
        super.requestData(query, lable);
        refreshLayout.autoRefresh();
        String typeText = searchNav.getData().get(query.position).getId()+"";
        presenter.getSearchResult(pageNo, pageSize, "",type,typeText);
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void setSearchNav(SearchNavData searchNav) {
        refreshLayout.refreshComplete();
        this.searchNav = searchNav;
        List<SearchFixedData> fixedDatas = new ArrayList<>();

        List<String> searchLectureNavs = new ArrayList<>();

        if (searchNav.getData() != null) {
            for (SearchNavData.DataBean bean : searchNav.getData()) {
                searchLectureNavs.add(bean.getSubcategory());
            }
        }
        fixedDatas.add(new SearchFixedData(
                false
                , ""
                , searchLectureNavs
                , searchNav.getData()
        ));

        fixedData.clear();
        fixedData.addAll(fixedDatas);
        showSearchFixedData();
    }


    @Override
    public void setData(MonthlyJSSearchData data) {
        refreshLayout.refreshComplete();
        if (pageNo == 1) {
            objectDisplayList.clear();
            setShowState(STATE_NORMAL, false);
            checkLoadMoreAble();
        }
        if (data!=null&&data.getData()!=null &&
                data.getData().getDatas()!=null&&!
                data.getData().getDatas().isEmpty()) {
            objectDisplayList.addAll(data.getData().getDatas());
            moreWrapper.notifyDataSetChanged();
        } else {
            if (pageNo != 1) {
                canLoadMore = false;
                loadMoreHelper.setStateDataNoMore();
            }
            if (objectDisplayList.isEmpty()) {
                setShowState(STATE_DATA_EMPTY, true);
                ToastUtils.showInUIThead(getBaseContext(), getString(R.string.null_data));
            } else {
            }
        }
    }


    @Override
    public void netError() {
        refreshLayout.refreshComplete();
        ToastUtils.showInUIThead(getBaseContext(), getString(R.string.net_error));
    }

    @Override
    public void dataError(String error) {
        refreshLayout.refreshComplete();
        ToastUtils.showInUIThead(getBaseContext(), getString(R.string.null_data));
    }
}