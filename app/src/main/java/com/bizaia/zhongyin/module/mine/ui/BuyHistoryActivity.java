package com.bizaia.zhongyin.module.mine.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.common.adapter.PagerAdapter;
import com.bizaia.zhongyin.module.common.util.TabHelper;
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
 * Created by zyz on 2017/3/7.
 */

public class BuyHistoryActivity extends BaseActivity{

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

    List<String> titles;
    private TabHelper tabHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        tvTitle.setText(R.string.mine_purchase);
        titles = new ArrayList<>();
//        titles.add(getString(R.string.mine_company_chair));
        titles.add(getString(R.string.mine_live));
        titles.add(getString(R.string.mine_video));
        titles.add(getString(R.string.mine_magazine_text));
        ivCall.setVisibility(View.GONE);
        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new BuyHisRecentlyFragment("0"));
        fragments.add(new BuyHisRecentlyFragment("1"));
        fragments.add(new BuyHisRecentlyFragment("2"));
        fragments.add(new BuyHisRecentlyFragment("3"));
        vpBuyHis.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments));
        tlBuyHis.setupWithViewPager(vpBuyHis);

        tabHelper = new TabHelper(tlBuyHis,true);
        tabHelper.modify( getString(R.string.mine_live),
                getString(R.string.mine_video),getString(R.string.mine_magazine_text));
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
//        tlBuyHis.setTabMode(TabLayout.MODE_SCROLLABLE);
//        vpBuyHis.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments, titles));
//        tlBuyHis.setupWithViewPager(vpBuyHis);
    }

    @OnClick({R.id.back_img, R.id.ivCall})
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.back_img:
               finish();
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
