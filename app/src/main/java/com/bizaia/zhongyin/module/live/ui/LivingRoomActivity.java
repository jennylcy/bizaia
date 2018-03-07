package com.bizaia.zhongyin.module.live.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.common.ui.LoadingDialog;
import com.bizaia.zhongyin.module.live.action.IMLoginSuccessAction;
import com.bizaia.zhongyin.module.live.action.LoadPDFAction;
import com.bizaia.zhongyin.module.live.action.LoadSuccessPDFAction;
import com.bizaia.zhongyin.module.live.adapter.DataImAdapterHelper;
import com.bizaia.zhongyin.module.live.agora.MediaManager;
import com.bizaia.zhongyin.module.live.agora.kit.KSYAgoraStreamer;
import com.bizaia.zhongyin.module.live.api.LiveDetailAPI;
import com.bizaia.zhongyin.module.live.api.LiveStateAPI;
import com.bizaia.zhongyin.module.live.api.StartStreamAPI;
import com.bizaia.zhongyin.module.live.contro.ITeacherContro;
import com.bizaia.zhongyin.module.live.contro.TeacherContro;
import com.bizaia.zhongyin.module.live.data.CameraStreamAddressEntity;
import com.bizaia.zhongyin.module.live.data.CoursewareStreamAddressEntity;
import com.bizaia.zhongyin.module.live.data.IMDataBean;
import com.bizaia.zhongyin.module.live.data.IMReDataBean;
import com.bizaia.zhongyin.module.live.data.LiveDetailBean;
import com.bizaia.zhongyin.module.live.data.LiveState;
import com.bizaia.zhongyin.module.live.imhelper.IMHelper;
import com.bizaia.zhongyin.module.live.iml.ILiveDetailView;
import com.bizaia.zhongyin.module.live.iml.ILiveStateView;
import com.bizaia.zhongyin.module.live.iml.IRequestIm;
import com.bizaia.zhongyin.module.live.iml.IStartStreamView;
import com.bizaia.zhongyin.module.login.data.UserBean;
import com.bizaia.zhongyin.module.monthly.action.HasDownloadAction;
import com.bizaia.zhongyin.module.monthly.service.DownLoadService;
import com.bizaia.zhongyin.module.monthly.ui.pdfdetail.PDFContract;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.repository.data.CoursewareBean;
import com.bizaia.zhongyin.repository.data.CoursewareBeanDB;
import com.bizaia.zhongyin.util.FilePathUtils;
import com.bizaia.zhongyin.util.FileUtils;
import com.bizaia.zhongyin.util.GsonUtils;
import com.bizaia.zhongyin.util.NetworkUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.ServiceUtils;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.ForgetPop;
import com.bizaia.zhongyin.widget.ScrollControlViewPager;
import com.bizaia.zhongyin.widget.ShareDialog;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreWrapper;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.ksyun.media.streamer.capture.camera.CameraTouchHelper;
import com.ksyun.media.streamer.encoder.VideoEncodeFormat;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterBase;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterMgt;
import com.ksyun.media.streamer.framework.AVConst;
import com.ksyun.media.streamer.kit.KSYStreamer;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.mob.MobSDK.getContext;

/**
 * Created by zyz on 2017/3/21.
 */

public class LivingRoomActivity extends BaseActivity implements ILiveDetailView, ITeacherContro, MediaManager.MediaUiHandler, IRequestIm, IStartStreamView, ILiveStateView {

    private final String TAG="LivingRoomActivity.this";
    @BindView(R.id.svHostCamera)
    GLSurfaceView svHostCamera;
    @BindView(R.id.rvImList)
    RecyclerView rvImList;
    @BindView(R.id.viewRight)
    View viewRight;
    @BindView(R.id.viewContro)
    View viewContro;
    @BindView(R.id.vpPdf)
    ScrollControlViewPager vpPdf;
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    @BindView(R.id.tvPlayerTitle)
    TextView tvPlayerTitle;

    private KSYAgoraStreamer mStreamer;
    private TeacherContro teacherContro;

    private boolean mRecording = false;
    private Handler mMainHandler;
    private final static int PERMISSION_REQUEST_CAMERA_AUDIOREC = 1;
    private boolean mIsCaling = false;

    private String initIM;
    private String url;
    private String title;
    private List<IMDataBean> listMsg;
    private List<IMReDataBean> lisRequst;

    private List<Object> listData;

    private boolean isPreper;
    private CustomAdapter dataLiveAdapterHelper;
    private     LoadMoreWrapper moreWrapper;
    private String groupNum;
    private boolean isLogin;
    private StartStreamAPI startStreamAPI;
    private long id;
    private PowerManager.WakeLock wakeLock;
    private CameraStreamAddressEntity cameraStreamAddressEntity;
    private CoursewareStreamAddressEntity coursewareStreamAddressEntity;

    ///

    private CoursewareBeanDB coursewareBeanDB ;

    private String pdfPath;
    private String pdfUrl;
    private PDFContract.Presenter presenter;
    private boolean isSaved = false;
    private int pageCount;
    boolean isLoadPDFError = false;
    private Disposable isRunningDisposable;
    private LoadingDialog loadingDialog;

    private boolean isHandUp;
    private ForgetPop forgetPop;
    private LiveStateAPI liveStateAPI;
    private long livingId;
    private boolean isStart;

    private LiveDetailAPI liveDetailAPI;
    private LiveDetailBean dataDetail;
    private boolean isRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_living_room);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        ButterKnife.bind(this);
        setViewParent(svHostCamera);
        coursewareBeanDB = new CoursewareBeanDB(getContext());
//        NotifyBarUtils.StatusBarLightMode(this);
        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();
        teacherContro = new TeacherContro((View)viewContro.getParent(),this);

        listData = new ArrayList<>();
        listMsg = new ArrayList<>();
        lisRequst = new ArrayList<>();

        startStreamAPI = new StartStreamAPI(this);
        liveStateAPI = new LiveStateAPI(this);

        rvImList.setLayoutManager(new LinearLayoutManager(getContext()));
        dataLiveAdapterHelper = new DataImAdapterHelper().getAdapter(getApplicationContext(), listData, false,this);
        moreWrapper = new LoadMoreWrapper(dataLiveAdapterHelper);
        moreWrapper.setLoadMoreView(getLoadMoreView());
        moreWrapper.setOnLoadMoreListener(null);
        moreWrapper.clearLoadMore(true);
        rvImList.setAdapter(moreWrapper);
        if(getIntent()!=null&& !StringUtils.isEmpty(getIntent().getStringExtra("url")))
        url = getIntent().getStringExtra("url");

        livingId = getIntent().getLongExtra("id",-1);
        pdfUrl = AddressConfiguration.MAIN_PATH+"download/pdf/2/"+livingId+"/"+getIntent().getStringExtra("pdfUrl");

        cameraStreamAddressEntity = (CameraStreamAddressEntity)getIntent().getSerializableExtra("cameraStreamAddress");
        coursewareStreamAddressEntity = (CoursewareStreamAddressEntity)getIntent().getSerializableExtra("coursewareStreamAddress");

        addSubscription(RxBus.getInstance().getEvent(IMLoginSuccessAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<IMLoginSuccessAction>(){
                    @Override
                    public void onNext(IMLoginSuccessAction value) {
                        if(!StringUtils.isEmpty(groupNum)&&coursewareStreamAddressEntity!=null) {
                            initIM(groupNum);
//                            initStream(coursewareStreamAddressEntity.getPushUrl());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

        tvPlayerTitle.setText(getIntent().getStringExtra("title"));

        if(getIntent()!=null)
            id = getIntent().getLongExtra("id",0);
        if(getIntent()!=null&& !StringUtils.isEmpty(getIntent().getStringExtra("roomNub"))) {
            groupNum = getIntent().getStringExtra("roomNub");
            initIM(groupNum);
            initStream(cameraStreamAddressEntity.getPushUrl());
        }
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
        loadPdf();
        rxActionInit();

        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();

        liveDetailAPI = new LiveDetailAPI(this);
        liveDetailAPI.getDetail(livingId);


        addSubscription(RxBus.getInstance().getEvent(HasDownloadAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<HasDownloadAction>() {
                    @Override
                    public void onNext(HasDownloadAction value) {
                        pdfPath = value.filePath;
                        pdfInit();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
        tvNumber.setText(getResources().getString(R.string.information_viewnum)+"0");
        Log.e(TAG, "onCreate: -------------------->"+ groupNum);
    }

    private void rxActionInit() {
        addSubscription(RxBus.getInstance().getEvent(LoadSuccessPDFAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoadSuccessPDFAction>() {
                    @Override
                    public void onNext(LoadSuccessPDFAction value) {
                        pdfPath = value.filePath;
                        coursewareBeanDB.update(id, BIZAIAApplication.getInstance().getUserBean().getId(),pdfPath);
                        isSaved=true;
                        pdfInit();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    private View getLoadMoreView() {
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                getBaseContext().getResources().getDimensionPixelSize(R.dimen.load_more_height));
        View loadMore = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_loadmore, null);
        loadMore.setLayoutParams(layoutParams);
        loadMore.setVisibility(View.GONE);
        return loadMore;
    }

    private void initIM(final String groupNum){
        Log.e("initIM","------------>onSuccess---"+groupNum);
        IMHelper.getInstance().joinGroup(groupNum, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.e("joingoup","------------>error---"+i+"-----"+s);
                IMHelper.getInstance().init(getApplicationContext());
                isLogin = false;
            }

            @Override
            public void onSuccess() {
                isLogin = true;
                IMHelper.getInstance().initSysSend(groupNum);
//                joinSuccess();
            }
        });
    }

    private void screenData(TIMMessage msg){
        for(int i=0;i<msg.getElementCount();i++) {
            TIMElem timElem = msg.getElement(i);
            if( timElem instanceof TIMTextElem) {
                TIMTextElem elem = (TIMTextElem)timElem;
                TIMElemType elemType = timElem.getType();
                Log.e(TAG, "screenData: ----------------"+ elem.getText()+"----"+msg.getConversation().getType());
                if (elemType == TIMElemType.Text) {
                    if (msg.getConversation().getType() == TIMConversationType.Group) {
                        groupMsg(GsonUtils.format(elem.getText(),IMDataBean.class));
                    } else if (msg.getConversation().getType() == TIMConversationType.C2C) {
                        sysMsg(elem.getText(),msg.getSender());
                    }
                }
            }
        }
    }

    private void groupMsg(IMDataBean imInfo){
        listMsg.add(imInfo);
        if(!isRequest) {
            listData.add(imInfo);
            moreWrapper.notifyDataSetChanged();
            rvImList.smoothScrollToPosition(dataLiveAdapterHelper.getItemCount() - 1);
        }
    }
    private  void sysMsg(String content,String id){
        try{
            if(IMHelper.STUDENT_ADD_ROOM.equals(content)){
                liveStateAPI.getDetail(livingId);
            }else if(IMHelper.STUDENT_EXIT_ROOM.equals(content)){
                liveStateAPI.getDetail(livingId);
            }else {
                IMDataBean dataBean= GsonUtils.format(content,IMDataBean.class);
                if(dataBean!=null){
                    Log.e(TAG, "sysMsg: ------------------->"+ dataBean.getNews());
                    if(IMHelper.STUDENT_HANG_UP.equals(dataBean.getNews())){
                        for(int i=0;i<listData.size();i++){
                            IMReDataBean imReDataBean = (IMReDataBean)listData.get(i);
                            if(imReDataBean.getSendId().equals(dataBean.getSendId())){
                                listData.remove(i);
                                lisRequst.remove(i);
                            }
                        }
                        moreWrapper.notifyDataSetChanged();
                        teacherContro.setRequest(lisRequst.size()+"");
                    }else if(IMHelper.WHEAT_INDENTIFIER_C.equals(dataBean.getNews())){
                        for(int i=0;i<listData.size();i++){
                            IMReDataBean imReDataBean = (IMReDataBean)listData.get(i);
                            if(imReDataBean.getSendId().equals(dataBean.getSendId())){
                                listData.remove(i);
                                lisRequst.remove(i);
                            }
                        }
                        moreWrapper.notifyDataSetChanged();
                        teacherContro.setRequest(lisRequst.size()+"");
                    }else if(IMHelper.WHEAT_INDENTIFIER_F.equals(dataBean.getNews())){
                        IMReDataBean imReDataBean = new IMReDataBean();
                        imReDataBean.setSendId(id);
                        imReDataBean.setNickName(dataBean.getNickName());
                        imReDataBean.setHeadImg(dataBean.getHeadImg());
                        imReDataBean.setNews(dataBean.getNews());
                        for(int i=0;i<lisRequst.size();i++){
                            if(lisRequst.get(i).getId().equals(imReDataBean.getId()))
                                return;
                        }
                        lisRequst.add(imReDataBean);
                        teacherContro.setRequest(lisRequst.size() + "");
                        if(isRequest) {
                            listData.add(imReDataBean);
                            moreWrapper.notifyDataSetChanged();
                        }
                    }
            }

            }
        }catch (Exception e){
        }

    }


    //  老师同意连麦
    private void agreeIndentifier(String userId){
        Log.e(TAG, "agreeIndentifier: --------------"+userId );
        IMHelper.getInstance().initPersonSend(userId);
        IMHelper.getInstance().agreeIndentifier(new TIMValueCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "agreeIndentifier: ------onError--------"+s+"---------"+i);
            }

            @Override
            public void onSuccess(Object o) {
                Log.e(TAG, "agreeIndentifier: ------onSuccess--------" );
            }
        });
    }

    private void hangUpIndentifier(final String userId, final int pos){
        IMHelper.getInstance().initPersonSend(userId);
        Log.e(TAG, "hangUpIndentifier: --------------"+userId );
        IMHelper.getInstance().hostHangUpIndentifier(new TIMValueCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "hangUpIndentifier: ------onError--------"+s+"---------"+i);
            }

            @Override
            public void onSuccess(Object o) {
                Log.e(TAG, "hangUpIndentifier: ------onSuccess--------" );
                isHandUp=false;
                for(int i=0;i<listData.size();i++){
                    IMReDataBean imReDataBean = (IMReDataBean)listData.get(i);
                    if(imReDataBean.getSendId().equals(userId)){
                        listData.remove(i);
                        lisRequst.remove(i);
                    }
                }
                moreWrapper.notifyDataSetChanged();
                requstPos=-1;
                teacherContro.setRequest(lisRequst.size()+"");
            }
        });
    }


    private void initStream(String url){
        mMainHandler = new Handler();
        mStreamer = new KSYAgoraStreamer(this);
        mStreamer.setUrl(url);
//        mStreamer.setUrl("rtmp://xlivepush.1905tech.com/lscym/933b2ac736eb44f587fb6c574f17d755");
        mStreamer.setPreviewFps(15);
        mStreamer.setTargetFps(15);
        mStreamer.setVideoKBitrate(800 * 3 / 4, 800, 800 / 4);
        mStreamer.setAudioKBitrate(48);
        mStreamer.setPreviewResolution(StreamerConstants.VIDEO_RESOLUTION_720P);
        mStreamer.setTargetResolution(StreamerConstants.VIDEO_RESOLUTION_480P);
        mStreamer.setVideoCodecId(AVConst.CODEC_ID_AVC);
        mStreamer.setEncodeMethod(StreamerConstants.ENCODE_METHOD_SOFTWARE);
        mStreamer.setVideoEncodeScene(VideoEncodeFormat.ENCODE_SCENE_DEFAULT);
        mStreamer.setVideoEncodeProfile(VideoEncodeFormat.ENCODE_PROFILE_LOW_POWER);
//        mStreamer.setRotateDegrees(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mStreamer.setRotateDegrees(90);
        mStreamer.setMuteAudio(true);
        mStreamer.setEnableAudioPreview(true);
        mStreamer.setDisplayPreview(svHostCamera);
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
        CameraTouchHelper cameraTouchHelper = new CameraTouchHelper();
        cameraTouchHelper.setCameraCapture(mStreamer.getCameraCapture());
        svHostCamera.setOnTouchListener(cameraTouchHelper);
        svHostCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacherContro.show();
            }
        });
        rvImList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacherContro.show();
            }
        });
        // set CameraHintView to show focus rect and zoom ratio
//        cameraTouchHelper.setCameraHintView(mCameraHintView);
//        mAutoStart = bundle.getBoolean(START_AUTO, false);
//        mPrintDebugInfo = bundle.getBoolean(SHOW_DEBUGINFO, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        startCameraPreviewWithPermCheck();
        mStreamer.onResume();
        mStreamer.setUseDummyAudioCapture(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        mStreamer.onPause();
        mStreamer.setUseDummyAudioCapture(true);
        mStreamer.stopCameraPreview();
    }

    private void stopPushPop(){
        if(forgetPop!=null)
            forgetPop.dismiss();
        forgetPop = new ForgetPop(getApplicationContext(), viewRight, R.layout.pop_im) {
            @Override
            public void viewInit() {
                ((TextView)findViewById(R.id.content)).setText(R.string.live_stop_pop);
                TextView start =(TextView)findViewById(R.id.tv_cancel);
                start.setText(R.string.mine_enter);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forgetPop.dismiss();
                        exitRoom();
                    }
                });
                TextView cancel =(TextView)findViewById(R.id.tv_sure);
                cancel.setText(R.string.btn_cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forgetPop.dismiss();
                    }
                });
                ImageView ivClose = (ImageView)findViewById(R.id.ivClose);
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
    public void onDestroy() {
        super.onDestroy();
        if (mMainHandler != null) {
            mMainHandler.removeCallbacksAndMessages(null);
            mMainHandler = null;
        }
        mStreamer.setOnLogEventListener(null);
        mStreamer.release();
    }



    private void startRTC(String roomId) {
        mStreamer.setMuteAudio(false);
        mStreamer.setRTCSubScreenRect(0.65f, 0.f, 0.3f, 0.35f, KSYAgoraStreamer
                    .SCALING_MODE_CENTER_CROP);
        mStreamer.setRTCMainScreen(KSYAgoraStreamer.RTC_MAIN_SCREEN_REMOTE);
        mStreamer.startRTC(roomId);
        mIsCaling = true;

    }



    private void stopRTC() {
        mStreamer.stopRTC();
        mIsCaling = false;
    }

    private void hangUp(){
            IMHelper.getInstance().hostHangUpIndentifier(new TIMValueCallBack (){

                @Override
                public void onError(int i, String s) {

                }

                @Override
                public void onSuccess(Object o) {
                   stopRTC();
                }
            });
    }

    private void exitRoom(){
            IMHelper.getInstance().hostExitIndentifier(new TIMValueCallBack() {
                @Override
                public void onError(int i, String s) {
                    Log.e(TAG, "onError: ----------------->hostExitIndentifier" );
                }

                @Override
                public void onSuccess(Object o) {
                    Log.e(TAG, "onSuccess: ----------------->hostExitIndentifier" );
                    IMHelper.getInstance().quitGroup(groupNum,null);
                    stopStream();
                    finish();
                }
            },GsonUtils.transformMsg(BIZAIAApplication.getInstance().getUserBean().getAvatarUrl(),
                    IMHelper.TEACHER_EXIT_ROOM,
                    BIZAIAApplication.getInstance().getUserBean().getNickname(),
                    BIZAIAApplication.getInstance().getMemberAccount()) );
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(isStart){
                stopPushPop();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN){
            Log.e(TAG, "onKeyDown: -------------->ACTION_DOWN");
            teacherContro.show();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void showLiveDetail(LiveDetailBean data) {
        dataDetail = data;
    }

    @Override
    public void showOrder(OrderData data) {

    }

    @Override
    public void showNotifiSuccess() {

    }

    @Override
    public void showUnnotifiSuccess() {

    }

    @Override
    public void onRelogin() {
        reLogin();
    }

    @Override
    public void showLiveDetailError(int code,String msg) {
      if(code==3000){
          reLogin();
      }
    }

    @Override
    public void startPush() {
        if(isPreper)
        startStream();
    }

    @Override
    public void stopPush() {
        stopPushPop();
    }

    @Override
    public void back() {
        if(isStart) {
            stopPushPop();
        }else{
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void share() {
        if(dataDetail!=null&&dataDetail.getData()!=null) {
            ShareDialog.share(LivingRoomActivity.this, dataDetail.getData().getLive().getTitle(),
                    dataDetail.getData().getLive().getIntroduction(), AddressConfiguration.MAIN_PATH + dataDetail.getData().getLive().getCoursewareCoverUrl(),
                    dataDetail.getData().getLive().getShareUrl());
        }
    }

    @Override
    public void showRequest() {
        if(!listData.isEmpty())
            listData.clear();
        isRequest =true;
        rvImList.removeAllViews();
        listData.addAll(lisRequst);
        Log.e(TAG, "showRequest: --------"+listData.size());
        moreWrapper.notifyDataSetChanged();
    }

    @Override
    public void showDiscuss() {
        if(!listData.isEmpty())
            listData.clear();
        isRequest =false;
        rvImList.removeAllViews();
        listData.addAll(listMsg);
        Log.e(TAG, "showDiscuss: --------"+listData.size());
        moreWrapper.notifyDataSetChanged();
        if(dataLiveAdapterHelper.getItemCount()>0)
        rvImList.smoothScrollToPosition(dataLiveAdapterHelper.getItemCount()-1);
    }

    @Override
    public void stopVideo() {
    }


    @Override
    public void onMediaEvent(int event, Object... data) {

    }

    private void startStream() {
        isStart =true;
        mStreamer.startStream();
        mRecording = true;
        startStreamAPI.start(id);
        startRTC(groupNum);
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
            Log.e(TAG, "onInfo: ------"+what+"--------"+msg1+"-------"+msg2 );
            switch (what) {
                case StreamerConstants.KSY_STREAMER_CAMERA_INIT_DONE:
                    Log.e(TAG, "KSY_STREAMER_CAMERA_INIT_DONE");
                    setCameraAntiBanding50Hz();
                    isPreper = true;
//                    startStream();
                    break;
                case StreamerConstants.KSY_STREAMER_OPEN_STREAM_SUCCESS:
                    Log.e(TAG, "KSY_STREAMER_OPEN_STREAM_SUCCESS");

                    break;
                case StreamerConstants.KSY_STREAMER_OPEN_FILE_SUCCESS:
                    Log.e(TAG, "KSY_STREAMER_OPEN_FILE_SUCCESS");

                    break;
                case StreamerConstants.KSY_STREAMER_FRAME_SEND_SLOW:
                    Log.e(TAG, "KSY_STREAMER_FRAME_SEND_SLOW " + msg1 + "ms");
                    break;
                case StreamerConstants.KSY_STREAMER_EST_BW_RAISE:
                    Log.d(TAG, "BW raise to " + msg1 / 1000 + "kbps");
                    break;
                case StreamerConstants.KSY_STREAMER_EST_BW_DROP:
                    Log.e(TAG, "BW drop to " + msg1 / 1000 + "kpbs");
                    break;
                default:
                    Log.e(TAG, "OnInfo: " + what + " msg1: " + msg1 + " msg2: " + msg2);
                    break;
            }
        }
    };

    private void startCameraPreviewWithPermCheck() {
        int cameraPerm = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int audioPerm = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (cameraPerm != PackageManager.PERMISSION_GRANTED ||
                audioPerm != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                Log.e(TAG, "No CAMERA or AudioRecord permission, please check");
                Toast.makeText(this, "No CAMERA or AudioRecord permission, please check",
                        Toast.LENGTH_LONG).show();
            } else {
                String[] permissions = {Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions,
                        PERMISSION_REQUEST_CAMERA_AUDIOREC);
            }
        } else {
            mStreamer.startCameraPreview();
        }
    }

    private void stopRecord() {
        mStreamer.stopRecord();
//        mIsFileRecording = false;
    }

    private void stopStream() {
        stopRTC();
        mStreamer.stopStream();
        mRecording = false;
        startStreamAPI.end(id);
    }

    private KSYStreamer.OnErrorListener mOnErrorListener = new KSYStreamer.OnErrorListener() {
        @Override
        public void onError(int what, int msg1, int msg2) {
            Log.e(TAG, "onError: ------"+what+"--------"+msg1+"-------"+msg2 );
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
                            startCameraPreviewWithPermCheck();
                        }
                    }, 5000);
                    break;
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_CLOSE_FAILED:
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_ERROR_UNKNOWN:
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_OPEN_FAILED:
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_FORMAT_NOT_SUPPORTED:
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_WRITE_FAILED:
                    stopRecord();
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

    private StatsLogReport.OnLogEventListener mOnLogEventListener =
            new StatsLogReport.OnLogEventListener() {
                @Override
                public void onLogEvent(StringBuilder singleLogContent) {
                    Log.i(TAG, "***onLogEvent : " + singleLogContent.toString());
                }
            };


    private int requstPos = -1;
    @Override
    public void agree(boolean agree,int pos) {
           if(agree){
               Log.e(TAG, "agree: ------------->"+pos );
               isHandUp=true;
               requstPos = pos;
               agreeIndentifier(lisRequst.get(pos).getSendId());
               IMReDataBean im = lisRequst.get(requstPos);
               im.setAgree(true);
               listData.set(requstPos, im);
               moreWrapper.notifyDataSetChanged();
           }else {
               hangUpIndentifier(lisRequst.get(pos).getSendId(),pos);
           }

    }

    @Override
    public void showLiveState(UserBean data) {

    }

    @Override
    public void showLiveState(LiveState data) {
        teacherContro.setNumber(getString(R.string.live_num)+data.getData().getOnlineMemberNum());
    }

    @Override
    public void showLiveStateError(int code ,String msg) {
        if(code==3000){
            reLogin();
        }
    }



    /**
     * load pdf
     */
    private void loadPdf() {
        CoursewareBean coursewareBean =coursewareBeanDB.queryBean(id, BIZAIAApplication.getInstance().getUserBean().getId());
        if(coursewareBean==null)
            isSaved=false;
        else {
            pdfPath = coursewareBean.getPdfUrl();
            isSaved = true;
        }

        if(!isSaved){
            String path = FilePathUtils.getDirDownloadPdfImgPath(this);
            File pdfFile = null;
            pdfFile = new File(path + "/" + title + "try" + ".pdf");
            if(pdfFile.exists()){
                pdfPath = pdfFile.getAbsolutePath();
                isSaved = true;
            }
        }

        if (!TextUtils.isEmpty(pdfPath)&&isSaved) {
//            ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.has_down_load) + pdfPath);
            pdfInit();
        }else if(!isSaved){
            if (NetworkUtils.getNetworkType(getBaseContext()) == NetworkUtils.NetworkType.NETWORK_NO) {
                if (!isSaved) {
                    ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.no_net));
                }
            } else if (NetworkUtils.getNetworkType(getBaseContext()) != NetworkUtils.NetworkType.NETWORK_WIFI) {
                ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.current_mobile_net));
            }
            starDownloadService();
        }
    }

    private void pdfInit() {
        ((PDFView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.fragment_monthly_pdf, null))
                .fromFile(new File(pdfPath))
                .onLoad(onLoadCompleteListener)
                .onError(onErrorListener)
                .load();
        vpPdf.setScrollable(true);
    }

    private OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
        @Override
        public void loadComplete(int nbPages) {
            Log.e(TAG, "loadComplete: " + nbPages);
            initViewPagerPdf(nbPages);
            pageCount = nbPages;
            loadingDialog.dismiss();
        }
    };

    private void initViewPagerPdf(final int nbPages) {
        vpPdf.setAdapter(
                new PagerAdapter() {
                    private ArrayList<PDFView> pdfViewArrayList = new ArrayList<>();


                    @Override
                    public int getCount() {
                        return nbPages;
                    }

                    @Override
                    public boolean isViewFromObject(View view, Object object) {
                        return view == object;
                    }

                    @Override
                    public Object instantiateItem(ViewGroup container, int position) {
                        PDFView pdfView;
                        if (pdfViewArrayList.size() == 0) {
                            pdfView = (PDFView) LayoutInflater.from(getBaseContext()).inflate(R.layout.fragment_monthly_pdf, null);
                        } else {
                            pdfView = pdfViewArrayList.remove(pdfViewArrayList.size() - 1);
                        }
                        pdfView.fromFile(new File(pdfPath))
                                .pages(position)
                                .enableSwipe(true)
                                .swipeHorizontal(true)
                                .enableDoubletap(true)
                                .onPageChange(onPageChangeListener)
                                .onPageScroll(onPageScrollListener)
                                .enableAnnotationRendering(true)
                                .password(null)
                                .scrollHandle(null)
                                .load();

                        container.addView(pdfView);
                        return pdfView;
                    }

                    @Override
                    public void destroyItem(ViewGroup container, int position, Object object) {
                        container.removeView((View) object);
                        pdfViewArrayList.add((PDFView) object);
                    }
                });
    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageChanged(int page, int pageCount) {
            Log.e(TAG, "onPageScrolled: " + page + "    " + pageCount);

        }
    };

    private OnPageScrollListener onPageScrollListener = new OnPageScrollListener() {
        @Override
        public void onPageScrolled(int page, float positionOffset) {
            Log.e(TAG, "onPageScrolled: " + page + "    " + positionOffset);
            if (page >= 1
                    || page <= 0
                    || Float.isNaN(page)) {
                vpPdf.setScrollable(true);
                Log.e(TAG, "onPageScrolled: ----------------->"+positionOffset+"----"+page );
            } else {
                vpPdf.setScrollable(false);
            }
        }
    };

    /**
     * start download service
     */
    private void starDownloadService() {
        Log.e(TAG, "canService: " + "download---------------->"+pdfUrl);
        if (!ServiceUtils.isRunning(getBaseContext(), DownLoadService.class)) {
            Intent intent = new Intent(this, DownLoadService.class);
            startService(intent);
            isRunningDisposable = Observable.interval(200, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread()).subscribe(
                    new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            Log.e(TAG, "canService: " + "post");
                            if (ServiceUtils.isRunning(getBaseContext(), DownLoadService.class)) {
                                RxBus.getInstance().post(new LoadPDFAction(id,title,pdfUrl));
                                isRunningDisposable.dispose();
                            }
                        }
                    },
                    new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                        }
                    });
            addSubscription(isRunningDisposable);
        } else {
            RxBus.getInstance().post(new LoadPDFAction(id,title,pdfUrl));
        }
    }

    private OnErrorListener onErrorListener = new OnErrorListener() {
        @Override
        public void onError(Throwable t) {
            loadingDialog.dismiss();
            if (!isLoadPDFError) {
                isLoadPDFError = true;
                if(BIZAIAApplication.getInstance().getUserBean()!=null)
                    coursewareBeanDB.delete(id, BIZAIAApplication.getInstance().getUserBean().getId());
                FileUtils.deleteFile(pdfPath);
                pdfPath=null;
                loadPdf();
                ToastUtils.showInUIThead(getBaseContext(),getString(R.string.file_load_error_down_load_again));
            }
        }
    };



}