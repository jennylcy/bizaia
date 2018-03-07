package com.bizaia.zhongyin.module.message;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bizaia.zhongyin.module.discovery.ui.chair.detail.DetailActivity;
import com.bizaia.zhongyin.module.live.ui.LiveDetailActivity;
import com.bizaia.zhongyin.module.message.ui.MessageActivity;
import com.bizaia.zhongyin.util.NotifyBarUtils;

/**
 * Created by yan on 2017/6/5.
 */

public class ActionPushActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotifyBarUtils.transparencyBar(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        switch (String.valueOf(PushDataConfig.currentType)) {
            case PushDataConfig.LECTURE:
                startActivity(new Intent(this, DetailActivity.class)
                        .putExtra("url", PushDataConfig.currentUrl)
                        .putExtra("title", PushDataConfig.currentTitle)
                        .putExtra("id", PushDataConfig.currentId)
                        .putExtra("type", 5)
                );

                break;
            case PushDataConfig.VOD:
                startActivity(new Intent(this,
                        com.bizaia.zhongyin.module.video.ui.detail.DetailActivity.class)
                        .putExtra("title", PushDataConfig.currentTitle)
                        .putExtra("videoId", Long.valueOf(PushDataConfig.currentId))
                );
                break;
            case PushDataConfig.LIVE:
                startActivity(new Intent(this, LiveDetailActivity.class)
                        .putExtra("id", Long.valueOf(PushDataConfig.currentId))
                        .putExtra("liveType", 0)
                );
                break;
            case PushDataConfig.MONTHLY:
                break;
            case PushDataConfig.MSG:
                startActivity(new Intent(this, MessageActivity.class));
                break;

        }
        finish();
    }
}
