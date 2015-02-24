package com.mayo.inspiee.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

import com.mayo.inspiee.Inspiee;
import com.mayo.inspiee.R;
import com.mayo.inspiee.data.AlarmTimers;

public class FragSettingAlarm extends Fragment {

	private NumberPicker mAmHours;
	private NumberPicker mAmMinutes;
	private NumberPicker mPmHours;
	private NumberPicker mPmMinutes;
	private Button mNext;
	
	private Context mContext;

	private int amHours;
	private int amMinutes;
	private int pmHours;
	private int pmMinutes;	
	private Activity mActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		mActivity = getActivity();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(Inspiee.TAG_INFO, "FragSettingAlarm onCreatedView");
		View view = inflater.inflate(R.layout.frag_setting_alarm, container,
				false);

		// mTimePicker = (TimePicker) view.findViewById(R.id.timePicker);
		// setCurrentTimeOnView();

		findViewsById(view);
		setAlarmDefaults();
		setValueChangeListeners();
		
		return view;
	}

	private void findViewsById(View view) {
		mAmHours = (NumberPicker) view.findViewById(R.id.amHours);
		mAmMinutes = (NumberPicker) view.findViewById(R.id.amMinutes);
		mPmHours = (NumberPicker) view.findViewById(R.id.pmHours);
		mPmMinutes = (NumberPicker) view.findViewById(R.id.pmMinutes);
		
		mNext = (Button) view.findViewById(R.id.next);
	}

	private void setAlarmDefaults() {
		mAmHours.setMinValue(0);
		mAmHours.setMaxValue(11);

		mAmMinutes.setMinValue(0);
		mAmMinutes.setMaxValue(59);

		mPmHours.setMinValue(12);
		mPmHours.setMaxValue(23);

		mPmMinutes.setMinValue(0);
		mPmMinutes.setMaxValue(59);
		
		mAmHours.setValue(6);
		mPmHours.setValue(18);
		
		amHours = 6;
		amMinutes = 0;
		
		pmHours = 18;
		pmMinutes = 0;
		
	}

	private void setValueChangeListeners() {
		mAmHours.setOnValueChangedListener(new OnValueChangeListener() {			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				amHours = newVal;
			}
		});
		
		mAmMinutes.setOnValueChangedListener(new OnValueChangeListener() {			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				amMinutes = newVal;
			}
		});
		
		mPmHours.setOnValueChangedListener(new OnValueChangeListener() {			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				pmHours = newVal;
			}
		});
		
		mPmMinutes.setOnValueChangedListener(new OnValueChangeListener() {			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				pmMinutes = newVal;
			}
		});
		
		/*mNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
								
//				mFinishActivity.finishActivity();
				
			}
		});*/
		
	}
	
	public AlarmTimers getAlarmTimers(){
		AlarmTimers alarmTimers = new AlarmTimers(amHours, amMinutes, pmHours, pmMinutes);
		return alarmTimers;
	}
	
}
