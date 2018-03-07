package com.bizaia.zhongyin.module.discovery.ui.chair;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.ui.CommonWebView;
import com.bizaia.zhongyin.module.discovery.api.BuyStateAPI;
import com.bizaia.zhongyin.module.discovery.api.IsCollectionAPI;
import com.bizaia.zhongyin.module.discovery.iml.ICollectionView;
import com.bizaia.zhongyin.module.discovery.iml.IsBuyView;
import com.bizaia.zhongyin.module.live.action.LoginSuccessAction;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.module.mine.action.CancelCollectAction;
import com.bizaia.zhongyin.module.mine.api.IsAttentionAPI;
import com.bizaia.zhongyin.module.mine.api.LectureresAPI;
import com.bizaia.zhongyin.module.mine.data.LectureresBean;
import com.bizaia.zhongyin.module.mine.data.LectureresDetail;
import com.bizaia.zhongyin.module.mine.iml.IAttentionView;
import com.bizaia.zhongyin.module.mine.iml.ILectureresView;
import com.bizaia.zhongyin.module.mine.ui.CompanyHostActivity;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.ShareDialog;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/4/17.
 */

public class DetailActivity extends CommonWebView implements IAttentionView, ICollectionView, ILectureresView, IsBuyView {
    private static final String TAG = "DetailActivity";
    protected long id;
    private long orgId;
    private int type;
    private IsAttentionAPI isAttentionAPI;
    private IsCollectionAPI isCollectionAPI;
    private volatile boolean isCollection;
    private volatile boolean isAttention;

    //
    private String title;
    private String des;
    private String coverUrl;
    private String h5Url;
    public  boolean isBuy;
    private LectureresDetail lectureresDetail;
    private LectureresAPI lectureresAPI;
    private BuyStateAPI buyStateAPI;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getLongExtra("id", 0L);
        type = getIntent().getIntExtra("type", 1);
        orgId = getIntent().getLongExtra("orgId", 0);

        title = getIntent().getStringExtra("title");
        des = getIntent().getStringExtra("des");
        coverUrl = getIntent().getStringExtra("cover");
        h5Url = getIntent().getStringExtra("url");

        Log.e(TAG, "attention: ------------------->"+orgId);

        isAttentionAPI = new IsAttentionAPI(this);
        isCollectionAPI = new IsCollectionAPI(this);
        buyStateAPI = new BuyStateAPI(this);
        lectureresAPI= new LectureresAPI(this);
        lectureresAPI.getDetail(id);

        isAttentionAPI.isAttention(orgId);
        isCollectionAPI.isCollection(id, type);
        buyStateAPI.isBuy(id,type);

        addSubscription(RxBus.getInstance().getEvent(LoginSuccessAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginSuccessAction>() {
                    @Override
                    public void onNext(LoginSuccessAction value) {
                        buyStateAPI.isBuy(id,type);
                        isAttentionAPI.isAttention(orgId);
                        isCollectionAPI.isCollection(id, type);
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));

    }

    @Override
    protected void stateInit(WebView webView) {
        if (isCollection) {
            web.loadUrl("javascript:collectSuccess()");
        }
        if (isAttention) {
            web.loadUrl("javascript:attentionSuccess()");
        }
        if(isBuy){
            web.loadUrl("javascript:paySuccess()");
        }
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        final String message = consoleMessage.message();
        addSubscription(Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "shouldOverrideUrlLoading: ------->"+ message);
                if (message.contains("jsbridge://Home?params=backHome")) {
                    e.onNext(1);
                } else if (message.contains("jsbridge://attention?params=quxiaoGZ")) {
                    if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()))
                    e.onNext(2);
                    else
                        startActivity(new Intent(DetailActivity.this, LoginActivity.class));
                } else if (message.contains("jsbridge://attention?params=GZ")) {
                    if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()))
                    e.onNext(3);
                    else
                        startActivity(new Intent(DetailActivity.this, LoginActivity.class));
                } else if (message.contains("jsbridge://collect?params=quxiaoSC")) {
                    if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()))
                    e.onNext(4);
                    else
                        startActivity(new Intent(DetailActivity.this, LoginActivity.class));
                } else if (message.contains("jsbridge://collect?params=SC")) {
                    if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()))
                    e.onNext(5);
                    else
                        startActivity(new Intent(DetailActivity.this, LoginActivity.class));
                } else if (message.contains("jsbridge://pay?params=YY")) {
                    if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()))
                    e.onNext(6);
                    else
                        startActivity(new Intent(DetailActivity.this, LoginActivity.class));
                } else if (message.contains("jsbridge://share?params=toShare")) {
//                    if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()))
                    e.onNext(7);
//                    else
//                        startActivity(new Intent(DetailActivity.this, LoginActivity.class));
                }else if(message.contains("jsbridge://sendUrl?params=")){
                    Log.e(TAG, "shouldOverrideUrlLoading: ------->"+ message);
                    String [] params=message.split("=");
                    if( params.length>1) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(params[1]));
                        startActivity(intent);
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Integer>() {
                    public void onNext(Integer value) {
                        switch (value) {
                            case 1:
                                onHomeCallBack();
                                break;
                            case 2:
                                attention(false, true, false);
                                break;
                            case 3:
                                attention(true, true, false);
                                break;
                            case 4:
                                collection(false, true, false);
                                break;
                            case 5:
                                collection(true, true, false);
                                break;
                            case 6:
                                onPay();
                                break;
                            case 7:
                                onShare();
                                break;
                        }
                    }

                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    public void onComplete() {
                    }
                }));
        return false;
    }

    protected void onPay() {

    }

    private void onShare() {
        if(lectureresDetail!=null&&!StringUtils.isEmpty(lectureresDetail.getData().getShareUrl()))
            ShareDialog.share(DetailActivity.this, title,
                    des, AddressConfiguration.MAIN_PATH+coverUrl,
                    lectureresDetail.getData().getShareUrl());
    }

    private void onHomeCallBack() {
        Intent intent = new Intent(DetailActivity.this, CompanyHostActivity.class);
        intent.putExtra("orgId", orgId);
        startActivity(intent);
        Log.e(TAG, "home: ");
    }

    @Override
    public void onRelogin() {
        reLogin();
    }

    @Override
    public void showIsAttention(boolean isAttention) {
        Log.e(TAG, "showIsAttention: ----------------->"+isAttention );
        attention(isAttention, false, true);
        if (isAttention) {
            web.loadUrl("javascript:attentionSuccess()");
        }
    }

    @Override
    public void showAddAttention(boolean isAttention) {
        if(lectureresDetail!=null&&!StringUtils.isEmpty(lectureresDetail.getData().getOrgName()))
        ToastUtils.show(this, lectureresDetail.getData().getOrgName()+getString(R.string.live_attention));
        attention(true, false, true);
    }

    @Override
    public void showDelAttention(boolean isAttention) {
        if(lectureresDetail!=null&&!StringUtils.isEmpty(lectureresDetail.getData().getOrgName()))
        ToastUtils.show(this, lectureresDetail.getData().getOrgName()+getString(R.string.live_dis_attention));
        attention(false, false, true);
    }

    @Override
    public void showAttentionError() {

    }

    @Override
    public void showIsCollection(boolean isAttention) {
        Log.e(TAG, "showIsCollection: " + isAttention);
        collection(isAttention, false, true);
        if (isAttention) {
            web.loadUrl("javascript:attentionSuccess()");
        }
    }

    @Override
    public void showAddCollection(boolean isAttention) {
        ToastUtils.show(DetailActivity.this, R.string.live_collection);
        collection(true, false, true);
    }

    @Override
    public void showDelCollection(boolean isAttention) {
        ToastUtils.show(DetailActivity.this, R.string.live_dis_collection);
        collection(false, false, true);
        RxBus.getInstance().post(new CancelCollectAction());
    }

    public void collection(boolean isAttention, boolean withCallBack, boolean withSetting) {
        this.isCollection = isAttention;
        Log.e(TAG, "collection: " + isAttention + "      " + withSetting);
        if (isAttention && withSetting) {
            loadJs("javascript:collectSuccess()");
        } else if (withSetting) {
            loadJs("javascript:collectError()");
        }

        if (!withCallBack) return;

        if (isCollection) {
            isCollectionAPI.addCollection(id, 5, 1);
        } else {
            isCollectionAPI.addCollection(id, 5, 2);
        }
    }

    public void attention(boolean isAttention, boolean withCallBack, boolean withSetting) {
        Log.e(TAG, "attention: -----------isAttention-------->"+ isAttention+"---------"+withSetting);
        this.isAttention = isAttention;
        if (isAttention && withSetting) {
            loadJs("javascript:attentionSuccess()");
            Log.e(TAG, "attention:Success " + isAttention);
        } else if (withSetting) {
            loadJs("javascript:attentionError()");
            Log.e(TAG, "attention:Error " + isAttention);
        }
        if (!withCallBack) return;

        if (isAttention)
            isAttentionAPI.addAttention(orgId, 1);
        else
            isAttentionAPI.addAttention(orgId, 2);
    }

    @Override
    public void showCollectionError() {

    }

    @Override
    public void showLectureres(LectureresBean data) {

    }

    @Override
    public void showLecturereDetail(LectureresDetail data) {
        lectureresDetail = data;
    }

    @Override
    public void showLectureresError() {

    }

    @Override
    public void showIsBuy(boolean isBuy) {
        this.isBuy = isBuy;
        if(isBuy){
            web.loadUrl("javascript:paySuccess()");
        }
    }

    @Override
    public void showStateError() {

    }

}
