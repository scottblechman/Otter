package com.scottblechman.otter.lifecycle.boot;

import android.content.Context;

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

    public AlarmReinitWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            List<Alarm> alarms = AlarmDatabase.getDatabase(getApplicationContext()).alarmDao().getAllAlarmsSync();
            for(Alarm alarm : alarms) {
                reinitialize(alarm, getApplicationContext());
            }
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }

    /**
     * Checks if an alarm requires being re-initialized, then sets a new pending intent
     * @param alarm database entry with missing pending intent
     */
    private static void reinitialize(Alarm alarm, Context context) {
        DateTime now = DateTime.now();
        if(alarm.getEnabled() && alarm.getDate().isAfter(now.toInstant().getMillis())) {
            BroadcastRepository.getInstance().insert((Otter) context, alarm);
        }
    }
}
