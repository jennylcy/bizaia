package com.bizaia.zhongyin.module.message.ui;

import android.content.Context;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.message.api.Message;
import com.bizaia.zhongyin.module.message.data.MessageData;
import com.bizaia.zhongyin.module.message.data.UnreadMsgBean;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by yan on 2017/4/17.
 */

public class MessagePresenter implements MessageContract.Presenter {
    private MessageContract.View view;
    private Context context;

    public MessagePresenter(MessageContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
    }

    @Override
    public void getMessage(int type, int pageNum, int pageSize) {
        Message message = AppRetrofit.getAppRetrofit().retrofit().create(Message.class);
        view.addSubscription(message.getMessage(type, pageNum, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<MessageData>() {
                    @Override
                    public void onNext(MessageData value) {
                        if (value.getCode() == 200) {
                            view.setData(value);
                        } else if (value.getCode() == 3000) {
                            view.reLogin();
                        } else {
                            view.error(value.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.error(context.getString(R.string.net_error));
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void delMessage(final long id) {
        Message message = AppRetrofit.getAppRetrofit().retrofit().create(Message.class);
        view.addSubscription(message.delMessage(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody value) {
                            view.delData(id);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.error(context.getString(R.string.net_error));
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void delMessage() {
        Message message = AppRetrofit.getAppRetrofit().retrofit().create(Message.class);
        view.addSubscription(message.delMessage()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody value) {
                        view.delData(-1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.error(context.getString(R.string.net_error));
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void readMessage(long id) {
        Message message = AppRetrofit.getAppRetrofit().retrofit().create(Message.class);
        view.addSubscription(message.read(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<UnreadMsgBean>() {
                    @Override
                    public void onNext(UnreadMsgBean value) {
                        view.hasRead(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.error(context.getString(R.string.net_error));
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
