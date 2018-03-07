package com.bizaia.zhongyin.module.message.api;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.message.data.UnreadMsgBean;
import com.bizaia.zhongyin.module.message.iml.IUnreadView;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/4/11.
 */

public class UnreadAPI {

    private IUnreadView view;
    private Message api;
    private DisposableObserver<UnreadMsgBean> msg;


    public UnreadAPI(IUnreadView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(Message.class);
    }

    public DisposableObserver<UnreadMsgBean> getUnread() {
        cancel();
        return msg = api.getUnread(BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UnreadMsgBean>() {
                    @Override
                    public void onNext(UnreadMsgBean data) {
                        if (data == null) {
                            view.showUnreadNumError(-1,"数据为空");
                        } else {
                            if (data.getCode() == 200) {
                                view.showUnreadNum(data);
                            } else {
                                view.showUnreadNumError(data.getCode(),data.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showUnreadNumError(-1,"数据为空");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }




    public void cancel() {
        if (msg != null)
            msg.dispose();

    }



}
