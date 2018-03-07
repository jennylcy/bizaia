package com.bizaia.zhongyin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.launch.LauncherActivity;
import com.bizaia.zhongyin.module.mine.ui.UpdateActivity;
import com.bizaia.zhongyin.util.PreferencesUtils;

public class FlushActivity extends BaseActivity {
//    private GifView gifView;
    private ImageView ivFlush;
    private boolean isStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        /*set it to be no title*/
       /*set it to be full screen*/
        setContentView(R.layout.flush);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ivFlush = (ImageView) findViewById(R.id.ivFlush);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(3500);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isStart) return;
                if (PreferencesUtils.activityIsGuided(getBaseContext(), "FlushActivity")) {
                    startActivity(new Intent(FlushActivity.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(FlushActivity.this, LauncherActivity.class));
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivFlush.startAnimation(alpha);
//        gifView = (GifView) findViewById(R.id.gif_view);
//        gifView.setGifResource(R.drawable.bizaia_flush_animation);
//        gifView.play();
//        gifView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (!isStart) return;
//                if (PreferencesUtils.activityIsGuided(getBaseContext(), "FlushActivity")) {
//                    startActivity(new Intent(FlushActivity.this, MainActivity.class));
//                    finish();
//                } else {
//                    startActivity(new Intent(FlushActivity.this, LauncherActivity.class));
//                    finish();
//                }
//            }
//        }, 3500);
        UpdateActivity.checkUpdate(getBaseContext(), new UpdateActivity.IUpdateBack() {
            @Override
            public void onUpdateBack(UpdateActivity.UpdateInfo updateInfo) {
                if (updateInfo.isUpdateRequired()) {
                    isStart = false;
                }
            }
        }, mSubscriptions);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        gifView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
