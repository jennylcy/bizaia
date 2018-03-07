package com.bizaia.zhongyin.module.mine.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.common.adapter.PagerAdapter;
import com.bizaia.zhongyin.module.common.util.TabHelper;
import com.bizaia.zhongyin.module.discovery.action.AttentionSuccessAction;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.mine.api.CompanyHostAPI;
import com.bizaia.zhongyin.module.mine.api.IsAttentionAPI;
import com.bizaia.zhongyin.module.mine.data.CompanyHostBean;
import com.bizaia.zhongyin.module.mine.iml.IAttentionView;
import com.bizaia.zhongyin.module.mine.iml.ICompanyView;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.util.ImageLoaderUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.AppBarStateChangeListener;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/9.
 */

public class CompanyHostActivity extends BaseActivity implements ICompanyView, IAttentionView {

    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivCall)
    ImageView ivCall;
    @BindView(R.id.rivLiver)
    RoundedImageView rivLiver;
    @BindView(R.id.ivAttention)
    ImageView ivAttention;
    @BindView(R.id.tvCompanyName)
    TextView tvCompanyName;
    @BindView(R.id.tlCompany)
    TabLayout tlCompany;
    @BindView(R.id.vpCompany)
    ViewPager vpCompany;

    private List<String> titles;
    private CompanyHostAPI companyHostAPI;
    private IsAttentionAPI isAttentionAPI;
    private DisplayImageOptions options;
    private boolean isAttention = false;
    private IntroduceFragment introduceFragment;
//    private CompanyNewsFragment companyNewsFragment;
    private CompanyVideoFragment companyVideoFragment;
    @BindView(R.id.pfLecturer)
    PtrClassicFrameLayout pfLecturer;
    @BindView(R.id.clParent)
    CoordinatorLayout clParent;
    @BindView(R.id.ablCompany)
    AppBarLayout ablCompany;

    private TabHelper tabHelper;

    private long orgId;
    private static final String TAG = "CompanyHostActivity";
    private CompanyHostBean company;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_host);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        orgId = getIntent().getLongExtra("orgId", 1);
        ivCall.setVisibility(View.GONE);
        pfLecturer.setLastUpdateTimeRelateObject(this);
        pfLecturer.setPtrHandler(defaultHandler);
        ablCompany.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
//                    if(introduceFragment!=null&&introduceFragment.isTop())
                    pfLecturer.setCanRefresh(true);
                } else if (state == State.COLLAPSED) {

                    //折叠状态
                    pfLecturer.setCanRefresh(false);

                } else {

                    //中间状态
                    pfLecturer.setCanRefresh(false);
                }
            }
        });
        pfLecturer.setViewPager(vpCompany);
        requestData(orgId);

        addSubscription(RxBus.getInstance().getEvent(ForceOffLine.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ForceOffLine>() {
                    @Override
                    public void onNext(ForceOffLine value) {
                        ivAttention.setImageResource(R.drawable.icon_jiaguanzhu);
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));
    }

    private void requestData(long id) {
        companyHostAPI = new CompanyHostAPI(this);
        companyHostAPI.getCompanyHost(id);
        isAttentionAPI = new IsAttentionAPI(this);
        isAttentionAPI.isAttention(id);
    }

    private void init(String url) {
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.mine_company_introduce));
//        titles.add(getResources().getString(R.string.mine_company_chair));
        titles.add(getResources().getString(R.string.mine_company_video));
        tabHelper = new TabHelper(tlCompany);
        tabHelper.modify(getResources().getString(R.string.mine_company_introduce),
                getResources().getString(R.string.mine_company_video));
        List<Fragment> fragments = new ArrayList<>();
        introduceFragment = new IntroduceFragment(url);
//        companyNewsFragment = new CompanyNewsFragment(orgId);
        companyVideoFragment = new CompanyVideoFragment(orgId);
        fragments.add(introduceFragment);
//        fragments.add(companyNewsFragment);
        fragments.add(companyVideoFragment);
        vpCompany.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments, titles));
        tlCompany.setupWithViewPager(vpCompany);
    }

    PtrClassicFrameLayout.PtrDefaultHandler defaultHandler = new PtrClassicFrameLayout.PtrDefaultHandler() {
        public void onRefreshBegin(PtrFrameLayout frame) {
            pfLecturer.refreshComplete();
        }
    };

    private void initData(CompanyHostBean.DataEntity dataEntity) {
        String imgPath = dataEntity.getAvatarUrl();
        if (imgPath.startsWith("http")) {
            ImageLoader.getInstance().displayImage(imgPath, rivLiver,
                    ImageLoaderUtils.getImageHighQualityOptions());
        } else {
            ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + imgPath, rivLiver,
                    ImageLoaderUtils.getImageHighQualityOptions());
        }
        tvTitle.setText(dataEntity.getName());
        tvCompanyName.setText(dataEntity.getName());
        if (isAttention)
            ivAttention.setImageResource(R.drawable.icon_jiaguanzhu_sel);
        else
            ivAttention.setImageResource(R.drawable.icon_jiaguanzhu);
    }

    @Override
    public void showCompanyHost(CompanyHostBean companyHostBean) {
        company = companyHostBean;
        initData(companyHostBean.getData());
        init(companyHostBean.getData().getH5Url());
//        introduceFragment.setCompanyHostBean(companyHostBean);
    }

    @OnClick({R.id.back_img, R.id.ivAttention})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.ivAttention:
                if (StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    startActivity(new Intent(CompanyHostActivity.this, LoginActivity.class));
                    return;
                }
                Log.e(TAG, "onClick: ------->" + isAttention);
                if (isAttention)
                    isAttentionAPI.addAttention(orgId, 2);
                else
                    isAttentionAPI.addAttention(orgId, 1);
                break;
        }
    }

    @Override
    public void showCompanyHostError() {

    }

    @Override
    public void showIsAttention(boolean isAttention) {
        this.isAttention = isAttention;
        Log.e(TAG, "showIsAttention: ------->" + isAttention);
        if (isAttention)
            ivAttention.setImageResource(R.drawable.icon_jiaguanzhu_sel);
        else
            ivAttention.setImageResource(R.drawable.icon_jiaguanzhu);

    }

    @Override
    public void showAddAttention(boolean isAttention) {
        Log.e(TAG, "showAddAttention: ------->" + isAttention);
        if (isAttention) {
            RxBus.getInstance().post(new AttentionSuccessAction(true));
        }
        if (isAttention) {
            ToastUtils.show(CompanyHostActivity.this, company.getData().getName()+getString(R.string.live_attention));
            this.isAttention = true;
            ivAttention.setImageResource(R.drawable.icon_jiaguanzhu_sel);
        }
    }

    @Override
    public void showDelAttention(boolean isAttention) {
        Log.e(TAG, "showDelAttention: ------->" + isAttention);
        if (isAttention) {
            RxBus.getInstance().post(new AttentionSuccessAction(false));
        }
        if (isAttention) {
            ToastUtils.show(CompanyHostActivity.this, company.getData().getName()+getString(R.string.live_dis_attention));
            this.isAttention = false;
            ivAttention.setImageResource(R.drawable.icon_jiaguanzhu);
        }
    }

    @Override
    public void onRelogin() {
        reLogin();
    }

    @Override
    public void showAttentionError() {

    }

    @Override
    protected void onPause() {
//        introduceFragment.stopPlay();
        super.onPause();
    }
}
