package com.bizaia.zhongyin.module.live.ui.search;

import android.content.Context;
import android.text.TextUtils;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.common.data.SearchNavData;
import com.bizaia.zhongyin.module.live.data.CameraStreamAddressEntity;
import com.bizaia.zhongyin.module.live.data.CoursewareStreamAddressEntity;
import com.bizaia.zhongyin.module.live.data.LiveNewBean;
import com.bizaia.zhongyin.module.live.data.SearchLivingData;
import com.bizaia.zhongyin.module.live.iml.IDataLiveRecom;
import com.bizaia.zhongyin.module.video.api.Video;
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
        Video monthly = AppRetrofit.getAppRetrofit().retrofit().create(Video.class);
        view.addSubscription(monthly.getCateList(2)
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
    public void getSearchResult(int pageNum, int pageSize, String queryStr,int type,String label) {
        IDataLiveRecom monthly = AppRetrofit.getAppRetrofit().retrofit().create(IDataLiveRecom.class);
        view.addSubscription(monthly.findVideo(pageNum, pageSize, queryStr,type,label)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SearchLivingData>() {
                    @Override
                    public void onNext(SearchLivingData value) {
                        if (value.getCode() == 200 && value.getData() != null
                                && value.getData().getDatas() != null) {
                            List<LiveNewBean.DataEntity.LiveListEntity.DatasEntity> list = new ArrayList<>();
                            for (SearchLivingData.DataBean.DatasBean bean : value.getData().getDatas()) {
                                LiveNewBean.DataEntity.LiveListEntity.DatasEntity datasEntity=new LiveNewBean.DataEntity.LiveListEntity.DatasEntity();
                                datasEntity.setId(bean.getId());
                                CameraStreamAddressEntity cameraStreamAddressEntity=new CameraStreamAddressEntity();
                                cameraStreamAddressEntity.setCid(bean.getCameraStreamAddress().getCid());
                                cameraStreamAddressEntity.setPullFlvUrl(bean.getCameraStreamAddress().getPullFlvUrl());
                                cameraStreamAddressEntity.setPullM3u8Url(bean.getCameraStreamAddress().getPullM3u8Url());
                                cameraStreamAddressEntity.setPushUrl(bean.getCameraStreamAddress().getPushUrl());
                                datasEntity.setCameraStreamAddress(cameraStreamAddressEntity);
                                datasEntity.setCateName(bean.getCateName());
                                datasEntity.setChatroomId(bean.getChatroomId());
                                CoursewareStreamAddressEntity coursewareStreamAddressEntity=new CoursewareStreamAddressEntity();
                                coursewareStreamAddressEntity.setPushUrl(bean.getCoursewareStreamAddress().getPushUrl());
                                coursewareStreamAddressEntity.setPullM3u8Url(bean.getCoursewareStreamAddress().getPullM3u8Url());
                                coursewareStreamAddressEntity.setCid(bean.getCoursewareStreamAddress().getCid());
                                coursewareStreamAddressEntity.setPullFlvUrl(bean.getCoursewareStreamAddress().getPullFlvUrl());
                                datasEntity.setCoursewareStreamAddress(coursewareStreamAddressEntity);
                                datasEntity.setCoverUrl(bean.getCoverUrl());
                                datasEntity.setCreateTime(bean.getCreateTime()+"");
                                datasEntity.setShareUrl(bean.getH5Url());
                                datasEntity.setMaxAudience(bean.getMaxAudience());
                                datasEntity.setLecturer(bean.getLecturer());
                                datasEntity.setDel(bean.isDel());
                                datasEntity.setStatus(bean.getStatus());
                                datasEntity.setChatroomId(bean.getChatroomId());
                                datasEntity.setTitle(bean.getTitle());
                                datasEntity.setOrganizationLogo(bean.getOrganizationLogo());
                                datasEntity.setOrganizationName(bean.getOrganizationName());
                                datasEntity.setPrice(bean.getPrice());
                                datasEntity.setPageViewNum(bean.getPageViewNum());
                                list.add(datasEntity);
                            }

                            view.setData(list);
                        } else if (value.getCode() == 3000) {
                            view.reLogin();
                        }else if(value.getCode() == 200 ){
                            view.dataError("");
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
    public void getLiving(int pageNum, int pageSize, long cateId) {
        IDataLiveRecom news = AppRetrofit.getAppRetrofit().retrofit().create(IDataLiveRecom.class);
        Observable<LiveNewBean> dataObservable;
        if (TextUtils.isEmpty(BIZAIAApplication.getInstance().getAreaId())) {
            dataObservable = news.getNew(pageNum, pageSize, cateId);
        } else {
            dataObservable = news.getNew(pageNum, pageSize, cateId, BIZAIAApplication.getInstance().getAreaId());
        }
        view.addSubscription(dataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LiveNewBean>() {
                    @Override
                    public void onNext(LiveNewBean value) {
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

}
