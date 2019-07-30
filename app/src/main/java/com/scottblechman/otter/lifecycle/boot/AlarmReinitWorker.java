package com.scottblechman.otter.lifecycle.boot;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.db.AlarmDatabase;

import java.util.List;

public class AlarmReinitWorker extends Worker {

    public AlarmReinitWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        try {
            List<Alarm> alarms = AlarmDatabase.getDatabase(getApplicationContext()).alarmDao().getAllAlarmsSync();
            for(Alarm alarm : alarms) {
                Log.d("AlarmReinitWorker", "doWork: " + alarm.getLabel());
            }
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }
}
