package com.bizaia.zhongyin.module.discovery.ui.search;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.data.SearchFixedData;
import com.bizaia.zhongyin.module.common.data.SendSearchDataToSearchAct;
import com.bizaia.zhongyin.module.common.ui.CommonSearchActivity;
import com.bizaia.zhongyin.module.discovery.adapter.DataAdapterHelper;
import com.bizaia.zhongyin.module.discovery.data.AssociationData;
import com.bizaia.zhongyin.module.discovery.data.LectureData;
import com.bizaia.zhongyin.module.discovery.data.RecentlyNewsData;
import com.bizaia.zhongyin.module.discovery.data.RecruitData;
import com.bizaia.zhongyin.module.discovery.data.SearchNavData;
import com.bizaia.zhongyin.module.live.action.LoginSuccessAction;
import com.bizaia.zhongyin.module.monthly.action.PaySuccessAction;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/3/7.
 */

public class SearchActivity extends CommonSearchActivity implements SearchContract.View {

    private SearchContract.Presenter presenter;
   private SearchNavData searchNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SearchPresenter(getBaseContext(), this);
        presenter.getSearchNav();
        addSubscription(RxBus.getInstance().getEvent(PaySuccessAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PaySuccessAction>() {
                    @Override
                    public void onNext(PaySuccessAction value) {
                        pageNo =1;
                        searchGo();
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));
        addSubscription(RxBus.getInstance().getEvent(LoginSuccessAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginSuccessAction>() {
                    @Override
                    public void onNext(LoginSuccessAction value) {
                        pageNo =1;
                        searchGo();
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));
    }

    @Override
    protected CustomAdapter adapter(List<Object> objectDisplayList) {
        return new DataAdapterHelper().getAdapter(getBaseContext(), objectDisplayList);
    }

    @Override
    protected void requestData(String query,String lable) {
        presenter.getSearchResult(pageNo, pageSize, query,type,null,"");
    }

    @Override
    protected void requestData(SendSearchDataToSearchAct query,String lable) {
        super.requestData(query,lable);
        int position = query.position;
        Log.e("SearchActivity", "requestData: -----get  select position--->"+ query.position);
        String typeText = searchNav.getData().getLecture().get(query.position).getId()+"";
        if (query.searchData instanceof List) {
            List list = (List) query.searchData;
            if (list.size() > position) {
                presenter.getSearchResult(pageNo, pageSize, "",type,typeText,"");
//                if (list.get(position) instanceof SearchNavData.DataBean.LectureBean) {
//                    presenter.getLectureData(pageNo, pageSize, ((SearchNavData.DataBean.LectureBean) list.get(position)).getId());
//                } else if (list.get(position) instanceof SearchNavData.DataBean.AssociationBean) {
//                    presenter.getLectureData(pageNo, pageSize, ((SearchNavData.DataBean.AssociationBean) list.get(position)).getId());
//                } else if (list.get(position) instanceof SearchNavData.DataBean.RecruitBean) {
//                    presenter.getLectureData(pageNo, pageSize, ((SearchNavData.DataBean.RecruitBean) list.get(position)).getId());
//                } else if (list.get(position) instanceof SearchNavData.DataBean.NewsBean) {
//                    presenter.getLectureData(pageNo, pageSize, ((SearchNavData.DataBean.NewsBean) list.get(position)).getId());
//                }
            }
        }
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setSearchNav(SearchNavData searchNav) {
        this.searchNav = searchNav;
        for(int i=0;i<searchNav.getData().getLecture().size();i++){
            Log.e("SearchActivity", "setSearchNav: -----show search type id---->"+searchNav.getData().getLecture().get(i).getId());
        }
        List<SearchFixedData> fixedDatas = new ArrayList<>();

        List<String> searchLectureNavs = new ArrayList<>();
        for (int i = 0; i < searchNav.getData().getLecture().size(); i++) {
            searchLectureNavs.add(searchNav.getData().getLecture().get(i).getSubcategory());
        }
        fixedDatas.add(new SearchFixedData(
                true
                , ""
                , searchLectureNavs
                , searchNav.getData().getLecture()
        ));

//        List<String> searchAssociationNavs = new ArrayList<>();
//        for (int i = 0; i < searchNav.getData().getAssociation().size(); i++) {
//            searchAssociationNavs.add(searchNav.getData().getAssociation().get(i).getSubcategory());
//        }
//        fixedDatas.add(new SearchFixedData(
//                true
//                , getString(R.string.mine_associ)
//                , searchAssociationNavs
//                , searchNav.getData().getAssociation()
//        ));
//
//        List<String> searchRecruitNavs = new ArrayList<>();
//        for (int i = 0; i < searchNav.getData().getRecruit().size(); i++) {
//            searchRecruitNavs.add(searchNav.getData().getRecruit().get(i).getSubcategory());
//        }
//        fixedDatas.add(new SearchFixedData(
//                true
//                , getString(R.string.mine_recruit)
//                , searchRecruitNavs
//                , searchNav.getData().getRecruit()
//        ));
//
//        List<String> searchNewsNavs = new ArrayList<>();
//        for (int i = 0; i < searchNav.getData().getNews().size(); i++) {
//            searchNewsNavs.add(searchNav.getData().getNews().get(i).getSubcategory());
//        }
//        fixedDatas.add(new SearchFixedData(
//                true
//                , getString(R.string.mine_news)
//                , searchNewsNavs
//                , searchNav.getData().getNews()
//        ));

        fixedData.clear();
        fixedData.addAll(fixedDatas);
        showSearchFixedData();
    }

    @Override
    public void setFindOutData(List<Object> data) {
        Log.e(  "setFindOutData: ",data.size()+"" );
        if (pageNo == 1) {
            objectDisplayList.clear();
            setShowState(STATE_NORMAL, false);
            checkLoadMoreAble();
        }
        if (!data.isEmpty()) {
            objectDisplayList.addAll(data);
            moreWrapper.notifyDataSetChanged();
        } else {
            if (pageNo != 1) {
                canLoadMore = false;
                loadMoreHelper.setStateDataNoMore();
            }
            if (objectDisplayList.isEmpty()) {
                setShowState(STATE_DATA_EMPTY, true);
            } else {
            }

        }
    }

    @Override
    public void setData(Object data) {
        if (pageNo == 1) {
            objectDisplayList.clear();
            setShowState(STATE_NORMAL, false);
            checkLoadMoreAble();
        }

        if (data instanceof RecentlyNewsData) {
            if (((RecentlyNewsData) data).getData() != null
                    && ((RecentlyNewsData) data).getData().getListData() != null
                    && ((RecentlyNewsData) data).getData().getListData().getDatas() != null
                    && !((RecentlyNewsData) data).getData().getListData().getDatas().isEmpty()
                    ) {
                objectDisplayList.addAll(((RecentlyNewsData) data).getData().getListData().getDatas());
                moreWrapper.notifyDataSetChanged();
            } else if (pageNo > 1) {
                canLoadMore = false;
                loadMoreHelper.setStateDataNoMore();
            } else if (pageNo == 1) {
                setShowState(STATE_DATA_EMPTY, true);
            }

        } else if (data instanceof LectureData) {
            if (((LectureData) data).getData() != null
                    && ((LectureData) data).getData().getListData() != null
                    && ((LectureData) data).getData().getListData().getDatas() != null
                    && !((LectureData) data).getData().getListData().getDatas().isEmpty()
                    ) {
                objectDisplayList.addAll(((LectureData) data).getData().getListData().getDatas());
                moreWrapper.notifyDataSetChanged();
            } else if (pageNo > 1) {
                canLoadMore = false;
                loadMoreHelper.setStateDataNoMore();
                Log.e("setData: ", "   canLoadMore      ");
            } else if (pageNo == 1) {
                setShowState(STATE_DATA_EMPTY, true);
            }
        } else if (data instanceof RecruitData) {
            if (((RecruitData) data).getData() != null
                    && ((RecruitData) data).getData().getListData() != null
                    && ((RecruitData) data).getData().getListData().getDatas() != null
                    && !((RecruitData) data).getData().getListData().getDatas().isEmpty()
                    ) {
                objectDisplayList.addAll(((RecruitData) data).getData().getListData().getDatas());
                moreWrapper.notifyDataSetChanged();
            } else if (pageNo > 1) {
                canLoadMore = false;
                loadMoreHelper.setStateDataNoMore();
            } else if (pageNo == 1) {
                setShowState(STATE_DATA_EMPTY, true);
            }
        } else if (data instanceof AssociationData) {
            if (((AssociationData) data).getData() != null
                    && ((AssociationData) data).getData().getListData() != null
                    && ((AssociationData) data).getData().getListData().getDatas() != null
                    && !((AssociationData) data).getData().getListData().getDatas().isEmpty()
                    ) {
                objectDisplayList.addAll(((AssociationData) data).getData().getListData().getDatas());
                moreWrapper.notifyDataSetChanged();
            } else if (pageNo > 1) {
                canLoadMore = false;
                loadMoreHelper.setStateDataNoMore();
            } else if (pageNo == 1) {
                setShowState(STATE_DATA_EMPTY, true);
            }
        }

    }

    @Override
    public void netError() {
        ToastUtils.showInUIThead(getBaseContext(), getString(R.string.net_error));
    }

    @Override
    public void dataError(String error) {
        if (!TextUtils.isEmpty(error)) {
            ToastUtils.showInUIThead(getBaseContext(), error);
        }else{
            ToastUtils.showInUIThead(getBaseContext(), getString(R.string.null_data));
        }
    }
}
