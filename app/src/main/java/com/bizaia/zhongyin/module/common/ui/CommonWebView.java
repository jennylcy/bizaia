package com.bizaia.zhongyin.module.common.ui;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
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

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yan on 2017/4/17.
 */

public class CommonWebView extends BaseActivity {
    private static final String TAG = "CommonWebView";

    @BindView(R.id.web)
    protected WebView web;
    @BindView(R.id.pb_web_progress)
    ProgressBar progressBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.content_view)
    View content_view;

    public String title;
    public String url;
    public String imgUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_common);
        ButterKnife.bind(this);
        setViewParent(content_view);
        init();
    }

    private void init() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        imgUrl = getIntent().getStringExtra("imgUrl");
        Log.e(TAG, "init: " + url);
        tvTitle.setText(title);
        webViewInit();
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

                    @SuppressLint("NewApi")
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        return super.shouldOverrideUrlLoading(view, request);
                    }

                    @Override
                    public void onPageFinished(final WebView view, String url) {
                        Log.e(TAG, "onPageFinished: "  );
                        web.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                stateInit(view);
                            }
                        },500);

                        if (!TextUtils.isEmpty(loadUrl(view))) {
                            view.loadUrl(loadUrl(view));
                        }
                    }
                });
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return CommonWebView.this.onConsoleMessage(consoleMessage) || super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                if (message.contains("jsbridge://")) {
                    result.confirm();
                    return true;
                }
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                if (message != null && defaultValue != null && defaultValue.contains("jsbridge://")) {
                    result.confirm();
                    return true;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
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
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        web.loadUrl(url);
        jsInit(web);
    }



    public void loadJs(String js) {
        web.loadUrl(js);
    }

    protected boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return false;
    }

    protected void stateInit(WebView webView) {

    }

    protected String loadUrl(WebView view) {
        return "";
    }

    /**
     * return interface name
     *
     * @param web
     */
    protected void jsInit(WebView web) {

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

    @OnClick({R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
