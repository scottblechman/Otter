package com.scottblechman.otter.lifecycle.boot;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.db.AlarmDatabase;
import com.scottblechman.otter.lifecycle.AlarmRepository;
import com.scottblechman.otter.lifecycle.AlarmViewModel;
import com.scottblechman.otter.lifecycle.BroadcastRepository;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Called whenever the device has been restarted. Allows for the application to re-initialize alarms
 * that have been cleared in {@link android.app.AlarmManager}.
 */
public class OnBootReceiver extends BroadcastReceiver {

    private static String TAG = OnBootReceiver.class.toString();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: received intent " + intent.getAction());
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d(TAG, "onReceive: got boot completion, queueing worker");
            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(AlarmReinitWorker.class)
                    .build();
            WorkManager.getInstance(context).enqueue(workRequest);
        }
    }
}
