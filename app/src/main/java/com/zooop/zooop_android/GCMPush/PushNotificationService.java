package com.zooop.zooop_android.GCMPush;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.zooop.zooop_android.R;
import com.zooop.zooop_android.models.DbHelper;
import com.zooop.zooop_android.ui.activities.HomeActivity;
import com.zooop.zooop_android.ui.fragments.DiggyFragment;

/**
 * Created by michael on 25/03/16.
 */
public class PushNotificationService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        sendNotification(message);

        addMessagetoDB(message);

        Intent intent = new Intent("newMessageEvent");
        intent.putExtra("message", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void addMessagetoDB(String message) {
        final DbHelper dbHelper = new DbHelper(getApplicationContext());
        dbHelper.insertChat("DIGGY", message);
    }

    private void sendNotification(String message) {
        int ID = message.hashCode() % 10000;

        Intent launchIntent = new Intent(this, HomeActivity.class);
        launchIntent.putExtra("id", "newDiggyMessage");

        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, launchIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.diggy)
                .setContentTitle("Diggy")
                .setContentText(message)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setAutoCancel(true)
                .setSound(Uri.parse("android.resource://com.zooop.zooop_android/raw/r2"))
                .setVibrate(new long[]{0, 300, 100, 50, 100, 50}) //{ delay, vibrate, sleep, vibrate, sleep } in millisecond
                .setPriority(2)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(ID, notificationBuilder.build());
    }
}
