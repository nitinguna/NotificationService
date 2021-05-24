package com.example.notification;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

import static com.example.notification.Utilities.isServiceRunning;

public class FirstFragment extends Fragment {

    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    private AlertDialog enableNotificationListenerAlertDialog;

    private static final String TAG = "ForeGroundService";

    @Override
    public void onResume() {
        super.onResume();
        Boolean isServiceRunning = isServiceRunning(
                getContext(),
                NotificationForegroundService.class);
        Log.d(TAG, "On Resume isServiceRunning foreGround " +isServiceRunning);
        if(!isServiceRunning){
            Boolean isNotificationServiceEnabled =isNotificationServiceEnabled();
            Log.d(TAG, "On Resume isNotificationServiceEnabled  " +isNotificationServiceEnabled);
            if(isNotificationServiceEnabled) {
                //getView().findViewById(R.id.button_first).
                startService();
            }
        }
    }



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        /*
        Boolean isServiceRunning = isServiceRunning(
                getContext(),
                NotificationForegroundService.class);
        Log.d(TAG, "On onCreateView isServiceRunning foreGround " +isServiceRunning);
        if(isServiceRunning){
            Log.d(TAG, "Killing Foreground service ");
            Intent serviceIntent = new Intent(getContext(), NotificationForegroundService.class);
            getContext().stopService(serviceIntent);
        }
        // check whether its really got killed
        isServiceRunning = isServiceRunning(
                getContext(),
                NotificationForegroundService.class);
        Log.d(TAG, "check On onCreateView isServiceRunning foreGround " +isServiceRunning);
        */
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If the user did not turn the notification listener service on we prompt him to do so

                if(!isNotificationServiceEnabled()){
                    enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog();
                    enableNotificationListenerAlertDialog.show();
                }

                //if(isNotificationServiceEnabled()) {
                  //  startService();
                //}
            }
        });
    }

    public void startService() {
        Intent serviceIntent = new Intent(getContext(), NotificationForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        getContext().startService(serviceIntent);
    }

    private boolean isNotificationServiceEnabled(){
        String pkgName = getContext().getPackageName();
        final String flat = Settings.Secure.getString(getContext().getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Build Notification Listener Alert Dialog.
     * Builds the alert dialog that pops up if the user has not turned
     * the Notification Listener Service on yet.
     * @return An alert dialog which leads to the notification enabling screen
     */
    private AlertDialog buildNotificationServiceAlertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle(R.string.notification_listener_service);
        alertDialogBuilder.setMessage(R.string.notification_listener_service_explanation);
        alertDialogBuilder.setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
                    }
                });
        alertDialogBuilder.setNegativeButton(R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If you choose to not enable the notification listener
                        // the app. will not work as expected
                    }
                });
        return(alertDialogBuilder.create());
    }


}