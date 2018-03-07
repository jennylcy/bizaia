package com.bizaia.zhongyin.module.video.ui.search;

import android.os.Bundle;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.data.SearchFixedData;
import com.bizaia.zhongyin.module.common.data.SearchNavData;
import com.bizaia.zhongyin.module.common.data.SendSearchDataToSearchAct;
import com.bizaia.zhongyin.module.common.ui.CommonSearchActivity;
import com.bizaia.zhongyin.module.video.adapter.DataAdapterHelper;
import com.bizaia.zhongyin.module.video.data.RecentlySearchVideoData;
import com.bizaia.zhongyin.module.video.data.RecentlyVideoData;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 2017/3/7.
 */

public class SearchActivity extends CommonSearchActivity implements SearchContract.View {

    private SearchContract.Presenter presenter;
    private String sid;
    private SearchNavData searchNav;

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
    protected void requestData(String query, String label) {
        presenter.getSearchResult(pageNo, pageSize, query, type, null, sid);
    }

    @Override
    protected void requestData(SendSearchDataToSearchAct query, String label) {
        super.requestData(query, label);
        int position = query.position;
        String typeText = searchNav.getData().get(query.position).getId()+"";
        if (query.searchData instanceof List) {
            presenter.getSearchResult(pageNo, pageSize,"", type, typeText, sid);
        }
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setSearchNav(SearchNavData searchNav) {
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
    public void setData(RecentlySearchVideoData data) {
        if (pageNo == 1) {
            objectDisplayList.clear();
            setShowState(STATE_NORMAL, false);
            checkLoadMoreAble();
        }
        if (data.getCode() != 200) {
            ToastUtils.showInUIThead(getBaseContext(), data.getMsg() + "");
        } else {
            if (data.getData() != null
                    && data.getData().getDatas() != null
                    && !data.getData().getDatas().isEmpty()) {
                objectDisplayList.addAll(data.getData().getDatas());
                moreWrapper.notifyDataSetChanged();
            } else {
                if (pageNo > 1) {
                    canLoadMore = false;
                    loadMoreHelper.setStateDataNoMore();
                } else {
                    setShowState(STATE_DATA_EMPTY, false);
                    moreWrapper.notifyDataSetChanged();
                    ToastUtils.showInUIThead(getBaseContext(), getString(R.string.null_data));
                }
            }

        }
    }

    @Override
    public void setData(RecentlyVideoData data) {

    }

    @Override
    public void netError() {
        ToastUtils.showInUIThead(getBaseContext(), getString(R.string.net_error));
    }

    @Override
    public void dataError(String error) {
        ToastUtils.showInUIThead(getBaseContext(), getString(R.string.null_data));
    }
}
