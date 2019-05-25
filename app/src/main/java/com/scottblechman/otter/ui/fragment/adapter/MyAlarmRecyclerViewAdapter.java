package com.scottblechman.otter.ui.fragment.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scottblechman.otter.R;
import com.scottblechman.otter.data.Alarm;
import com.scottblechman.otter.ui.fragment.AlarmFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Alarm} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAlarmRecyclerViewAdapter extends RecyclerView.Adapter<MyAlarmRecyclerViewAdapter.ViewHolder> {

    private List<Alarm> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyAlarmRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_alarm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Log.d("AlarmRecyclerViewAdapte", "onBindViewHolder: "+mValues.get(position).toString());
        holder.mDateView.setText(mValues.get(position).getDate().toString());
        holder.mTimeView.setText(mValues.get(position).getDate().toString());
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
        final TextView mLabelView;
        Alarm mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mDateView = view.findViewById(R.id.date);
            mTimeView = view.findViewById(R.id.time);
            mLabelView = view.findViewById(R.id.label);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mTimeView.getText() + "'";
        }
    }
}
