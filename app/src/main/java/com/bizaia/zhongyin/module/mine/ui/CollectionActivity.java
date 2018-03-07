package com.bizaia.zhongyin.module.mine.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.common.adapter.PagerAdapter;
import com.bizaia.zhongyin.module.mine.action.CollectAction;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.util.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/15.
 */

public class CollectionActivity extends BaseActivity {

    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivCall)
    ImageView ivCall;
    @BindView(R.id.vpBuyHis)
    ViewPager vpBuyHis;
    @BindView(R.id.tlBuyHis)
    TabLayout tlBuyHis;
    @BindView(R.id.tv_modify)
    TextView tv_modify;
    @BindView(R.id.tv_btn_delete_all)
    TextView tv_btn_delete_all;

    List<String> titles;


    private BuyHisRecentlyFragment inforFragment;
    private BuyHisRecentlyFragment videoFragment;
    private int pos;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        ivCall.setVisibility(View.GONE);
        tv_modify.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.mine_collection);
        titles = new ArrayList<>();
//        titles.add(getString(R.string.mine_company_chair));
        titles.add(getString(R.string.mine_video));
//        titles.add(getString(R.string.mine_company_chair));
//        titles.add(getString(R.string.mine_associ));
//        titles.add(getString(R.string.mine_recruit));
        inforFragment = new BuyHisRecentlyFragment("4");
        videoFragment = new BuyHisRecentlyFragment("5");
        fragments = new ArrayList<>();
//        fragments.add(inforFragment);
        fragments.add(videoFragment);
//        fragments.add(new BuyHisRecentlyFragment("6"));
//        fragments.add(new BuyHisRecentlyFragment("7"));
//        fragments.add(new BuyHisRecentlyFragment("8"));
        vpBuyHis.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments, titles));
        vpBuyHis.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
                BuyHisRecentlyFragment fragment = (BuyHisRecentlyFragment) fragments.get(pos);
                if (fragment.getSate()) {
                    Log.e("CollectionActivity", "onPageSelected: ------------->true---------" + position);
                    tv_modify.setText(R.string.complete);
                    tv_btn_delete_all.setVisibility(View.VISIBLE);
                } else {
                    Log.e("CollectionActivity", "onPageSelected: ------------->false---------" + position);
                    tv_modify.setText(R.string.edit);
                    tv_btn_delete_all.setVisibility(View.GONE);
                }
                if (fragment.isEmpty()) {
                    tv_modify.setVisibility(View.GONE);
                    tv_btn_delete_all.setVisibility(View.GONE);
                } else {
                    tv_modify.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tlBuyHis.setupWithViewPager(vpBuyHis);

        addSubscription(RxBus.getInstance().getEvent(CollectAction.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<CollectAction>() {
                    @Override
                    public void onNext(CollectAction value) {
                        if (value.type == 0) {
                            tv_modify.setVisibility(View.GONE);
                            tv_btn_delete_all.setVisibility(View.GONE);
                        }
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
                        reLogin();
                        finish();
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));
    }

    @OnClick({R.id.back_img, R.id.ivCall, R.id.tv_modify, R.id.tv_btn_delete_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.tv_modify:
                BuyHisRecentlyFragment fragment = (BuyHisRecentlyFragment) fragments.get(pos);
                if (fragment.isEdit()) {
                    fragment.eidtAll(false);
                    tv_modify.setText(R.string.edit);
                    tv_btn_delete_all.setVisibility(View.GONE);
                } else {
                    fragment.eidtAll(true);
                    tv_modify.setText(R.string.complete);
                    tv_btn_delete_all.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_btn_delete_all:
                BuyHisRecentlyFragment seFragment = (BuyHisRecentlyFragment) fragments.get(pos);
                seFragment.delete();
                break;
        }
    }

    @OnClick(R.id.ivCall)
    public void onViewClicked() {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL
                , Uri.parse("tel:" + getResources().getString(R.string.tel)));
        startActivity(dialIntent);
    }
}
