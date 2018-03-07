package com.bizaia.zhongyin.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.util.Locale;
import java.util.UUID;

/**
 * 设备信息获取工具
 * Created by jensen on 11/17/15.
 */
public class DevUtils {

    private DevUtils() {
    }

    /**
     * 获得设备系统
     *
     * @return
     */
    public static String getOs() {
        return "android";
    }

    /**
     * 获得设备型号
     *
     * @return
     */
    public static String getModel() {
        return android.os.Build.MANUFACTURER + "/" + android.os.Build.MODEL;
    }


    public static String getVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获得设备语言
     *
     * @return
     */
    public static String getLanguage(Context context) {
        return context.getResources().getConfiguration().locale
                .getDisplayName()
                + "/"
                + context.getResources().getConfiguration().locale
                .getLanguage();
    }

    /**
     * 获得设备ID
     *
     * @return
     */
    public static String getDeviceId(Context context) {
        try {
            TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephonyManager == null)
                return null;
            return mTelephonyManager.getDeviceId();
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 获取mac 地址
     *
     * @return
     */
    public static String getMac() {
        try {
            return OtherUtils.loadFileAsString("/sys/class/net/wlan0/address")
                    .toUpperCase(Locale.CHINA).substring(0, 17);
        } catch (Exception e) {
            try {
                return OtherUtils.loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase(Locale.CHINA).substring(0, 17);
            } catch (Exception ex) {
                return "";
            }
        }
    }

    /**
     * 获取uuid
     *
     * @return
     */
    public static String getUUID(Context context) {
        try {
            TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephonyManager == null)
                return "";
            String deviceId, serialId, androidId;
            deviceId = "" + mTelephonyManager.getDeviceId();
            serialId = "" + mTelephonyManager.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            UUID uuid = new UUID(androidId.hashCode(), ((long) deviceId.hashCode() << 32) | serialId.hashCode());
            return uuid.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param context
     * @return
     */
    public static int getWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

}
