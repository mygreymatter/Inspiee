package com.mayo.inspiee.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.mayo.inspiee.R;
import com.mayo.inspiee.utils.Communications;

public class FragPersonalitiesPrefs  extends Fragment {
	
	private FragmentManager fm;
	private Context mContext;
    private Communications.ShowPersonalities mShowPersonalities;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		mContext = getActivity();
        mShowPersonalities = (Communications.ShowPersonalities) mContext;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_personalities_prefs, container, false);
		
		view.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				mShowPersonalities.showPersonalities();
			}
			
		});
		
		return view;
	}
}
