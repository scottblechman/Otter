package com.scottblechman.otter.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.scottblechman.otter.R;
import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.ui.interfaces.FormsInterface;

import java.util.Objects;


public class LabelFragment extends DialogFragment {

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
    @SuppressLint("InflateParams")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.label_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                Objects.requireNonNull(getContext()));

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = promptsView
                .findViewById(R.id.editTextDialogUserInput);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.dialogOk,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                mAlarm.setLabel(userInput.getText().toString());
                                mCallback.onLabelCreated(mAlarm);
                            }
                        })
                .setNegativeButton(R.string.dialogCancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        return alertDialogBuilder.create();
    }
}
