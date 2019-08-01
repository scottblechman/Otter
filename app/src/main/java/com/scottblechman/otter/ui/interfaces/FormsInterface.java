package com.scottblechman.otter.ui.interfaces;

import com.scottblechman.otter.db.Alarm;

/**
 * A communications interface to pass form data to the main Activity in order
 * to persist it.
 */
public interface FormsInterface {

    /**
     * Called when {@link com.scottblechman.otter.ui.fragment.TimePickerFragment}
     * returns a time.
     */
    void onTimeSet(Alarm oldAlarm, Alarm alarm, boolean newAlarm);

    /**
     * Called when {@link com.scottblechman.otter.ui.fragment.DatePickerFragment}
     * returns a date.
     */
    void onDateSet(Alarm oldAlarm, Alarm alarm, boolean newAlarm);

    /**
     * Called when {@link com.scottblechman.otter.ui.fragment.LabelFragment}
     * returns a label.
     */
    void onLabelCreated(Alarm oldAlarm, Alarm alarm, boolean newAlarm);
}
