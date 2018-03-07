package com.bizaia.zhongyin.module.mine.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgreementActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.pb_web_progress)
    ProgressBar mPbWebProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        ButterKnife.bind(this);

        mPbWebProgress.setMax(100);
        mPbWebProgress.setProgressDrawable(
                ContextCompat.getDrawable(getBaseContext(),
                        R.drawable.web_progressbar));

        WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mPbWebProgress.setProgress(newProgress);
                if (mPbWebProgress != null && newProgress != 100) {
                    mPbWebProgress.setVisibility(View.VISIBLE);
                } else if (mPbWebProgress != null) {
                    mPbWebProgress.setVisibility(View.GONE);
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
        mWebView.loadUrl("https://api.bizaia.com/api/dist/html/agreement.html");
    }

    @OnClick({R.id.back_img})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }
}
