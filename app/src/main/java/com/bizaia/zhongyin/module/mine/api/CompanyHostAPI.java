package com.bizaia.zhongyin.module.mine.api;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.mine.data.CompanyHostBean;
import com.bizaia.zhongyin.module.mine.iml.ICompanyView;
import com.bizaia.zhongyin.module.mine.iml.IDataCompanyHost;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/10.
 */

public class CompanyHostAPI {
    private ICompanyView view;
    private IDataCompanyHost api;
    private DisposableObserver<CompanyHostBean> subscription;

    public CompanyHostAPI(ICompanyView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataCompanyHost.class);
    }



    public DisposableObserver<CompanyHostBean> getCompanyHost(long id) {
        cancel();
        return subscription = api.getCompanyHost(id,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CompanyHostBean>() {
                    @Override
                    public void onNext(CompanyHostBean data) {
                        if (data == null) {
                            view.showCompanyHostError();
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData() == null) {
                                    view.showCompanyHostError();
                                } else {
                                    view.showCompanyHost(data);
                                }
                            } else {
                                view.showCompanyHostError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showCompanyHostError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void cancel() {
        if (subscription != null)
            subscription.dispose();
    }
}
