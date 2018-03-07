package com.bizaia.zhongyin.util;

import android.graphics.Bitmap;

import com.bizaia.zhongyin.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by yan on 2017/2/28.
 */

public class ImageLoaderUtils {
    public static DisplayImageOptions getImageOptions() {
        return getImageOptions(R.drawable.bizaia_default_img);
    }

    public static DisplayImageOptions getImageHighQualityOptions() {
        return getImageHighQualityOptions(R.drawable.icon_user_blue);

    }

    public static DisplayImageOptions getImageOptions(int defaultImgId) {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImgId) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(defaultImgId)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(defaultImgId) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .build();
    }

    public static DisplayImageOptions getImageHighQualityOptions(int defaultImgId) {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImgId) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(defaultImgId)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(defaultImgId) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类型//
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .build();
    }

}
