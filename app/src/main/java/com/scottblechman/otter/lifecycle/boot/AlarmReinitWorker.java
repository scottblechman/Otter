package com.scottblechman.otter.lifecycle.boot;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.scottblechman.otter.Otter;
import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.db.AlarmDatabase;
import com.scottblechman.otter.lifecycle.BroadcastRepository;

import org.joda.time.DateTime;

import java.util.List;

public class AlarmReinitWorker extends Worker {

    private static String TAG = AlarmReinitWorker.class.toString();

    public AlarmReinitWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: performing reinitialization");
        try {
            List<Alarm> alarms = AlarmDatabase.getDatabase(getApplicationContext()).alarmDao().getAllAlarmsSync();
            Log.d(TAG, "doWork: got " + alarms.size() + " alarm(s).");
            for(Alarm alarm : alarms) {
                Log.d(TAG, "doWork: reinitializing alarm " + alarm.getLabel());
                reinitialize(alarm, getApplicationContext());
            }
            return Result.success();
        } catch (Exception e) {
            Log.d(TAG, "doWork: error reinitializing: " + e.getMessage());
            return Result.failure();
        }
    }

    /**
     * Checks if an alarm requires being re-initialized, then sets a new pending intent
     * @param alarm database entry with missing pending intent
     */
    private static void reinitialize(Alarm alarm, Context context) {
        DateTime now = DateTime.now();
        Log.d(TAG, "reinitialize: checking alarm " + alarm.getLabel() + " against " +now.toString());
        Log.d(TAG, "reinitialize: alarm is enabled: " + alarm.getEnabled());
        Log.d(TAG, "reinitialize: alarm is after current time: " + alarm.getDate().isAfter(now.toInstant().getMillis()));
        if(alarm.getEnabled() && alarm.getDate().isAfter(now.toInstant().getMillis())) {
            Log.d(TAG, "reinitialize: adding alarm "+alarm.getLabel());
            BroadcastRepository.getInstance().insert((Otter) context, alarm);
        }
    }
}
