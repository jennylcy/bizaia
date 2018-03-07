package com.bizaia.zhongyin.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.base.BroadcastAction;

/**
 * Created by zyz on 2017/6/22.
 */

public class NetWorkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("网络状态发生变化");
        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//                Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
//                Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//                Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
                if(!BIZAIAApplication.getInstance().isIgnore())
                RxBus.getInstance().post(new BroadcastAction(true));
            } else {
//                Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
            }
//API大于23时使用下面的方式进行网络监听
        }else {
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            //用于存放网络连接信息
            StringBuilder sb = new StringBuilder();
            //通过循环将网络信息逐个取出来
            boolean isDisconnect =false;
            boolean isConnect=false;
            for (int i=0; i < networks.length; i++){
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                Log.e("NetWorkStateReceiver", "onReceive:------------> "+networkInfo.getType()+"------->"+networkInfo.isConnected() );
                if(networkInfo.getType()==ConnectivityManager.TYPE_MOBILE&&networkInfo.isConnected()){
//                    RxBus.getInstance().post(new BroadcastAction(true));
                    isConnect = true;
                }
                if(networkInfo.getType()==ConnectivityManager.TYPE_WIFI&&!networkInfo.isConnected()){
                    isDisconnect =true;
                }
                sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
            }
            Log.e("NetWorkStateReceiver", "onReceive:------------> "+isDisconnect+"------->"+isConnect );
            if(isConnect&&!BIZAIAApplication.getInstance().isIgnore()){
                RxBus.getInstance().post(new BroadcastAction(true));
            }


        }
    }
}