package com.scottblechman.otter.lifecycle;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;

import com.scottblechman.otter.db.Alarm;

import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

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
        Intent intent = new Intent(mApplication, AlarmBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(mApplication, 192837, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) mApplication.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
    }
}
