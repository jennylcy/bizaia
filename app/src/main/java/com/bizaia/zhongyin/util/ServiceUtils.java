package com.bizaia.zhongyin.util;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by yan on 2017/3/13.
 */

public class ServiceUtils {

    public static boolean isRunning(Context context, Class aClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (aClass.getCanonicalName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
