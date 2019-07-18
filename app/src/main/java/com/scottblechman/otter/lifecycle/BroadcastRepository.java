package com.scottblechman.otter.lifecycle;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;

import com.scottblechman.otter.db.Alarm;

import org.joda.time.DateTime;


import static android.content.Context.ALARM_SERVICE;

@SuppressWarnings("deprecation")
class BroadcastRepository {

    private Application mApplication;

    BroadcastRepository(Application application) {
        mApplication = application;
    }

    void insert(Alarm alarm) {
        DateTime dateTime = alarm.getDate().withSecondOfMinute(0);

        Intent intent = new Intent(mApplication, AlarmBroadcastReceiver.class);
        intent.putExtra("label", alarm.getLabel());
        intent.putExtra("time", alarm.getDate().getMillis());
        PendingIntent sender = PendingIntent.getBroadcast(mApplication, alarm.getUid(), intent,
                PendingIntent.FLAG_ONE_SHOT);

        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) mApplication.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, dateTime.getMillis(), sender);
    }

    void delete(Alarm alarm) {
        Intent intent = new Intent(mApplication, AlarmBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(mApplication, alarm.hashCode(), intent,
                PendingIntent.FLAG_ONE_SHOT);

        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) mApplication.getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }

    void update(Alarm oldAlarm, Alarm newAlarm) {
        delete(oldAlarm);
        if(newAlarm.getEnabled()) {
            insert(newAlarm);
        }
    }
}
