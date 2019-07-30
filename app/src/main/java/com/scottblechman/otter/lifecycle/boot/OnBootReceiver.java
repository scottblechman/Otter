package com.scottblechman.otter.lifecycle.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

/**
 * Called whenever the device has been restarted. Allows for the application to re-initialize alarms
 * that have been cleared in {@link android.app.AlarmManager}.
 */
public class OnBootReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(AlarmReinitWorker.class)
                    .build();
            WorkManager.getInstance(context).enqueue(workRequest);
        }
    }
}
