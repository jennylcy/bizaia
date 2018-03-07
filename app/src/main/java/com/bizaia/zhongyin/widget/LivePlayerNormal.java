package com.bizaia.zhongyin.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bizaia.zhongyin.R;

/**
 * Created by zyz on 2017/3/9.
 */

public class LivePlayerNormal extends RelativeLayout{

    private TextureView surfaceView;
    private ILivePlayer iLivePlayer;
    public LivePlayerNormal(Context context) {
        super(context);
    }

    public LivePlayerNormal(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LivePlayerNormal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void initView(){
        surfaceView =  (TextureView) LayoutInflater.from(getContext()).inflate(R.layout.view_palyer_sur, this,
                false);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                212);
        surfaceView.setLayoutParams(layoutParams);
        addView(surfaceView);
        initNormal();
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
        ivFull = (ImageView) viewNormal.findViewById(R.id.ivPandS);
        ivFull.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewNormal.setVisibility(View.GONE);
            }
        });
        addView(viewNormal);
    }

    public void setiLivePlayer(ILivePlayer iLivePlayer) {
        this.iLivePlayer = iLivePlayer;
    }
}
