package com.example.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ManagedConfigurationsUpdateInitiatedReceiver extends BroadcastReceiver {
    private static final String TAG = "ForeGroundService";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "GOT com.android.vending.APPLICATION_RESTRICTIONS_CHANGE_INITIATED");
    }
}