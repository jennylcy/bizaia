package com.bizaia.zhongyin.module.monthly.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.live.action.LoadPDFAction;
import com.bizaia.zhongyin.module.live.action.LoadSuccessPDFAction;
import com.bizaia.zhongyin.module.mine.action.DeleteNotifyServiceAction;
import com.bizaia.zhongyin.module.mine.action.PDFDownloadUpdateAction;
import com.bizaia.zhongyin.module.mine.ui.MonthlyPdfActivity;
import com.bizaia.zhongyin.module.monthly.action.HasDownloadAction;
import com.bizaia.zhongyin.module.monthly.action.ToServiceAction;
import com.bizaia.zhongyin.repository.data.CoursewareBean;
import com.bizaia.zhongyin.repository.data.CoursewareBeanDB;
import com.bizaia.zhongyin.repository.data.MonthlyNewestBean;
import com.bizaia.zhongyin.repository.data.MonthlyNewestBeanDB;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.FilePathUtils;
import com.bizaia.zhongyin.util.FileUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.util.entity.DownloadProListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yan on 2016/12/16.
 */
public class DownLoadService extends Service {
    private static final String TAG = "DownLoadService";
    private Context context;
    private NotificationCompat.Builder builder;
    private double downLoadResult;
    private long currentTime;

    private List<MonthlyNewestBean> pdfDatas;
    private long currentId;

    private List<CoursewareBean> coursewareBeen;
    private long liveId;

    private boolean isDownloadTask;
    private boolean needDelete;

    private int retryTimes;

    private CompositeDisposable compositeDisposable;
    private boolean isBuy;
    private boolean shouldDown;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");
        isBuy = intent.getBooleanExtra("isBuy", false);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate:  ");
        pdfDatas = new ArrayList<>();
        Collections.synchronizedList(pdfDatas);
        context = this;
        compositeDisposable = new CompositeDisposable();
        initRxAction();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        compositeDisposable.clear();
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    private void initRxAction() {
        compositeDisposable.add(RxBus.getInstance().getEvent(ToServiceAction.class)
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<ToServiceAction>() {
                    @Override
                    public void accept(ToServiceAction serviceAction) throws Exception {
                        if (pdfDatas.isEmpty()) {
                            if (BIZAIAApplication.getInstance().getUserBean() != null) {
                                MonthlyNewestBeanDB newestBeanDB = new MonthlyNewestBeanDB(getBaseContext());
                                List<MonthlyNewestBean> monthlyNewestBeens = newestBeanDB.queryList();
                                for (MonthlyNewestBean newestBean : monthlyNewestBeens) {
                                    if (newestBean.getId() == serviceAction.pdfId) {
                                        pdfDatas.add(newestBean);
                                    }
                                }
                            }
                        } else {
                            for (MonthlyNewestBean monthlyJSData : pdfDatas) {
                                if (monthlyJSData.getId() == serviceAction.pdfId) {
                                    return;
                                }
                            }
                        }
                        shouldDown = serviceAction.shouldDown;

                        if (!isDownloadTask) {
                            isDownloadTask = true;
//                             initNotification(title);
                            AppRetrofit.getAppRetrofit().getClient().dispatcher().cancelAll();
                            getDownloadTask();
                        }
                    }
                }));

        compositeDisposable.add(RxBus.getInstance().getEvent(LoadPDFAction.class)
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<LoadPDFAction>() {
                    @Override
                    public void accept(LoadPDFAction serviceAction) throws Exception {
                        AppRetrofit.getAppRetrofit().getClient().dispatcher().cancelAll();
                        Log.e(TAG, "accept: --------------------->LoadPDFAction" + serviceAction.filePath);
                        downLoadApp(serviceAction.pdfId
                                , serviceAction.filePath
                                , serviceAction.title + "try" + ".pdf");
                    }
                }));

        compositeDisposable.add(RxBus.getInstance().getEvent(DeleteNotifyServiceAction.class)
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<DeleteNotifyServiceAction>() {
                    @Override
                    public void accept(DeleteNotifyServiceAction serviceAction) throws Exception {
                        for (int i = 0; i < pdfDatas.size(); i++) {
                            if (i == 0) {
                                needDelete = true;
                            } else if (pdfDatas.get(i).getId() == serviceAction.id) {
                                pdfDatas.remove(i);
                            }
                            break;
                        }
                    }
                }));
    }

    private void getDownloadTask() {
        Log.e(TAG, "canService: " + "getDownloadTask");

        if (pdfDatas.isEmpty()) {
            AppRetrofit.getAppRetrofit().removeProgressListener(downloadProListener);
            stopSelf();
            return;
        }
        MonthlyNewestBean pdfData = pdfDatas.get(0);
        initNotification(pdfData.getTitle());
        if (shouldDown) {
            downLoadApp(pdfData.getId()
                    , pdfData.getFileUrl()
                    , pdfData.getTitle() + ".pdf");
        } else {
            downLoadApp(pdfData.getId()
                    , pdfData.getFileUrl()
                    , pdfData.getTitle() + "try" + ".pdf");
        }
    }

    private void downLoadApp(final Long id, String url, final String name) {
        Log.e(TAG, "canService: " + "downLoadApp------------------->" + url);

        currentId = id;
        final String path = FilePathUtils.getDirDownloadPdfImgPath(context);
        if (!new File(path).exists()) {
            return;
        }
        final File pdfFile = new File(path + "/" + name);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection", "keep-alive")
                .build();
        AppRetrofit.getAppRetrofit().getClient().dispatcher().cancelAll();
        AppRetrofit.getAppRetrofit().addProgressListener(downloadProListener)
                .getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e(TAG, "onFailure: ------------>"+e.getMessage() );
                if (e instanceof SocketTimeoutException) {
                    if (retryTimes++ < 5) {
                        getDownloadTask();
                    } else {
                        retryTimes = 0;
                        new MonthlyNewestBeanDB(context).update(id, "");
                        pdfDatas.remove(0);
                        ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.connect_time_out_fail_download) + name);
                        getDownloadTask();

                    }
                } else {
                    new MonthlyNewestBeanDB(context).update(id, "");
                    AppRetrofit.getAppRetrofit().removeProgressListener(downloadProListener);
                    ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.net_error));
                    stopSelf();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    InputStream inputStream = response.body().byteStream();
                    FileOutputStream outputStream = new FileOutputStream(pdfFile);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                        if (needDelete) {
                            break;
                        }
                    }
                    inputStream.close();
                    outputStream.close();

                    if (needDelete) {
                        FileUtils.deleteFile(pdfFile);
                        needDelete = false;
                    }
                    RxBus.getInstance().post(new HasDownloadAction(pdfFile.getAbsolutePath()));
                    if (!pdfDatas.isEmpty())
                        pdfDatas.remove(0);
                    RxBus.getInstance().post(new PDFDownloadUpdateAction(currentId, 100));

                    new MonthlyNewestBeanDB(context).update(id, pdfFile.getAbsolutePath());
                    getDownloadTask();

                } catch (IOException e) {
                    e.printStackTrace();
                    ToastUtils.showInUIThead(getBaseContext(), getString(R.string.download_error));
                    new MonthlyNewestBeanDB(context).update(id, "");
                    pdfDatas.remove(0);
                    getDownloadTask();
                }
            }
        });
    }

    private void downLoadCourseware(final Long id, final String url, final String name) {
        Log.e(TAG, "canService: " + "downLoadApp");

        liveId = id;
        final String path = FilePathUtils.getDirDownloadPdfImgPath(context);
        if (!new File(path).exists()) {
            return;
        }
        final File pdfFile = new File(path + "/" + name);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection", "keep-alive")
                .build();
        AppRetrofit.getAppRetrofit().addProgressListener(downloadProListener)
                .getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                if (e instanceof SocketTimeoutException) {
                    if (retryTimes++ < 5) {
                        downLoadCourseware(id, url, name);
                    } else {
                        retryTimes = 0;
                        ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.connect_time_out_fail_download) + name);
                    }
                } else {
                    AppRetrofit.getAppRetrofit().removeProgressListener(downloadProListener);
                    stopSelf();
                    ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    InputStream inputStream = response.body().byteStream();
                    FileOutputStream outputStream = new FileOutputStream(pdfFile);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                        if (needDelete) {
                            break;
                        }
                    }
                    inputStream.close();
                    outputStream.close();

                    if (needDelete) {
                        FileUtils.deleteFile(pdfFile);
                        needDelete = false;
                    }
                    Log.e(TAG, "onResponse: LoadSuccessPDFAction ---------》" + pdfFile.getAbsolutePath());
                    RxBus.getInstance().post(new LoadSuccessPDFAction(pdfFile.getAbsolutePath()));
                    if (BIZAIAApplication.getInstance().getUserBean() != null) {
                        CoursewareBean coursewareBean = new CoursewareBean();
                        coursewareBean.setLiveId(id);
                        coursewareBean.setUserId(BIZAIAApplication.getInstance().getUserBean().getId());
                        coursewareBean.setPdfUrl(pdfFile.getAbsolutePath());
                        new CoursewareBeanDB(context).insert(coursewareBean);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showInUIThead(getBaseContext(), getString(R.string.download_error));
                    new CoursewareBeanDB(context).delete(id, BIZAIAApplication.getInstance().getUserBean().getId());
                    downLoadCourseware(id, url, name);
                }
            }
        });
    }

    /**
     * 初始化Notification通知
     */
    public void initNotification(String title) {
        Intent intent = new Intent(this, MonthlyPdfActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentText("0%")
                .setContentTitle(title)
                .setSubText("0%")
                .setContentIntent(pIntent)
                .setProgress(100, 0, false);

        startForeground(1, builder.build());
    }

    /**
     * 更新通知
     */
    public void updateNotification(long progress) {
        // builder.setContentText(progress + "%");
        if (builder != null) {
            builder.setSubText(progress + "%");
            builder.setProgress(100, (int) progress, false);

            startForeground(1, builder.build());
        }
    }

    private DownloadProListener downloadProListener = new DownloadProListener() {
        @Override
        public void onProgress(long currentLength, long totalLength) {
            try {
                downLoadResult = (double) currentLength / (double) totalLength * 100;

                if (System.currentTimeMillis() - currentTime > 1000) {
                    RxBus.getInstance().post(new PDFDownloadUpdateAction(currentId, downLoadResult));
                    updateNotification((int) (downLoadResult));
                    currentTime = System.currentTimeMillis();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}