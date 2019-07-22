package com.scottblechman.otter.lifecycle;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.scottblechman.otter.Otter;
import com.scottblechman.otter.db.Alarm;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Called whenever the device has been restarted. Allows for the application to re-initialize alarms
 * that have been cleared in {@link android.app.AlarmManager}.
 */
public class OnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(OnBootReceiver.class.toString(), "onReceive:  got action " + intent.getAction());
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Application application = (Otter) context.getApplicationContext();

            AlarmRepository alarmRepository = AlarmRepository.getInstance();
            alarmRepository.initializeDataAccess(application);

            BroadcastRepository broadcastRepository = BroadcastRepository.getInstance();

            List<Alarm> alarms = alarmRepository.getAllAlarms().getValue();

            if (alarms != null) {
                for (Alarm alarm : alarms) {
                    reinitialize(alarm, broadcastRepository, application);
                }
            }
        }
    }

    /**
     * Checks if an alarm requires being re-initialized, then sets a new pending intent
     * @param alarm database entry with missing pending intent
     */
    private void reinitialize(Alarm alarm, BroadcastRepository repository, Application application) {
        DateTime now = DateTime.now();
        if(alarm.getEnabled() && alarm.getDate().isAfter(now.toInstant().getMillis())) {
            repository.insert(application, alarm);
        }
    }
}
