package com.bizaia.zhongyin.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by yan on 2017/3/6.
 */

public class TimeUtils {


    public static String converTime(long timestamp) {
        long currentSeconds = System.currentTimeMillis() / 1000;
        long timeGap = currentSeconds - timestamp;//与现在时间相差秒数
        String timeStr = null;
        if (timeGap > 24 * 60 * 60) {//1天以上
            timeStr = timeGap / (24 * 60 * 60) + "天前";
        } else if (timeGap > 60 * 60) {//1小时-24小时
            timeStr = timeGap / (60 * 60) + "小时前";
        } else if (timeGap > 60) {//1分钟-59分钟
            timeStr = timeGap / 60 + "分钟前";
        } else {//1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    public static String playTimeTransform(int time) {

        String str_time = "";
        time = time / 1000;
        if (time > 3600) {
            if (time / 3600 > 9) {
                str_time = time / 3600 + "";
            } else {
                str_time = "0" + time / 3600;
            }
            time = time % 3600;
        } else {
            str_time = "00";
        }
        if (time > 60) {
            if (time / 60 > 9) {
                str_time = str_time + ":" + time / 60 + "";
            } else {
                str_time = str_time + ":" + "0" + time / 60;
            }
            time = time % 60;
        } else {
            str_time = str_time + ":00";
        }

        if (time > 0) {
            if (time > 9) {
                str_time = str_time + ":" + time;
            } else {
                str_time = str_time + ":" + "0" + time;
            }
        } else {
            str_time = str_time + ":" + "00";
        }
        return str_time;
    }

    public static String getStandardTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return sdf.format(calendar.getTime());
    }

    public static String getStandardDay(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return sdf.format(calendar.getTime());
    }

    public static String getStandardTimeOne(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date date = new Date(timestamp);
        sdf.format(date);
        return sdf.format(date).replace(" ", getWeekDate(timestamp));
    }

    public static String getWeekDate(long timeStamp) {
        int mydate = 0;
        String week = null;
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date(timeStamp));
        mydate = cd.get(Calendar.DAY_OF_WEEK);
        // 获取指定日期转换成星期几
        if (mydate == 1) {
            week = "(日)";
        } else if (mydate == 2) {
            week = "(月)";
        } else if (mydate == 3) {
            week = "(火)";
        } else if (mydate == 4) {
            week = "(水)";
        } else if (mydate == 5) {
            week = "(木)";
        } else if (mydate == 6) {
            week = "(金)";
        } else if (mydate == 7) {
            week = "(土)";
        }
        return week;
    }


    public static String getWeekDateTwo(String timeStamp) {
        String week = "";
        try {
            int mydate = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(timeStamp));
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
            if (mydate == 1) {
                week = "(日)";
            } else if (mydate == 2) {
                week = "(月)";
            } else if (mydate == 3) {
                week = "(火)";
            } else if (mydate == 4) {
                week = "(水)";
            } else if (mydate == 5) {
                week = "(木)";
            } else if (mydate == 6) {
                week = "(金)";
            } else if (mydate == 7) {
                week = "(土)";
            }
        }catch (Exception e){
            return "";
        }
        return week;
    }

    public static String getTodayDate() {
        long currentSeconds = System.currentTimeMillis() / 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentSeconds);
        return sdf.format(calendar.getTime());
    }


    public static String getTodayDateA() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);//获取年份
        int month = c.get(Calendar.MONTH)+1;//获取月份
        int day = c.get(Calendar.DATE);//获取日
        String date = year + "-" + month + "-" + day;
        Log.e("TimeUtils", "getTodayDateA: --------" + date + "---" + c.getTime());
        return date;
    }

    public static String getMonthToToday(int month){
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,month);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE);
    }


    public static String dateTransition(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat newsdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String dateStr = "";
        if (str == null)
            return "";
        Date date = null;
        try {
            date = sdf.parse(str);
            dateStr = newsdf.format(date);
        } catch (ParseException e) {

        }

        return dateStr;
    }

    public static String timeTransGBToCN(String time){
        SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            cal.setTimeInMillis(sdf.parse(time).getTime());
            cal.add(Calendar.HOUR, getCurrentTimeZone());
        }catch (Exception e){
          return "";
        }

        return sdfs.format(cal.getTime());
    }

    public static String timeTransGBToCNDate(String time){
        SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cal.setTimeInMillis(sdf.parse(time).getTime());
            cal.add(Calendar.HOUR, getCurrentTimeZone());
        }catch (Exception e){
            return "";
        }

        return sdfs.format(cal.getTime());
    }

    public static long timeTranstimestamp(String time){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long times =0;
        try {
            cal.setTimeInMillis(sdf.parse(time).getTime());
            cal.add(Calendar.HOUR, getCurrentTimeZone());
            times = cal.getTimeInMillis();
        }catch (Exception e){
            return times;
        }

        return times;
    }

    public static long transTimestamp(String time){
         long timestamp=0;
        try {
            SimpleDateFormat format =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            Date date = format.parse(time);
            timestamp = date.getTime();
        }catch (Exception e){

        }
        return timestamp;
    }

    public static int getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        String strTz = tz.getDisplayName(false, TimeZone.SHORT);
        return Integer.parseInt(strTz.substring(4,6));

    }
}
