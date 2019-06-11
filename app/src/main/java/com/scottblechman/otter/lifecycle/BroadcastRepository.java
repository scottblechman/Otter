package com.scottblechman.otter.lifecycle;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.scottblechman.otter.db.Alarm;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

class BroadcastRepository {

    private Application mApplication;

    BroadcastRepository(Application application) {
        mApplication = application;
    }

    void insert(Alarm alarm) {
        // get a Calendar object with current time
        Calendar cal = Calendar.getInstance();
        // add 30 seconds to the calendar object
        cal.add(Calendar.SECOND, 30);
        Intent intent = new Intent(mApplication, AlarmBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(mApplication, 192837, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) mApplication.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
    }
}
