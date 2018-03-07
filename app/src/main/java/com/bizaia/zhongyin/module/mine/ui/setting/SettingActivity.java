package com.bizaia.zhongyin.module.mine.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.mine.ui.UpdateActivity;
import com.bizaia.zhongyin.module.mine.ui.setting.advice.AdviceActivity;
import com.bizaia.zhongyin.module.mine.ui.setting.area.AreaActivity;
import com.bizaia.zhongyin.module.mine.ui.setting.push.PushContract;
import com.bizaia.zhongyin.module.mine.ui.setting.push.PushPresenter;
import com.bizaia.zhongyin.repository.SPConfiguration;
import com.bizaia.zhongyin.repository.data.MonthlyNewestBeanDB;
import com.bizaia.zhongyin.util.ACache;
import com.bizaia.zhongyin.util.AppUtils;
import com.bizaia.zhongyin.util.CleanUtils;
import com.bizaia.zhongyin.util.SPUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.ForgetPop;
import com.bizaia.zhongyin.widget.ShareDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/3/17.
 */

public class SettingActivity extends BaseActivity implements PushContract.View {
    private static final String TAG = "SettingActivity";
    @BindView(R.id.ll_btn_container)
    LinearLayout llBtnContainer;
    @BindView(R.id.iv_push)
    ImageView ivPush;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.tv_area_data)
    TextView tvAreaData;
    @BindView(R.id.iv_update)
    ImageView ivUpdate;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    private PushContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_setting);
        ButterKnife.bind(this);
        setViewParent(llBtnContainer);
        init();
        if (BIZAIAApplication.getInstance().isAllArea()) {
            tvAreaData.setText(R.string.select_all);
        }
        presenter = new PushPresenter(getBaseContext(), this);
        tvVersion.setText(AppUtils.getAppVersionName(getApplicationContext()));
    }

    private void init() {
        initView();
        initBtnClick();
        UpdateActivity.checkUpdate(getApplicationContext(), new UpdateActivity.IUpdateBack() {
            @Override
            public void onUpdateBack(UpdateActivity.UpdateInfo updateInfo) {
                SettingActivity.this.updateInfo = updateInfo;
                if (AppUtils.getAppVersionCode(getApplicationContext()) < updateInfo.getVersion()) {
                    ivUpdate.setVisibility(View.VISIBLE);
                }
            }
        }, mSubscriptions);
    }

    UpdateActivity.UpdateInfo updateInfo;

    private void initView() {
        boolean isPushOn = SPUtils.getBoolean(getBaseContext(), MODE_PRIVATE
                , SPConfiguration.APP_NAME
                , SPConfiguration.SETTING_PUSH_SWITCH
                , true);
        ivPush.setSelected(isPushOn);

        setAreaData();
        setCacheSize();
    }

    private void setAreaData() {
        tvAreaData.setText("");
        String countyAndAreas = SPUtils.getString(getBaseContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.AREA_STR_DATA);

        if (BIZAIAApplication.getInstance().isAllArea())
            tvAreaData.setText(R.string.select_all);
        else {
            if (!TextUtils.isEmpty(countyAndAreas) && !"{}".equals(countyAndAreas)) {
                Map<String, String> mapAreaData = new Gson().fromJson(countyAndAreas, new TypeToken<Map<String, String>>() {
                }.getType());
                for (Map.Entry<String, String> entry : mapAreaData.entrySet()) {
                    tvAreaData.append(entry.getValue());
                    tvAreaData.append("•");
                    tvAreaData.append(entry.getKey() + " ");
                }
            } else {
                tvAreaData.setText(R.string.select_all);
            }
        }
    }

    private void initBtnClick() {
        for (int i = 0; i < llBtnContainer.getChildCount(); i++) {
            View container = llBtnContainer.getChildAt(i);
            container.setTag(i);
            container.setOnClickListener(onClickListener);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int index = (int) view.getTag();
            Log.e(TAG, "onClick:index = " + index);

            switch (index) {
                case 0:
                    startActivityForResult(new Intent(getApplicationContext(), AreaActivity.class), 100);
                    break;
                case 1:
                    triggerSwitch();
                    break;
                case 2:
                    showRemindPop();
                    break;
                case 3:
                    UpdateActivity.downloadApk(mSubscriptions, getBaseContext(), updateInfo);
                    break;
                case 4:
                    ShareDialog.share(SettingActivity.this, "BizAiA!（ビザイア）",
                            "BizAiAはアジア在住日本人向けのビジネス講座、ビジネスイベント掲載、ライブ講座、インタビュー、対談動画をメインにしたビジネス情報アプリです。",
                            "https://api.bizaia.com/api/share/img/slogo@2x.png",
                            "https://api.bizaia.com/api/share/html/share.html");
                    break;
                case 5:
                    startActivity(new Intent(getApplicationContext(), AdviceActivity.class));
                    break;
                case 6:
                    startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                    break;
            }
        }
    };

    private void setCacheSize() {
        addSubscription(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(CleanUtils.getFormatSize(CleanUtils.getFolderSize(getBaseContext().getExternalFilesDir(null)) +
                        CleanUtils.getFolderSize(getBaseContext().getExternalCacheDir())
                ));
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String value) {
                        tvCache.setText(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    private void clearCache() {
        addSubscription(Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                CleanUtils.cleanCustomCache(getBaseContext().getExternalCacheDir());
                CleanUtils.cleanCustomCache(getBaseContext().getExternalFilesDir(null));
                ACache.get(getBaseContext()).clear();
                new MonthlyNewestBeanDB(getBaseContext()).delete();
                e.onNext(true);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean value) {
                        ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.set_clear_cache));
                        setCacheSize();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }

    private void triggerSwitch() {
        boolean isPushOn = presenter.getPushState();
        ivPush.setSelected(!isPushOn);

        presenter.setRemoteSwitchState(!isPushOn);
    }

    private ForgetPop forgetPop;

    private void showRemindPop() {
        if (forgetPop != null)
            forgetPop.dismiss();
        forgetPop = new ForgetPop(getApplicationContext(), llBtnContainer, R.layout.pop_im) {
            @Override
            public void viewInit() {
                ((TextView) findViewById(R.id.content)).setText(R.string.seting_clear);
                TextView start = (TextView) findViewById(R.id.tv_cancel);
                start.setText("いいえ");
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forgetPop.dismiss();
                    }
                });
                TextView cancel = (TextView) findViewById(R.id.tv_sure);
                cancel.setText("はい");
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clearCache();
                        forgetPop.dismiss();
                    }
                });
                ImageView ivClose = (ImageView) findViewById(R.id.ivClose);
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        forgetPop.dismiss();

                    }
                });
            }
        };
        forgetPop.show();
    }


    @OnClick(R.id.btn_back)
    public void onClick() {
        finish();
    }

    @Override
    public void setSwitchState(boolean isPushOpen) {

        presenter.setPushState(isPushOpen);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && data != null) {
            boolean isNeedRefreshArea = data.getBooleanExtra("refreshArea", false);
            if (isNeedRefreshArea) {
                setAreaData();
            }
        }
    }
}
