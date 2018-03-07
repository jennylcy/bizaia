package com.bizaia.zhongyin.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.live.adapter.DataImAdapterHelper;
import com.bizaia.zhongyin.module.live.data.IMDataBean;
import com.bizaia.zhongyin.module.live.data.LiveDetailBean;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.repository.data.HasWartchBean;
import com.bizaia.zhongyin.repository.data.HasWartchBeanDB;
import com.bizaia.zhongyin.util.ImageLoaderUtils;
import com.bizaia.zhongyin.util.NotifyBarUtils;
import com.bizaia.zhongyin.util.OtherUtils;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.TimeUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreWrapper;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.forcetech.player.widget.ForceVideoView;

import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by zyz on 2017/3/8.
 */

public class LivePlayer extends RelativeLayout {


    private static final String TAG="LivePlayer.this";
    private ForceVideoView fvvMainPlayer;
    private ILivePlayer iLivePlayer;
    public boolean isFull=false;
    private Activity context;
    private View viewParent;
    private View viewPlayer;
    private ImageView ivCurtain;
    private boolean hasPay = false;

    public LivePlayer(Context context) {
        super(context);
    }

    public LivePlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LivePlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ILivePlayer getiLivePlayer() {
        return iLivePlayer;
    }

    public void setiLivePlayer(ILivePlayer iLivePlayer) {
        this.iLivePlayer = iLivePlayer;
    }

    private void initView(){
        viewParent = this;
        viewPlayer = LayoutInflater.from(getContext()).inflate(R.layout.view_player_im, this,
                false);
        viewIm = (View)viewPlayer.findViewById(R.id.viewIm);
        tvHostName = (TextView)viewPlayer.findViewById(R.id.tvHostName);
        btnCamera = (ImageView)viewPlayer.findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isHostCameraShow) {
                    isHostCameraShow =false;
                    rlHostCamera.setVisibility(View.GONE);
                }else{
                    isHostCameraShow =true;
                    rlHostCamera.setVisibility(View.VISIBLE);
                }
            }
        });
        rlHostCamera = (RelativeLayout)viewPlayer.findViewById(R.id.rlHostCamera);
        fvvHostPlayer = (ForceVideoView)viewPlayer.findViewById(R.id.fvvHostPlayer);
        relCamre = (RelativeLayout) viewPlayer.findViewById(R.id.relCamre);
        fvvHostPlayer.setRender(2);
//        fvvHostPlayer.setRealtime(true);
//        fvvHostPlayer.setRealtimeMaxDelay(1000);
        fvvHostPlayer.toggleAspectRatio();
        fvvHostPlayer.setOnVideoFrameTimestampListener(onVideoFrameTimestampListener);

        linHostCameraTips = (LinearLayout)viewPlayer.findViewById(R.id.linHostCameraTips);
        rvImFullList = (RecyclerView)viewPlayer.findViewById(R.id.rvImFullList);
        etImInput = (EditText)viewPlayer.findViewById(R.id.etImInput);
        tvSend = (TextView)viewPlayer.findViewById(R.id.tvSend);
        tvSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    ToastUtils.show(context, R.string.live_unlogin_error);
                    context.startActivity(new Intent(context, LoginActivity.class));
                    return;
                }
                if(!hasPay){
                    ToastUtils.show(context,R.string.send_not_buy);
                    iLivePlayer.pay();
                    return;
                }

                String content =etImInput.getText().toString().trim();
                if(!StringUtils.isEmpty(content)) {
                    iLivePlayer.pinLun(content);
                    etImInput.setText("");
                }else{
                    ToastUtils.show(context,context.getString(R.string.live_empty_error));
                }
            }
        });
        etImInput.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus&&StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())){
                        ToastUtils.show(context, R.string.live_unlogin_error);
                        context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
        listData = new ArrayList<>();
        ScrollSpeedLinearLayoutManger layoutManager = new ScrollSpeedLinearLayoutManger(getContext());
//        layoutManager.setStackFromEnd(true);
        rvImFullList.setLayoutManager(layoutManager);
        dataLiveAdapterHelper = new DataImAdapterHelper().getAdapter(getContext(), listData, false,null);
        moreWrapper = new LoadMoreWrapper(dataLiveAdapterHelper);
        moreWrapper.setLoadMoreView(getLoadMoreView());
        moreWrapper.setOnLoadMoreListener(null);
        moreWrapper.clearLoadMore(true);
        rvImFullList.setAdapter(moreWrapper);
        viewIm.setVisibility(View.GONE);
        fvvMainPlayer = (ForceVideoView)viewPlayer.findViewById(R.id.fvvMainPlayer);
        fvvMainPlayer.setRender(2);
//        fvvMainPlayer.setRealtime(true);
//        fvvMainPlayer.setRealtimeMaxDelay(1000);
//        fvvMainPlayer.getAudioSessionId()
        fvvMainPlayer.toggleAspectRatio();
        fvvMainPlayer.setAspectRatio(ForceVideoView.AspectRatio.AR_ASPECT_FIT_PARENT);
        fvvMainPlayer.setOnInfoListener(mOnInfoListener);
        fvvMainPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
        fvvMainPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        fvvMainPlayer.setOnCompletionListener(mOnCompletionListener);
        fvvMainPlayer.setOnErrorListener(mOnErrorListener);
        fvvMainPlayer.setOnVideoFrameTimestampListener(onVideoFrameCarTimestampListener);
        ivCurtain = (ImageView)viewPlayer.findViewById(R.id.ivCurtain);
        etImInput.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showKeyboard();
            }
        });
        addView(viewPlayer);
        initNormal();
        initImView();
        initFull();
        viewParent.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    if (!isControShow) {
                        mHandler.removeMessages(SHOW_HIDE);
                        mHandler.sendEmptyMessage(SHOW_CONTRO);
                    } else {
                        mHandler.removeMessages(SHOW_HIDE);
                        mHandler.sendEmptyMessage(SHOW_HIDE);
                    }
                    if (isFull)
                        iLivePlayer.hide();
                }
                return false;
            }
        });
//        viewParent.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etImInput.getWindowToken(), 0);
        etImInput.clearFocus();
    }

    private void showKeyboard() {
        etImInput.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etImInput, InputMethodManager.SHOW_FORCED);
    }



    private ImageView ivPandS;
    private ImageView ivFull;
    private View viewNormal;
    private void initNormal(){
        viewNormal = LayoutInflater.from(getContext()).inflate(R.layout.view_player_normal_contro, this,
               false);
        ivPandS = (ImageView) viewNormal.findViewById(R.id.ivPandS);
        ivPandS.setSelected(true);
        ivPandS.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
             if(view.isSelected()){
                 ivPandS.setSelected(false);
                 iLivePlayer.pause();
             }else{
                 ivPandS.setSelected(true);
                 iLivePlayer.start();
             }
           }
       });
        ivFull = (ImageView) viewNormal.findViewById(R.id.ivFull);
        ivFull.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLiving) {
//                    if (isFree)
                        context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                    else
//                        ToastUtils.show(context, R.string.live_unlogin_error);
                }
            }
        });
        addView(viewNormal);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        onScreenChanged(newConfig.orientation);
        super.onConfigurationChanged(newConfig);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                WindowManager.LayoutParams attr = context.getWindow().getAttributes();
                attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                context.getWindow().setAttributes(attr);
                context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                NotifyBarUtils.StatusBarLightMode(context);
            } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                NotifyBarUtils.transparencyBar(context);
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                context.getWindow().setAttributes(lp);
                context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        onScreenChanged();
        super.onAttachedToWindow();
    }

    private void onScreenChanged(int orientation) {
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params == null)
            return;


        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isFull = true;
            iLivePlayer.fullScreen();
            viewIm.clearAnimation();
            viewIm.setVisibility(View.VISIBLE);
            viewIm.requestLayout();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            viewFull.clearAnimation();
            viewFull.setVisibility(View.VISIBLE);
            viewNormal.clearAnimation();
            viewNormal.setVisibility(View.GONE);
            viewFull.requestLayout();
            viewNormal.requestLayout();
            viewParent.requestLayout();
        } else {
            isFull=false;
            iLivePlayer.zoomOut();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = (int) (getResources().getDisplayMetrics().widthPixels / 16 * 9.f);

            viewFull.clearAnimation();
            viewFull.setVisibility(View.GONE);
            viewIm.clearAnimation();
            viewIm.setVisibility(View.GONE);
            viewNormal.clearAnimation();
            viewNormal.setVisibility(View.VISIBLE);
            viewIm.requestLayout();
            viewFull.requestLayout();
            viewNormal.requestLayout();
            viewParent.requestLayout();
        }
        setLayoutParams(params);
        mHandler.sendEmptyMessage(SHOW_CONTRO);
    }

    private void onScreenChanged() {
        onScreenChanged(getResources().getConfiguration().orientation);
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    private View viewFull;
    private ImageView ivBack;
    private TextView tvPlayerTitle;
    private TextView tvNumber;
    private ImageView ivShare;
    private Button btnGuanZhu;
    private TextView tvStartTime;
    private ImageView ivFaYan;
    private ImageView ivPinLun;
    private ImageView ivCollect;
    private ImageView ivKeJian;
    private ImageView ivMicrophone;
    private TextView tvLianMaiTips;
    private RelativeLayout rlTitleBar;
    private RelativeLayout rlContro;

    private boolean isShowKejian;
    private void initFull(){
        viewFull = LayoutInflater.from(getContext()).inflate(R.layout.view_player_controllers, this,
                false);
        ivBack = (ImageView)viewFull.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                iLivePlayer.zoomOut();
            }
        });
        tvPlayerTitle = (TextView)viewFull.findViewById(R.id.tvPlayerTitle);
        tvNumber = (TextView)viewFull.findViewById(R.id.tvNumber);
        ivShare = (ImageView)viewFull.findViewById(R.id.ivShare);
        ivShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    iLivePlayer.share();
                }else{
                    ToastUtils.show(context, R.string.live_unlogin_error);
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
        btnGuanZhu = (Button)viewFull.findViewById(R.id.btnGuanZhu);
        btnGuanZhu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    iLivePlayer.guanZhu();
                }else{
                    context.startActivity(new Intent(context,LoginActivity.class));
                    ToastUtils.show(context,""+getResources().getString(R.string.live_unlogin_error));
                }
            }
        });
        tvStartTime = (TextView)viewFull.findViewById(R.id.tvStartTime);
        ivFaYan = (ImageView)viewFull.findViewById(R.id.ivFaYan);
        ivFaYan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    iLivePlayer.faYan();
                }else{
                    ToastUtils.show(context, R.string.live_unlogin_error);
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
        ivPinLun = (ImageView)viewFull.findViewById(R.id.ivPinLun);
        ivPinLun.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showOrDismPinLun();
//                iLivePlayer.pinLun();
            }
        });
        ivCollect = (ImageView)viewFull.findViewById(R.id.ivCollect);
        ivCollect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
//                    iLivePlayer.collect();
//                }else{
//                    ToastUtils.show(context,getResources().getString(R.string.live_unlogin_error));
//                }
            }
        });
        rlTitleBar = (RelativeLayout)viewFull.findViewById(R.id.rlTitleBar);
        rlTitleBar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        rlContro = (RelativeLayout)viewFull.findViewById(R.id.rlContro);
        rlContro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ivKeJian = (ImageView)viewFull.findViewById(R.id.ivKeJian);
        ivKeJian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                iLivePlayer.kejian(isShowKejian);
                if(!isShowKejian)
                    isShowKejian=true;
                else
                    isShowKejian=false;
            }
        });
        ivMicrophone = (ImageView)viewFull.findViewById(R.id.ivMicrophone);
        ivMicrophone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    if (!isHandUp) {
                        iLivePlayer.microphone();
                    } else {
                        iLivePlayer.hangUp();
                    }
                }else{
                    ToastUtils.show(context, R.string.live_unlogin_error);
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
        tvLianMaiTips = (TextView)viewFull.findViewById(R.id.tvLianMaiTips);
        viewFull.setVisibility(View.GONE);
        addView(viewFull);
    }

    public boolean isHandUp;

    public void setTitle(String title){
        tvPlayerTitle.setText(title);
    }

    public void setNumber(String num){
        tvNumber.setText(getResources().getString(R.string.information_viewnum)+num);
    }

    public void setAttentionState(boolean attention){
        if(attention){
            btnGuanZhu.setText(R.string.live_dis_atteion);
        }else{
            btnGuanZhu.setText(R.string.live_atteion);
        }
//        if(!isPause){
//            isPause =true;
//            pause();
//        } else {
//            isPause =false;
//            start();
//        }
    }

    boolean isPause;

    public void setCollectState(boolean collectState){
        if(collectState){
            ivCollect.setSelected(true);
        }else{
            ivCollect.setSelected(false);
        }
    }

    private boolean  isShow = true ;
    public void showOrDismPinLun(){
        if(isShow) {
            isShow = false;
            ivPinLun.setSelected(true);
            viewIm.setVisibility(View.GONE);
        }else {
            isShow =true;
            ivPinLun.setSelected(false);
            viewIm.setVisibility(View.VISIBLE);
        }
    }

    public void handUpSuccess(){
        if(isHandUp){
            isHandUp=false;
            ivMicrophone.setSelected(false);
            tvLianMaiTips.setVisibility(View.GONE);
        }else{
            isHandUp=true;
            ivMicrophone.setSelected(true);
            tvLianMaiTips.setText("接続を待っています");
            tvLianMaiTips.setVisibility(View.VISIBLE);
        }
    }

    public void microConnect(){
        tvLianMaiTips.setText("接続中です");
    }

    public void setMsgData(IMDataBean data){
        listData.add(data);
        moreWrapper.notifyDataSetChanged();
        rvImFullList.smoothScrollToPosition(listData.size()-1);
        Log.e(TAG, "setMsgData: ---->"+listData.size() );
    }

    public void showFull(List<Object> msgList){
        Log.e(TAG, "showFull: ---->"+msgList.size() );
        if(!listData.isEmpty())
            listData.clear();
        listData.addAll(msgList);
        moreWrapper.notifyDataSetChanged();
        rvImFullList.smoothScrollToPosition(listData.size()-1);
    }

    private View viewIm;
    private TextView tvHostName;
    private ImageView btnCamera;
    private RelativeLayout rlHostCamera;
    private RelativeLayout relCamre;
    private  ForceVideoView  fvvHostPlayer;
    private LinearLayout linHostCameraTips;
    private RecyclerView rvImFullList;
    private EditText etImInput;
    private TextView tvSend;
    private boolean isHostCameraShow =true;
    private List<Object> listData;
    private     LoadMoreWrapper moreWrapper;
   private void initImView(){

   }

    private View getLoadMoreView() {
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
               100);
        View loadMore = LayoutInflater.from(getContext()).inflate(R.layout.item_loadmore, null);
        loadMore.setLayoutParams(layoutParams);
        loadMore.setVisibility(View.VISIBLE);
        return loadMore;
    }

    private Handler syncHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            long sum = mainPlayTime-hoztPlayTime;
            if(sum>0)
                sum = sum-Math.abs(mainFirst-hostFirst);
                else if(sum<0)
                sum =sum+Math.abs(mainFirst-hostFirst);
//            Log.e(TAG, "onVideoFrameTimestamp:   mainPlayTime  :"
//                    +mainPlayTime+"  hoztPlayTime: "+hoztPlayTime+"   :"
//                    +(sum)+" : "+(mainFirst-hostFirst));
//            Log.e(TAG, "handleMessage: ------hozt------"+hoztPlayTime+"---main--"+mainPlayTime+"---sum--:"+sum );
           if(sum>1000){
//               Log.e(TAG, "handleMessage: ------sum>1000------"+hoztPlayTime+"-----"+mainPlayTime+"-----:"+sum );
                fvvHostPlayer.setRealtime(true);
                fvvHostPlayer.setRealtimeMaxDelay(1000);
                fvvMainPlayer.setRealtime(true);
                fvvMainPlayer.setRealtimeMaxDelay(5000);
            }else if(sum<-100&&sum>-1000){
//               Log.e(TAG, "handleMessage: ------sum<0&&sum>-1000------"+hoztPlayTime+"-----"+mainPlayTime+"-----:"+sum );
                fvvHostPlayer.setRealtime(true);
                fvvHostPlayer.setRealtimeMaxDelay(3000);
                fvvMainPlayer.setRealtime(true);
                fvvMainPlayer.setRealtimeMaxDelay(1000);
            }else if(sum<1000&&sum>100){
//               Log.e(TAG, "handleMessage: ------sum<1000&&sum>0------"+hoztPlayTime+"-----"+mainPlayTime+"-----:"+sum );
               fvvHostPlayer.setRealtime(true);
               fvvHostPlayer.setRealtimeMaxDelay(1000);
               fvvMainPlayer.setRealtime(true);
               fvvMainPlayer.setRealtimeMaxDelay(3000);
           }else if(sum<-1000){
//               Log.e(TAG, "handleMessage: ------sum<-1000------"+hoztPlayTime+"-----"+mainPlayTime+"-----:"+sum );
               fvvHostPlayer.setRealtime(true);
               fvvHostPlayer.setRealtimeMaxDelay(5000);
               fvvMainPlayer.setRealtime(true);
               fvvMainPlayer.setRealtimeMaxDelay(1000);
           }else if(sum<100||sum>-100){
//               Log.e(TAG, "handleMessage: ------sum<200||sum>-200------"+hoztPlayTime+"-----"+mainPlayTime+"-----:"+sum );
               fvvHostPlayer.setRealtime(true);
               fvvHostPlayer.setRealtimeMaxDelay(3000);
               fvvMainPlayer.setRealtime(true);
               fvvMainPlayer.setRealtimeMaxDelay(3000);
           }
            syncHandler.sendEmptyMessageDelayed(0,10);
            super.handleMessage(msg);
        }
    };


    private IjkMediaPlayer.OnInfoListener mOnInfoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer mp, int what, int extra) {

            if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_START) {

            } else if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_END || what == IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START || what == IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {

            }
            return true;
        }
    };


    private IjkMediaPlayer.OnErrorListener mOnErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer mp, int what, int extra) {
            Log.e(TAG, "onError: ------->"+what+"------>"+extra );
            return true;
        }
    };


    private IjkMediaPlayer.OnCompletionListener mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer iMediaPlayer) {
            Log.e(TAG, "onCompletion: ------->finsh");
            iLivePlayer.stop();
        }
    };

    private IjkMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int percent) {

        }
    };

    private IjkMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {

        }
    };

    private  long hoztPlayTime;
    private IjkMediaPlayer.OnVideoFrameTimestampListener  onVideoFrameTimestampListener = new IjkMediaPlayer.OnVideoFrameTimestampListener() {
        @Override
        public void onVideoFrameTimestamp(IMediaPlayer iMediaPlayer, long l) {
//            long timeMian = fvvMainPlayer.getCurrentPosition();
            if(hostFirst==0)
                hostFirst =System.currentTimeMillis();
//            Log.e(TAG, "onVideoFrameTimestamp: -----host--"
//                    +l+"-----"+mainPlayTime+"-------"+(mainPlayTime-l-(mainFirst-hostFirst))
//                    +"---"+(mainFirst-hostFirst));
            hoztPlayTime = l;
//            long time=fvvHostPlayer.getCurrentPosition();
//            long sus = timeMian-time;
        }
    };
    private  long mainPlayTime;
    private IjkMediaPlayer.OnVideoFrameTimestampListener  onVideoFrameCarTimestampListener = new IjkMediaPlayer.OnVideoFrameTimestampListener() {
        @Override
        public void onVideoFrameTimestamp(IMediaPlayer iMediaPlayer, long l) {
            if(mainFirst==0)
                mainFirst =System.currentTimeMillis();
//            Log.e(TAG, "onVideoFrameTimestamp: -----main--"
//                    +l+"-----"+hoztPlayTime+"-------"
//                    +(l-hoztPlayTime-(mainFirst-hostFirst))+"---"+(mainFirst-hostFirst));
            mainPlayTime = l;

//            long timeSu = mainPlayTime-hoztPlayTime;
//            if((timeMian-time)>1000){
//                fvvHostPlayer.setRealtime(true);
//                fvvHostPlayer.setRealtimeMaxDelay(5000);
//                fvvMainPlayer.setRealtime(true);
//                fvvMainPlayer.setRealtimeMaxDelay(1000);
//            }else if((timeMian-time)<1000&&(timeMian-time)>200){
//                fvvHostPlayer.setRealtime(true);
//                fvvHostPlayer.setRealtimeMaxDelay(3000);
//                fvvMainPlayer.setRealtime(true);
//                fvvMainPlayer.setRealtimeMaxDelay(1000);
//            }

//            Log.e(TAG, "onVideoFrameTimestamp: -----fvvHostPlayer----"+l);
        }
    };

    private long mainFirst;
    private long hostFirst;
    private HasWartchBeanDB hasWartchBeanDB = new HasWartchBeanDB(context);
    private HasWartchBean hasWartchBean;
    private long liveId;
    private long startTime;
    private boolean isLiving;
    private boolean isFree;
    private String typePush;
    private LiveDetailBean detailBean;

    public void setData(LiveDetailBean data){
                setTitle(data.getData().getLive().getTitle());
                setNumber(data.getData().getLive().getMaxAudience()+"");
        detailBean = data;
        hasPay = data.getData().getLive().isPay();
        tvHostName.setText("講師");
        if(data.getData().getLive().getPrice()==0)
        isFree = true;
        typePush = data.getData().getLive().getPushPlatformType();
        startTime = TimeUtils.timeTranstimestamp(data.getData().getLive().getRealStartTime());
        liveId = data.getData().getLive().getId();
        if(!hasPay&&data.getData().getLive().getPrice()!=0) {
            ivFull.setVisibility(View.GONE);
            if(BIZAIAApplication.getInstance().getUserBean()!=null) {
                hasWartchBean = hasWartchBeanDB.queryBean(liveId,
                        String.valueOf(BIZAIAApplication.getInstance().getUserBean().getId()));
            }else{
                hasWartchBean = hasWartchBeanDB.queryBean(liveId,
                        OtherUtils.getIM(context));
            }

            if("1".equals(data.getData().getLive().getStatus())&&!hasPay&& hasWartchBean==null){
                HasWartchBean hasWartchBean = new HasWartchBean();
                hasWartchBean.setSeeId(liveId);
                if(BIZAIAApplication.getInstance().getUserBean()!=null) {
                    hasWartchBean.setUserId(String.valueOf(BIZAIAApplication.getInstance().getUserBean().getId()));
                }else{
                    hasWartchBean.setUserId(OtherUtils.getIM(context));
                }
                hasWartchBeanDB.insert(hasWartchBean);
            }else if("1".equals(data.getData().getLive().getStatus())&&!hasPay&&hasWartchBean!=null){
                iLivePlayer.hasSeen();
            }

        }

        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH+data.getData().getLive().getCoverUrl(),
                ivCurtain, ImageLoaderUtils.getImageHighQualityOptions(R.drawable.ad_default_img));

        if("1".equals(data.getData().getLive().getStatus())) {
            isLiving=true;
            if ("1".equals(data.getData().getLive().getPushPlatformType()))
                setUrl(data.getData().getLive().getCoursewareStreamAddress().getPullFlvUrl(), data.getData().getLive().getCameraStreamAddress().getPullFlvUrl());
            else {
                setUrl(data.getData().getLive().getCameraStreamAddress().getPullFlvUrl(), null);
                relCamre.setVisibility(View.GONE);
            }
        }else {
            isLiving = false;
        }
    }

    private long liveStartTime=0;
    public void setUrl(String url1,String url2){
        Log.e(TAG, "setUrl: ------>"+url1+"\n"+url2 );
        fvvMainPlayer.setVideoURI(Uri.parse(url1));
        fvvMainPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                ivCurtain.setVisibility(View.GONE);
                if(hasPay||hasWartchBean==null) {
                    fvvMainPlayer.start();
                    if(liveStartTime==0)
                    liveStartTime = System.currentTimeMillis();
                    tHandler.sendEmptyMessageDelayed(0, 500);
                }
            }
        });
//        if(url2!=null) {
            if(!"2".equals(typePush)) {
                fvvHostPlayer.setVideoURI(Uri.parse(url2));
            fvvHostPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(IMediaPlayer iMediaPlayer) {
                    if(hasPay||hasWartchBean==null) {
                        fvvHostPlayer.start();
//                     fvvHostPlayer.start();
//                    fvvHostPlayer.seekTo(fvvMainPlayer.getCurrentPosition());
                        syncHandler.sendEmptyMessage(0);
                    }
                }
            });
            }
//        }
//        if(hasPay) {
//            fvvHostPlayer.start();
//            fvvMainPlayer.start();
//        }else if(hasWartchBean==null){
//            fvvHostPlayer.start();
//            fvvMainPlayer.start();
//        }
    }

    private CustomAdapter dataLiveAdapterHelper;
    public void pause(){
        tHandler.removeMessages(0);
        syncHandler.removeMessages(0);
        fvvMainPlayer.pause();
        fvvHostPlayer.pause();
    }

    public void start(){
        if(!isFree) {
            if (isShowPay && !hasPay)
                return;

            if (hasWartchBean != null)
                return;
        }

//        if(!isFree&&!hasPay)
        tHandler.sendEmptyMessageDelayed(0,500);
        if(!"2".equals(typePush))
        syncHandler.sendEmptyMessage(0);
        fvvMainPlayer.start();
        fvvHostPlayer.start();

        fvvHostPlayer.setRealtime(true);
        fvvHostPlayer.setRealtimeMaxDelay(3000);
        fvvMainPlayer.setRealtime(true);
        fvvMainPlayer.setRealtimeMaxDelay(3000);

        mainFirst =0;
        hostFirst =0;
    }


    public  void stop(){
        tHandler.removeMessages(0);
        syncHandler.removeMessages(0);
        fvvMainPlayer.stopPlayback();
        fvvHostPlayer.stopPlayback();
    }

    private static final int SHOW_CONTRO=1001;
    private static final int SHOW_HIDE=1002;
    private boolean isControShow =true;


    private boolean isShowPay;
    private boolean hasSave;
    private Handler tHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(fvvMainPlayer.isPlaying()) {
                long time = System.currentTimeMillis() - startTime;
                long timeSeen =System.currentTimeMillis() -  liveStartTime;
                if(time>0)
                tvStartTime.setText(TimeUtils.playTimeTransform((int)time));
                tHandler.sendEmptyMessageDelayed(0, 500);
                if (!isFree&&!hasPay && timeSeen > 3 * 60 * 1000 && !isShowPay) {
                    Log.e(TAG, "handleMessage: --------->"+time+"-------->"+startTime );
                    isShowPay = true;
                    if(!hasPay)
                    iLivePlayer.tryAndSee();
                    pause();
                }
            }
            super.handleMessage(msg);
        }
    };

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SHOW_CONTRO:
                    isControShow = true;
                    if(isFull) {
                        viewFull.setVisibility(View.VISIBLE);
                    }else {
                        viewNormal.setVisibility(View.VISIBLE);
                    }
                    Log.e(TAG, "handleMessage: ----->SHOW_CONTRO");
                    mHandler.sendEmptyMessageDelayed(SHOW_HIDE,5000);
                    break;
                case SHOW_HIDE:
                    isControShow = false;
                    if(isFull) {
                        viewFull.setVisibility(View.GONE);
                    }else {
                        viewNormal.setVisibility(View.GONE);
                    }
                    Log.e(TAG, "handleMessage: ----->SHOW_HIDE");
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(isFull&&keyCode==KeyEvent.KEYCODE_BACK){
            Log.e(TAG, "onKeyDown: ----》"+keyCode );
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            iLivePlayer.zoomOut();
            return  true;
        }
        return false;
    }

    public void setHasPay(boolean hasPay) {
        this.hasPay = hasPay;
        this.isShowPay=false;
        ivFull.setVisibility(View.VISIBLE);
        if(hasPay){
            if(BIZAIAApplication.getInstance().getUserBean()!=null)
            hasWartchBeanDB.delete(liveId,String.valueOf(BIZAIAApplication.getInstance().getUserBean().getId()));
        }else{
            hasWartchBeanDB.delete(liveId,OtherUtils.getIM(context));
        }
        hasWartchBean=null;
    }
}
