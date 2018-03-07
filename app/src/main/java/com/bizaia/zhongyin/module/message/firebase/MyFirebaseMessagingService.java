/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bizaia.zhongyin.module.message.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.message.ActionPushActivity;
import com.bizaia.zhongyin.module.message.PushDataConfig;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody() +
                "   " + remoteMessage.getNotification().getTitle());
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());

            PushDataConfig.currentType = remoteMessage.getData().get(PushDataConfig.NAME_TYPE);
            PushDataConfig.currentId = remoteMessage.getData().get(PushDataConfig.NAME_ID);
            PushDataConfig.currentUrl = remoteMessage.getData().get(PushDataConfig.NAME_URL);
            PushDataConfig.currentTitle = remoteMessage.getNotification().getTitle();
            PushDataConfig.currentSubTitle = remoteMessage.getNotification().getBody();

            notification(PushDataConfig.currentTitle, PushDataConfig.currentSubTitle);
        }
    }

    NotificationCompat.Builder builder;

    /**
     * 初始化Notification通知
     */
    public void notification(String title, String content) {
        Intent intent = new Intent(this, ActionPushActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder = new NotificationCompat.Builder(getBaseContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pIntent);
        NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);
    }
}
