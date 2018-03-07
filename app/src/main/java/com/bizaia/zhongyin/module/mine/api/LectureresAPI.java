package com.bizaia.zhongyin.module.mine.api;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.mine.data.LectureresBean;
import com.bizaia.zhongyin.module.mine.data.LectureresDetail;
import com.bizaia.zhongyin.module.mine.iml.IDataLectureres;
import com.bizaia.zhongyin.module.mine.iml.ILectureresView;
import com.bizaia.zhongyin.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/10.
 */

public class LectureresAPI {
    private ILectureresView view;
    private IDataLectureres api;
    private DisposableObserver<LectureresBean> subscription;
    private DisposableObserver<LectureresDetail> detail;

    public LectureresAPI(ILectureresView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataLectureres.class);
    }



    public DisposableObserver<LectureresBean> getLectureres(long id,int pageNum,int pageSize) {
        cancel();
        return subscription = api.getLectureres(id,pageNum,pageSize,
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LectureresBean>() {
                    @Override
                    public void onNext(LectureresBean data) {
                        if (data == null) {
                            view.showLectureresError();
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData() == null) {
                                    view.showLectureresError();
                                } else {
                                    view.showLectureres(data);
                                }
                            } else {
                                view.showLectureresError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLectureresError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<LectureresDetail> getDetail(long id) {
        cancelDetail();
        return detail = api.getDetail(id,BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LectureresDetail>() {
                    @Override
                    public void onNext(LectureresDetail data) {
                        if (data == null) {
                            view.showLectureresError();
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData() == null) {
                                    view.showLectureresError();
                                } else {
                                    view.showLecturereDetail(data);
                                }
                            } else {
                                view.showLectureresError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLectureresError();
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

    public void cancelDetail(){
        if(detail!=null)
            detail.dispose();
    }
}
