package com.bizaia.zhongyin.module.mine.api;

import android.graphics.Bitmap;
import android.util.Log;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.live.data.LiveRecomBean;
import com.bizaia.zhongyin.module.live.iml.IDataLiveRecom;
import com.bizaia.zhongyin.module.login.iml.IDataLogin;
import com.bizaia.zhongyin.module.mine.data.UserInfoBean;
import com.bizaia.zhongyin.module.mine.iml.IUserInfoView;
import com.bizaia.zhongyin.util.AppRetrofit;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by zyz on 2017/3/6.
 */

public class UserInfoAPI {

    private final String TAG = "UserInfoAPI";
    private IUserInfoView view;
    private IDataLiveRecom api;
    private DisposableObserver<LiveRecomBean> subscription;
    private DisposableObserver<ResponseBody> updateIcon;
    private DisposableObserver<UserInfoBean> info;
    static private File realFile = new File(BIZAIAApplication.getInstance()
            .getApplicationContext().getFilesDir().getAbsolutePath(),
            "vip_photo.jpg");
    private Bitmap bitmap;

    public UserInfoAPI(IUserInfoView view) {
        this.view = view;
        api = AppRetrofit.getAppRetrofit().retrofit().create(IDataLiveRecom.class);
    }

    public DisposableObserver<UserInfoBean> getInfo() {
        cancel();
        return info = api.getInfo(BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserInfoBean>() {
                    @Override
                    public void onNext(UserInfoBean data) {
                        if (data == null) {
                            view.showUserInfoError();
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData() == null) {
                                    view.showUserInfoError();
                                } else {
                                    view.showUserInfo(data);
                                }
                            } else {
                                view.showUserInfoError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showUserInfoError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<LiveRecomBean> getNew(int pageNum, int pageSize) {
        cancel();
        return subscription = api.getRecom(pageNum, pageSize, "",
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LiveRecomBean>() {
                    @Override
                    public void onNext(LiveRecomBean data) {
                        if (data == null) {
                            view.showUserInfoError();
                        } else {
                            if (data.getCode() == 200) {
                                if (data.getData() == null) {
                                    view.showUserInfoError();
                                } else {
//                                    view.showLiveRecom();
                                }
                            } else {
                                view.showUserInfoError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showUserInfoError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<ResponseBody> updateIcon(Bitmap bitmap) {
        cancelIcon();
        this.bitmap = bitmap;
        savePhoto();
        return updateIcon = api.updateIcon(upload(realFile),
                BIZAIAApplication.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody data) {
                        try {
                            JSONObject dataJson = new JSONObject(data.string());

                            if (dataJson.getInt("code") == 200) {
                                if (dataJson.getString("data") == null) {
                                    view.showUserInfoError();
                                } else {
                                    view.showIcon(dataJson.getString("data"));
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            view.showUserInfoError();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showUserInfoError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void savePhoto() {
        try {
            Log.d("TAG", "file path : " + realFile.getAbsolutePath());
            FileOutputStream out = new FileOutputStream(realFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Log.e(TAG, "onActivityResult: --------->savePhoto");
            // 发广播
//			Intent intent = new Intent(
//					AppConsts.LOCAL_MSG_FILTER.ISUPDATAPHOTO.toString());
//			((Activity) context).sendStickyBroadcast(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public MultipartBody.Part upload(File file) {
        Log.e(TAG, "onActivityResult: --------->upload");
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData("file", file.getName(), requestFile);
    }

    public void logOut() {
        IDataLogin iDataLogin = AppRetrofit.getAppRetrofit().retrofit().create(IDataLogin.class);
        iDataLogin.logOut().subscribeOn(Schedulers.io())
                .subscribe(new DisposableSubscriber<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {

                    }

                    @Override
                    public void onError(Throwable t) {

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

    public void cancelIcon() {
        if (updateIcon != null)
            updateIcon.dispose();
    }

}
