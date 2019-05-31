package com.scottblechman.otter.ui.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.ui.interfaces.FormsInterface;

import java.util.Calendar;
import java.util.Objects;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private FormsInterface mCallback;

    private Alarm mAlarm;

    @Override
    public void onActivityCreated(@Nullable Bundle outState) {
        super.onActivityCreated(outState);
        mCallback = (FormsInterface) getActivity();
        mAlarm = Objects.requireNonNull(getArguments()).getParcelable("alarm");

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @SuppressWarnings("deprecation")
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mAlarm.getDate().setHours(hourOfDay);
        mAlarm.getDate().setMinutes(minute);
        mCallback.onTimeSet(mAlarm);
    }
}