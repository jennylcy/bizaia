package com.bizaia.zhongyin.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.repository.ShareSDKConfig;
import com.bizaia.zhongyin.util.ShareUtil;
import com.bizaia.zhongyin.util.UiUtils;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.line.Line;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Administrator
 * Created on 2017/8/24 15:23
 */

public class ShareDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private String title;
    private String text;
    private String img;
    private String url;

    public static void share(Context context, String title, String text, String img, String url) {
        new ShareDialog(context, title, text, img, url).show();
    }

    private ShareDialog(@NonNull Context context, String title, String text, String img, String url) {
        super(context, R.style.DialogTheme);
        init(context, title, text, img, url);
    }

    private void init(Context context, String title, String text, String img, String url) {
        mContext = context;
        this.title = title;
        this.text = text;
        this.img = img;
        this.url = url;

        setContentView(R.layout.dialog_share);
        Window window = getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
            window.setWindowAnimations(R.style.SelectPhotoDialogAnim);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = UiUtils.getScreenWidth();
            window.setAttributes(layoutParams);
        }

        findViewById(R.id.wechat).setOnClickListener(this);
        findViewById(R.id.line).setOnClickListener(this);
        findViewById(R.id.twitter).setOnClickListener(this);
        findViewById(R.id.email).setOnClickListener(this);
        findViewById(R.id.qq).setOnClickListener(this);
        findViewById(R.id.wechat_friend).setOnClickListener(this);
        findViewById(R.id.message).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ShareSDK.initSDK(mContext, ShareSDKConfig.APP_KEY_FOR_LOGIN);
        String paltform;
        switch (v.getId()) {
            case R.id.wechat:
                paltform = ShareSDK.getPlatform(Wechat.NAME).getName();
                break;
            case R.id.line:
                paltform = ShareSDK.getPlatform(Line.NAME).getName();
                break;
            case R.id.twitter:
                paltform = ShareSDK.getPlatform(Twitter.NAME).getName();
                break;
            case R.id.email:
                paltform = ShareSDK.getPlatform(Email.NAME).getName();
                break;
            case R.id.qq:
                paltform = ShareSDK.getPlatform(QQ.NAME).getName();
                break;
            case R.id.wechat_friend:
                paltform = ShareSDK.getPlatform(WechatMoments.NAME).getName();
                break;
            case R.id.message:
                paltform = ShareSDK.getPlatform(ShortMessage.NAME).getName();
                break;
            default:
                return;
        }

        ShareUtil.share(mContext, paltform, title, text, img, url);

        dismiss();
    }
}
