package com.bizaia.zhongyin.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;

import com.bizaia.zhongyin.module.login.data.UserBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashSet;
import java.util.Set;

/**
 * SharedPreferences工具
 * Created by jensen on 11/19/15.
 */
public class SPUtils {

    private SPUtils() {
    }

    // StringSet
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void putStringSet(Context mContext, int mode, String name, String key, Set<String> values) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet(key, values);
        editor.apply();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> getStringSet(Context mContext, int mode, String name, String key) {
        return getStringSet(mContext, mode, name, key, new HashSet<String>());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> getStringSet(Context mContext, int mode, String name, String key, Set<String> def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getStringSet(key, def);
    }

    // String
    public static void putString(Context mContext, int mode, String name, String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context mContext, int mode, String name, String key) {
        return getString(mContext, mode, name, key, "");
    }

    public static String getString(Context mContext, int mode, String name, String key, String def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getString(key, def);
    }

    //long
    public static void putLong(Context mContext, int mode, String name, String key, long value) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLong(Context mContext, int mode, String name, String key) {
        return getLong(mContext, mode, name, key, 0l);
    }

    public static long getLong(Context mContext, int mode, String name, String key, long def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getLong(key, def);
    }

    // float
    public static void putFloat(Context mContext, int mode, String name, String key, float value) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloat(Context mContext, int mode, String name, String key) {
        return getFloat(mContext, mode, name, key, 0.0f);
    }

    public static float getFloat(Context mContext, int mode, String name, String key, float def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getFloat(key, def);
    }

    // int
    public static void putInt(Context mContext, int mode, String name, String key, int value) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context mContext, int mode, String name, String key) {
        return getInt(mContext, mode, name, key, 0);
    }

    public static int getInt(Context mContext, int mode, String name, String key, int def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getInt(key, def);
    }

    // boolean
    public static void putBoolean(Context mContext, int mode, String name, String key, boolean value) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context mContext, int mode, String name, String key) {
        return getBoolean(mContext, mode, name, key, false);
    }

    public static boolean getBoolean(Context mContext, int mode, String name, String key, boolean def) {
        SharedPreferences sp = mContext.getSharedPreferences(
                name, mode);
        return sp.getBoolean(key, def);
    }


    public static void putUser(Context mContext, int mode, String name, String key, UserBean.DataEntity user) {
        try {
            // 保存对象
            SharedPreferences.Editor sharedata = mContext.getSharedPreferences(name, mode).edit();
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(user);
            //将序列化的数据转为16进制保存
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            //保存该16进制数组
            sharedata.putString(key, bytesToHexString);
            sharedata.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserBean.DataEntity getUser(Context mContext, int mode, String name, String key) {
        try {
            SharedPreferences sharedata = mContext.getSharedPreferences(name, mode);
            if (sharedata.contains(key)) {
                String string = sharedata.getString(key, "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                } else {
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object readObject = is.readObject();
                    return (UserBean.DataEntity) readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //所有异常返回null
        return null;
    }
//
//    public static void putPersonInfo(Context mContext, PersonInfoBean.DataEntity personInfo) {
//        try {
//            // 保存对象
//            SharedPreferences.Editor sharedata = mContext.getSharedPreferences(SPConfiguration.APP_NAME, mContext.MODE_PRIVATE).edit();
//            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            ObjectOutputStream os = new ObjectOutputStream(bos);
//            //将对象序列化写入byte缓存
//            os.writeObject(personInfo);
//            //将序列化的数据转为16进制保存
//            String bytesToHexString = bytesToHexString(bos.toByteArray());
//            //保存该16进制数组
//            sharedata.putString(SPConfiguration.USER_INFO, bytesToHexString);
//            sharedata.commit();
//        } catch (IOException e) {
//            e.printStackTrace();
//
//        }
//    }
//
//    public static PersonInfoBean.DataEntity getPersonInfo(Context mContext) {
//        try {
//            SharedPreferences sharedata = mContext.getSharedPreferences(SPConfiguration.APP_NAME, mContext.MODE_PRIVATE);
//            if (sharedata.contains(SPConfiguration.USER_INFO)) {
//                String string = sharedata.getString(SPConfiguration.USER_INFO, "");
//                if (TextUtils.isEmpty(string)) {
//                    return null;
//                } else {
//                    //将16进制的数据转为数组，准备反序列化
//                    byte[] stringToBytes = StringToBytes(string);
//                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
//                    ObjectInputStream is = new ObjectInputStream(bis);
//                    //返回反序列化得到的对象
//                    Object readObject = is.readObject();
//                    return (PersonInfoBean.DataEntity) readObject;
//                }
//            }
//        } catch (StreamCorruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        //所有异常返回null
//        return null;
//    }
//
//
    /**
     * desc:将数组转为16进制
     *
     * @param bArray
     * @return modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * desc:将16进制的数据转为数组
     *
     * @param data
     * @return modified:
     */
    public static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch3;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch3 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch3 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch4;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch4 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch4 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch3 + int_ch4;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }
}
