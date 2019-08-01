package com.scottblechman.otter.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.widget.DatePicker;

import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.ui.interfaces.FormsInterface;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Objects;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private FormsInterface mCallback;

    private Alarm mOriginalAlarm;
    private Alarm mAlarm;

    private boolean mIsNewAlarm;

    @Override
    public void onActivityCreated(@Nullable Bundle outState) {
        super.onActivityCreated(outState);
        mCallback = (FormsInterface) getActivity();
        mAlarm = Objects.requireNonNull(getArguments()).getParcelable("alarm");
        mOriginalAlarm = mAlarm;
        mIsNewAlarm = Objects.requireNonNull(getArguments()).getBoolean("newAlarm");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar calendar = Calendar.getInstance();

        // Create a new instance of DatePickerDialog and return it.
        // Set the earliest possible date to the current date.
        DatePickerDialog dialog = new DatePickerDialog(Objects.requireNonNull(getActivity()),
                this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));

        DatePicker datePicker = dialog.getDatePicker();
        datePicker.setMinDate(calendar.getTimeInMillis());

        return dialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);

        DateTime selectedDate = new DateTime(calendar.getTimeInMillis());
        mAlarm.setDate(selectedDate);
        mCallback.onDateSet(mOriginalAlarm, mAlarm, mIsNewAlarm);
    }
}