package com.bizaia.zhongyin.base;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Administrator
 * Created on 2017/5/6 17:23
 */

public class StackActivityManager {
    private static Stack<Activity> activityStack;
    private static StackActivityManager instance;
    private StackActivityManager(){
    }
    public static StackActivityManager getScreenManager(){
        if(instance==null){
            instance=new StackActivityManager();
        }
        return instance;
    }

    public static StackActivityManager getInstance() {
        return instance;
    }

    //退出栈顶Activity
    public void popActivity(Activity activity){
        if(activity!=null){
            activityStack.remove(activity);
        }
    }

    //获得当前栈顶Activity
    public Activity currentActivity(){
        Activity activity=activityStack.lastElement();
        return activity;
    }

    //将当前Activity推入栈中
    public void pushActivity(Activity activity){
        if(activityStack==null){
            activityStack=new Stack<Activity>();
        }
        activityStack.add(activity);
    }
    //退出栈中所有Activity
    public void popAllActivityExceptOne(Class cls){
        while(true){
            Activity activity=currentActivity();
            if(activity==null){
                break;
            }
            if(activity.getClass().equals(cls) ){
                break;
            }
            popActivity(activity);
        }
    }

    public void popAllActivity(){
        while(true){
            Activity activity=currentActivity();
            if(activity==null){
                break;
            }
            if(!activity.isFinishing())
            activity.finish();
        }
    }
}
