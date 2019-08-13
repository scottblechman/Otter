package com.scottblechman.otter.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.scottblechman.otter.Otter;
import com.scottblechman.otter.R;
import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.lifecycle.BroadcastRepository;

import org.joda.time.DateTime;

/**
 * Responds to actions selected from the notification.
 */
public class ActionReceiver extends BroadcastReceiver {

    private int mUid;
    private String mLabel;
    private DateTime mDateTime;
    private boolean mEnabled;

    private static String TAG = ActionReceiver.class.toString();

    @Override
    public void onReceive(Context context, Intent intent) {

        mUid = intent.getIntExtra("uid", -1);
        mLabel = intent.getStringExtra("label");
        mDateTime = new DateTime(intent.getLongExtra("time", new DateTime().getMillis()));
        mEnabled = intent.getBooleanExtra("enabled", false);

        String action = intent.getStringExtra("action");

        Log.d(TAG, "onReceive: performing action " + action + " on " + intent.getExtras().toString());
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
        oldAlarm.setUid(mUid);
        oldAlarm.setEnabled(mEnabled);

        Alarm newAlarm = createSnoozedAlarm(context, oldAlarm);
        BroadcastRepository broadcastRepository = BroadcastRepository.getInstance();
        broadcastRepository.update((Otter)context.getApplicationContext(), oldAlarm, newAlarm);
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
        Alarm newAlarm = originalAlarm;

        int minutes = context.getResources().getInteger(R.integer.snooze_minutes);
        DateTime dateTime = DateTime.now().plusMinutes(minutes);

        newAlarm.setDate(dateTime);
        return newAlarm;
    }
}
