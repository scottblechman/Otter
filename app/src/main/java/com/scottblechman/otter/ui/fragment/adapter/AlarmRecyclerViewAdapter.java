package com.scottblechman.otter.ui.fragment.adapter;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.scottblechman.otter.R;
import com.scottblechman.otter.lifecycle.AlarmViewModel;
import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.ui.fragment.AlarmFragment;
import com.scottblechman.otter.ui.fragment.AlarmFragment.OnListFragmentInteractionListener;
import com.scottblechman.otter.ui.fragment.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Alarm} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.ViewHolder> {

    private List<Alarm> mValues;
    private final OnListFragmentInteractionListener mListener;
    private AlarmViewModel mAlarmViewModel;
    private AlarmFragment mFragment;

    public AlarmRecyclerViewAdapter(OnListFragmentInteractionListener listener, AlarmFragment fragment) {
        mListener = listener;
        mFragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_alarm, parent, false);
        mAlarmViewModel = ViewModelProviders.of(mFragment).get(AlarmViewModel.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        String timeFormat = "hh:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.US);
        holder.mTimeView.setText(sdf.format(mValues.get(position).getDate().toDate()));

        String timePeriodFormat = "a";
        sdf = new SimpleDateFormat(timePeriodFormat, Locale.US);
        holder.mTimePeriodView.setText(sdf.format(mValues.get(position).getDate().toDate()));

        String dateFormat = "MM/dd/yyyy";
        sdf = new SimpleDateFormat(dateFormat, Locale.US);
        holder.mDateView.setText(sdf.format(mValues.get(position).getDate().toDate()));

        holder.mLabelView.setText(mValues.get(position).getLabel());

        holder.mToggleSwitch.setChecked(mValues.get(position).getEnabled());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.mDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarm alarm = new Alarm();
                Bundle bundle = new Bundle();
                bundle.putParcelable("alarm", alarm);

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setArguments(bundle);
                assert mFragment.getFragmentManager() != null;
                newFragment.show(mFragment.getFragmentManager(), "datePicker");
            }
        });

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlarmViewModel.delete(holder.mItem);
                Toast.makeText(v.getContext(),"Delete item " + holder.mItem.getLabel(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        holder.mToggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Alarm oldAlarm = holder.mItem;
                holder.mItem.setEnabled(isChecked);
                mAlarmViewModel.update(oldAlarm, holder.mItem, true);
            }
        });
    }

    public void setAlarms(List<Alarm> alarms){
        mValues = alarms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mValues != null) {
            return mValues.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mDateView;
        final TextView mTimeView;
        final TextView mTimePeriodView;
        final TextView mLabelView;
        final Button mDeleteButton;
        final Switch mToggleSwitch;
        Alarm mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mDateView = view.findViewById(R.id.date);
            mTimeView = view.findViewById(R.id.time);
            mTimePeriodView = view.findViewById(R.id.timePeriod);
            mLabelView = view.findViewById(R.id.label);
            mDeleteButton = view.findViewById(R.id.buttonDelete);
            mToggleSwitch = view.findViewById(R.id.toggle);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mTimeView.getText() + "'";
        }
    }
}
