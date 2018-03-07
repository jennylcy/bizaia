package com.bizaia.zhongyin.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.live.imhelper.IMHelper;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.util.ToastUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/2/8.
 */

public abstract class ResumeFragment extends BaseFragment {
    private static final String TAG = "ResumeFragment";
    private boolean isRootViewSet;
    protected boolean isLazyLoad;
    protected boolean isCreateActivity = false;
    private Bundle reLoadBundle;
    private View rootView;
    private static boolean isEdit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = createView(inflater, container, savedInstanceState);
            isRootViewSet = false;

        } else {
            if (rootView.getParent() != null) {
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            }
        }
        return rootView;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (!isRootViewSet) {
            isRootViewSet = true;
            activityCreated();
        }
        isCreateActivity = true;

        super.onActivityCreated(savedInstanceState);
    }


    public void activityCreated() {

    }

    @Override
    protected void onReloadArguments(Bundle bundle) {
        super.onReloadArguments(bundle);
        Log.e(TAG, "onReloadArguments: ");
        reLoadBundle = bundle;
        isLazyLoad = bundle.getBoolean("isLazyLoad", true);
    }

    @Override
    protected void onSaveArguments(Bundle bundle) {
        super.onSaveArguments(bundle);
        bundle.putBoolean("isLazyLoad", isLazyLoad);
    }

    protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.e(TAG, "setUserVisibleHint: " + Thread.currentThread().getName());
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isLazyLoad) {
            isLazyLoad = true;
            getDisposable();
        }
    }

    private void getDisposable() {
        addSubscription(Observable.timer(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (isCreateActivity) {
                            onLoadLazy(reLoadBundle);
                            return;
                        }
                        getDisposable();
                    }
                }));
    }

    public void reLogin(){
        ToastUtils.show(getActivity(), R.string.app_logout_other);
        BIZAIAApplication.getInstance().setToken("");
        BIZAIAApplication.getInstance().setUserBean(null);
        IMHelper.getInstance().imLoginout();
        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    protected void onLoadLazy(Bundle reLoadBundle) {

    }

    public void showNum(){

    }

    public void eidtAll(boolean edit){
        if(isEdit){
            isEdit =edit;
        }else{
            isEdit =edit;
        }
    }

    public static boolean isEdit() {
        return isEdit;
    }
}
