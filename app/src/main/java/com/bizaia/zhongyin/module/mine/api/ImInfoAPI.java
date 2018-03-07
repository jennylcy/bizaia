package com.bizaia.zhongyin.module.mine.api;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.mine.data.ImInfo;
import com.bizaia.zhongyin.module.mine.iml.IDataImInfo;
import com.bizaia.zhongyin.module.mine.iml.IImInfoView;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/18.
 */

public class ImInfoAPI {

    private IImInfoView view;
    private IDataImInfo api;
    private DisposableObserver<ImInfo> subscription;

    public ImInfoAPI(IImInfoView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataImInfo.class);
    }



    public DisposableObserver<ImInfo> getImInfo() {
        cancel();
        //Test api_token_116b2ba9920d4c8a9117ef2dbf93b6e3
        return subscription = api.imInfo(BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ImInfo>() {
                    @Override
                    public void onNext(ImInfo data) {
                        if (data == null) {
                            view.showImInfoError();
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData() == null) {
                                    view.showImInfoError();
                                } else {
                                    view.showImInfo(data);
                                }
                            } else {
                                view.showImInfoError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showImInfoError();
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
