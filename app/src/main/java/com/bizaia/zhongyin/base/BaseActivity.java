package com.bizaia.zhongyin.base;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.live.action.LoginSuccessAction;
import com.bizaia.zhongyin.module.live.imhelper.IMHelper;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.util.NetWorkStateReceiver;
import com.bizaia.zhongyin.util.NotifyBarUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.ForgetPop;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected P mPresenter;
    protected CompositeDisposable mSubscriptions = new CompositeDisposable();
    private NetWorkStateReceiver netWorkStateReceiver;
    private View viewParent;
    private Disposable broadcast;

    private boolean showNetwordDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        replaceFont();
        super.onCreate(savedInstanceState);
        NotifyBarUtils.StatusBarLightMode(this);
        StackActivityManager.getScreenManager().pushActivity(this);
        broadcast = RxBus.getInstance().getEvent(BroadcastAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BroadcastAction>() {
                    @Override
                    public void onNext(BroadcastAction value) {
                        Log.e("BaseActivity", "BroadcastAction: ------------------>" + value.state);
                        showNetWork();
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                });
    }

    protected boolean isNotifyBarLight() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StackActivityManager.getScreenManager().popActivity(this);
        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        Bugtags.onResume(this);
        addSubscription(broadcast);
        netWorkStateReceiver = new NetWorkStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateReceiver, filter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(netWorkStateReceiver);
        removeSub(broadcast);
        super.onPause();
//        Bugtags.onPause(this);
    }

    public void addSubscription(Disposable subscription) {
        mSubscriptions.add(subscription);
    }

    public void removeSub(Disposable subscription) {
        mSubscriptions.remove(subscription);
    }

    @Override
    public void finish() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        super.finish();
    }

    public void reLogin() {
        if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()))
            ToastUtils.show(this, R.string.app_logout_other);
        BIZAIAApplication.getInstance().setToken("");
        BIZAIAApplication.getInstance().setUserBean(null);
        IMHelper.getInstance().imLoginout();
        RxBus.getInstance().post(new LoginSuccessAction(false));
        startActivity(new Intent(this, LoginActivity.class));
    }

    private ForgetPop forgetPop;

    private void showNetWork() {
        if (!showNetwordDialog) {
            if (forgetPop != null)
                forgetPop.dismiss();
            forgetPop = new ForgetPop(getApplicationContext(), viewParent, R.layout.pop_network_state) {
                @Override
                public void viewInit() {
                    TextView start = (TextView) findViewById(R.id.tv_cancel);
                    start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            forgetPop.dismiss();
                            finish();
                        }
                    });
                    TextView cancel = (TextView) findViewById(R.id.tv_sure);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            forgetPop.dismiss();
                            BIZAIAApplication.getInstance().setIgnore(true);

                        }
                    });
                }
            };
            forgetPop.show();
            showNetwordDialog = true;
        }
    }

//    private void replaceFont() {
//        final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/texttype.otf");
//        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
//
//            @Override
//            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//                // TODO Auto-generated method stub
//                AppCompatDelegate delegate = getDelegate();
//                View view = delegate.createView(parent, name, context, attrs);
//                if(view != null ){
//                    if(view instanceof TextView){
//                        ((TextView)view).setTypeface(typeface);
//                    } else if(view instanceof EditText){
//                        ((EditText)view).setTypeface(typeface);
//                    }else if(view instanceof Button){
//                        ((Button)view).setTypeface(typeface);
//                    }else if(view instanceof RadioButton){
//                        ((RadioButton)view).setTypeface(typeface);
//                    }
//                }
//                return view;
//            }
//        });
//    }

    public void setViewParent(View viewParent) {
        this.viewParent = viewParent;
    }

    public void setmPresenter(P mPresenter) {
        this.mPresenter = mPresenter;
    }
}
