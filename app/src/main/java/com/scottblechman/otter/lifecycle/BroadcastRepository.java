package com.scottblechman.otter.lifecycle;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.scottblechman.otter.db.Alarm;

import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

@SuppressWarnings("deprecation")
class BroadcastRepository {

    private Application mApplication;

    BroadcastRepository(Application application) {
        mApplication = application;
    }

    void insert(Alarm alarm) {
        // get a Calendar object with current time
        Calendar cal = Calendar.getInstance();
        // set date and time to alarm values
        Date date = alarm.getDate();

        cal.set(date.getYear(), date.getMonth(), date.getDate(),
                date.getHours(), date.getMinutes(), 0);
        Log.d("BroadcastRepository", "insert: "+cal.getTime());
        Intent intent = new Intent(mApplication, AlarmBroadcastReceiver.class);
        intent.putExtra("label", alarm.getLabel());
        PendingIntent sender = PendingIntent.getBroadcast(mApplication, alarm.getUid(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) mApplication.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
    }

    void delete(Alarm alarm) {
        // get a Calendar object with current time
        Calendar cal = Calendar.getInstance();
        // set date and time to alarm values
        Date date = alarm.getDate();

        cal.set(date.getYear(), date.getMonth(), date.getDate(),
                date.getHours(), date.getMinutes(), 0);
        Intent intent = new Intent(mApplication, AlarmBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(mApplication, alarm.hashCode(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

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
