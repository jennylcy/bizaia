package com.bizaia.zhongyin;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.widget.FrameLayout;

import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.base.ResumeFragment;
import com.bizaia.zhongyin.module.email.EmailFragment;
import com.bizaia.zhongyin.module.live.action.LoginSuccessAction;
import com.bizaia.zhongyin.module.live.imhelper.IMHelper;
import com.bizaia.zhongyin.module.live.ui.LiveFragment;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.module.message.aciton.DeleteMsgAction;
import com.bizaia.zhongyin.module.message.api.UnreadAPI;
import com.bizaia.zhongyin.module.message.data.UnreadMsgBean;
import com.bizaia.zhongyin.module.message.iml.IUnreadView;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.mine.data.ImInfo;
import com.bizaia.zhongyin.module.mine.iml.IImInfoView;
import com.bizaia.zhongyin.module.mine.ui.MineFragment;
import com.bizaia.zhongyin.module.monthly.ui.FragmentMonthly;
import com.bizaia.zhongyin.module.video.ui.FragmentVideo;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.NavigationView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

//import com.bizaia.zhongyin.module.message.AWSManager;

public class MainActivity extends BaseActivity implements IImInfoView, MainContract.View, IUnreadView {

    private  final static String TAG = "MainActivity";

    @BindView(R.id.bottom_navigation_bar)
    NavigationView bottomNavigationBar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.fl_center_button)
    FrameLayout centerButton;

    private SparseArray<Fragment> fragmentList;
    private int currentFragmentIndex = 0;

    private MainContract.Presenter presenter;

    private UnreadAPI unreadAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        {
            Locale myLocale = new Locale("jp");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setViewParent(fragmentContainer);
        requestPermission();
        new MainPresent(getBaseContext(), this);
        init();

        reload(savedInstanceState);

//        Log.e(TAG, "onCreate: ------------->"+ TimeUtils.getCurrentTimeZone());
    }

    private void init() {
        presenter.requireSwitchState();
        IMHelper.getInstance().init(getApplicationContext());
        initView();

        unreadAPI = new UnreadAPI(this);
        unreadAPI.getUnread();
        addSubscription(RxBus.getInstance().getEvent(DeleteMsgAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<DeleteMsgAction>() {
                    @Override
                    public void onNext(DeleteMsgAction value) {
                        if(unreadAPI!=null)
                        unreadAPI.getUnread();
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));
        addSubscription(RxBus.getInstance().getEvent(ForceOffLine.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ForceOffLine>() {
                    @Override
                    public void onNext(ForceOffLine value) {
                        BIZAIAApplication.getInstance().setToken("");
                        BIZAIAApplication.getInstance().setUserBean(null);
                        IMHelper.getInstance().imLoginout();

                        BIZAIAApplication.getInstance().setMsgNum(0);
                        ToastUtils.show(MainActivity.this,"アカウントは別のデバイスに登録されました");

                        if(currentFragmentIndex==4){
                            RxBus.getInstance().post(new LoginSuccessAction(false));
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));


    }

    private void reload(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            int index = savedInstanceState.getInt("currentFragmentIndex", 0);
            fragmentShow(index);
            bottomNavigationBar.setSelect(index);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentFragmentIndex", currentFragmentIndex);
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void initAWS() {
        try {
//            AWSManager.install(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestPermission() {
        RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
        rxPermissions.request(android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.MODIFY_AUDIO_SETTINGS,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.WAKE_LOCK,
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.READ_CALENDAR,
                android.Manifest.permission.WRITE_CALENDAR
        )
                .subscribe(
                        new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean granted) throws Exception {
                                if (granted) {
//                                    BIZAIAApplication.getInstance().setMediaManager(new MediaManager(getApplication()));
//                                    StreamingEnv.init(getApplicationContext());
                                } else {

                                }
                            }
                        });
    }

    private void initView() {
        initNavigationBar();
        initFragment();
        bottomNavigationBar.setSelect(0);
        fragmentShow(0);
    }

    private void addFragment(FragmentTransaction transaction, String tag, int index) {
        if (getSupportFragmentManager().findFragmentByTag(tag) != null) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            fragmentList.put(index, fragment);
            if (!fragment.isAdded()) {
                transaction.add(R.id.fragment_container, fragmentList.get(index), tag);
            }
        } else {
            fragmentList.put(index, newFragment(index));
            transaction.add(R.id.fragment_container, fragmentList.get(index), tag);
        }
        transaction.hide(fragmentList.get(index));

    }

    private Fragment newFragment(int index) {
        Fragment fragment = null;
        switch (index) {
//            case 0:
//                fragment = FragmentDiscovery.newInstance();
//                break;
            case 0:
                fragment = FragmentVideo.newInstance();
                break;
            case 1:
                fragment = LiveFragment.newInstance();
                break;
            case 2:
                fragment = EmailFragment.newInstance();
                break;
            case 3:
                fragment = FragmentMonthly.newInstance();
                break;
            case 4:
                fragment = MineFragment.newInstance();
                break;
        }
        return fragment;
    }


    private void initFragment() {
        fragmentList = new SparseArray<>();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        addFragment(fragmentTransaction, "first", 0);
        addFragment(fragmentTransaction, "two", 1);
        addFragment(fragmentTransaction, "three", 2);
        addFragment(fragmentTransaction, "four", 3);
        addFragment(fragmentTransaction, "five", 4);
        fragmentTransaction.show(fragmentList.get(currentFragmentIndex));
        fragmentTransaction.commit();
    }

    private void initNavigationBar() {
        bottomNavigationBar.setOnItemClickListener(onItemClickListener);
        bottomNavigationBar.setSelect(0);
    }

    private NavigationView.OnItemClickListener onItemClickListener = new NavigationView.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            fragmentShow(position);

            switch (position) {
                case 0:
                    unreadAPI.getUnread();
                    break;
                case 1:
                    unreadAPI.getUnread();
                    break;
                case 2:
                    unreadAPI.getUnread();
                    break;
                case 3:
                    unreadAPI.getUnread();
                    break;

            }
        }
    };

    private void fragmentShow(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragmentIndex != position) {
            transaction.hide(fragmentList.get(currentFragmentIndex))
                    .show(fragmentList.get(position))
                    .commit();
            currentFragmentIndex = position;
        }

        if (position == 3 && fragmentList.get(position) instanceof MineFragment) {
            ((MineFragment) fragmentList.get(position)).requestInfo();
        }
        ((ResumeFragment) fragmentList.get(position)).showNum();
    }

    private long lastBackPressedTime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastBackPressedTime < 1000) {
            IMHelper.getInstance().imLoginout();
            if(BIZAIAApplication.getInstance().isIgnore())
                BIZAIAApplication.getInstance().setIgnore(false);
            super.onBackPressed();
        } else {
            lastBackPressedTime = System.currentTimeMillis();
            ToastUtils.show(getApplicationContext(), R.string.more_press_exit);
        }
    }

    @Override
    public void showImInfo(ImInfo data) {

    }

    @Override
    public void showImInfoError() {

    }

    @Override
    public void setSwitchState(boolean isPushOpen) {
        if (presenter.getPushState() || isPushOpen) {
//            initAWS();
        }
    }

    private boolean checkPlayServicesUseAble() throws Exception {
        boolean googleServiceAble = true;

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this,
                        resultCode, 2404).show();
            }
            googleServiceAble = false;
        }

        return googleServiceAble;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showUnreadNum(UnreadMsgBean data) {
        BIZAIAApplication.getInstance().setMsgNum((int) data.getData());
    }

    @Override
    public void showUnreadNumError(int code, String msg) {

    }
}
