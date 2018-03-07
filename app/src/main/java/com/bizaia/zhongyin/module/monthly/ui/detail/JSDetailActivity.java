package com.bizaia.zhongyin.module.monthly.ui.detail;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.live.action.LoadPDFAction;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.monthly.action.HasDownloadAction;
import com.bizaia.zhongyin.module.monthly.action.ToServiceAction;
import com.bizaia.zhongyin.module.monthly.data.IsNeedToBuyData;
import com.bizaia.zhongyin.module.monthly.data.MonthlyDetail;
import com.bizaia.zhongyin.module.monthly.service.DownLoadService;
import com.bizaia.zhongyin.module.monthly.ui.detail.jscomment.JSDetailCommentActivity;
import com.bizaia.zhongyin.module.monthly.ui.pdfdetail.PDFContract;
import com.bizaia.zhongyin.module.monthly.ui.pdfdetail.PDFPresenter;
import com.bizaia.zhongyin.module.pay.PayActivity;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.repository.data.MonthlyNewestBean;
import com.bizaia.zhongyin.repository.data.MonthlyNewestBeanDB;
import com.bizaia.zhongyin.util.FilePathUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.ServiceUtils;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.ForgetPop;
import com.bizaia.zhongyin.widget.ShareDialog;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/3/9.
 */

public class JSDetailActivity extends BaseActivity implements PDFContract.View {
    private static final String TAG = "JSDetailActivity";

    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.pb_web_progress)
    ProgressBar progressBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_total_count)
    TextView tvTotalCount;
    @BindView(R.id.tv_nick_name)
    TextView nickName;
    @BindView(R.id.content_view)
    View contentView;
    @BindView(R.id.ll_button_layout)
    View ivMassage;

    private String url;
    private String title;
    private String author;
    private String time;
    private String subTile;
    private String image;
    private String fileUrl;
    private boolean isBuy;
    private long id;
    private long monthId;
    private ForgetPop popBuy;
    private double price;
    private String share;
    private boolean shouldDown;

    private PDFContract.Presenter presenter;
    private MonthlyNewestBeanDB newestBeanDB;
    private MonthlyDetail monthlyDetail;

    private boolean hasSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_detail_js);
        ButterKnife.bind(this);
        setViewParent(web);
        init();
    }


    private void init() {
        url = getIntent().getStringExtra("url");
        id = getIntent().getLongExtra("id", 0);
        title = getIntent().getStringExtra("title");
        author = getIntent().getStringExtra("author");
        time = getIntent().getStringExtra("time");
        subTile = getIntent().getStringExtra("subTile");
        image = getIntent().getStringExtra("image");
        monthId = getIntent().getLongExtra("monthId", 0);
        price = getIntent().getDoubleExtra("price", 0);
        share = getIntent().getStringExtra("share");
        fileUrl = getIntent().getStringExtra("filePath");
        popBuy = getPayPop();
        tvTitle.setText(subTile);
        webViewInit();
        tvTotalCount.setText(getIntent().getStringExtra("totalCount"));
        if (BIZAIAApplication.getInstance().getUserBean() != null) {
//            if (!TextUtils.isEmpty(BIZAIAApplication.getInstance().getUserBean().getNickname()))
//                nickName.setText(BIZAIAApplication.getInstance().getUserBean().getNickname() + ":");
//            else {
//                nickName.setText(BIZAIAApplication.getInstance().getUserBean().getId() + ":");
//            }
        }
        Log.e(TAG, "init: --------------------->"+monthId );
        new PDFPresenter(this);
        presenter.isNeedToBuy(monthId);
        presenter.getDetail(id);
        presenter.getMonthlyDetail(monthId);
    }

    private void webViewInit() {
        progressBar.setMax(100);
        progressBar.setProgressDrawable(
                ContextCompat.getDrawable(getBaseContext(),
                        R.drawable.web_progressbar));

        WebSettings webSetting = web.getSettings();
        webSetting.setJavaScriptEnabled(true);
        web.setWebViewClient(
                new WebViewClient() {
                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                        super.onReceivedError(view, request, error);
                    }

                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        super.onReceivedSslError(view, handler, error);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        Map<String, String> headerMap = new HashMap<>();
                        headerMap.put("token", ((BIZAIAApplication) getApplication()).getToken());
                        view.loadUrl(url, headerMap);
                        return true;
                    }

                    @Override
                    public void onPageFinished(final WebView view, String url) {
                        if(isBuy)
                        web.loadUrl("javascript:buy()");
                    }
                });
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                String message = consoleMessage.message();
                Log.e(TAG, "onConsoleMessage: " + message.contains("jsbridge://notepad?params=GM"));
                if (message.contains("jsbridge://notepad?params=GM")) {
                    return true;
                }
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                result.confirm();
                Log.e(TAG, "onJsAlert: -------》" + monthId);
                if (!isBuy)
                    presenter.isNeedToBuy(monthId);
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (progressBar != null && newProgress != 100) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(false);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        web.loadUrl(url);
        ivMassage.setVisibility(View.VISIBLE);
        newestBeanDB = new MonthlyNewestBeanDB(getBaseContext());
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

        addSubscription(RxBus.getInstance().getEvent(HasDownloadAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<HasDownloadAction>() {
                    @Override
                    public void onNext(HasDownloadAction value) {
                        String path = FilePathUtils.getDirDownloadPdfImgPath(getApplicationContext());
                        File pdfFile = new File(path + "/" + title + ".pdf");
                        if(isBuy&&pdfFile.exists()) {
                            hasSave = true;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected boolean isNotifyBarLight() {
        return false;
    }

    @OnClick({R.id.iv_back_btn, R.id.iv_down_load, R.id.iv_massage, R.id.ivShare})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_btn:
                finish();
                break;
            case R.id.iv_down_load:
                if (StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    if(hasSave) {
                        ToastUtils.show(JSDetailActivity.this, R.string.month_has_down);
                        return;
                    }
                    if (isBuy) {
                        shouldDown = true;
                        ToastUtils.show(JSDetailActivity.this, R.string.month_create);
                        download();
                    } else {
                        popBuy.show();
                    }
                }

                break;
            case R.id.iv_massage:
                startActivity(new Intent(getBaseContext(), JSDetailCommentActivity.class)
                        .putExtra("id", id)
                        .putExtra("title", title)
                        .putExtra("author", author)
                        .putExtra("time", time)
                        .putExtra("subTile", subTile)
                );
                break;
            case R.id.ivShare:
                if(monthlyDetail!=null)
                ShareDialog.share(this, title, monthlyDetail.getData().getMonthly().getDescription(),
                        AddressConfiguration.MAIN_PATH + image,
                        share);
                break;
        }
    }

    private Disposable isRunningDisposable;
    private String download() {
        Log.e(TAG, "download: _________________>" );
        if (BIZAIAApplication.getInstance().getUserBean() != null) {
            List<MonthlyNewestBean> monthlyNewestBeens = newestBeanDB.queryList();
            for (MonthlyNewestBean newestBean : monthlyNewestBeens) {
                if (newestBean.getId() == monthId) {
                    if (!TextUtils.isEmpty(newestBean.getDownloadPath())) {
                        return newestBean.getDownloadPath();
                    }
                    newestBeanDB.delete(newestBean.getId());
                    break;
                }
            }
        }else{
            return null;
        }
        // add data to database
        if (BIZAIAApplication.getInstance().getUserBean() != null
                &&shouldDown
                && monthlyDetail!=null
                &&monthlyDetail.getData().getChapterList()!=null) {
            if (!fileUrl.contains(AddressConfiguration.MAIN_PATH)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(AddressConfiguration.MAIN_PATH)
                        .append("download/pdf/1/")
                        .append(monthId)
                        .append("/")
                        .append(fileUrl);
                fileUrl = stringBuilder.toString();
            }
            newestBeanDB.insert(new MonthlyNewestBean(
                    monthId
                    , title
                    , image
                    , fileUrl
                    , price
                    , isBuy
                    , ""
                    , null
                    , monthlyDetail.getData().getChapterList().get(0).getTitle()
                    , monthlyDetail.getData().getChapterList().size() > 1 ? monthlyDetail.getData().getChapterList().get(1).getTitle() : "",
                    BIZAIAApplication.getInstance().getUserBean().getId() + "",
                    url
            ));
        }

        if (!ServiceUtils.isRunning(getBaseContext(), DownLoadService.class)) {
            Intent intent = new Intent(this, DownLoadService.class);
            intent.putExtra("isBuy",isBuy);
            startService(intent);
            isRunningDisposable = Observable.interval(200, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread()).subscribe(
                    new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            Log.e(TAG, "canService: " + "post");
                            if (shouldDown) {
                                Log.e(TAG, "starDownloadService: " + "111111111111111111111111111");
                                RxBus.getInstance().post(new ToServiceAction(monthId, shouldDown));
                            } else {
                                Log.e(TAG, "starDownloadService: " + "2222222222222222222222222");
                                RxBus.getInstance().post(new LoadPDFAction(monthId, title,
                                        fileUrl));
                            }
                            isRunningDisposable.dispose();
                        }
                    },
                    new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                        }
                    });
            addSubscription(isRunningDisposable);
        } else {
            RxBus.getInstance().post(new ToServiceAction(monthId, true));
        }
        return null;
    }

    private ForgetPop getPayPop() {
        return new ForgetPop(getBaseContext(), contentView, R.layout.pop_pay) {
            @Override
            public void viewInit() {
                getView(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popBuy.dismiss();
                    }
                });
                ((TextView)findViewById(R.id.content)).setText(R.string.monthly_see_tips);
                getView(R.id.dialog_pay_continue).setOnClickListener(popOnClickListener);
                getView(R.id.dialog_pay).setOnClickListener(popOnClickListener);
            }
        };
    }

    View.OnClickListener popOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_pay_continue:
                    popBuy.dismiss();
                    break;
                case R.id.dialog_pay:
                    popBuy.dismiss();
                    if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                        startActivity(new Intent(getBaseContext(), PayActivity.class)
                                .putExtra("price", price)
                                .putExtra("id", monthId)
                                .putExtra("title", getString(R.string.monthly_pdf_buy))
                                .putExtra("type", 4)
                        );
                    } else {
                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    }
                    break;
            }
        }
    };

    @Override
    public void setPresenter(PDFContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setIsNeedToBuy(IsNeedToBuyData isNeedToBuy) {
        if (isNeedToBuy.getData() != null
                && isNeedToBuy.getData().isIsBuy()) {
            isBuy = true;
            ivMassage.setVisibility(View.VISIBLE);
            String path = FilePathUtils.getDirDownloadPdfImgPath(this);
            File pdfFile = new File(path + "/" + title + ".pdf");
            if(isBuy&&pdfFile.exists()) {
                hasSave = true;
            }
            Log.e(TAG, "setIsNeedToBuy: _________has  save________>" +hasSave+"-----"+title+"--------"+pdfFile.exists());
//            web.loadUrl(url + ((isBuy) ? "&buyId=60002" : ""));
            return;
        } else if (isNeedToBuy.getData() != null
                && !isNeedToBuy.getData().isIsBuy()) {
            if (popBuy != null && !popBuy.isShowing())
                popBuy.show();
        }

    }

    @Override
    public void setOrderData(OrderData orderData) {
        startActivity(new Intent(this, PayActivity.class)
                .putExtra("price", price)
                .putExtra("order", orderData));
    }

    @Override
    public void reLogin() {
        super.reLogin();
        this.finish();
    }

    @Override
    public void error(String msg) {
        ToastUtils.showInUIThead(getBaseContext(), msg);
    }

    @Override
    public void setJSDetail() {

    }

    @Override
    public void setMonthlyDetail(MonthlyDetail detail) {
        monthlyDetail = detail;
    }

    @Override
    public void netError() {
        ToastUtils.showInUIThead(getBaseContext(), getString(R.string.net_error));
    }
}
