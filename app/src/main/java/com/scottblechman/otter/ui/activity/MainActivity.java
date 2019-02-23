package com.scottblechman.otter.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.scottblechman.otter.R;
import com.scottblechman.otter.data.AlarmManager;
import com.scottblechman.otter.interfaces.FormsInterface;
import com.scottblechman.otter.ui.fragment.AlarmFragment;
import com.scottblechman.otter.ui.fragment.DatePickerFragment;
import com.scottblechman.otter.ui.fragment.LabelFragment;
import com.scottblechman.otter.ui.fragment.TimePickerFragment;

public class MainActivity extends AppCompatActivity
        implements AlarmFragment.OnListFragmentInteractionListener, FormsInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
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
    public void onListFragmentInteraction(AlarmManager.Alarm item) {

    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        DialogFragment newFragment = new LabelFragment();
        newFragment.show(getSupportFragmentManager(), "label");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Open time picker after date set
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onLabelCreated(String label) {

    }
}
