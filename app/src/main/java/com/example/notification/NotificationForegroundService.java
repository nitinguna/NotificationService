package com.example.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationForegroundService extends NotificationListenerService {

    private static final String TAG = "ForeGroundService";
    private static final String CHANNEL_ID = "ForegroundServiceChannel";


    public NotificationForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return super.onBind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        // String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Zebra Notification Service")
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
         matchNotificationCode(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn){

           StatusBarNotification[] activeNotifications = this.getActiveNotifications();

            if(activeNotifications != null && activeNotifications.length > 0) {
                Log.e(TAG, "Stacked Notification" );
                for (int i = 0; i < activeNotifications.length; i++) {
                    Bundle extras = activeNotifications[i].getNotification().extras;
                    String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
                    CharSequence notificationText = extras.getCharSequence(Notification.EXTRA_TEXT);
                    CharSequence notificationSubText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);

                    if (null != notificationTitle)
                        Log.e(TAG, notificationTitle );

                    if (null != notificationText)
                        Log.e(TAG, notificationText.toString() );

                    if (null != notificationSubText)
                    Log.e(TAG, notificationSubText.toString() );
                }
            }
        }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void matchNotificationCode(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        String chnID= sbn.getNotification().getChannelId();
        Bundle extras = sbn.getNotification().extras;

        if(chnID.equals("DEVICE_ADMIN_ALERTS"))
        {
            if (extras.getCharSequence(Notification.EXTRA_TEXT).toString().equals("Installed by your admin") ||
                    extras.getCharSequence(Notification.EXTRA_TEXT).toString().equals("Updated by your admin")) {
                Log.e(TAG, "Removing " + extras.getCharSequence(Notification.EXTRA_TEXT).toString() );
                this.cancelNotification(sbn.getKey());
            }
        }
        else if (chnID.equals("alerting auto granted permissions")){
            if (extras.getCharSequence(Notification.EXTRA_TITLE).toString().equals("Location can be accessed")) {
                Log.e(TAG, "Removing " + extras.getCharSequence(Notification.EXTRA_TEXT).toString() );
                this.cancelNotification(sbn.getKey());
            }
        }


    }
}