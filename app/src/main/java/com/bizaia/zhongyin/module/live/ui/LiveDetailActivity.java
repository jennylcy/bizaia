package com.bizaia.zhongyin.module.live.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.bizaia.zhongyin.module.live.action.IMLoginSuccessAction;
import com.bizaia.zhongyin.module.live.action.LiveEndAction;
import com.bizaia.zhongyin.module.live.agora.kit.KSYAgoraStreamer;
import com.bizaia.zhongyin.module.live.api.LiveDetailAPI;
import com.bizaia.zhongyin.module.live.api.LiveStateAPI;
import com.bizaia.zhongyin.module.live.api.LiveThumbUpAPI;
import com.bizaia.zhongyin.module.live.data.CameraStreamAddressEntity;
import com.bizaia.zhongyin.module.live.data.CoursewareStreamAddressEntity;
import com.bizaia.zhongyin.module.live.data.IMDataBean;
import com.bizaia.zhongyin.module.live.data.LiveDetailBean;
import com.bizaia.zhongyin.module.live.data.LiveState;
import com.bizaia.zhongyin.module.live.data.ThumbState;
import com.bizaia.zhongyin.module.live.imhelper.IMHelper;
import com.bizaia.zhongyin.module.live.iml.ILiveDetailView;
import com.bizaia.zhongyin.module.live.iml.ILiveStateView;
import com.bizaia.zhongyin.module.live.iml.ILiveThumbUpView;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.mine.api.IsAttentionAPI;
import com.bizaia.zhongyin.module.mine.iml.IAttentionView;
import com.bizaia.zhongyin.module.mine.ui.CompanyHostActivity;
import com.bizaia.zhongyin.module.monthly.action.PaySuccessAction;
import com.bizaia.zhongyin.module.pay.PayActivity;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.util.GsonUtils;
import com.bizaia.zhongyin.util.ImageLoaderUtils;
import com.bizaia.zhongyin.util.NotifyBarUtils;
import com.bizaia.zhongyin.util.OtherUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.SizeUtils;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.ForgetPop;
import com.bizaia.zhongyin.widget.ILivePlayer;
import com.bizaia.zhongyin.widget.LivePlayer;
import com.bizaia.zhongyin.widget.ScrollControlViewPager;
import com.bizaia.zhongyin.widget.ShareDialog;
import com.ksyun.media.streamer.encoder.VideoEncodeFormat;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterBase;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterMgt;
import com.ksyun.media.streamer.framework.AVConst;
import com.ksyun.media.streamer.kit.KSYStreamer;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/16.
 */

public class LiveDetailActivity extends BaseActivity implements ILiveDetailView,
        ILivePlayer, IAttentionView, ILiveStateView, LiveInfoFragment.IsetRemind, ILiveThumbUpView,
        LivePdfFragment.ShowPdf {

    private final String TAG = "LiveDetailActivity.this";
    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rivIcon)
    RoundedImageView rivIcon;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvLiveState)
    TextView tvLiveState;
    @BindView(R.id.tvNumberAll)
    TextView tvNumber;
    @BindView(R.id.ivAttention)
    ImageView ivAttention;
    @BindView(R.id.tlLive)
    TabLayout tlLive;
    @BindView(R.id.vpLive)
    ViewPager vpLive;
    @BindView(R.id.rlParenttest)
    RelativeLayout rlParent;
    @BindView(R.id.lPlayer)
    LivePlayer lplayer;
    @BindView(R.id.viewBar)
    View viewBar;
    @BindView(R.id.rlInput)
    LinearLayout rlInput;
    @BindView(R.id.etImInputAll)
    EditText etImInputAll;
    @BindView(R.id.btnSendAll)
    Button btnSendAll;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.linInput)
    LinearLayout linInput;
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
    @BindView(R.id.ivCall)
    ImageView ivCall;
    @BindView(R.id.pdfBar)
    RelativeLayout pdfBar;


    private TabHelper tabHelper;
    private LiveDetailAPI liveDetailAPI;
    private LiveStateAPI liveStateAPI;


    private String groupNum;
    private boolean isLogin;
    private List<Object> listMsg = new ArrayList<>();
    private Handler mMainHandler;
    private KSYAgoraStreamer mStreamer;
    private boolean isPreper;
    private boolean mRecording = false;
    private ForgetPop forgetPop;
    private IsAttentionAPI isAttentionAPI;
    //    private IsCollectionAPI isCollectionAPI;
    private LiveThumbUpAPI liveThumbUpAPI;
    private boolean isAttetion;
    private boolean isCollection;
    private long id;
    private LiveInfoFragment liveInfoFragment;
    private LivePdfFragment livePdfFragment;
    private IMLiveFragment imLiveFragment;
    private CoursewareStreamAddressEntity coursewareStreamAddress;
    private CameraStreamAddressEntity cameraStreamAddress;
    private int posi = 0;
    private long orgId;
    private int liveType;
    private PowerManager.WakeLock wakeLock;
//    private PhoneReceiver phoneReceiver;

    private LivePDF livePDF;
    protected LoadingDialog loadingDialog;
    private boolean hasIndentifier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if(OtherUtils.checkDeviceHasNavigationBar(getBaseContext())){
            Window window = getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            window.setAttributes(params);
        }
        setContentView(R.layout.activity_live_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ButterKnife.bind(this);
        setViewParent(tlLive);
        setSupportActionBar(toolbar);
        NotifyBarUtils.StatusBarLightMode(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            NotifyBarUtils.transparencyBar(this);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
            int height = SizeUtils.getNotifyBarHeight(this);
            layoutParams.topMargin = height;
            toolbar.setLayoutParams(layoutParams);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) pdfBar.getLayoutParams();
            layoutParams.topMargin = SizeUtils.getNotifyBarHeight(this);
            pdfBar.setLayoutParams(layoutParams);
        }
        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();

        if (getIntent() != null)
            liveType = getIntent().getIntExtra("liveType", 1);

        id = getIntent().getLongExtra("id", 0);

        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });

        liveDetailAPI = new LiveDetailAPI(this);
        liveStateAPI = new LiveStateAPI(this);
        lplayer.setiLivePlayer(this);
        lplayer.setContext(LiveDetailActivity.this);
        isAttentionAPI = new IsAttentionAPI(this);
        liveThumbUpAPI = new LiveThumbUpAPI(this);
        if (getIntent() != null && getIntent().getLongExtra("id", 0) != 0) {
            id = getIntent().getLongExtra("id", 0);
            liveThumbUpAPI.isThumb(id);
            liveDetailAPI.getDetail(id);
//            if(liveType==1)
//            liveStateAPI.getDetail(id);
        }

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSendAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    ToastUtils.show(LiveDetailActivity.this, R.string.live_unlogin_error);
                    startActivity(new Intent(LiveDetailActivity.this, LoginActivity.class));
                    return;
                }
                if (dataDetail != null && dataDetail.getData() != null && !dataDetail.getData().getLive().isPay()) {
                    ToastUtils.show(LiveDetailActivity.this, R.string.send_not_buy);
                    return;
                }
                String conent = etImInputAll.getText().toString().trim();
                if (!StringUtils.isEmpty(conent)) {
                    sendMsg(conent);
                    hideKeyboard();
                }
            }
        });
        etImInputAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    hideKeyboard();
                    startActivity(new Intent(LiveDetailActivity.this, LoginActivity.class));
                } else {
                    if (!isPay) {
                        showRemindPop("オンライン予約");
                    }
                }
            }
        });
        etImInputAll.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus&&StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())){
                    hideKeyboard();
                    startActivity(new Intent(LiveDetailActivity.this, LoginActivity.class));
                }
            }
        });
        linInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    startActivity(new Intent(LiveDetailActivity.this, LoginActivity.class));
                } else {
                    if (!isPay) {
                        showRemindPop("オンライン予約");
                    }
                }
            }
        });
        ivAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    ToastUtils.show(LiveDetailActivity.this, R.string.live_unlogin_error);
                    return;
                }
                if (!isAttetion)
                    isAttentionAPI.addAttention(orgId, 1);
                else
                    isAttentionAPI.addAttention(orgId, 2);
            }
        });
        rivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orgId != 0) {
                    Intent intent = new Intent(LiveDetailActivity.this, CompanyHostActivity.class);
                    intent.putExtra("orgId", orgId);
                    startActivity(intent);
                }
            }
        });
        ivBackPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linPdf.setVisibility(View.GONE);
            }
        });
        ivPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        initStream();


        addSubscription(RxBus.getInstance().getEvent(PaySuccessAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PaySuccessAction>() {
                    @Override
                    public void onNext(PaySuccessAction value) {
                        isPay = true;
                        dataDetail.getData().getLive().setPay(true);
                        lplayer.setHasPay(true);
                        livePDF.setBuy(true);
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));
        addSubscription(RxBus.getInstance().getEvent(IMLoginSuccessAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<IMLoginSuccessAction>() {
                    @Override
                    public void onNext(IMLoginSuccessAction value) {
                        if (dataDetail != null && dataDetail.getData() != null && groupNum != null) {
                            initIM(groupNum);
                            initImHandsup(dataDetail.getData().getLive().getLecturerIdentifier());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

        addSubscription(RxBus.getInstance().getEvent(ForceOffLine.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ForceOffLine>() {
                    @Override
                    public void onNext(ForceOffLine value) {
                        finish();
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));

//        addLayoutListener(rlInput, linInput);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();

        Log.e(TAG, "onCreate: -------------------->" + groupNum);
    }

    private void initView() {

        List<Fragment> fragments = new ArrayList<>();
        liveInfoFragment = new LiveInfoFragment(liveType, id);
        liveInfoFragment.setIsetRemind(this);
        livePdfFragment = new LivePdfFragment();
        livePdfFragment.setShowPdf(this);
        if (liveType != 0) {
            imLiveFragment = new IMLiveFragment();
            fragments.add(imLiveFragment);
        }
        fragments.add(liveInfoFragment);
        fragments.add(livePdfFragment);
        vpLive.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments));
        vpLive.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                posi = position;
                if (position == 0) {
                    Log.e(TAG, "onPageSelected: -------------->"+ dataDetail.getData().getLive().getStatus());
                    if ("1".equals(dataDetail.getData().getLive().getStatus()))
                        rlInput.setVisibility(View.VISIBLE);
                } else {
                    rlInput.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tlLive.setupWithViewPager(vpLive);
        tabHelper = new TabHelper(tlLive);
        if (liveType != 0) {
            tabHelper.modify(getResources().getString(R.string.live_dis_online),
                    getResources().getString(R.string.live_info),
                    getResources().getString(R.string.live_courseware));
        } else {
            tabHelper.modify(
                    getResources().getString(R.string.live_info),
                    getResources().getString(R.string.live_courseware));
        }

    }

    private IMDataBean imDataBean;

    private void sendMsg(String content) {
        imDataBean = new IMDataBean();
        imDataBean.setHeadImg(BIZAIAApplication.getInstance().getUserBean().getAvatarUrl());
        imDataBean.setSendId(BIZAIAApplication.getInstance().getMemberAccount());
        imDataBean.setNews(content);
        imDataBean.setNickName(BIZAIAApplication.getInstance().getUserBean().getNickname());
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(GsonUtils.transformMsg(BIZAIAApplication.getInstance().getUserBean().getAvatarUrl(),
                content,
                BIZAIAApplication.getInstance().getUserBean().getNickname(),
                BIZAIAApplication.getInstance().getMemberAccount()));
        timMessage.addElement(elem);
        etImInputAll.setText("");
        IMHelper.getInstance().sendSysMsg(timMessage, new TIMValueCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "onError:----- " + i + "-----" + s);
            }

            @Override
            public void onSuccess(Object o) {
                if (lplayer.isFull && rlInput.getVisibility() == View.VISIBLE) {
                    rlInput.setVisibility(View.GONE);
                }
            }
        });
        listMsg.add(imDataBean);
        mMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!lplayer.isFull) {
                    imLiveFragment.setData(imDataBean);
                } else {
                    lplayer.setMsgData(imDataBean);
                }
            }
        }, 0);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewBar.setVisibility(View.GONE);
        } else {
            viewBar.setVisibility(View.VISIBLE);
        }
        viewBar.requestLayout();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void finish() {
        if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()) && !lplayer.isHandUp) {
            hangUp();
        }
        stopStream();
        exitRoom();
        super.finish();
    }

    private void initIM(final String groupNum) {
        Log.e("initIM", "------------>initIM---" + groupNum);
        IMHelper.getInstance().joinGroup(groupNum, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.e("joingoup", "------------>error---" + i + "-----" + s);
                if (i != 10013) {
                    isLogin = false;
                    IMHelper.getInstance().init(getApplicationContext());
                } else {
                    isLogin = true;
                    IMHelper.getInstance().initSysSend(groupNum);
                    joinSuccess();
                }
            }

            @Override
            public void onSuccess() {
                Log.e("joingoup", "------------>onSuccess---"+groupNum);
                isLogin = true;
                IMHelper.getInstance().initSysSend(groupNum);
                joinSuccess();
            }
        });
    }


    private void initImHandsup(String id) {
        IMHelper.getInstance().initPerson(id);
    }

    private LiveDetailBean dataDetail;
    private boolean isPay;

    @Override
    public void showLiveDetail(LiveDetailBean data) {
        loadingDialog.dismiss();
        this.dataDetail = data;
        isNotifi = data.getData().getLive().isNotifi();
        isPay = data.getData().getLive().isPay();
        tvTitle.setText(data.getData().getLive().getTitle());
        orgId = data.getData().getLive().getOrganizationId();
        liveType = Integer.parseInt(data.getData().getLive().getStatus());
        if (liveType == 1)
            liveStateAPI.getDetail(id);
        initView();
        if (data.getData().getLive().getCoursewareStreamAddress() != null)
            coursewareStreamAddress = data.getData().getLive().getCoursewareStreamAddress();
        if (data.getData().getLive().getCameraStreamAddress() != null)
            cameraStreamAddress = data.getData().getLive().getCameraStreamAddress();
        isAttentionAPI.isAttention(orgId);
        groupNum = data.getData().getLive().getChatroomId();
        if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()) && liveType != 0) {
            Log.e(TAG, "showLiveDetail: ----------->" + cameraStreamAddress.getPushUrl());
//            mStreamer.setUrl(cameraStreamAddress.getPushUrl());
            initIM(groupNum);
            initImHandsup(data.getData().getLive().getLecturerIdentifier());
        }
        if ("0".equals(data.getData().getLive().getStatus())) {
            rlInput.setVisibility(View.GONE);
        }
        if ("1".equals(this.dataDetail.getData().getLive().getStatus())) {
            tvNumber.setText(getResources().getString(R.string.live_online_num) + "  " + data.getData().getLive().getBuyNum());
        } else if ("0".equals(this.dataDetail.getData().getLive().getStatus())) {
            if (dataDetail.getData().getLive().getPrice() == 0) {
                tvNumber.setText(getResources().getString(R.string.bought_count) + "  " + "なし");
            } else {
                tvNumber.setText(getResources().getString(R.string.bought_count) + "  " + data.getData().getLive().getBuyNum());
            }
        }
        lplayer.setData(data);
        liveInfoFragment.setData(data);
        livePdfFragment.setData(data.getData().getLive().getCoursewareCoverUrl());
        livePDF = new LivePDF(this, vpPDF, data.getData().getLive().getCoursewareUrl(),
                data.getData().getLive().getId(),
                data.getData().getLive().getTitle(),
                data.getData().getLive().isPay(), rvBrowse, flBrowse, ivPreview, new LivePDF.BuyLive() {
            @Override
            public void buy() {
                if (linPdf.getVisibility() == View.GONE)
                    linPdf.setVisibility(View.VISIBLE);
                if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    startActivity(new Intent(getBaseContext(), PayActivity.class)
                            .putExtra("price", dataDetail.getData().getLive().getPrice())
                            .putExtra("title", getString(R.string.live_buy_title))
                            .putExtra("id", id)
                            .putExtra("type", 2)
                    );
                } else {
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                }
            }
        });
        addSubscription(RxBus.getInstance().getEvent(TIMMessage.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<TIMMessage>() {
                    @Override
                    public void onNext(TIMMessage msg) {
                        screenData(msg);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
        setData(data);
    }

    @Override
    public void showOrder(OrderData order) {

    }

    @Override
    public void showNotifiSuccess() {
        isNotifi = true;
        liveInfoFragment.showNotifiSuccess();
        if (!isPay) {
            showRemindPop(null);
        } else {
            ToastUtils.show(LiveDetailActivity.this, getString(R.string.live_remind_success));
        }
    }

    @Override
    public void showUnnotifiSuccess() {
        isNotifi = false;
        liveInfoFragment.showUnnotifiSuccess();
        ToastUtils.show(LiveDetailActivity.this, getString(R.string.live_unremind_success));
    }

    @Override
    public void showLiveDetailError(int code, String msg) {
        loadingDialog.dismiss();
        if (code == 3000) {
            reLogin();
        }
    }

    private void screenData(TIMMessage msg) {
        msg.timestamp();
        for (int i = 0; i < msg.getElementCount(); i++) {
            TIMElem timElem = msg.getElement(i);
            if (timElem instanceof TIMTextElem) {
                TIMTextElem elem = (TIMTextElem) timElem;
                TIMElemType elemType = timElem.getType();
                Log.e(TAG, "screenData: ----------------" + elem.getText() + "----" + msg.getConversation().getType());
                if (elemType == TIMElemType.Text) {
                    if (msg.getConversation().getType() == TIMConversationType.Group && !elem.getText().equals("Teacher_Hang_Up_Sig")) {
                        IMDataBean imDataBean = GsonUtils.format(elem.getText(), IMDataBean.class);
                        if(imDataBean!=null&&!IMHelper.TEACHER_EXIT_ROOM.equals(imDataBean.getNews()))
                        groupMsg(imDataBean, msg.getSender());
                        else if(imDataBean!=null&&IMHelper.TEACHER_EXIT_ROOM.equals(imDataBean.getNews())){
                            ToastUtils.show(this, "ライブ配信はすでに終了しました");
                            RxBus.getInstance().post(new LiveEndAction());
                            stopRTC();
                            finish();
                        }

                    } else if (msg.getConversation().getType() == TIMConversationType.C2C) {
                        sysMsg(elem.getText(), msg.getSender());
                    } else if (elem.getText().equals("Teacher_Hang_Up_Sig")) {
                        ToastUtils.show(this, "質問リクエストを切る");
                        stopRTC();
                    }
                }
            }
        }
    }

    private void groupMsg(IMDataBean imInfo, String id) {
        if (id.equals(dataDetail.getData().getLive().getLecturerIdentifier())) {
            imInfo.setTeacher(true);
        }
        listMsg.add(imInfo);
        if (!lplayer.isFull) {
            imLiveFragment.setData(imInfo);
        } else {
            lplayer.setMsgData(imInfo);
        }
    }

    private void sysMsg(String content, String id) {
        Log.e(TAG, "sysMsg: ------->TEACHER_HANG_UP---"+content );
        try {
            IMDataBean imInfo = GsonUtils.format(content, IMDataBean.class);
            if (imInfo != null) {
                if (IMHelper.TEACHER_HANG_UP.equals(imInfo.getNews())) {
                    Log.e(TAG, "sysMsg: ------->TEACHER_HANG_UP" );
                    ToastUtils.show(this, "質問リクエストを切る");
                    stopRTC();
                    lplayer.handUpSuccess();
                } else if (IMHelper.WHEAT_INDENTIFIER_C.equals(imInfo.getNews())) {
                    stopRTC();
                    lplayer.handUpSuccess();
                }
            } else {
                if (IMHelper.WHEAT_INDENTIFIER_S.equals(content)) {
                    showRTCPop();
                }else if(IMHelper.TEACHER_HANG_UP.equals(content)){
                    Log.e(TAG, "sysMsg: ------->TEACHER_HANG_UP" );
                    ToastUtils.show(this, "質問リクエストを切る");
                    lplayer.handUpSuccess();
                    stopRTC();
                }else if (IMHelper.TEACHER_EXIT_ROOM.equals(content)){
                    ToastUtils.show(this, "ライブ配信はすでに終了しました");
                    RxBus.getInstance().post(new LiveEndAction());
                    stopRTC();
                    finish();
                }
            }
        } catch (Exception e) {
            if (IMHelper.WHEAT_INDENTIFIER_S.equals(content)) {
                showRTCPop();
            }else if (IMHelper.TEACHER_EXIT_ROOM.equals(content)){
                RxBus.getInstance().post(new LiveEndAction());
                ToastUtils.show(this, "ライブ配信はすでに終了しました");
                stopRTC();
                finish();
            }else if(IMHelper.TEACHER_HANG_UP.equals(content)){
                Log.e(TAG, "sysMsg: ------->TEACHER_HANG_UP" );
                ToastUtils.show(this, "質問リクエストを切る");
                lplayer.handUpSuccess();
                stopRTC();
            }
        }


    }

    private void initStream() {
        mMainHandler = new Handler();
        mStreamer = new KSYAgoraStreamer(this);
        mStreamer.setUrl("rtmp://zyph.forcetechcloud.com/lzy/7d49f4257f7d4a2791b3e60bbd160ed8");
        mStreamer.setPreviewFps(15);
        mStreamer.setTargetFps(15);
        mStreamer.setVideoKBitrate(800 * 3 / 4, 800, 800 / 4);
        mStreamer.setAudioKBitrate(48);
        mStreamer.setPreviewResolution(StreamerConstants.VIDEO_RESOLUTION_360P);
        mStreamer.setTargetResolution(StreamerConstants.VIDEO_RESOLUTION_360P);
        mStreamer.setVideoCodecId(AVConst.CODEC_ID_AVC);
        mStreamer.setEncodeMethod(StreamerConstants.ENCODE_METHOD_SOFTWARE);
        mStreamer.setVideoEncodeScene(VideoEncodeFormat.ENCODE_SCENE_DEFAULT);
        mStreamer.setVideoEncodeProfile(VideoEncodeFormat.ENCODE_PROFILE_LOW_POWER);
        mStreamer.setRotateDegrees(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mStreamer.setRotateDegrees(90);
        mStreamer.setEnableAutoRestart(true, 3000);
        mStreamer.setAudioMode(AudioManager.MODE_NORMAL);
        mStreamer.setOnInfoListener(mOnInfoListener);
        mStreamer.setOnErrorListener(mOnErrorListener);
        mStreamer.setOnLogEventListener(mOnLogEventListener);
        mStreamer.getImgTexFilterMgt().setOnErrorListener(new ImgTexFilterBase.OnErrorListener() {
            @Override
            public void onError(ImgTexFilterBase filter, int errno) {
                mStreamer.getImgTexFilterMgt().setFilter(mStreamer.getGLRender(),
                        ImgTexFilterMgt.KSY_FILTER_BEAUTY_DISABLE);
            }
        });
        mStreamer.setSpeakerphone(true);
    }

    private void setCameraAntiBanding50Hz() {
        Camera.Parameters parameters = mStreamer.getCameraCapture().getCameraParameters();
        if (parameters != null) {
            parameters.setAntibanding(Camera.Parameters.ANTIBANDING_50HZ);
            mStreamer.getCameraCapture().setCameraParameters(parameters);
        }
    }

    private KSYStreamer.OnInfoListener mOnInfoListener = new KSYStreamer.OnInfoListener() {
        @Override
        public void onInfo(int what, int msg1, int msg2) {
            Log.e(TAG, "onInfo: ------" + what + "--------" + msg1 + "-------" + msg2);
            switch (what) {
                case StreamerConstants.KSY_STREAMER_CAMERA_INIT_DONE:
                    Log.d(TAG, "KSY_STREAMER_CAMERA_INIT_DONE");
                    setCameraAntiBanding50Hz();
                    isPreper = true;
//                    startStream();
                    break;
                case StreamerConstants.KSY_STREAMER_OPEN_STREAM_SUCCESS:
                    Log.d(TAG, "KSY_STREAMER_OPEN_STREAM_SUCCESS");

                    break;
                case StreamerConstants.KSY_STREAMER_OPEN_FILE_SUCCESS:
                    Log.d(TAG, "KSY_STREAMER_OPEN_FILE_SUCCESS");

                    break;
                case StreamerConstants.KSY_STREAMER_FRAME_SEND_SLOW:
                    Log.d(TAG, "KSY_STREAMER_FRAME_SEND_SLOW " + msg1 + "ms");
                    break;
                case StreamerConstants.KSY_STREAMER_EST_BW_RAISE:
                    Log.d(TAG, "BW raise to " + msg1 / 1000 + "kbps");
                    break;
                case StreamerConstants.KSY_STREAMER_EST_BW_DROP:
                    Log.d(TAG, "BW drop to " + msg1 / 1000 + "kpbs");
                    break;
                default:
                    Log.d(TAG, "OnInfo: " + what + " msg1: " + msg1 + " msg2: " + msg2);
                    break;
            }
        }
    };

    private StatsLogReport.OnLogEventListener mOnLogEventListener =
            new StatsLogReport.OnLogEventListener() {
                @Override
                public void onLogEvent(StringBuilder singleLogContent) {
                    Log.i(TAG, "***onLogEvent : " + singleLogContent.toString());
                }
            };


    private KSYStreamer.OnErrorListener mOnErrorListener = new KSYStreamer.OnErrorListener() {
        @Override
        public void onError(int what, int msg1, int msg2) {
            Log.e(TAG, "onError: ------" + what + "--------" + msg1 + "-------" + msg2);
            switch (what) {
                case StreamerConstants.KSY_STREAMER_ERROR_DNS_PARSE_FAILED:
                    Log.d(TAG, "KSY_STREAMER_ERROR_DNS_PARSE_FAILED");
                    break;
                case StreamerConstants.KSY_STREAMER_ERROR_CONNECT_FAILED:
                    Log.d(TAG, "KSY_STREAMER_ERROR_CONNECT_FAILED");
                    break;
                case StreamerConstants.KSY_STREAMER_ERROR_PUBLISH_FAILED:
                    Log.d(TAG, "KSY_STREAMER_ERROR_PUBLISH_FAILED");
                    break;
                case StreamerConstants.KSY_STREAMER_ERROR_CONNECT_BREAKED:
                    Log.d(TAG, "KSY_STREAMER_ERROR_CONNECT_BREAKED");
                    break;
                case StreamerConstants.KSY_STREAMER_ERROR_AV_ASYNC:
                    Log.d(TAG, "KSY_STREAMER_ERROR_AV_ASYNC " + msg1 + "ms");
                    break;
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNSUPPORTED:
                    Log.d(TAG, "KSY_STREAMER_VIDEO_ENCODER_ERROR_UNSUPPORTED");
                    break;
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNKNOWN:
                    Log.d(TAG, "KSY_STREAMER_VIDEO_ENCODER_ERROR_UNKNOWN");
                    break;
                case StreamerConstants.KSY_STREAMER_AUDIO_ENCODER_ERROR_UNSUPPORTED:
                    Log.d(TAG, "KSY_STREAMER_AUDIO_ENCODER_ERROR_UNSUPPORTED");
                    break;
                case StreamerConstants.KSY_STREAMER_AUDIO_ENCODER_ERROR_UNKNOWN:
                    Log.d(TAG, "KSY_STREAMER_AUDIO_ENCODER_ERROR_UNKNOWN");
                    break;
                case StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_START_FAILED:
                    Log.d(TAG, "KSY_STREAMER_AUDIO_RECORDER_ERROR_START_FAILED");
                    break;
                case StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_UNKNOWN:
                    Log.d(TAG, "KSY_STREAMER_AUDIO_RECORDER_ERROR_UNKNOWN");
                    break;
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_UNKNOWN:
                    Log.d(TAG, "KSY_STREAMER_CAMERA_ERROR_UNKNOWN");
                    break;
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_START_FAILED:
                    Log.d(TAG, "KSY_STREAMER_CAMERA_ERROR_START_FAILED");
                    break;
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_SERVER_DIED:
                    Log.d(TAG, "KSY_STREAMER_CAMERA_ERROR_SERVER_DIED");
                    break;
                //Camera was disconnected due to use by higher priority user.
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_EVICTED:
                    Log.d(TAG, "KSY_STREAMER_CAMERA_ERROR_EVICTED");
                    break;
                default:
                    Log.d(TAG, "what=" + what + " msg1=" + msg1 + " msg2=" + msg2);
                    break;
            }
            switch (what) {
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_UNKNOWN:
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_START_FAILED:
                case StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_START_FAILED:
                case StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_UNKNOWN:
                    break;
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_SERVER_DIED:
                    mStreamer.stopCameraPreview();
                    mMainHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 5000);
                    break;
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_CLOSE_FAILED:
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_ERROR_UNKNOWN:
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_OPEN_FAILED:
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_FORMAT_NOT_SUPPORTED:
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_WRITE_FAILED:
                    break;
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNSUPPORTED:
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNKNOWN: {
//                    handleEncodeError();
                    stopStream();
                    mMainHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startStream();
                        }
                    }, 3000);
                }
                break;
                default:
                    if (mStreamer.getEnableAutoRestart()) {
                        mRecording = false;
                    } else {
                        stopStream();
                        mMainHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startStream();
                            }
                        }, 3000);
                    }
                    break;
            }
        }
    };

    private void startStream() {
        startRTC(groupNum);
    }

    private void startRTC(String roomId) {
        mStreamer.setRTCSubScreenRect(0.65f, 0.f, 0.3f, 0.35f, KSYAgoraStreamer
                .SCALING_MODE_CENTER_CROP);
        mStreamer.setRTCMainScreen(KSYAgoraStreamer.RTC_MAIN_SCREEN_REMOTE);
        mStreamer.startRTC(roomId);
    }


    private void stopStream() {
        stopRTC();
    }

    private void stopRTC() {
        mStreamer.stopRTC();
    }


    //  加入房间成功
    private void joinSuccess() {
        IMHelper.getInstance().sendAddSuccess(new TIMValueCallBack() {
            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onSuccess(Object o) {
                Log.e("sendjoin", "------------>onSuccess");
            }
        });
    }

    //  学生同意连麦
    private void stuAgreeIndentifier() {
        IMHelper.getInstance().stuAgreeIndentifier(new TIMValueCallBack() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(Object o) {
                lplayer.microConnect();
            }
        });
        hasIndentifier = true;
        startStream();
    }

    private void userHangUp() {
        String path = "";
        if (BIZAIAApplication.getInstance().getUserBean() != null)
            path = BIZAIAApplication.getInstance().getUserBean().getAvatarUrl();
        IMHelper.getInstance().stuDisAgreeIndentifier(AddressConfiguration.MAIN_PATH + path,
                BIZAIAApplication.getInstance().getUserBean().getNickname(),
                BIZAIAApplication.getInstance().getMemberAccount(), new TIMValueCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e(TAG, "userHangUp:----- error--" + i + "----" + s);
                    }

                    @Override
                    public void onSuccess(Object o) {
                        Log.e(TAG, "userHangUp:----- success------>" + lplayer.isHandUp);
                        if (lplayer.isHandUp)
                            ToastUtils.show(LiveDetailActivity.this, R.string.microphone_request_cancelled);
                        lplayer.handUpSuccess();
                    }
                });
    }

    private void stuHangUp() {
        String path = "";
        if (BIZAIAApplication.getInstance().getUserBean() != null)
            path = BIZAIAApplication.getInstance().getUserBean().getAvatarUrl();
        IMHelper.getInstance().stuHangUpIndentifier(AddressConfiguration.MAIN_PATH + path,
                BIZAIAApplication.getInstance().getUserBean().getNickname(),
                BIZAIAApplication.getInstance().getMemberAccount(), new TIMValueCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e(TAG, "userHangUp:----- error--" + i + "----" + s);
                    }

                    @Override
                    public void onSuccess(Object o) {
                        Log.e(TAG, "userHangUp:----- success");
                        stopRTC();
                        if (lplayer.isHandUp && hasIndentifier)
                            ToastUtils.show(LiveDetailActivity.this, getString(R.string.microphone_disconnect));
                        lplayer.handUpSuccess();
                    }
                });
    }

    private void handsUp() {
        String path = "";
        if (BIZAIAApplication.getInstance().getUserBean() != null)
            path = BIZAIAApplication.getInstance().getUserBean().getAvatarUrl();
        IMHelper.getInstance().requestIndentifier(AddressConfiguration.MAIN_PATH + path,
                BIZAIAApplication.getInstance().getUserBean().getNickname(),
                BIZAIAApplication.getInstance().getMemberAccount(), new TIMValueCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e(TAG, "handsUp:----- error--" + i + "----" + s);
                        ToastUtils.show(LiveDetailActivity.this, R.string.microphone_request_not_sent);
                    }

                    @Override
                    public void onSuccess(Object o) {
                        Log.e(TAG, "handsUp:----- success");
                        lplayer.handUpSuccess();
                        ToastUtils.show(LiveDetailActivity.this, getString(R.string.microphone_request_sent));
                    }
                });
    }

    private void exitRoom() {
        IMHelper.getInstance().stuExitIndentifier(null);
        IMHelper.getInstance().quitGroup(groupNum, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess() {
                Log.e(TAG, "onSuccess: ---------------------->exit room" );
            }
        });
    }

    private void showRTCPop() {
        if (forgetPop != null)
            forgetPop.dismiss();
        forgetPop = new ForgetPop(getApplicationContext(), back_img, R.layout.pop_im) {
            @Override
            public void viewInit() {
                ((TextView) findViewById(R.id.content)).setText(R.string.live_im_enter);
                TextView start = (TextView) findViewById(R.id.tv_cancel);
                start.setText(R.string.live_im_start);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forgetPop.dismiss();
                        stuAgreeIndentifier();
                    }
                });
                TextView cancel = (TextView) findViewById(R.id.tv_sure);
                cancel.setText(R.string.live_im_cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forgetPop.dismiss();
                        userHangUp();
                    }
                });
                ImageView ivClose = (ImageView) findViewById(R.id.ivClose);
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        forgetPop.dismiss();
                        userHangUp();
                    }
                });
            }
        };
        forgetPop.show();
    }

    private void showRemindPop(final String content) {
        if (forgetPop != null)
            forgetPop.dismiss();
        forgetPop = new ForgetPop(getApplicationContext(), back_img, R.layout.pop_im) {
            @Override
            public void viewInit() {
                if (content == null)
                    ((TextView) findViewById(R.id.content)).setText(R.string.live_remind_txt);
                else
                    ((TextView) findViewById(R.id.content)).setText(content);
                TextView start = (TextView) findViewById(R.id.tv_cancel);
                start.setText(R.string.live_remind_enter);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                            startActivity(new Intent(getBaseContext(), PayActivity.class)
                                    .putExtra("price", dataDetail.getData().getLive().getPrice())
                                    .putExtra("id", id)
                                    .putExtra("title", getString(R.string.live_buy_title))
                                    .putExtra("type", 2)
                            );
                        } else {
                            startActivity(new Intent(getBaseContext(), LoginActivity.class));
                        }
                        forgetPop.dismiss();
                    }
                });
                TextView cancel = (TextView) findViewById(R.id.tv_sure);
                cancel.setText(R.string.live_remind_cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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

    @Override
    public void start() {
        lplayer.start();
    }


    private boolean shouldEnd;

    @Override
    public void stop() {
        shouldEnd = true;
        liveStateAPI.getDetail(id);
    }

    @Override
    public void pause() {

    }


    @Override
    public void fullScreen() {
        runOnUiThread(new Runnable() {
            public void run() {
                rlInput.setVisibility(View.GONE);
                viewBar.clearAnimation();
                viewBar.setVisibility(View.GONE);
                if (!listMsg.isEmpty())
                    lplayer.showFull(listMsg);
            }
        });

    }

    @Override
    public void zoomOut() {
        if (posi == 0) {
            rlInput.setVisibility(View.VISIBLE);
            if (!listMsg.isEmpty())
                imLiveFragment.showZoom(listMsg);
        }
    }

    @Override
    public void hide() {
        Log.e(TAG, "hide: -------------->input hide");
        if (rlInput.getVisibility() == View.VISIBLE)
            rlInput.setVisibility(View.GONE);
    }

    @Override
    public void share() {
        if (dataDetail != null && dataDetail.getData() != null
                && mLiveDetailBean != null && mLiveDetailBean.getData() != null
                && mLiveDetailBean.getData().getLive() != null) {
            ShareDialog.share(LiveDetailActivity.this, dataDetail.getData().getLive().getTitle()
                    , dataDetail.getData().getLive().getIntroduction()
                    , AddressConfiguration.MAIN_PATH + mLiveDetailBean.getData().getLive().getCoverUrl()
                    , dataDetail.getData().getLive().getShareUrl());
        }
    }

    @Override
    public void faYan() {
        rlInput.setVisibility(View.VISIBLE);
    }

    @Override
    public void pinLun(String content) {
        sendMsg(content);
    }

    @Override
    public void collect() {
        if (isCollection)
            liveThumbUpAPI.thumbDown(id);
        else
            liveThumbUpAPI.thumbUp(id);
    }

    @Override
    public void kejian(boolean isShow) {
        if (livePDF != null && livePDF.isSaved()) {
            if (!isShow) {
                linPdf.setVisibility(View.VISIBLE);
            } else {
                linPdf.setVisibility(View.GONE);
            }
        } else {
            ToastUtils.show(this, R.string.live_pdf_unsave);
        }
    }


    @Override
    public void microphone() {
        handsUp();
    }

    @Override
    public void guanZhu() {
        if (isAttetion) {
            isAttentionAPI.addAttention(orgId, 2);
        } else {
            isAttentionAPI.addAttention(orgId, 1);
        }
    }

    @Override
    public void hangUp() {
        if (!hasIndentifier) {
            userHangUp();
        } else {
            stuHangUp();
        }
    }

    @Override
    public void tryAndSee() {
        Log.e(TAG, "handleMessage: --------->tryAndSee");
        showPayPop();
    }

    @Override
    public void hasSeen() {
        Log.e(TAG, "handleMessage: --------->hasSeen");
        showPayPop();
    }

    @Override
    public void pay() {
        showRemindPop("オンライン予約");
    }


    private Handler payHandler;
    private int timeNum = 120;

    private void showPayPop() {
        if (forgetPop != null)
            forgetPop.dismiss();
        forgetPop = new ForgetPop(getApplicationContext(), back_img, R.layout.pop_im) {
            @Override
            public void viewInit() {
                ((TextView) findViewById(R.id.content)).setText(R.string.live_pay_tips);
                TextView start = (TextView) findViewById(R.id.tv_cancel);
                start.setText(R.string.live_pay_enter);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        payHandler.removeMessages(0);
                        if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                            startActivity(new Intent(getBaseContext(), PayActivity.class)
                                    .putExtra("price", dataDetail.getData().getLive().getPrice())
                                    .putExtra("id", id)
                                    .putExtra("title", getString(R.string.live_buy_title))
                                    .putExtra("type", 2)
                            );
                            forgetPop.dismiss();
                        } else {
                            TextView textView = (TextView) forgetPop.findViewById(R.id.tv_sure);
                            textView.setText(R.string.live_remind_cancel);
                            startActivity(new Intent(getBaseContext(), LoginActivity.class));
                        }
                    }
                });
                TextView cancel = (TextView) findViewById(R.id.tv_sure);
                cancel.setText(getResources().getString(R.string.live_pay_cancel) + " " + timeNum + "s");
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


    @Override
    public void showIsAttention(boolean isAttention) {
        this.isAttetion = isAttention;
        lplayer.setAttentionState(isAttention);
        if (isAttention) {
            ivAttention.setImageResource(R.drawable.icon_jiaguanzhu_sel);
        } else {
            ivAttention.setImageResource(R.drawable.icon_jiaguanzhu);
        }
    }

    @Override
    public void showAddAttention(boolean isAttention) {
        if (isAttention) {
            this.isAttetion = true;
            lplayer.setAttentionState(true);
            ivAttention.setImageResource(R.drawable.icon_jiaguanzhu_sel);
            ToastUtils.show(LiveDetailActivity.this, dataDetail.getData().getLive().getOrganizationName() + getString(R.string.live_attention));
        } else {
            ToastUtils.show(LiveDetailActivity.this, R.string.live_attention_erro);
        }
    }

    @Override
    public void showDelAttention(boolean isAttention) {
        if (isAttention) {
            this.isAttetion = false;
            lplayer.setAttentionState(false);
            ivAttention.setImageResource(R.drawable.icon_jiaguanzhu);
            ToastUtils.show(LiveDetailActivity.this, dataDetail.getData().getLive().getOrganizationName() + getString(R.string.live_dis_attention));
        } else {
            ToastUtils.show(LiveDetailActivity.this, R.string.live_dis_attention_error);
        }
    }

    @Override
    public void showAttentionError() {

    }


    private boolean isPause;

    @Override
    protected void onPause() {
        lplayer.pause();
        isPause = true;
        super.onPause();
        mStreamer.onPause();
        mStreamer.setUseDummyAudioCapture(true);
        mStreamer.stopCameraPreview();
    }


    @Override
    protected void onDestroy() {
        wakeLock.release();
        lplayer.stop();
        mStreamer.setOnLogEventListener(null);
        mStreamer.release();
        if (livePDF != null)
            livePDF.onDestroy();
//        IMHelper.getInstance().imLoginout();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isPause) {
            isPause = false;
            lplayer.start();
        }
        mStreamer.onResume();
        mStreamer.setUseDummyAudioCapture(false);

    }

    private LiveDetailBean mLiveDetailBean;

    private void setData(LiveDetailBean data) {
        mLiveDetailBean = data;
        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + data.getData().getLive().getOrganizationLogo(),
                rivIcon, ImageLoaderUtils.getImageHighQualityOptions());
        tvName.setText(data.getData().getLive().getLecturer());
        if ("0".equals(data.getData().getLive().getStatus())) {
            tvLiveState.setText(R.string.live_state_advance);
        } else if ("1".equals(data.getData().getLive().getStatus())) {
            tvLiveState.setText(R.string.live_state_living);
        }
    }

    @Override
    public void showLiveState(LiveState data) {
        if (this.dataDetail != null && "1".equals(this.dataDetail.getData().getLive().getStatus())) {
            tvNumber.setText(getResources().getString(R.string.live_online_num) + "  " + data.getData().getOnlineMemberNum());
            lplayer.setNumber(data.getData().getOnlineMemberNum() + "");
            if (shouldEnd && data.getData().isLecturerOnline()) {
                shouldEnd = false;
                lplayer.start();
            } else if (!data.getData().isLecturerOnline()) {
                ToastUtils.show(LiveDetailActivity.this, getString(R.string.live_has_end));
            }
        }
    }

    @Override
    public void showLiveStateError(int code, String msg) {
        if (code == 3000) {
            reLogin();
        }
    }

    private boolean isNotifi;

    @Override
    public void remind() {
        if (StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        if (isNotifi) {
            liveDetailAPI.setNotifi(id, false);
        } else {
            liveDetailAPI.setNotifi(id, true);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (lplayer.onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void showThumbUp(ThumbState data) {
        this.isCollection = true;
        lplayer.setCollectState(true);
        ToastUtils.show(LiveDetailActivity.this, R.string.live_collection);

    }

    @Override
    public void showThumbDown(ThumbState data) {
        this.isCollection = false;
        lplayer.setCollectState(false);
        ToastUtils.show(LiveDetailActivity.this, R.string.live_like_cancel);

    }

    @Override
    public void showThumbState(ThumbState data) {
        if ("up".equals(data.getThumb())) {
            this.isCollection = true;
            lplayer.setCollectState(true);
        } else if ("down".equals(data.getThumb())) {
            this.isCollection = false;
            lplayer.setCollectState(false);
        }
    }

    @Override
    public void showThumbUpError(int code, String msg) {

    }

    @Override
    public void onRelogin() {
        reLogin();
    }


    public void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                main.getWindowVisibleDisplayFrame(rect);
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                if (mainInvisibleHeight > 100) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                    Log.e(TAG, "onGlobalLayout: --------------->"+srollHeight);
                    main.scrollTo(0, srollHeight);
                } else {
                    main.scrollTo(0, 0);
                }
            }
        });
    }

    private void hideKeyboard() {
//        rlInput.setVisibility(View.GONE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etImInputAll.getWindowToken(), 0);
        etImInputAll.clearFocus();
    }

    @Override
    public void show() {
//        if(livePDF.isSaved()) {
        if (linPdf.getVisibility() == View.GONE)
            linPdf.setVisibility(View.VISIBLE);
//        }else{
//            ToastUtils.show(this,R.string.live_pdf_unsave);
//        }
    }
}
