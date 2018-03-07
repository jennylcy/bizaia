package com.bizaia.zhongyin.util;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zyz on 2016/5/19.
 */
public class StringUtils {


    /**
     *
     * @param millis
     * @return
     */
    public static String formatTime(int millis) {
        int sec = millis / 1000;
        int hour = sec / 60 / 60;
        int minute = (sec - hour * 60 * 60) / 60;
        int second = sec - hour * 60 * 60 - minute * 60;
        if (hour > 0)
            return String.format("%d:%02d:%02d", hour, minute, second);
        else
            return String.format("%02d:%02d", minute, second);
    }

    /**
     * 是否为空
     *
     * @param text
     * @return
     */
    public static boolean isEmpty(CharSequence text) {
        return TextUtils.isEmpty(text) || isNull(text);
    }

    /**
     * 是否为空（"null"）
     *
     * @param text
     * @return
     */
    public static boolean isNull(CharSequence text) {
        return text == null || "null".equalsIgnoreCase(text.toString());
    }

    /**
     *  返回评论条数
     * @param commments
     * @return
     */
    public static String setNumber(int commments ){

        if(commments>9999){
            return  9999+"";
        }else{
            return String.valueOf(commments);
        }
    }

    /**
     *  处理时间
     * @param time
     * @return
     */
    public static String getPublishTime(String time)  {
        long currentTime= System.currentTimeMillis();
        long publishTime= Long.parseLong(time)*1000;
        try {
            long difference = currentTime - publishTime;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String publish = format.format(publishTime);
            String today = format.format(currentTime);

            String[]str_publis = publish.split(" ");
            String[] str_today = today.split(" ");
            if(str_publis.length<2||str_today.length<2)
                return "";
            if(str_publis[0].equalsIgnoreCase(str_today[0])){
                return str_publis[1];
            }else{
                return str_publis[0].substring(5,str_publis[0].length());
            }

        }catch (Exception e){

        }
        return "";
    }

    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }


    public static boolean isMobilePhone(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isMail(String mail){
        Pattern p = Pattern
                .compile("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        Matcher m = p.matcher(mail);
        return m.matches();
    }

    public static boolean checkPassword(String password) {
        Pattern p = Pattern.compile("[A-Za-z0-9]+$");
        return p.matcher(password).lookingAt();
    }

    public static String formatTimeToDateTime(long time) {
        return formatTimeToFormat("yyyy-MM-dd HH:mm:ss", time);
    }

    public static String formatTimeToFormat(String format, long time) {
        if (TextUtils.isEmpty(format))
            return String.valueOf(time);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (String.valueOf(time).length() < 13) {
            return sdf.format(time * 1000l);
        } else {
            return sdf.format(time);
        }
    }


    public static void copy(String content, Context context)
    {
        ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

}
