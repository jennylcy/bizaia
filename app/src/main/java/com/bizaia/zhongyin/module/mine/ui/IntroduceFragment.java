package com.bizaia.zhongyin.module.mine.ui;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.ResumeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zyz on 2017/3/9.
 */

@SuppressLint("ValidFragment")
public class IntroduceFragment extends ResumeFragment {


    private final String TAG = "IntroduceFragment.this";
    @BindView(R.id.web)
      WebView web;
//    @BindView(R.id.pb_web_progress)
//    ProgressBar progressBar;
//    @BindView(R.id.rivCompany)
//    RoundedImageView rivCompany;
//    @BindView(R.id.tvCompanyName)
//    TextView tvCompanyName;
//    @BindView(R.id.wvIntroduction)
//    TextView wvIntroduction;
//    @BindView(R.id.rvTeacher)
//    RecyclerView rvTeacher;
//    @BindView(R.id.ivvPlayer)
//    IjkVideoView ivvPlayer;
//    @BindView(R.id.linBg)
//    LinearLayout linBg;
//    @BindView(R.id.ivState)
//    ImageView ivState;
//    @BindView(R.id.relVideo)
//    RelativeLayout relVideo;

//    private LectureresAPI lectureresAPI;
    private String url;
//    private int pageNum;
//    private int pageSize=10;
//    private CompanyHostBean companyHostBean;
//    private CustomAdapter adapter;
//    List<Object> dataList;
//    LoadMoreWrapper moreWrapper;

    public IntroduceFragment(String url){
        this.url = url;
    }

    public IntroduceFragment( ){
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_introduce, container, false);
        ButterKnife.bind(this, view);
        webViewInit();
        return view;
    }

    private void webViewInit() {
//        progressBar.setMax(100);
//        progressBar.setProgressDrawable(
//                ContextCompat.getDrawable(getContext(),
//                        R.drawable.web_progressbar));

        WebSettings webSetting = web.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setPluginState(WebSettings.PluginState.ON);
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
                        return super.shouldOverrideUrlLoading(view, request);
                    }

                    @Override
                    public void onPageFinished(final WebView view, String url) {
//                        if (!TextUtils.isEmpty(loadUrl(view))) {
//                            view.loadUrl(loadUrl(view));
//                        }
                    }
                });
        web.setWebChromeClient(new WebChromeClient() );
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
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

    protected void jsInit(WebView web) {

    }

    protected String loadUrl(WebView view) {
        return "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            web.getClass().getMethod("onPause").invoke(web,(Object[])null);
        }catch (Exception e){
            web.onPause();
            web.pauseTimers();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            web.getClass().getMethod("onResume").invoke(web,(Object[])null);
        }catch (Exception e){
            web.onResume();
            web.resumeTimers();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        web.destroy();
        web = null;
    }

    public boolean isTop(){
        if(web!=null)
            return web.getScaleY()==0;
        return false;
    }
}
