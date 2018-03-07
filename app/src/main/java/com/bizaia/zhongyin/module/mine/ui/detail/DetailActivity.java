package com.bizaia.zhongyin.module.mine.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.discovery.api.INewsDetailAPI;
import com.bizaia.zhongyin.module.mine.api.IsAttentionAPI;
import com.bizaia.zhongyin.module.mine.iml.IAttentionView;
import com.bizaia.zhongyin.module.mine.ui.CompanyHostActivity;
import com.bizaia.zhongyin.module.discovery.api.IsCollectionAPI;
import com.bizaia.zhongyin.module.discovery.data.NewsDetailBean;
import com.bizaia.zhongyin.module.discovery.iml.ICollectionView;
import com.bizaia.zhongyin.module.discovery.iml.INewsDetailView;
import com.bizaia.zhongyin.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yan on 2017/3/7.
 */

public class DetailActivity extends BaseActivity implements ICollectionView, IAttentionView, INewsDetailView {
    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.wvDetail)
    WebView wvDetail;
    @BindView(R.id.ivCall)
    ImageView ivCall;

    private IsCollectionAPI isCollectionAPI;
    private IsAttentionAPI isAttentionAPI;
    private INewsDetailAPI iNewsDetailAPI;

    private boolean isCollection;
    private boolean isAttention;

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        id = getIntent().getLongExtra("id",1);
        ivCall.setVisibility(View.GONE);
        wvDetail.addJavascriptInterface(new WebAppInterface(getApplicationContext()), "messageHandlers");
        wvDetail.setWebViewClient(new WebViewClientImp());
        wvDetail.setWebChromeClient(new WebChromeClientImp());
        WebSettings webSettings = wvDetail.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        isCollectionAPI = new IsCollectionAPI(this);
        isAttentionAPI = new IsAttentionAPI(this);
        iNewsDetailAPI = new INewsDetailAPI(this);
        iNewsDetailAPI.getNewsDetail(id);

    }

    @OnClick({R.id.back_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }

    @Override
    public void showIsCollection(boolean isAttention) {
             isCollection=isAttention;
             mHandler.sendEmptyMessage(CHANGE_COLLECT);
    }

    @Override
    public void showAddCollection(boolean isAttention) {
        isCollection=isAttention;
        mHandler.sendEmptyMessage(CHANGE_COLLECT);
    }

    @Override
    public void showDelCollection(boolean isAttention) {
        isCollection=!isAttention;
        mHandler.sendEmptyMessage(CHANGE_COLLECT);
    }

    @Override
    public void showCollectionError() {

    }

    @Override
    public void showIsAttention(boolean isAttention) {
        this.isAttention=isAttention;
        mHandler.sendEmptyMessage(CHANGE_ATTENTION);
    }

    @Override
    public void showAddAttention(boolean isAttention) {
        this.isAttention=isAttention;
        mHandler.sendEmptyMessage(CHANGE_ATTENTION);
    }

    @Override
    public void showDelAttention(boolean isAttention) {
        this.isAttention=!isAttention;
        mHandler.sendEmptyMessage(CHANGE_ATTENTION);
    }

    @Override
    public void onRelogin() {
        reLogin();
    }

    @Override
    public void showAttentionError() {

    }

    @Override
    public void showNewsDetail(NewsDetailBean newsDetailBean) {
          tvTitle.setText(newsDetailBean.getData().getTitle());
          wvDetail.loadUrl(newsDetailBean.getData().getH5Url());
    }

    @Override
    public void showNewsDetailError() {

    }


    class WebAppInterface {
        Context context;
        public WebAppInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void aaa(String message) {
            if (message != null) {
                ToastUtils.show(DetailActivity.this,message);
            }
        }
    }

    class WebViewClientImp extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null && url.contains("jsbridge://")) {
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            isCollectionAPI.isCollection(id,1);
            isAttentionAPI.isAttention(id);
            super.onPageFinished(view, url);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    class WebChromeClientImp extends WebChromeClient {

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            if (message != null && defaultValue != null && defaultValue.contains("jsbridge://")) {

                result.confirm();// 给js回调，否则会有问题
                return true;
            }
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            String message = consoleMessage.message();
            if (message.contains("jsbridge://collect?params=SC")) {
                mHandler.sendEmptyMessage(COLLECT);
                return true;
            }else if(message.contains("jsbridge://share?params=toShare")){
                mHandler.sendEmptyMessage(SHARE);
                return true;
            }else if(message.contains("jsbridge://attention?params=GZ")){
                mHandler.sendEmptyMessage(ATTENTION);
                return true;
            } else if(message.contains("jsbridge://Home?params=backHome")){
                mHandler.sendEmptyMessage(HOME);
                return true;
            }else if(message.contains("jsbridge://pay?params=YY")){
                mHandler.sendEmptyMessage(BOOK);
                return true;
            }else if(message.contains("jsbridge://collect?params=quxiaoSC")){
                mHandler.sendEmptyMessage(COLLECT);
                return true;
            }else if(message.contains("jsbridge://collect?params=quxiaoGZ")){
                mHandler.sendEmptyMessage(ATTENTION);
                return true;
            }
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            if (message.contains("jsbridge://")) {

                result.confirm();// 给js回调，否则会有问题
                return true;
            }
            return super.onJsAlert(view, url, message, result);
        }
    }

    private final  int ATTENTION=100;
    private final  int SHARE=101;
    private final  int BOOK=102;
    private final  int HOME=103;
    private final  int COLLECT=104;

    private final int CHANGE_COLLECT=105;
    private final int CHANGE_ATTENTION=106;
    private final int CHANGE_PAY=107;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent = null;
            switch (msg.what){
                case ATTENTION:
                    Log.e("logshow","----------------->关注");
                    if(isAttention)
                        isAttentionAPI.addAttention(1,1);
                    else
                        isAttentionAPI.addAttention(1,2);
                    break;
                case SHARE:
                    Log.e("logshow","----------------->分享");
                    break;
                case BOOK:
                    Log.e("logshow","----------------->预约");
                    break;
                case HOME:
                    intent = new Intent(DetailActivity.this, CompanyHostActivity.class);
                    startActivity(intent);
                    break;
                case COLLECT:
                    Log.e("logshow","----------------->收藏");
                    if(isCollection)
                    isCollectionAPI.addCollection(id,2,2);
                    else
                    isCollectionAPI.addCollection(id,2,1);
                    break;
                case CHANGE_COLLECT:
                    if(isCollection)
                        wvDetail.loadUrl("javascript:collectSuccess()");
                    else
                        wvDetail.loadUrl("javascript:collectError()");

                    break;
                case CHANGE_ATTENTION:
                    if(isAttention)
                        wvDetail.loadUrl("javascript:attentionSuccess()");
                    else
                        wvDetail.loadUrl("javascript:attentionError()");
                    break;
                case CHANGE_PAY:
                    if(isAttention)
                        wvDetail.loadUrl("javascript:paySuccess()");
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
