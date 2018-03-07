package com.bizaia.zhongyin.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by jensen on 11/17/15.
 */
public class OtherUtils {

    static String nickNameReg = "^[\4e00-\u9fa5_a-zA-Z0-9]+$";

    private OtherUtils() {
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    public static String loadFileAsString(String filePath) {
        String result = "";
        InputStream in = null;
        try {
            File file = new File(filePath);
            in = new FileInputStream(file);
            // 获取文件的字节数
            int lenght = in.available();
            // 创建byte数组
            byte[] buffer = new byte[lenght];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
            result = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Utils.close(in);
        }
        return result;
    }

    /**
     * dp转换到px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * @param context
     * @param sp
     * @return
     */
    public static int sp2px(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

    public static boolean MatcherNickName(String s) {
        Pattern p = Pattern.compile(nickNameReg);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private static final BigDecimal _100M = new BigDecimal("100000000");
    private static final BigDecimal _10M = new BigDecimal("10000000");
    private static final BigDecimal _M = new BigDecimal("1000000");
    private static final BigDecimal _10K = new BigDecimal("10000");
    private static final BigDecimal _K = new BigDecimal("1000");

    public static String formatJinDou(String jinDou) {
        String value = "——";
        if (TextUtils.isEmpty(jinDou))
            return value;
        try {
            BigDecimal decimal = new BigDecimal(jinDou);
            if (decimal.compareTo(_M) != -1) {
                value = "1000K+";
            } else {
                value = jinDou;
            }
        } catch (NumberFormatException e) {
        }
        return value;
    }

    public static String formatMember(String member) {
        String value = "——";
        if (TextUtils.isEmpty(member))
            return value;
        try {
            BigDecimal decimal = new BigDecimal(member);
            if (decimal.compareTo(_M) != -1) {
                value = "100K+";
            } else if (decimal.compareTo(_10K) != -1) {
                value = "10K+";
            } else {
                value = member;
            }
        } catch (NumberFormatException e) {
        }
        return value;
    }

    /**
     *  get  im   number
     * @param context
     * @return
     */
    public static String getIM(Context context){
        String imei ="";
        try {
             imei = ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE))
                    .getDeviceId();
        }catch (Exception e){
            return "zongying0000001";
        }

        return imei;
    }

    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }

}
