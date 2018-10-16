package com.shaary.neverforget.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.shaary.neverforget.R;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public static final String EXTRA_HOUR = "com.shaary.android.grudgeintent.hour";
    public static final String EXTRA_MINUTE = "com.shaary.android.grudgeintent.minute";
    private int hour;
    private int minute;

    @BindView(R.id.dialog_time_picker) TimePicker timePicker;

    public static TimePickerFragment newInstance(String time) {

        Bundle args = new Bundle();
        args.putString("time", time);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        splitTime(getArguments().getString("time"));

        if (hour == 0 &&  minute == 0) {
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        }

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);
        ButterKnife.bind(this, view);

        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        if ("ru".equals(Locale.getDefault().getLanguage())) {
            // russian
            timePicker.setIs24HourView(true);
        } else {
            timePicker.setIs24HourView(false);
        }
        //timePicker.setIs24HourView(DateFormat.is24HourFormat(getActivity()));

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.time_of_grudge)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> onTimeSet(timePicker, timePicker.getCurrentHour(), timePicker.getCurrentMinute()))
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dismiss())
                .create();
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        sendResult(Activity.RESULT_OK, hourOfDay, minute);
    }

    private void sendResult(int resultCode, int hourOfDay, int minute) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_HOUR, hourOfDay);
        intent.putExtra(EXTRA_MINUTE, minute);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    private void splitTime(String time) {
        String[] split = time.split("[\\s,:]+");
        hour = Integer.valueOf(split[0]);
        minute = Integer.valueOf(split[1]);
    }
}
