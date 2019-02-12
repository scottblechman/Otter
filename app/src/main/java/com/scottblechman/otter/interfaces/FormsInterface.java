package com.scottblechman.otter.interfaces;

import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * A communications interface to pass form data to the main Activity in order
 * to persist it.
 */
public interface FormsInterface {

    /**
     * Called when {@link com.scottblechman.otter.ui.fragment.TimePickerFragment}
     * returns a time.
     * @param view
     * @param hour
     * @param minute
     */
    void onTimeSet(TimePicker view, int hour, int minute);

    /**
     * Called when {@link com.scottblechman.otter.ui.fragment.DatePickerFragment}
     * returns a date.
     * @param view
     * @param year
     * @param month
     * @param day
     */
    void onDateSet(DatePicker view, int year, int month, int day);

    void onLabelCreated(String label);
}
