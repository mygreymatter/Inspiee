package com.mayo.inspiee.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.mayo.inspiee.Inspiee;
import com.mayo.inspiee.R;

/**
 * Created by mayo on 2/1/15.
 */
public class TimePickerDialog extends DialogFragment {
    NumberPicker hours;
    NumberPicker minutes;

    int newHours;
    int newminutes;

    Button okButton;
    Button cancelButton;

    boolean isAm = false;
    private SharedPreferences db;
    private Bundle mArgs;
    private Context mContext;

    public TimePickerDialog(){
        mArgs = getArguments();
        isAm =  mArgs.getBoolean("isAm");
        mContext = getActivity();
        db = mContext.getSharedPreferences(Inspiee.DB, mContext.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Morning Alarm");
        View view = inflater.inflate(R.layout.dialog_timepicker, container,
                false);

        hours = (NumberPicker) view.findViewById(R.id.dialogHours);
        minutes = (NumberPicker) view.findViewById(R.id.dialogMinutes);
        okButton = (Button) view.findViewById(R.id.ok);
        cancelButton = (Button) view.findViewById(R.id.cancel);

        setDefaults();
        setListeners();
        return view;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    private void setDefaults() {
        if (isAm) {
            hours.setMinValue(0);
            hours.setMaxValue(11);
            minutes.setMinValue(0);
            minutes.setMaxValue(59);

            newHours = db.getInt(Inspiee.AM_HOURS, 0);
            newminutes = db.getInt(Inspiee.AM_MINUTES, 0);

            hours.setValue(newHours);
            minutes.setValue(newminutes);

            Log.i(Inspiee.TAG_INFO,
                    "mPrefs Hours: " + Integer.toString(db.getInt(Inspiee.AM_HOURS, 0)) +
                            " Minutes: " + Integer.toString(db.getInt(Inspiee.AM_MINUTES, 0)));
        }else{
            hours.setMinValue(12);
            hours.setMaxValue(23);
            minutes.setMinValue(0);
            minutes.setMaxValue(59);

            newHours = db.getInt(Inspiee.PM_HOURS, 0);
            newminutes = db.getInt(Inspiee.PM_MINUTES, 0);

            hours.setValue(newHours);
            minutes.setValue(newminutes);

            Log.i(Inspiee.TAG_INFO,
                    "Prefs Hours: "+ Integer.toString(db.getInt(Inspiee.PM_HOURS, 0)) +
                            " Minutes: "+ Integer.toString(db.getInt(Inspiee.PM_MINUTES, 0)));
        }
    }

    private void setListeners() {

        hours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal) {
                newHours = newVal;
                Log.i(Inspiee.TAG_INFO,
                        "New Hours: " + Integer.toString(newHours));
            }
        });

        minutes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal) {
                newminutes = newVal;
                Log.i(Inspiee.TAG_INFO,
                        "New Minutes: " + Integer.toString(newminutes));
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = db.edit();
                Log.i(Inspiee.TAG_INFO,
                        "Ok New Hours:" + Integer.toString(newHours)
                                + " Minutes: "
                                + Integer.toString(newminutes));
                if(isAm){
                    editor.putInt(Inspiee.AM_HOURS, newHours);
                    editor.putInt(Inspiee.AM_MINUTES, newminutes);
                }else{
                    editor.putInt(Inspiee.PM_HOURS, newHours);
                    editor.putInt(Inspiee.PM_MINUTES, newminutes);
                }
                editor.commit();
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
