package com.scottblechman.otter.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.ui.interfaces.FormsInterface;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Objects;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

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
        // Use the current date as the default date in the picker
        final DateTime today = new DateTime();

        // Create a new instance of DatePickerDialog and return it.
        // Set the earliest possible date to the current date.
        DatePickerDialog dialog = new DatePickerDialog(Objects.requireNonNull(getActivity()),
                this, today.getYear(), today.getMonthOfYear(), today.getDayOfMonth());

        DatePicker datePicker = dialog.getDatePicker();
        datePicker.setMinDate(today.getMillis());

        return dialog;
    }

    @SuppressWarnings("deprecation")
    public void onDateSet(DatePicker view, int year, int month, int day) {
        DateTime selectedDate = new DateTime()
                .withYear(year)
                .withMonthOfYear(month)
                .withDayOfMonth(day)
                .withHourOfDay(0)
                .withMinuteOfHour(0)
                .withSecondOfMinute(0);

        mAlarm.setDate(selectedDate);
        mCallback.onDateSet(mAlarm);
    }
}