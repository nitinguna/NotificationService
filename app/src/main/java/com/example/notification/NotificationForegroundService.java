package com.example.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.RestrictionEntry;
import android.content.RestrictionsManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.notification.model.ManagedProperty;
import com.example.notification.model.NotificationControllerBlacklistrule;
import com.example.notification.model.NotificationControllerBlacklistrule__1;
import com.example.notification.model.NotificationControllerWhitelistrule;
import com.example.notification.model.NotificationControllerWhitelistrule__1;
import com.example.notification.model.NotificationManagedConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.time.temporal.TemporalAccessor;
import java.util.Iterator;
import java.util.List;

import static com.example.notification.BundleTools.createJsonStringFromBundle;
import static com.example.notification.JsonUtiity.getJson;
import static com.example.notification.Utilities.isServiceRunning;

public class NotificationForegroundService extends NotificationListenerService {

    public static final String ACTION_APPLICATION_RESTRICTIONS_CHANGED_FROM_VENDING = "android.intent.action.ACTION_APPLICATION_RESTRICTIONS_CHANGED_FROM_VENDING";
    private static final String TAG = "ForeGroundService";
    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    NotificationManagedConfig managedConfig;
    Boolean isControlled = false;
    String ruleType;
    List<NotificationControllerBlacklistrule> blkListRules = null;
    List<NotificationControllerWhitelistrule> whtListRules = null;
    // Observes restriction changes
    private BroadcastReceiver mBroadcastReceiver;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int onStartCommand(Intent intent, int flags, int startId) {

        // String input = intent.getStringExtra("inputExtra");
        // check whether we have any managed configuation stored
        Log.d(TAG, "Checking managed configuration on Service start");
        try {
            refreshRules();
        } catch (BundleTools.BundleException | IOException e) {
            e.printStackTrace();
        }

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    refreshRules();
                } catch (BundleTools.BundleException | IOException e) {
                    e.printStackTrace();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_APPLICATION_RESTRICTIONS_CHANGED);
        intentFilter.addAction(ACTION_APPLICATION_RESTRICTIONS_CHANGED_FROM_VENDING);
        registerReceiver(mBroadcastReceiver,
                intentFilter);

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
            mBroadcastReceiver = null;
        }
        stopForeground(true);
        stopSelf();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        matchNotificationCode(sbn);
    }

    private void executeBlackListLogic(StatusBarNotification activeNotifications, String packageName, String chnID, String text, String subText, String title) {
        Log.e(TAG, "Enabling Black List rule " + ruleType);
        if (null != blkListRules && !blkListRules.isEmpty()) {
            Iterator<NotificationControllerBlacklistrule> genericItr = blkListRules.iterator();
            while (genericItr.hasNext()) {
                NotificationControllerBlacklistrule blRuless = genericItr.next();
                List<NotificationControllerBlacklistrule__1> blrules = blRuless.getNotificationControllerBlacklistrule();
                for (NotificationControllerBlacklistrule__1 blRule : blrules) {
                    Boolean packageMaskDisable = (blRule.getBlPackagename() == null || blRule.getBlPackagename().isEmpty());
                    Boolean channelMaskDisable = (blRule.getBlChannelid() == null || blRule.getBlChannelid().isEmpty());
                    Boolean titleMaskDisable = (blRule.getBlTitle() == null || blRule.getBlTitle().isEmpty());
                    Boolean textMaskDisable = (blRule.getBlText() == null || blRule.getBlText().isEmpty());
                    Boolean subTextMaskDisable = (blRule.getBlSubtext() == null || blRule.getBlSubtext().isEmpty());
                    if (packageMaskDisable || blRule.getBlPackagename().equalsIgnoreCase(packageName))
                        if (channelMaskDisable || blRule.getBlChannelid().equalsIgnoreCase(chnID))
                            if (titleMaskDisable || blRule.getBlTitle().equalsIgnoreCase(title))
                                if (textMaskDisable || blRule.getBlText().equalsIgnoreCase(text))
                                    if (subTextMaskDisable || blRule.getBlSubtext().equalsIgnoreCase(subText)) {
                                        Log.d(TAG, "Notification Blacklisted " + activeNotifications.getKey());
                                        Log.d(TAG, "Notification in blacklist Cancelling " + activeNotifications.getKey());
                                        this.cancelNotification(activeNotifications.getKey());
                                    }
                }
            }
        }
    }

    private void executeWhiteListLogic(StatusBarNotification activeNotifications, String packageName, String chnID, String text, String subText, String title) {
        Log.e(TAG, "Enabling White List rule " + ruleType);
        if (null != whtListRules && !whtListRules.isEmpty()) {
            Iterator<NotificationControllerWhitelistrule> genericItr = whtListRules.iterator();
            Boolean inWList = false;
            while (genericItr.hasNext()) {
                inWList = false;
                NotificationControllerWhitelistrule whtRuless = genericItr.next();
                List<NotificationControllerWhitelistrule__1> whtrules = whtRuless.getNotificationControllerWhitelistrule();
                for (NotificationControllerWhitelistrule__1 whtRule : whtrules) {
                    Boolean packageMaskDisable = (whtRule.getWlPackagename() == null || whtRule.getWlPackagename().isEmpty());
                    Boolean channelMaskDisable = (whtRule.getWlChannelid() == null || whtRule.getWlChannelid().isEmpty());
                    Boolean titleMaskDisable = (whtRule.getWlTitle() == null || whtRule.getWlTitle().isEmpty());
                    Boolean textMaskDisable = (whtRule.getWlText() == null || whtRule.getWlText().isEmpty());
                    Boolean subTextMaskDisable = (whtRule.getWlSubtext() == null || whtRule.getWlSubtext().isEmpty());
                    if ((packageMaskDisable || whtRule.getWlPackagename().equalsIgnoreCase(packageName)) &&
                            (channelMaskDisable || whtRule.getWlChannelid().equalsIgnoreCase(chnID)) &&
                            (titleMaskDisable || whtRule.getWlTitle().equalsIgnoreCase(title)) &&
                            (textMaskDisable || whtRule.getWlText().equalsIgnoreCase(text)) &&
                            (subTextMaskDisable || whtRule.getWlSubtext().equalsIgnoreCase(subText))) {
                        Log.d(TAG, "Notification in Whitelist " + activeNotifications.getKey());
                        inWList = true;
                        break;
                    } else {
                        Log.d(TAG, "Notification NOT in Whitelist as per Rule " + activeNotifications.getKey());
                        //this.cancelNotification(sbn.getKey());
                        inWList = false;
                    }
                }
                if (inWList)
                    break;
            }
            if (!inWList) {
                Log.d(TAG, "Notification NOT in Whitelist Cancelling " + activeNotifications.getKey());
                this.cancelNotification(activeNotifications.getKey());
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void refreshActiveNotifications() {
        Log.e(TAG, "refreshActiveNotifications");
        StatusBarNotification[] activeNotifications = this.getActiveNotifications();

        if (activeNotifications != null && activeNotifications.length > 0) {

            for (int i = 0; i < activeNotifications.length; i++) {
                matchNotificationCode(activeNotifications[i]);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void matchNotificationCode(StatusBarNotification sbn) {
        Log.e(TAG, "matchNotificationCode");
        String packageName = sbn.getPackageName();
        String chnID = sbn.getNotification().getChannelId();
        Bundle extras = sbn.getNotification().extras;
        String text = null;
        String subText = null;
        String title = null;

        if (null != extras.getCharSequence(Notification.EXTRA_SUB_TEXT)) {
            subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT).toString();
        }
        if (null != extras.getCharSequence(Notification.EXTRA_TITLE)) {
            title = extras.getCharSequence(Notification.EXTRA_TITLE).toString();
        }
        if (null != extras.getCharSequence(Notification.EXTRA_TEXT)) {
            text = extras.getCharSequence(Notification.EXTRA_TEXT).toString();
        }

        if (null != isControlled && isControlled == true) {
            Log.e(TAG, "Feature Enabled " + isControlled);
            if (ruleType.equals("Black List")) {
                executeBlackListLogic(sbn, packageName, chnID, text, subText, title);
            } else {
                executeWhiteListLogic(sbn, packageName, chnID, text, subText, title);
            }
        } else {
            Log.e(TAG, "Feature disabled " + isControlled);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        StatusBarNotification[] activeNotifications = this.getActiveNotifications();
        if (activeNotifications != null && activeNotifications.length > 0) {
            Log.e(TAG, "Stacked Notification");
            for (int i = 0; i < activeNotifications.length; i++) {
                Bundle extras = activeNotifications[i].getNotification().extras;
                String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
                CharSequence notificationText = extras.getCharSequence(Notification.EXTRA_TEXT);
                CharSequence notificationSubText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);

                Log.e(TAG, "Notification no " + i);
                String packageName = activeNotifications[i].getPackageName();
                String chnID = activeNotifications[i].getNotification().getChannelId();

                if (null != packageName)
                    Log.e(TAG, "Package Name " + packageName);

                if (null != chnID)
                    Log.e(TAG, "Channel Id " + chnID);

                if (null != notificationTitle)
                    Log.e(TAG, "Title " + notificationTitle);

                if (null != notificationText)
                    Log.e(TAG, "Text " + notificationText.toString());

                if (null != notificationSubText)
                    Log.e(TAG, "Sub Text " + notificationSubText.toString());
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void refreshRules() throws BundleTools.BundleException, IOException {
        RestrictionsManager manager =
                (RestrictionsManager) getSystemService(Context.RESTRICTIONS_SERVICE);
        Bundle restrictions = manager.getApplicationRestrictions();
        List<RestrictionEntry> entries = manager.getManifestRestrictions(
                getApplicationContext().getPackageName());
        if (null != restrictions && !restrictions.isEmpty()) {
            String jsonString = createJsonStringFromBundle(restrictions, true);
            ObjectMapper objectMapper = new ObjectMapper();
            // check whether main file exist
            File mainFile = new File(getFilesDir().toString(), "main.json");
            if (mainFile.exists()) {
                // for now replace the same file again
                // todo compare previous and new file
                objectMapper.writeValue(new File(getFilesDir().toString(), "main.json"), jsonString);
            } else {
                // create main.json file
                objectMapper.writeValue(new File(getFilesDir().toString(), "main.json"), jsonString);
            }
            Log.d(TAG, "jsonString: " + jsonString);
            managedConfig = objectMapper.readValue(jsonString, NotificationManagedConfig.class);
            if (null != managedConfig) {
                List<ManagedProperty> mngPropertys = managedConfig.getManagedProperty();
                if (null != mngPropertys && !mngPropertys.isEmpty()) {
                    for (ManagedProperty mngProperty : mngPropertys) {
                        if (null != mngProperty.getNotificationIsControlled())
                            isControlled = mngProperty.getNotificationIsControlled();
                        if (null != mngProperty.getNotificationRuleType())
                            ruleType = mngProperty.getNotificationRuleType();
                        if (null != mngProperty.getNotificationControllerBlacklistrules())
                            blkListRules = mngProperty.getNotificationControllerBlacklistrules();
                        if (null != mngProperty.getNotificationControllerWhitelistrules())
                            whtListRules = mngProperty.getNotificationControllerWhitelistrules();
                    }
                }
                refreshActiveNotifications();
            }
        }
    }

    public static class LockedBootCompletedReceiver extends BroadcastReceiver {
        private static final String TAG = "BootCompletedReceiver";
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: " + intent.toString());
            Boolean isServiceRunning = isServiceRunning(
                    context.getApplicationContext(),
                    NotificationForegroundService.class);
            Intent serviceIntent = new Intent(context, NotificationForegroundService.class);
            serviceIntent.setAction(intent.getAction());
            context.startForegroundService(serviceIntent);
        }
    }
}