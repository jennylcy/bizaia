package com.bizaia.zhongyin.module.mine.api;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.mine.data.BillBean;
import com.bizaia.zhongyin.module.mine.iml.IBillView;
import com.bizaia.zhongyin.module.mine.iml.IDataBill;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/7.
 */

public class BillAPI {
    private IBillView view;
    private IDataBill api;
    private DisposableObserver<BillBean> subscription;

    public BillAPI(IBillView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataBill.class);
    }



    public DisposableObserver<BillBean> getBill(int pageNum,int pageSize) {
        cancel();
        return subscription = api.getBill(pageNum,pageSize,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BillBean>() {
                    @Override
                    public void onNext(BillBean data) {
                        if (data == null) {
                            view.showBillError();
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData() == null) {
                                    view.showBillError();
                                } else {
                                    view.showBill(data);
                                }
                            }else if(data.getCode()==3000) {
                                    view.onRelogin();
                            }else {
                                view.showBillError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showBillError();
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
