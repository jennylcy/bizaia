package com.bizaia.zhongyin.module.discovery.ui.search;

import android.content.Context;
import android.text.TextUtils;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.discovery.api.Discovery;
import com.bizaia.zhongyin.module.discovery.data.AssociationData;
import com.bizaia.zhongyin.module.discovery.data.LectureData;
import com.bizaia.zhongyin.module.discovery.data.RecentlyNewsData;
import com.bizaia.zhongyin.module.discovery.data.RecruitData;
import com.bizaia.zhongyin.module.discovery.data.SearchFindData;
import com.bizaia.zhongyin.module.discovery.data.SearchNavData;
import com.bizaia.zhongyin.util.AppRetrofit;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public final class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View view;
    private Context context;

    SearchPresenter(Context context, SearchContract.View view) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
    }

    @Override
    public void getSearchNav() {
        Discovery monthly = AppRetrofit.getAppRetrofit().retrofit().create(Discovery.class);
        view.addSubscription(monthly.getFindCateList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SearchNavData>() {
                    @Override
                    public void onNext(SearchNavData value) {
                        if (value.getCode() == 200) {
                            view.setSearchNav(value);
                        } else if (value.getCode() == 3000) {
                            view.reLogin();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.netError();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }


    @Override
    public void getSearchResult(int pageNum, int pageSize, String queryStr, int type, String label, String sid) {
        Discovery monthly = AppRetrofit.getAppRetrofit().retrofit().create(Discovery.class);
        view.addSubscription(monthly.getSearchResult(pageNum, pageSize, queryStr, type, label, sid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SearchFindData>() {
                    @Override
                    public void onNext(SearchFindData value) {
                        List<Object> objects = new ArrayList<>();

                        if (value.getCode() == 200 && value.getData() != null && value.getData().getDatas() != null) {
                            for (SearchFindData.DataBean.DatasBean findData : value.getData().getDatas()) {
                                if (findData.getType() == 1) {
                                    LectureData.DataBean.ListDataBean.DatasBean bean = new LectureData.DataBean.ListDataBean.DatasBean();
                                    bean.setProvince(findData.getProvince());
                                    bean.setId(findData.getId());
                                    bean.setCountry(findData.getCountry());
                                    bean.setCoverUrl(findData.getCoverUrl());
                                    bean.setCreateTime(findData.getCreateTime());
                                    bean.setH5Url(findData.getH5Url());
                                    bean.setTitle(findData.getTitle());
                                    bean.setCategory(findData.getCategory());
                                    bean.setAdmissionFee(findData.getAdmissionFee());
                                    bean.setLectureTime(findData.getLectureTime()+"");
                                    bean.setOrgId(findData.getOrgId());
                                    bean.setOrgName(findData.getOrgName());
                                    bean.setSubcategory(findData.getSubcategory());
                                    bean.setReserveStatus(findData.getReserveStatus());
                                    bean.setPageViewNum(findData.getPageViewNum());
                                    objects.add(bean);
                                } else if (findData.getType() == 2) {
                                    AssociationData.DataBean.ListDataBean.DatasBean bean = new AssociationData.DataBean.ListDataBean.DatasBean();
                                    bean.setId(findData.getId());
                                    bean.setCoverUrl(findData.getCoverUrl());
                                    bean.setCreateTime(findData.getCreateTime());
                                    bean.setH5Url(findData.getH5Url());
                                    bean.setTitle(findData.getTitle());
                                    bean.setOrgId(findData.getOrgId());
                                    bean.setFavorite(findData.isFavorite());
                                    objects.add(bean);

                                } else if (findData.getType() == 3) {
                                    RecruitData.DataBean.ListDataBean.DatasBean bean = new RecruitData.DataBean.ListDataBean.DatasBean();
                                    bean.setId(findData.getId());
                                    bean.setCoverUrl(findData.getCoverUrl());
                                    bean.setCreateTime(findData.getCreateTime());
                                    bean.setH5Url(findData.getH5Url());
                                    bean.setTitle(findData.getTitle());
                                    bean.setOrgId(findData.getOrgId());
                                    bean.setFavorite(findData.isFavorite());
                                    bean.setCompany(findData.getCompany());
                                    bean.setWorkCity(findData.getWorkCity());
                                    bean.setWorkCountry(findData.getWorkCountry());
                                    bean.setWorkLocation(findData.getWorkLocation());
                                    bean.setSalery(findData.getSalery());
                                    objects.add(bean);

                                } else if (findData.getType() == 4) {
                                    RecentlyNewsData.DataBean.ListDataBean.DatasBean bean = new RecentlyNewsData.DataBean.ListDataBean.DatasBean();
                                    bean.setProvince(findData.getProvince());
                                    bean.setId(findData.getId());
                                    bean.setCountry(findData.getCountry());
                                    bean.setCoverUrl(findData.getCoverUrl());
                                    bean.setCreateTime(findData.getCreateTime());
                                    bean.setH5Url(findData.getH5Url());
                                    bean.setTitle(findData.getTitle());
                                    bean.setCategory(findData.getCategory());
                                    bean.setAdmissionFee(findData.getAdmissionFee());
                                    bean.setLectureTime(findData.getLectureTime()+"");
                                    bean.setOrgId(findData.getOrgId());
                                    bean.setSubcategory(findData.getSubcategory());
                                    objects.add(bean);
                                }
                            }
                            view.setFindOutData(objects);
                        } else if (value.getCode() == 3000) {
                            view.reLogin();
                        } else {
                            view.dataError(value.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.netError();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    @Override
    public void getNewsData(int pageNum, int pageSize, long cateId) {
        Discovery news = AppRetrofit.getAppRetrofit().retrofit().create(Discovery.class);
        Observable<RecentlyNewsData> dataObservable;
        if (TextUtils.isEmpty(BIZAIAApplication.getInstance().getAreaId())) {
            dataObservable = news.getNewsList(pageNum, pageSize, cateId);
        } else {
            dataObservable = news.getNewsList(pageNum, pageSize, cateId, BIZAIAApplication.getInstance().getAreaId());
        }
        view.addSubscription(dataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<RecentlyNewsData>() {
                    @Override
                    public void onNext(RecentlyNewsData value) {
                        if (value.getCode() == 200)
                            view.setData(value);
                        else if (value.getCode() == 3000)
                            view.reLogin();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.netError();
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    @Override
    public void getAssociationData(int pageNum, int pageSize, long cateId) {
//        Discovery news = AppRetrofit.getAppRetrofit().retrofit().create(Discovery.class);
//        Observable<AssociationData> dataObservable;
//        if (TextUtils.isEmpty(BIZAIAApplication.getInstance().getAreaId())) {
//            dataObservable = news.getAssociationList(pageNum, pageSize, cateId);
//        } else {
//            dataObservable = news.getAssociationList(pageNum, pageSize, cateId, BIZAIAApplication.getInstance().getAreaId());
//        }
//        view.addSubscription(dataObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableObserver<AssociationData>() {
//                    @Override
//                    public void onNext(AssociationData value) {
//                        if (value.getCode() == 200) {
//                            view.setData(value);
//                        } else if (value.getCode() == 3000) {
//                            view.reLogin();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        view.netError();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                }));
    }

    @Override
    public void getRecruitData(int pageNum, int pageSize, long cateId) {
        Discovery news = AppRetrofit.getAppRetrofit().retrofit().create(Discovery.class);
        Observable<RecruitData> dataObservable;
        if (TextUtils.isEmpty(BIZAIAApplication.getInstance().getAreaId())) {
            dataObservable = news.getRecruitList(pageNum, pageSize, cateId);
        } else {
            dataObservable = news.getRecruitList(pageNum, pageSize, cateId, BIZAIAApplication.getInstance().getAreaId());
        }
        view.addSubscription(dataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<RecruitData>() {
                    @Override
                    public void onNext(RecruitData value) {
                        if (value.getCode() == 200) {
                            view.setData(value);
                        } else if (value.getCode() == 3000) {
                            view.reLogin();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.netError();
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    @Override
    public void getLectureData(int pageNum, int pageSize, long cateId) {
        Discovery news = AppRetrofit.getAppRetrofit().retrofit().create(Discovery.class);
        Observable<LectureData> dataObservable;
        if (TextUtils.isEmpty(BIZAIAApplication.getInstance().getAreaId())) {
            dataObservable = news.getLectureList(pageNum, pageSize, cateId);
        } else {
            dataObservable = news.getLectureList(pageNum, pageSize, cateId, BIZAIAApplication.getInstance().getAreaId());
        }
        view.addSubscription(dataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LectureData>() {
                    @Override
                    public void onNext(LectureData value) {
                        if (value.getCode() == 200) {
                            view.setData(value);
                        } else if (value.getCode() == 3000) {
                            view.reLogin();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.netError();
                    }

                    @Override
                    public void onComplete() {
                    }
                }));

    }
}
