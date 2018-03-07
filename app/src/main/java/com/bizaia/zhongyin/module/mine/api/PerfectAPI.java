package com.bizaia.zhongyin.module.mine.api;

import com.bizaia.zhongyin.module.mine.data.InfoBean;
import com.bizaia.zhongyin.module.mine.data.InfoData;
import com.bizaia.zhongyin.module.mine.iml.IDataInfo;
import com.bizaia.zhongyin.module.mine.iml.IPerfectView;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator
 * Created on 2017/7/7 15:40
 */

public class PerfectAPI {
    private IPerfectView mView;
    private IDataInfo mApi;
    private DisposableObserver<InfoBean> mObserver;

    public PerfectAPI(IPerfectView view) {
        mView = view;
        mApi = AppRetrofit.getAppRetrofit().retrofit().create(IDataInfo.class);
    }

    public void getInfo(String memberId) {
        mObserver = mApi.getInfo(memberId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<InfoBean>() {
                    @Override
                    public void onNext(InfoBean bean) {
                        if (bean != null && bean.getCode() == 200) {
                            mView.getInfoSuccess(bean);
                        } else {
                            mView.getInfoError(bean == null ? 0 : bean.getCode());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.getInfoError(0);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void changeInfo(String memberId, InfoData data) {
        cancel();
        mObserver = mApi.changeInfo(memberId, data.getName(), data.getFakeName()
                , data.getMail(), data.getBirthday(), data.getGender(), data.getProfessional(), data.getIndustry()
                , data.getPosition(), data.getPost(), data.getPlace(), data.getCompany(), data.getPhone()
                , data.getPersonalWeb(), data.getCompanyWeb())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<InfoBean>() {
                    @Override
                    public void onNext(InfoBean bean) {
                        if (bean != null && bean.getCode() == 200) {
                            mView.changeInfoSuccess();
                        } else {
                            mView.changeInfoError(bean == null ? 0 : bean.getCode());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.changeInfoError(0);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void cancel() {
        if (mObserver != null) mObserver.dispose();
    }
}
