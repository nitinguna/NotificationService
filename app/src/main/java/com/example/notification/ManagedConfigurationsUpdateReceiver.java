package com.example.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.example.notification.NotificationForegroundService.ACTION_APPLICATION_RESTRICTIONS_CHANGED_FROM_VENDING;

public class ManagedConfigurationsUpdateReceiver extends BroadcastReceiver {

    private static final String TAG = "ForeGroundService";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "GOT com.android.vending.APPLICATION_RESTRICTIONS_CHANGED");
        final Intent changedIntent =
                new Intent(ACTION_APPLICATION_RESTRICTIONS_CHANGED_FROM_VENDING);
        context.sendBroadcast(changedIntent);
    }
}