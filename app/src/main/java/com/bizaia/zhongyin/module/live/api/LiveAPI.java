package com.bizaia.zhongyin.module.live.api;

import android.util.Log;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.live.data.LiveDailyBean;
import com.bizaia.zhongyin.module.live.data.LiveNewBean;
import com.bizaia.zhongyin.module.live.data.LiveRecomBean;
import com.bizaia.zhongyin.module.live.iml.IDataLiveRecom;
import com.bizaia.zhongyin.module.live.iml.ILiveRecomView;
import com.bizaia.zhongyin.util.AppRetrofit;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/6.
 */

public class LiveAPI {

    private ILiveRecomView view;
    private IDataLiveRecom api;
    private DisposableObserver<LiveRecomBean> recom;
    private DisposableObserver<LiveNewBean> live;
    private DisposableObserver<LiveDailyBean> dailyLive;
    private String areaId="";

    public LiveAPI(ILiveRecomView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataLiveRecom.class);
    }

    public DisposableObserver<LiveRecomBean> getRecom(int pageNum, int pageSize) {
        cancel();
        return recom = api.getRecom(pageNum, pageSize,BIZAIAApplication.getInstance().getAreaId()
                ,BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LiveRecomBean>() {
                    @Override
                    public void onNext(LiveRecomBean data) {
                        if (data == null) {
                            view.showLiveRecomError(-1,"数据为空");
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData() == null) {
                                    view.showLiveRecomError(data.getCode(),data.getMsg());
                                } else {
                                    List<Object> dataList = new ArrayList<Object>();
                                    if(data.getData().getLiveFocusList()!=null&&!data.getData().getLiveFocusList().isEmpty())
                                    dataList.add(data.getData().getLiveFocusList());
                                    if(data.getData().getLiveRecommendList()!=null
                                            &&data.getData().getLiveRecommendList().getDatas()!=null
                                            &&!data.getData().getLiveRecommendList().getDatas().isEmpty())
                                    dataList.addAll(data.getData().getLiveRecommendList().getDatas());
                                    view.showLiveRecom(dataList,data.getData().getLiveRecommendList().getTotalPage());
                                }
                            } else {
                                view.showLiveRecomError(data.getCode(),data.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLiveRecomError(-1,"数据为空");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<LiveNewBean> getNew(int pageNum, int pageSize) {
        cancel();
        return live = api.getNew(pageNum, pageSize,BIZAIAApplication.getInstance().getAreaId(),
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LiveNewBean>() {
                    @Override
                    public void onNext(LiveNewBean data) {
                        if (data == null) {
                            view.showLiveRecomError(-1,"数据为空");
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData()== null) {
                                    view.showLiveRecomError(data.getCode(),data.getMsg());
                                } else {
                                    List<Object> dataList = new ArrayList<Object>();
                                    if(data.getData().getLiveFocusList()!=null&&!data.getData().getLiveFocusList().isEmpty())
                                        dataList.add(data.getData().getLiveFocusList());
                                    if(data.getData().getLiveList()!=null
                                            &&data.getData().getLiveList().getDatas()!=null
                                            &&!data.getData().getLiveList().getDatas().isEmpty())
                                        dataList.addAll(data.getData().getLiveList().getDatas());
                                    view.showLiveRecom(dataList,data.getData().getLiveList().getTotalPage());
                                }
                            } else {
                                view.showLiveRecomError(data.getCode(),data.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLiveRecomError(-1,"数据为空");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<LiveDailyBean> getDailyLive(String date,String email) {
        cancel();
        Log.e("LiveAPI", "getDailyLive: -------"+date );
        return dailyLive = api.getMyList(date, email,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LiveDailyBean>() {
                    @Override
                    public void onNext(LiveDailyBean data) {
                        if (data == null) {
                            view.showLiveRecomError(-1,"数据为空");
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData()== null) {
                                    view.showLiveRecomError(data.getCode(),data.getMsg());
                                } else {
                                    List<Object> dataList = new ArrayList<Object>();
                                    if(data.getData()!=null
                                            &&!data.getData().isEmpty())
                                        dataList.addAll(data.getData());
                                    view.showLiveRecom(dataList,1);
                                }
                            } else {
                                view.showLiveRecomError(data.getCode(),data.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLiveRecomError(-1,"数据为空");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void cancel() {
        if (recom != null)
            recom.dispose();
        if (live != null)
            live.dispose();
        if (dailyLive != null)
            dailyLive.dispose();
    }

}
