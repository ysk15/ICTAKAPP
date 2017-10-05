package com.example.user.ictakapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.user.steps_fisat.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private static final String KEY_TEXT_REPLY = "key_text_reply";

   public void onMessageReceived(RemoteMessage remoteMessage) {
       Intent intent = new Intent(MyFirebaseMessagingService.this, Login.class);
       PendingIntent contentIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
       NotificationCompat.Builder b = new NotificationCompat.Builder(MyFirebaseMessagingService.this);
       b.setAutoCancel(true)
               .setDefaults(Notification.DEFAULT_ALL)
               .setWhen(System.currentTimeMillis())
               .setSmallIcon(R.drawable.icon)
               .setContentTitle("New notification")
               .setContentText(remoteMessage.getData().get("body"))
               .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
               .setContentIntent(contentIntent)
               .setContentInfo("Info");
       NotificationManager notificationManager = (NotificationManager) MyFirebaseMessagingService.this.getSystemService(Context.NOTIFICATION_SERVICE);
       notificationManager.notify(1, b.build());
        Log.e(TAG, "From: " + remoteMessage.getFrom());
}

}