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

import org.joda.time.DateTime;

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
        final DateTime now = new DateTime();

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, now.getHourOfDay(), now.getMinuteOfHour(),
                DateFormat.is24HourFormat(getActivity()));
    }

    @SuppressWarnings("deprecation")
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        DateTime originalTime = mAlarm.getDate();
        DateTime selectedTime = new DateTime()
                .withYear(originalTime.getYear())
                .withMonthOfYear(originalTime.getMonthOfYear())
                .withDayOfMonth(originalTime.getDayOfMonth())
                .withHourOfDay(hourOfDay)
                .withMinuteOfHour(minute)
                .withSecondOfMinute(0);

        mAlarm.setDate(selectedTime);
        mCallback.onTimeSet(mAlarm);
    }
}