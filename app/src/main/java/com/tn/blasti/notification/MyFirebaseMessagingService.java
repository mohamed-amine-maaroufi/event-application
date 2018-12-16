package com.tn.blasti.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tn.blasti.R;
import com.tn.blasti.activity.MainActivity;
import com.tn.blasti.activity.NotificationDetailsActivity;
import com.tn.blasti.activity.PostDetailsActivity;
import com.tn.blasti.data.constant.AppConstant;
import com.tn.blasti.data.preference.AppPreference;
import com.tn.blasti.data.sqlite.NotificationDbController;

import java.util.Map;

/**
 * Created by amine 15/12/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            Map<String, String> params = remoteMessage.getData();

            if (AppPreference.getInstance(MyFirebaseMessagingService.this).isNotificationOn()) {

                sendNotification(params.get("title"), params.get("message"), params.get("newsId"));
                broadcastNewNotification();
            }
        }
    }

    private void sendNotification(String title, String messageBody, String postId) {

        // insert data into database
        NotificationDbController notificationDbController = new NotificationDbController(MyFirebaseMessagingService.this);
        notificationDbController.insertData(title, messageBody, postId);

        int id = -1;
        try {
            id = Integer.parseInt(postId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID = "noti01";

        Intent intent;
        if (id != -1) {
            intent = new Intent(this, PostDetailsActivity.class);
            intent.putExtra(AppConstant.BUNDLE_KEY_TITLE, title);
            intent.putExtra(AppConstant.BUNDLE_KEY_POST_ID, id);
            intent.putExtra(AppConstant.BUNDLE_FROM_PUSH, true);
        } else {
            intent = new Intent(this, NotificationDetailsActivity.class);
            intent.putExtra(AppConstant.BUNDLE_KEY_TITLE, title);
            intent.putExtra(AppConstant.BUNDLE_KEY_MESSAGE, messageBody);
            intent.putExtra(AppConstant.BUNDLE_KEY_POST_ID, id);
            intent.putExtra(AppConstant.BUNDLE_FROM_PUSH, true);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000})
                .setSound(defaultSoundUri)
                .setChannelId(CHANNEL_ID)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }

    private void broadcastNewNotification() {
        Intent intent = new Intent(AppConstant.NEW_NOTI);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
