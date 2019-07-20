package com.scottblechman.otter.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.scottblechman.otter.R;
import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.lifecycle.AlarmViewModel;
import com.scottblechman.otter.services.NotificationService;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class AlarmActivity extends AppCompatActivity {

    private AlarmViewModel mAlarmViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        mAlarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel.class);

        TextView labelTv = findViewById(R.id.labelText);
        labelTv.setText(getIntent().getStringExtra("label"));

        TextView timeTv = findViewById(R.id.timeText);
        final DateTime dateTime = new DateTime(getIntent().getLongExtra("time",
                new DateTime().getMillis()));
        DateTimeFormatter fmt = DateTimeFormat.forPattern("EEEE, MMMM d y, h:mm a");
        timeTv.setText(dateTime.toString(fmt));

        Button mSnoozeButton = findViewById(R.id.buttonSnooze);
        mSnoozeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Alarm oldAlarm = new Alarm(dateTime, getIntent().getStringExtra("label"));
                Alarm newAlarm = createSnoozedAlarm(oldAlarm);
                mAlarmViewModel.update(oldAlarm, newAlarm,false);
                finish();
            }
        });

        Button mCancelButton = findViewById(R.id.buttonCancel);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getApplicationContext(), NotificationService.class));
                finish();
            }
        });
    }

    /**
     * Creates a copy of an ongoing alarm with a new activation time based on user preferences.
     * @param originalAlarm currently activated alarm
     * @return alarm with a new activation time
     */
    private Alarm createSnoozedAlarm(Alarm originalAlarm) {
        int minutes = getResources().getInteger(R.integer.snooze_minutes);
        DateTime dateTime = DateTime.now().plusMinutes(minutes);
        return new Alarm(dateTime, originalAlarm.getLabel());
    }
}
