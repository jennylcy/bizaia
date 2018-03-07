package com.bizaia.zhongyin.module.live.contro;

import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zyz on 2017/3/21.
 */

public class TeacherContro {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvPlayerTitle)
    TextView tvPlayerTitle;
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    @BindView(R.id.ivShare)
    ImageView ivShare;
    @BindView(R.id.btnLive)
    TextView tvStartPush;
    @BindView(R.id.viewContro)
    View viewContro;
    @BindView(R.id.lRequest)
    LinearLayout lRequest;
    @BindView(R.id.ivLiuyan)
    ImageView ivLiuyan;
    @BindView(R.id.btnCamera)
    ImageView btnCamera;
    @BindView(R.id.tvRequestNum)
    TextView tvRequestNum;
    @BindView(R.id.linHostCameraTips)
    LinearLayout linHostCameraTips;
    @BindView(R.id.ivRequest)
    ImageView ivRequest;
    @BindView(R.id.tvHostName)
    TextView tvHostName;
    @BindView(R.id.rvImList)
    RecyclerView rvImList;
    @BindView(R.id.viewRight)
    View viewRight;
    @BindView(R.id.svHostCamera)
    GLSurfaceView svHostCamera;
    private ITeacherContro iTeacherContro;
    private boolean isStart;
    private boolean isControShow=true;

    private static final int SHOW_CONTRO=1001;
    private static final int SHOW_HIDE=1002;


    public  TeacherContro(View top,ITeacherContro iContro){
        ButterKnife.bind(this,top);
//        ButterKnife.bind(this,right);
       this.iTeacherContro =iContro;
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iTeacherContro.back();
            }
        });
        tvStartPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isStart) {
                    iTeacherContro.startPush();
                    tvStartPush.setText(R.string.live_stop);
                    isStart = true;
                }else{
                    iTeacherContro.stopPush();
                }
            }
        });
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iTeacherContro.share();
            }
        });
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isControShow) {
                    mHandler.sendEmptyMessage(SHOW_CONTRO);
                }
            }
        });
        lRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ivRequest.isSelected()){
                }else {
                    ivLiuyan.setSelected(false);
                    ivRequest.setSelected(true);
                    iTeacherContro.showRequest();
                }
            }
        });
//        svHostCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("TeacherContro", "onClick: ---------------->"+isControShow);
//                if(!isControShow) {
//                    mHandler.removeMessages(SHOW_HIDE);
//                    mHandler.sendEmptyMessage(SHOW_CONTRO);
//                }
//            }
//        });
        ivLiuyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ivLiuyan.isSelected()) {
                    ivLiuyan.setSelected(true);
                    ivRequest.setSelected(false);
                    iTeacherContro.showDiscuss();
                }
            }
        });
        viewContro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isControShow) {
                    mHandler.removeMessages(SHOW_HIDE);
                    mHandler.sendEmptyMessage(SHOW_CONTRO);
                }
            }
        });
//        rvImList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("TeacherContro", "onClick: ---------------->"+isControShow);
//                if(!isControShow) {
//                    mHandler.removeMessages(SHOW_HIDE);
//                    mHandler.sendEmptyMessage(SHOW_CONTRO);
//                }
//            }
//        });
        viewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TeacherContro", "onClick: ---------------->"+isControShow);
                if(!isControShow) {
                    mHandler.removeMessages(SHOW_HIDE);
                    mHandler.sendEmptyMessage(SHOW_CONTRO);
                }
            }
        });
//        viewContro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isControShow){
//                    mHandler.removeMessages(SHOW_HIDE);
//                    mHandler.sendEmptyMessage(SHOW_HIDE);
//                }else{
//                    mHandler.sendEmptyMessage(SHOW_CONTRO);
//                }
//            }
//        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iTeacherContro.stopVideo();
            }
        });
        linHostCameraTips.setVisibility(View.GONE);


        ivLiuyan.setSelected(true);
        mHandler.sendEmptyMessageDelayed(SHOW_HIDE,5000);

        tvHostName.setText(BIZAIAApplication.getInstance().getUserBean().getNickname());
    }


    public void setTitle(String title){
        tvPlayerTitle.setText(title);
    }

    public void setNumber(String num){
        tvNumber.setText(num);
    }

    public void show(){
        if(!isControShow) {
            mHandler.removeMessages(SHOW_HIDE);
            mHandler.sendEmptyMessage(SHOW_CONTRO);
        }
    }

    public void setRequest(String num){
        tvRequestNum.setText("("+num+"人)");
    }



    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SHOW_CONTRO:
                    viewContro.setVisibility(View.VISIBLE);
                    mHandler.sendEmptyMessageDelayed(SHOW_HIDE,5000);
                    isControShow = true;
                    break;
                case SHOW_HIDE:
                    viewContro.setVisibility(View.GONE);
                    isControShow = false;
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
