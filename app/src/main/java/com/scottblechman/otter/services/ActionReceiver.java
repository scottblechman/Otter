package com.scottblechman.otter.services;

import android.app.NotificationManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.scottblechman.otter.R;
import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.lifecycle.AlarmViewModel;

import org.joda.time.DateTime;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Responds to actions selected from the notification.
 */
public class ActionReceiver extends BroadcastReceiver {

    private String mLabel;
    private DateTime mDateTime;

    @Override
    public void onReceive(Context context, Intent intent) {

        mLabel = intent.getStringExtra("label");
        mDateTime = new DateTime(intent.getLongExtra("time", new DateTime().getMillis()));

        String action = intent.getStringExtra("action");

        if(action.equals("snooze")){
            snoozeAlarm(context);
        }
        else if(action.equals("dismiss")){
            dismissAlarm(context);

        }

        // Close the notification
        context.stopService(new Intent(context, NotificationService.class));
    }

    public void snoozeAlarm(Context context){
        Toast.makeText(context, R.string.alarm_snoozed, Toast.LENGTH_SHORT).show();
        Alarm oldAlarm = new Alarm(mDateTime, mLabel);
        Alarm newAlarm = createSnoozedAlarm(context, oldAlarm);
        // TODO: update broadcast repository
    }

    public void dismissAlarm(Context context){
        Toast.makeText(context, R.string.notification_service_stopped, Toast.LENGTH_SHORT).show();
    }

    /**
     * Creates a copy of an ongoing alarm with a new activation time based on user preferences.
     * @param originalAlarm currently activated alarm
     * @return alarm with a new activation time
     */
    private Alarm createSnoozedAlarm(Context context, Alarm originalAlarm) {
        int minutes = context.getResources().getInteger(R.integer.snooze_minutes);
        DateTime dateTime = DateTime.now().plusMinutes(minutes);
        return new Alarm(dateTime, originalAlarm.getLabel());
    }
}
