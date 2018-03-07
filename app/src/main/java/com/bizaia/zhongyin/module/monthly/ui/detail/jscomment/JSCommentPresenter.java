package com.bizaia.zhongyin.module.monthly.ui.detail.jscomment;

import com.bizaia.zhongyin.module.monthly.api.Monthly;
import com.bizaia.zhongyin.module.monthly.data.CommendData;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by yan on 2017/4/19.
 */

public class JSCommentPresenter implements JSCommentContract.Presenter {
    private JSCommentContract.View view;

    public JSCommentPresenter(JSCommentContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void getCommentList(long monthlyId, int pageNum, int pageSize) {
        Monthly monthly = AppRetrofit.getAppRetrofit().retrofit().create(Monthly.class);
        view.addSubscription(monthly.getCommentList(monthlyId, pageNum, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CommendData>() {
                    @Override
                    public void onNext(CommendData value) {
                        view.setCommendData(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.error();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void publishComment(long chapterId, String content) {
        Monthly monthly = AppRetrofit.getAppRetrofit().retrofit().create(Monthly.class);
        view.addSubscription(monthly.publishComment(chapterId, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody value) {
                        view.publishSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.error();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
