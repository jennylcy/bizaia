package com.bizaia.zhongyin.module.video.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.common.adapter.PagerAdapter;
import com.bizaia.zhongyin.module.common.ui.LoadingDialog;
import com.bizaia.zhongyin.module.common.util.TabHelper;
import com.bizaia.zhongyin.module.discovery.api.IsCollectionAPI;
import com.bizaia.zhongyin.module.discovery.iml.ICollectionView;
import com.bizaia.zhongyin.module.live.ui.LivePDF;
import com.bizaia.zhongyin.module.live.ui.LivePdfFragment;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.module.mine.action.CancelCollectAction;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.mine.api.IsAttentionAPI;
import com.bizaia.zhongyin.module.mine.iml.IAttentionView;
import com.bizaia.zhongyin.module.mine.ui.CompanyHostActivity;
import com.bizaia.zhongyin.module.monthly.action.PaySuccessAction;
import com.bizaia.zhongyin.module.pay.PayActivity;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.module.video.api.ISavePlayView;
import com.bizaia.zhongyin.module.video.api.PlaySaveAPI;
import com.bizaia.zhongyin.module.video.data.PlayUrlDetailBean;
import com.bizaia.zhongyin.module.video.data.SaveResultBean;
import com.bizaia.zhongyin.module.video.data.VideoDetailData;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.repository.SPConfiguration;
import com.bizaia.zhongyin.util.GsonUtils;
import com.bizaia.zhongyin.util.ImageLoaderUtils;
import com.bizaia.zhongyin.util.NotifyBarUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.SPUtils;
import com.bizaia.zhongyin.util.SizeUtils;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.TimeUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.ForgetPop;
import com.bizaia.zhongyin.widget.ScrollControlViewPager;
import com.bizaia.zhongyin.widget.ShareDialog;
import com.bizaia.zhongyin.widget.player.listener.OnShowThumbnailListener;
import com.bizaia.zhongyin.widget.player.widget.PlayStateParams;
import com.bizaia.zhongyin.widget.player.widget.PlayerView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * create by yan
 */
public class DetailActivity extends BaseActivity<VideoDetailContract.Presenter>
        implements VideoDetailContract.View<VideoDetailData>, IAttentionView, ICollectionView, PlayerView.ISeeLimit, LivePdfFragment.ShowPdf, ISavePlayView {
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.pdfBar)
    RelativeLayout pdfBar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.container)
    CoordinatorLayout container;
    @BindView(R.id.iv_head)
    RoundedImageView ivHead;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.app_video_box)
    View appVideoBox;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.tvBuyNum)
    TextView tvBuyNum;
    @BindView(R.id.ivAttention)
    ImageView ivAttention;

    @BindView(R.id.linPdf)
    RelativeLayout linPdf;
    @BindView(R.id.ivBackPdf)
    ImageView ivBackPdf;
    @BindView(R.id.tvTitlePdf)
    TextView tvTitlePdf;
    @BindView(R.id.vpPDF)
    ScrollControlViewPager vpPDF;
    @BindView(R.id.ivPreview)
    ImageView ivPreview;
    @BindView(R.id.rvBrowse)
    RecyclerView rvBrowse;
    @BindView(R.id.flBrowse)
    FrameLayout flBrowse;
    @BindView(R.id.viewFrame)
    FrameLayout viewFrame;
    @BindView(R.id.ivCollect)
    ImageView ivCollect;


    private List<String> titles;
    private PlayerView player;
    private Context mContext;

    private PowerManager.WakeLock wakeLock;

    private ForgetPop morePop;
    private VideoDetailContract.Presenter presenter;
    private DetailFragmentSub detailFragmentSub;
    private IsAttentionAPI isAttentionAPI;
    private IsCollectionAPI isCollectionAPI;
    private boolean isCollect;
    private boolean isAttention;
    private long orgId;

    private static final String TAG = "DetailActivity";

    private long videoId;
    private boolean hasPay;
    private LoadingDialog loadingDialog;

    private String title;
    private String cover;
    private LivePdfFragment livePdfFragment;
    private LivePDF livePDF;
    private TabHelper tabHelper;

    private static final String PLAY_TIME_SAVE="savetime";
    private PlaySaveAPI playSaveAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        mContext = this;
        setSupportActionBar(toolbar);
        NotifyBarUtils.StatusBarLightMode(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            NotifyBarUtils.transparencyBar(this);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
            layoutParams.topMargin = SizeUtils.getNotifyBarHeight(this);
            toolbar.setLayoutParams(layoutParams);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) pdfBar.getLayoutParams();
            layoutParams.topMargin = SizeUtils.getNotifyBarHeight(this);
            pdfBar.setLayoutParams(layoutParams);
        }
        appVideoBox.setVisibility(View.INVISIBLE);
        new DetailPresenter(getBaseContext(), this);

        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();

        videoId = getIntent().getLongExtra("videoId", -1);
        title = getIntent().getStringExtra("title");
        cover = getIntent().getStringExtra("cover");
        if (videoId == -1) return;


        isAttentionAPI = new IsAttentionAPI(this);
        isCollectionAPI = new IsCollectionAPI(this);
        playSaveAPI = new PlaySaveAPI(this);


        isCollectionAPI.isCollection(videoId, 3);

        tvDetailTitle.setText(title);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();

        ivBackPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linPdf.setVisibility(View.GONE);
            }
        });
        ivAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    startActivity(new Intent(DetailActivity.this, LoginActivity.class));
                    return;
                }

                if (isAttention) {
                    isAttentionAPI.addAttention(orgId, 2);
                } else {
                    isAttentionAPI.addAttention(orgId, 1);
                }
            }
        });
        ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    startActivity(new Intent(DetailActivity.this, LoginActivity.class));
                    return;
                }
                if (isCollect) {
                    isCollectionAPI.addCollection(videoId, 3, 2);
                    RxBus.getInstance().post(new CancelCollectAction());
                } else {
                    isCollectionAPI.addCollection(videoId, 3, 1);
                }
            }
        });

        Observable.timer(100, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        presenter.requireData(videoId, BIZAIAApplication.getInstance().getToken());
                    }
                });


        addSubscription(RxBus.getInstance().getEvent(PaySuccessAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PaySuccessAction>() {
                    @Override
                    public void onNext(PaySuccessAction value) {
                        player.setPayState(true, videoId);
                        livePDF.setBuy(true);
                        videoDetailData.getData().getVod().setIsPaid(true);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        MediaUtils.muteAudioFocus(mContext, false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            if(videoDetailData.getData().getVod().getPrice()==0||
                    videoDetailData.getData().getVod().isIsPaid())
            savePlayTime();
            player.onPause();
        }
        MediaUtils.muteAudioFocus(mContext, true);
    }

    @Override
    protected void onDestroy() {
        wakeLock.release();
        super.onDestroy();
        if (livePDF != null)
            livePDF.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    private void init(int type) {
        titles = new ArrayList<>();
        titles.add(getString(R.string.qb_detail));
        if (type == 1) {
            titles.add(getResources().getString(R.string.live_courseware));
        } else {
            viewFrame.setVisibility(View.GONE);
        }
        List<Fragment> fragments = new ArrayList<>();
        detailFragmentSub = new DetailFragmentSub();
        fragments.add(detailFragmentSub);
        if (type == 1) {
            livePdfFragment = new LivePdfFragment();
            livePdfFragment.setShowPdf(this);
            fragments.add(livePdfFragment);

            livePDF = new LivePDF(this, vpPDF, videoDetailData.getData().getVod().getCoursewareUrl(),
                    videoDetailData.getData().getVod().getLiveId(),
                    videoDetailData.getData().getVod().getTitle(),
                    videoDetailData.getData().getVod().isIsPaid(), rvBrowse, flBrowse, ivPreview, new LivePDF.BuyLive() {
                @Override
                public void buy() {
                    if (linPdf.getVisibility() == View.GONE)
                        linPdf.setVisibility(View.VISIBLE);
                    if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                        startActivity(new Intent(getBaseContext(), PayActivity.class)
                                .putExtra("price", videoDetailData.getData().getVod().getPrice())
                                .putExtra("id", videoId)
                                .putExtra("title", getString(R.string.video_buy_title))
                                .putExtra("type", 3));
                    } else {
                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    }
                }
            });
            livePdfFragment.setData(videoDetailData.getData().getVod().getCoursewareUrl());
        }
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabs.setupWithViewPager(viewPager);
        tabHelper = new TabHelper(tabs);
        if (type == 1) {
            tabHelper.modify(getString(R.string.qb_detail),
                    getString(R.string.live_courseware));
        }


        this.mContext = this;


    }

    private void savePlayTime(){
        if(StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())){
               if(videoDetailData.getData().getVod().getPrice()==0){
                   Log.e(TAG, "savePlayTime: -----------palyTime---------->"+player.getCurrentPosition()/1000 );
                   SPUtils.putInt(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME,
                           PLAY_TIME_SAVE+videoDetailData.getData().getVod().getId(),
                           player.getCurrentPosition()/1000);
               }
        }else{
             playSaveAPI.save(videoDetailData.getData().getVod().getId(),
                     videoDetailData.getData().getVod().getType(),
                     player.getCurrentPosition()/1000);
        }
    }

    private void initVideo(final String imgUrl, String videoUrl, String title,
                           boolean isPay,int palyTime) {
        appVideoBox.setVisibility(View.VISIBLE);
        Log.e(TAG, "initVideo: -----------palyTime---------->"+palyTime );
        player = new PlayerView(this)
                .setTitle(title)
                .setScaleType(PlayStateParams.fitparent)
                .forbidTouch(false)
                .lockRotation()
                .hideRotation(true)
                .setiSeeLimit(this)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        ImageLoader.getInstance().displayImage(imgUrl,
                                ivThumbnail);
                    }
                })
                .setPlaySource(videoUrl)
                .setPayState(isPay, videoId)
                .setSeekTo(palyTime)
                .startPlay();


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                NotifyBarUtils.StatusBarLightMode(this);
            } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                NotifyBarUtils.StatusBarDarkMode(this);
            }

        }
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @OnClick({R.id.iv_back_btn, R.id.iv_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_btn:
                finish();
                break;
            case R.id.iv_more:
                if (videoDetailData != null && videoDetailData.getData() != null &&
                        videoDetailData.getData().getVod() != null)
                    ShareDialog.share(this, title, title,
                            AddressConfiguration.MAIN_PATH + videoDetailData.getData().getVod().getCoverUrl(),
                            videoDetailData.getData().getVod().getShareUrl());
//                morePop = getMorePop();
//                morePop.show();
                break;
        }
    }


    private ForgetPop getMorePop() {
        return new ForgetPop(getBaseContext(), container, R.layout.pop_more) {
            @Override
            public void viewInit() {
                getView(R.id.ll_dialog_container).setOnClickListener(popOnClickListener);
                TextView tvCollection = (TextView) findViewById(R.id.tv_save);
                tvCollection.setVisibility(View.GONE);
                TextView tvPhone = (TextView) findViewById(R.id.tvPhone);
                tvPhone.setOnClickListener(popOnClickListener);
                TextView tvPopEmail = (TextView) findViewById(R.id.tvPopEmail);
                tvPopEmail.setOnClickListener(popOnClickListener);
            }
        };
    }

    private View.OnClickListener popOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_dialog_container:
                    morePop.dismiss();
                    break;
                case R.id.tv_save:
                    morePop.dismiss();
                    if (isCollect)
                        isCollectionAPI.addCollection(videoId, 3, 2);
                    else
                        isCollectionAPI.addCollection(videoId, 3, 1);
                    break;
                case R.id.tvPhone:
                    morePop.dismiss();
                    if (videoDetailData != null && videoDetailData.getData() != null)
                        ShareDialog.share(DetailActivity.this,
                                videoDetailData.getData().getVod().getTitle(),
                                videoDetailData.getData().getVod().getIntroduction(),
                                AddressConfiguration.MAIN_PATH + videoDetailData.getData().getVod().getCoverUrl(),
                                videoDetailData.getData().getVod().getShareUrl());
                    break;
                case R.id.tvPopEmail:
                    morePop.dismiss();
                    break;
            }
        }
    };

    private VideoDetailData videoDetailData;

    @Override
    public void dataSuccess(VideoDetailData news) {
        loadingDialog.dismiss();
        if (news.getData() == null)
            return;
        isAttentionAPI.isAttention(news.getData().getVod().getOrgId());
        videoDetailData = news;
        init(news.getData().getVod().getType());
        orgId = news.getData().getVod().getOrgId();


        VideoDetailData.DataEntity dataBean = news.getData();
//        Log.e(TAG, "dataSuccess: -------->" + playList.size());
//        if (!playList.isEmpty() && playList.get(0).getUrl() != null)
//            initVideo(AddressConfiguration.MAIN_PATH + dataBean.getVod().getCoverUrl(), playList.get(0).getUrl(),
//                    dataBean.getVod().getTitle(),
//                    news.getData().getVod().isIsPaid(),
//                    0);
        if(!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
            playSaveAPI.get(videoDetailData.getData().getVod().getId(), dataBean.getVod().getType());
        }else{
            List<PlayUrlDetailBean> playList = new ArrayList<>();
            if (!StringUtils.isEmpty(videoDetailData.getData().getVod().getPlayUrl())) {
                playList.addAll(GsonUtils.transStringToList(videoDetailData.getData().getVod().getPlayUrl().replace("\\", "")));
            }
            if (!playList.isEmpty() && playList.get(0).getUrl() != null)
                initVideo(AddressConfiguration.MAIN_PATH + videoDetailData.getData().getVod().getCoverUrl(), playList.get(0).getUrl(),
                        videoDetailData.getData().getVod().getTitle(),
                        videoDetailData.getData().getVod().isIsPaid(),
                        SPUtils.getInt(getApplicationContext(),
                                MODE_PRIVATE, SPConfiguration.APP_NAME,
                                PLAY_TIME_SAVE+videoDetailData.getData().getVod().getId())*1000);
        }

        tvTitle.setText(dataBean.getVod().getOrgName());
        tvBuyNum.setText(String.valueOf(getString(R.string.live_player_nums) + dataBean.getVod().getPageViewNum()));
        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + dataBean.getVod().getAvatarUrl(),
                ivHead, ImageLoaderUtils.getImageHighQualityOptions());
        if (dataBean.getVod().getType() == 1) {
            tvType.setText(R.string.video_look_back);
        } else if (dataBean.getVod().getType() == 2) {
            tvType.setText(R.string.video_video);
        } else if (dataBean.getVod().getType() == 3) {
            tvType.setText(R.string.video_type_frequency);
            player.setAudio(true);
        }
        detailFragmentSub.setIvHead(dataBean.getVod().getAvatarUrl());
        detailFragmentSub.setTitle(dataBean.getVod().getTitle());
        detailFragmentSub.setTvDetail(dataBean.getVod().getIntroduction());
        if (dataBean.getVod().getPrice() != 0) {
            detailFragmentSub.setTvInfo(
                    getString(R.string.play_time) + TimeUtils.timeTransGBToCN(dataBean.getVod().getCreateTime()) + "\n"
                            + getString(R.string.fy_price) + dataBean.getVod().getPrice() + "\n"
                            + getString(R.string.jz_talker) + dataBean.getVod().getLecturers() + "\n"
                            + getString(R.string.bought_count) + dataBean.getVod().getBuyNum()

            );
        } else {
            detailFragmentSub.setTvInfo(
                    getString(R.string.play_time) + TimeUtils.timeTransGBToCN(dataBean.getVod().getCreateTime()) + "\n"
                            + getString(R.string.live_cost) + getString(R.string.video_free) + "\n"
                            + getString(R.string.jz_talker) + dataBean.getVod().getLecturers() + "\n"
                            + getString(R.string.bought_count) + "なし"

            );
        }
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoDetailData != null && videoDetailData.getData() != null) {
                    Intent intent = new Intent(DetailActivity.this, CompanyHostActivity.class);
                    intent.putExtra("orgId", videoDetailData.getData().getVod().getOrgId());
                    startActivity(intent);
                }
            }
        });
        detailFragmentSub.setLectureList(dataBean.getLecturerList());
    }

    @Override
    public void dataError() {
        loadingDialog.dismiss();
    }

    @Override
    public void onRelogin() {
        loadingDialog.dismiss();
        reLogin();
        finish();
    }

    @Override
    public void setPresenter(VideoDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showIsAttention(boolean isAttention) {
        this.isAttention = isAttention;
        if (isAttention) {
            ivAttention.setImageResource(R.drawable.icon_jiaguanzhu_sel);
        } else {
            ivAttention.setImageResource(R.drawable.icon_jiaguanzhu);
        }
    }

    @Override
    public void showAddAttention(boolean isAttention) {
        if (isAttention) {
            this.isAttention = true;
            ivAttention.setImageResource(R.drawable.icon_jiaguanzhu_sel);
            ToastUtils.show(getApplicationContext(), videoDetailData.getData().getVod().getOrgName() + getString(R.string.live_attention));
        } else {
            ivAttention.setImageResource(R.drawable.icon_jiaguanzhu);
        }
    }

    @Override
    public void showDelAttention(boolean isAttention) {
        if (isAttention) {
            this.isAttention = false;
            ivAttention.setImageResource(R.drawable.icon_jiaguanzhu);
            ToastUtils.show(getApplicationContext(), videoDetailData.getData().getVod().getOrgName() + getString(R.string.live_dis_attention));
        } else {
            ivAttention.setImageResource(R.drawable.icon_jiaguanzhu_sel);
        }
    }

    @Override
    public void showAttentionError() {

    }

    @Override
    public void showIsCollection(boolean isAttention) {
        isCollect = isAttention;
        if (isAttention) {
            ivCollect.setImageResource(R.drawable.icon_star_sel);
        } else {
            ivCollect.setImageResource(R.drawable.icon_star);
        }

    }

    @Override
    public void showAddCollection(boolean isAttention) {
        if (isAttention) {
            isCollect = true;
            ivCollect.setImageResource(R.drawable.icon_star_sel);
            ToastUtils.show(this, getString(R.string.live_collection));
        } else {
            ToastUtils.show(this, getString(R.string.live_collection_error));
        }
    }

    @Override
    public void showDelCollection(boolean isAttention) {
        if (isAttention) {
            isCollect = false;
            ToastUtils.show(this, getString(R.string.live_dis_collection));
            ivCollect.setImageResource(R.drawable.icon_star);
        } else {
            ToastUtils.show(this, getString(R.string.live_dis_collection_error));
        }
    }

    @Override
    public void showCollectionError() {

    }

    @Override
    public void setOrder(OrderData order) {
        startActivity(new Intent(getBaseContext(), PayActivity.class)
                .putExtra("price", videoDetailData.getData().getVod().getPrice())
                .putExtra("order", order));
    }

    @Override
    public void error(String str) {

    }


    @Override
    public void limit() {
        if (player != null && player.isPlaying())
            player.pausePlay();
        showRemindPop();
    }

    private ForgetPop forgetPop;
    private Handler payHandler;
    private int timeNum = 120;

    private void showRemindPop() {
        Log.e(TAG, "showRemindPop: ------------>isLimit");
        if (forgetPop != null)
            forgetPop.dismiss();
        forgetPop = new ForgetPop(getApplicationContext(), toolbar, R.layout.pop_im) {
            @Override
            public void viewInit() {
                ((TextView) findViewById(R.id.content)).setText(R.string.live_pay_tips);
                TextView start = (TextView) findViewById(R.id.tv_cancel);
                start.setText(R.string.live_remind_enter);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        payHandler.removeMessages(0);
                        if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                            startActivity(new Intent(getBaseContext(), PayActivity.class)
                                    .putExtra("price", videoDetailData.getData().getVod().getPrice())
                                    .putExtra("id", videoId)
                                    .putExtra("title", getString(R.string.video_buy_title))
                                    .putExtra("type", 3));
                            forgetPop.dismiss();
                        } else {
                            TextView cancel = (TextView) forgetPop.findViewById(R.id.tv_sure);
                            cancel.setText(R.string.live_remind_cancel);
                            startActivity(new Intent(getBaseContext(), LoginActivity.class));
                        }
                    }
                });
                TextView cancel = (TextView) findViewById(R.id.tv_sure);
                cancel.setText(R.string.live_remind_cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forgetPop.dismiss();
                        payHandler.removeMessages(0);
                        finish();
                    }
                });
                ImageView ivClose = (ImageView) findViewById(R.id.ivClose);
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        forgetPop.dismiss();
                        payHandler.removeMessages(0);
                    }
                });
            }
        };
        forgetPop.show();
        payHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                timeNum--;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (timeNum > 0 || timeNum == 0) {
                            TextView textView = (TextView) forgetPop.findViewById(R.id.tv_sure);
                            textView.setText(getResources().getString(R.string.live_pay_cancel) + " " + timeNum + "s");
                            if (timeNum == 0) {
                                forgetPop.dismiss();
                                finish();
                            }
                        }
                    }
                });
                if (timeNum > 0)
                    payHandler.sendEmptyMessageDelayed(0, 1000);
                super.handleMessage(msg);
            }
        };
        forgetPop.setOnDismissFinishListener(new ForgetPop.OnDismissFinishListener() {
            @Override
            public void OnDismissFinish() {
                payHandler.removeMessages(0);
            }
        });
        payHandler.sendEmptyMessageDelayed(0, 1000);
    }

    @OnClick(R.id.icon_share)
    public void onViewClicked() {
        if (videoDetailData != null && videoDetailData.getData() != null &&
                videoDetailData.getData().getVod() != null)
            ShareDialog.share(this, title, title, AddressConfiguration.MAIN_PATH
                            + videoDetailData.getData().getVod().getCoverUrl(),
                    videoDetailData.getData().getVod().getShareUrl());
    }

    @Override
    public void show() {
//        if (livePDF.isSaved()) {
        if (linPdf.getVisibility() == View.GONE)
            linPdf.setVisibility(View.VISIBLE);
//        } else {
//            ToastUtils.show(this, R.string.live_pdf_unsave);
//        }
    }

    @Override
    public void showSave(SaveResultBean resultBean) {

    }

    @Override
    public void showSaveResult(SaveResultBean resultBean) {
        if(resultBean.getCode()==200&&resultBean.getData()!=null) {
            List<PlayUrlDetailBean> playList = new ArrayList<>();
            if (!StringUtils.isEmpty(videoDetailData.getData().getVod().getPlayUrl())) {
                playList.addAll(GsonUtils.transStringToList(videoDetailData.getData().getVod().getPlayUrl().replace("\\", "")));
            }

            if (!playList.isEmpty() && playList.get(0).getUrl() != null)
                initVideo(AddressConfiguration.MAIN_PATH + videoDetailData.getData().getVod().getCoverUrl(), playList.get(0).getUrl(),
                        videoDetailData.getData().getVod().getTitle(),
                        videoDetailData.getData().getVod().isIsPaid(),
                        resultBean.getData().getPoint()*1000);
        }
    }

    @Override
    public void showSaveError() {

    }
}
