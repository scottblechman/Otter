package com.scottblechman.otter.ui.fragment.adapter;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.scottblechman.otter.R;
import com.scottblechman.otter.lifecycle.AlarmViewModel;
import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.ui.activity.AlarmActivity;
import com.scottblechman.otter.ui.fragment.AlarmFragment;
import com.scottblechman.otter.ui.fragment.AlarmFragment.OnListFragmentInteractionListener;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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
        holder.mTimeView.setText(sdf.format(mValues.get(position).getDate()));

        String timePeriodFormat = "a";
        sdf = new SimpleDateFormat(timePeriodFormat, Locale.US);
        holder.mTimePeriodView.setText(sdf.format(mValues.get(position).getDate()));

        String dateFormat = "MM/dd/yyyy";
        sdf = new SimpleDateFormat(dateFormat, Locale.US);
        holder.mDateView.setText(sdf.format(mValues.get(position).getDate()));

        holder.mLabelView.setText(mValues.get(position).getLabel());

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
        Alarm mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mDateView = view.findViewById(R.id.date);
            mTimeView = view.findViewById(R.id.time);
            mTimePeriodView = view.findViewById(R.id.timePeriod);
            mLabelView = view.findViewById(R.id.label);
            mDeleteButton = view.findViewById(R.id.buttonDelete);

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlarmViewModel.delete(mItem);
                    Toast.makeText(v.getContext(),"Delete item " + mItem.getLabel(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mTimeView.getText() + "'";
        }
    }
}
