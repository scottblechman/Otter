package com.scottblechman.otter.interfaces;

import android.widget.DatePicker;
import android.widget.TimePicker;

import com.scottblechman.otter.data.Alarm;

/**
 * A communications interface to pass form data to the main Activity in order
 * to persist it.
 */
public interface FormsInterface {

    /**
     * Called when {@link com.scottblechman.otter.ui.fragment.TimePickerFragment}
     * returns a time.
     * @param view TimePicker fragment
     * @param hour 0 through 23 representing the hour
     * @param minute 0 through 59 representing the minute
     */
    void onTimeSet(TimePicker view, int hour, int minute);

    /**
     * Called when {@link com.scottblechman.otter.ui.fragment.DatePickerFragment}
     * returns a date.
     * @param view DatePicker fragment
     * @param year current year as a number
     * @param month 1 through 12 representing the month
     * @param day 1 through 31 representing the day
     */
    void onDateSet(DatePicker view, int year, int month, int day, Alarm alarm);

    /**
     * Called when {@link com.scottblechman.otter.ui.fragment.LabelFragment}
     * returns a label.
     * @param label labels a specific alarm.
     */
    void onLabelCreated(String label);
}
