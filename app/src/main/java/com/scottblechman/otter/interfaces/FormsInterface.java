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
     */
    void onTimeSet(Alarm alarm);

    /**
     * Called when {@link com.scottblechman.otter.ui.fragment.DatePickerFragment}
     * returns a date.
     */
    void onDateSet(Alarm alarm);

    /**
     * Called when {@link com.scottblechman.otter.ui.fragment.LabelFragment}
     * returns a label.
     */
    void onLabelCreated(Alarm alarm);
}
