package com.scottblechman.otter.lifecycle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Called whenever the device has been restarted. Allows for the application to re-initialize alarms
 * that have been cleared in {@link android.app.AlarmManager}.
 */
public class OnBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: Get alarms from database
        // TODO: Set alarms in AlarmManager if enabled and in future
    }
}
