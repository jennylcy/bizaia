package com.bizaia.zhongyin.util;

import android.content.Context;
import android.util.Log;

import com.bizaia.zhongyin.R;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator
 * Created on 2017/5/8 10:04
 */

public class ShareUtil {

    public static void share(Context context, String title, String text, String img, String url) {
        share(context, null, title, text, img, url);
    }

    public static void share(Context context, String platform, String title, String text, String img, String url) {
        title = title == null ? context.getString(R.string.app_name) : title;
        text = text == null ? title : text;
        OnekeyShare oks = new OnekeyShare();
        if (platform != null) {
            oks.setPlatform(platform);
        }
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(title);
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(img);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        Log.e("ShareUtil", "share:-----------> url===>" + url + "\n" + img + "\n" + text + "\n" + title);
        oks.setUrl(url);
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(title);
//        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(title);
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

        // 启动分享GUI
        oks.show(context);
    }
}
