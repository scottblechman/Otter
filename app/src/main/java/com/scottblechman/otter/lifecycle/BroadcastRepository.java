package com.scottblechman.otter.lifecycle;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.scottblechman.otter.db.Alarm;

import org.joda.time.DateTime;


import java.io.Serializable;

import static android.content.Context.ALARM_SERVICE;

@SuppressWarnings("WeakerAccess")
public class BroadcastRepository implements Serializable {

    private static volatile BroadcastRepository instance;


    private BroadcastRepository() {
        if (instance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static BroadcastRepository getInstance() {
        if (instance == null) {
            synchronized (BroadcastRepository.class) {
                if (instance == null) instance = new BroadcastRepository();
            }
        }

        return instance;
    }

    @SuppressWarnings("unused")
    protected BroadcastRepository readResolve() {
        return getInstance();
    }

    public void insert(Application application, Alarm alarm) {
        DateTime dateTime = alarm.getDate().withSecondOfMinute(0);

        Intent intent = new Intent(application, AlarmBroadcastReceiver.class);
        intent.putExtra("uid", alarm.getUid());
        intent.putExtra("label", alarm.getLabel());
        intent.putExtra("time", alarm.getDate().getMillis());
        intent.putExtra("enabled", alarm.getEnabled());
        PendingIntent sender = PendingIntent.getBroadcast(application, alarm.getUid(), intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) application.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, dateTime.getMillis(), sender);
    }

    public void delete(Application application, Alarm alarm) {
        Intent intent = new Intent(application, AlarmBroadcastReceiver.class);
        intent.putExtra("uid", alarm.getUid());
        intent.putExtra("label", alarm.getLabel());
        intent.putExtra("time", alarm.getDate().getMillis());
        intent.putExtra("enabled", alarm.getEnabled());
        PendingIntent sender = PendingIntent.getBroadcast(application, alarm.getUid(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) application.getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }

    public void update(Application application, Alarm oldAlarm, Alarm newAlarm) {
        Log.d(BroadcastRepository.class.toString(), "update: " + oldAlarm.toString() + newAlarm.toString());
        delete(application, oldAlarm);
        if(newAlarm.getEnabled()) {
            insert(application, newAlarm);
        }
    }
}
