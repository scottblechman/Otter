package com.scottblechman.otter.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.scottblechman.otter.R;
import com.scottblechman.otter.lifecycle.AlarmViewModel;
import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.ui.interfaces.FormsInterface;
import com.scottblechman.otter.ui.fragment.AlarmFragment;
import com.scottblechman.otter.ui.fragment.DatePickerFragment;
import com.scottblechman.otter.ui.fragment.LabelFragment;
import com.scottblechman.otter.ui.fragment.TimePickerFragment;

public class MainActivity extends AppCompatActivity
        implements AlarmFragment.OnListFragmentInteractionListener, FormsInterface {

    private AlarmViewModel mAlarmViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MAINACTIVITY", "onCreate: start");
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
    public void onTimeSet(Alarm alarm) {
        Log.d(MainActivity.class.toString(), "onTimeSet: "+alarm.getDate().toString());
        Bundle bundle = new Bundle();
        bundle.putParcelable("alarm", alarm);

        DialogFragment newFragment = new LabelFragment();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "label");
    }

    @Override
    public void onDateSet(Alarm alarm) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("alarm", alarm);

        // Open time picker after date set
        Log.d(MainActivity.class.toString(), "onDateSet: "+alarm.getDate().toString());
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onLabelCreated(Alarm alarm) {
        Log.d("MainActivity", "onLabelCreated: "+alarm.getLabel());
        mAlarmViewModel.insert(alarm);
    }
}
