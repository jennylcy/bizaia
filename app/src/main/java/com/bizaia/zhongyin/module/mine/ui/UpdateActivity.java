package com.bizaia.zhongyin.module.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.mine.action.UpdateToServiceAction;
import com.bizaia.zhongyin.module.mine.service.UpdateService;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.AppUtils;
import com.bizaia.zhongyin.util.NetworkUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.ServiceUtils;
import com.bizaia.zhongyin.util.ToastUtils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author yan
 */
public class UpdateActivity extends BaseActivity {

    @BindView(R.id.pb_update)
    ProgressBar pbUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        initRxAction();
    }

    private void initRxAction() {
        RxBus.getInstance().getEvent(UpdateService.UpdateInfo.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UpdateService.UpdateInfo>() {
                    @Override
                    public void onNext(UpdateService.UpdateInfo value) {
                        pbUpdate.setProgress(value.result);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        addSubscription(RxBus.getInstance().getEvent(ForceOffLine.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ForceOffLine>() {
                    @Override
                    public void onNext(ForceOffLine value) {
                        reLogin();
                        finish();
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));
    }

    public static void checkUpdate(Context context, CompositeDisposable compositeDisposable) {
        checkUpdate(context, null, compositeDisposable);
    }

    public static void checkUpdate(final Context context, final IUpdateBack IUpdateBack, final CompositeDisposable compositeDisposable) {
        IUpdate iUpdate = AppRetrofit.getAppRetrofit().retrofit().create(IUpdate.class);
        iUpdate.getUpdateInfo("android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UpdateInfo>() {
                    @Override
                    public void onNext(UpdateInfo value) {
                        if (IUpdateBack != null) {
                            IUpdateBack.onUpdateBack(value);
                        }
                        if (compositeDisposable != null) {
                            if (value.isUpdateRequired()) {
                                context.startActivity(new Intent(context, UpdateActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                downloadApk(compositeDisposable, context, value);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private static Disposable isRunningDisposable;

    private static boolean checkNet(Context context) {
        if (NetworkUtils.getNetworkType(context) == NetworkUtils.NetworkType.NETWORK_NO) {
            ToastUtils.showInUIThead(context, "当前没有网络");
        } else if (NetworkUtils.getNetworkType(context) != NetworkUtils.NetworkType.NETWORK_WIFI) {
            ToastUtils.showInUIThead(context, "当前为数据流量");
            return true;
        } else {
            return true;
        }
        return false;
    }

    public static void downloadApk(CompositeDisposable compositeDisposable, final Context context, final UpdateInfo updateInfo) {
        if (updateInfo == null || AppUtils.getAppVersionCode(context) >= updateInfo.getVersion()) {
            ToastUtils.show(context, "the version is the latest");
            return;
        }
        if (checkNet(context) && !ServiceUtils.isRunning(context, UpdateService.class)) {
            Intent intent = new Intent(context, UpdateService.class);
            context.startService(intent);
            isRunningDisposable = Observable.interval(200, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread()).subscribe(
                    new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            if (ServiceUtils.isRunning(context, UpdateService.class)) {
                                RxBus.getInstance().post(new UpdateToServiceAction(updateInfo.getDownload()
                                        , "bizaia.apk"));
                                isRunningDisposable.dispose();
                            }
                        }
                    },
                    new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                        }
                    });
            compositeDisposable.add(isRunningDisposable);
        }
    }

    @Override
    public void onBackPressed() {
    }

    public interface IUpdateBack {
        void onUpdateBack(UpdateInfo updateInfo);
    }

    private interface IUpdate {
        @GET(AddressConfiguration.URL_UPDATE)
        Observable<UpdateInfo> getUpdateInfo(@Query("type") String type);
    }

    public class UpdateInfo implements Serializable {
        String id;
        String download;
        String content;
        String createTime;
        int version;
        boolean isDel;
        String type;
        boolean updateRequired;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public boolean isDel() {
            return isDel;
        }

        public void setDel(boolean del) {
            isDel = del;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isUpdateRequired() {
            return updateRequired;
        }

        public void setUpdateRequired(boolean updateRequired) {
            this.updateRequired = updateRequired;
        }
    }

}
