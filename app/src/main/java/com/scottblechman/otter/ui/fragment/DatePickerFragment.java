package com.scottblechman.otter.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.scottblechman.otter.data.Alarm;
import com.scottblechman.otter.interfaces.FormsInterface;

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
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(Objects.requireNonNull(getActivity()),
                this, year, month, day);
    }

    @SuppressWarnings("deprecation")
    public void onDateSet(DatePicker view, int year, int month, int day) {
        mAlarm.getDate().setYear(year);
        mAlarm.getDate().setMonth(month);
        mAlarm.getDate().setDate(day);
        mCallback.onDateSet(mAlarm);
    }
}