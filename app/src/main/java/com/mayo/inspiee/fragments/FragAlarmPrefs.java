package com.mayo.inspiee.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mayo.inspiee.R;
import com.mayo.inspiee.utils.Communications;

public class FragAlarmPrefs extends Fragment {
	private boolean isAm = true;

	private String mAlarm;
	private String mAlarmSummary;

	private TextView mAlarmText;
	private TextView mAlarmSummaryText;

	private FragmentManager fm;
	private Context mContext;
    private Communications.ShowTimerDialog mShowTimerDialog;

	public FragAlarmPrefs() {
        mContext = getActivity();
		isAm = getArguments().getBoolean("isAm");
        mShowTimerDialog = (Communications.ShowTimerDialog)mContext;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_alarm_prefs, container,
				false);
		final boolean isMorning = isAm;
		if (isAm) {
			mAlarm = "Morning Alarm";
			mAlarmSummary = "set morning alarm";
		} else {
			mAlarm = "Evening Alarm";
			mAlarmSummary = "set Evening alarm";
		}

		mAlarmText = (TextView) view.findViewById(R.id.alarmText);
		mAlarmSummaryText = (TextView) view.findViewById(R.id.alarmSummaryText);

		mAlarmText.setText(mAlarm);
		mAlarmSummaryText.setText(mAlarmSummary);

		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                mShowTimerDialog.showTimerDialog(isMorning);
			}
		});
		return view;
	}
}
