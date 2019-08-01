package com.scottblechman.otter.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scottblechman.otter.R;
import com.scottblechman.otter.lifecycle.AlarmViewModel;
import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.ui.interfaces.FormsInterface;
import com.scottblechman.otter.ui.fragment.AlarmFragment;
import com.scottblechman.otter.ui.fragment.DatePickerFragment;
import com.scottblechman.otter.ui.fragment.LabelFragment;
import com.scottblechman.otter.ui.fragment.TimePickerFragment;

import org.joda.time.DateTime;

public class MainActivity extends AppCompatActivity
        implements AlarmFragment.OnListFragmentInteractionListener, FormsInterface {

    private AlarmViewModel mAlarmViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAlarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alarm alarm = new Alarm();
                Bundle bundle = new Bundle();
                bundle.putParcelable("alarm", alarm);
                bundle.putBoolean("newAlarm", true);

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Alarm item) {
        Log.d("MainActivity", "onListFragmentInteraction: "+item.getLabel());
    }

    @Override
    public void onTimeSet(Alarm originalAlarm, Alarm alarm, boolean newAlarm) {
        if(newAlarm) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("alarm", alarm);
            bundle.putBoolean("newAlarm", true);

            DialogFragment newFragment;
            if (mAlarmViewModel.isAlarmValid(alarm, DateTime.now())) {
                newFragment = new LabelFragment();
            } else {
                newFragment = new TimePickerFragment();
            }
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "label");
        } else {
            mAlarmViewModel.update(originalAlarm, alarm, true);
        }
    }

    @Override
    public void onDateSet(Alarm originalAlarm, Alarm alarm, boolean newAlarm) {
        if(newAlarm) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("alarm", alarm);
            bundle.putBoolean("newAlarm", true);

            DialogFragment newFragment = new TimePickerFragment();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "timePicker");
        } else {
            mAlarmViewModel.update(originalAlarm, alarm, true);
        }
    }

    @Override
    public void onLabelCreated(Alarm originalAlarm, Alarm alarm, boolean newAlarm) {
        if(newAlarm) {
            mAlarmViewModel.insert(alarm);
        } else {
            mAlarmViewModel.update(originalAlarm, alarm, true);
        }
    }
}
