package com.bizaia.zhongyin.module.mine.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.mine.action.UpdateToServiceAction;
import com.bizaia.zhongyin.module.mine.ui.setting.SettingActivity;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.AppUtils;
import com.bizaia.zhongyin.util.FilePathUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.util.entity.DownloadProListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yan on 2016/12/16.
 */
public class UpdateService extends Service {
    private static final String TAG = "UpdateService";
    private Context context;
    private NotificationCompat.Builder builder;
    private double downLoadResult;
    private long currentTime;

    private CompositeDisposable compositeDisposable;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, Service.START_FLAG_REDELIVERY, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate:  ");
        context = this;
        compositeDisposable = new CompositeDisposable();
        initRxAction();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        cancelNotification();
        compositeDisposable.clear();
    }

    private void initRxAction() {
        compositeDisposable.add(RxBus.getInstance().getEvent(UpdateToServiceAction.class)
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<UpdateToServiceAction>() {
                    @Override
                    public void accept(UpdateToServiceAction serviceAction) throws Exception {
                        ToastUtils.showInUIThead(context, "update...");
                        initNotification(serviceAction.name);
                        downLoadApp(serviceAction.updatePath, serviceAction.name);
                    }
                }));
    }

    private void downLoadApp(String url, String name) {
        Log.e(TAG, "canService: " + "downLoadApp");

        final String path = FilePathUtils.getDirDownloadUpdatePath(context);
        if (!new File(path).exists()) {
            return;
        }
        final File appFile = new File(path + "/" + name);
        Request request = new Request.Builder().url(url).build();
        AppRetrofit.getAppRetrofit().addProgressListener(downloadProListener)
                .getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    InputStream inputStream = response.body().byteStream();
                    FileOutputStream outputStream = new FileOutputStream(appFile);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }
                    inputStream.close();
                    outputStream.close();
                    AppRetrofit.getAppRetrofit().removeProgressListener(downloadProListener);
                    installApk(appFile.getAbsolutePath());
                    stopSelf();
                } catch (IOException e) {
                    e.printStackTrace();
                    ToastUtils.showInUIThead(getBaseContext(), "error");
                    AppRetrofit.getAppRetrofit().removeProgressListener(downloadProListener);
                    stopSelf();
                }
            }
        });
    }

    private void installApk(final String filePath) {
        Observable.just(filePath)
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String appFile) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Uri contentUri = FileProvider.getUriForFile(context, "com.bizaia.zhongyin.fileprovider"
                                    , new File(filePath));
                            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                            startActivity(intent);
                        } else {
                            AppUtils.installApp(getBaseContext(), appFile);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtils.showInUIThead(context, "install error");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 初始化Notification通知
     */
    public void initNotification(String title) {
        Intent intent = new Intent(this, SettingActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setSubText("0%")
                .setContentIntent(pIntent)
                .setProgress(100, 0, false);
//        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification notification = builder.build();
//        notificationManager.notify(NOTIFY_ID, notification);
        startForeground(1, builder.build());

    }

    /**
     * 更新通知
     */
    public void updateNotification(long progress) {
        // builder.setContentText(progress + "%");
        builder.setSubText(progress + "%");
        builder.setProgress(100, (int) progress, false);
        startForeground(1, builder.build());
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
//        notificationManager.cancel(NOTIFY_ID);
    }

    private DownloadProListener downloadProListener = new DownloadProListener() {
        @Override
        public void onProgress(long currentLength, long totalLength) {
            downLoadResult = (double) currentLength / (double) totalLength * 100;

            if (System.currentTimeMillis() - currentTime > 1000) {
                updateNotification((int) (downLoadResult));
                RxBus.getInstance().post(updateInfo.setResult((int) (downLoadResult)));
                currentTime = System.currentTimeMillis();
            }
        }
    };
    UpdateInfo updateInfo = new UpdateInfo();

    public class UpdateInfo {
        public int result;

        public UpdateInfo setResult(int result) {
            this.result = result;
            return this;
        }
    }

}