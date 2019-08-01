package com.scottblechman.otter.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.scottblechman.otter.R;
import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.ui.interfaces.FormsInterface;

import java.util.Objects;


public class LabelFragment extends DialogFragment {

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
    @SuppressLint("InflateParams")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.label_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                Objects.requireNonNull(getContext()));

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = promptsView
                .findViewById(R.id.editTextDialogUserInput);

        final TextView charactersRemaining = promptsView
                .findViewById(R.id.labelCharacters);

        userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                charactersRemaining.setText(String.format("%s/%s", s.length(),
                        getResources().getInteger(R.integer.max_length)));
            }

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c)
            { }
            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a)
            { }
        });

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.dialogOk,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                mAlarm.setLabel(userInput.getText().toString());
                                mCallback.onLabelCreated(mOriginalAlarm, mAlarm, mIsNewAlarm);
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
