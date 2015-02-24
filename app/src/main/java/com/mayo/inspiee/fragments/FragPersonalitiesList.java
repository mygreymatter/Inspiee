package com.mayo.inspiee.fragments;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.mayo.inspiee.Inspiee;
import com.mayo.inspiee.R;
import com.mayo.inspiee.utils.StringUtils;

public class FragPersonalitiesList extends ListFragment {
	private Activity mActivity;
	private ListView mPersonalitiesList;
	private Button mSignup;
	private AssetManager mAssetManager;
	private ArrayList<String> mPersonalities;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mActivity = getActivity();		
		mAssetManager = mActivity.getAssets();
		getPersonalitiesList();		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_personalities_list,
				container, false);

		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mPersonalitiesList = getListView();		
		mPersonalitiesList.setAdapter(new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_checked, android.R.id.text1, mPersonalities));		
		mPersonalitiesList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

	}
	
	public ArrayList<String> getSelectedPersonalities(){
		ArrayList<String> selectedList = new ArrayList();
		ListView list = getListView();
		int count = list.getCount();

		for (int index = 0; index < count; index++) {
			if (list.isItemChecked(index))
			 selectedList.add(mPersonalities.get(index));
		}
		 return selectedList;
	}

	private void getPersonalitiesList() {
		String[] filesList;
		mPersonalities = new ArrayList<String>();
		int dotAt;
		try {
			filesList = mAssetManager.list("quotes");
			int count = filesList.length;
			for(int index = 0; index < count; index++){
				dotAt =  filesList[index].indexOf('.');
				mPersonalities.add(StringUtils.getNameInCaps(filesList[index].substring(0, dotAt)));
			}
		} catch (IOException e) {
			Log.i(Inspiee.TAG_INFO,"IOException: " + e.getMessage());
		}

		Log.i(Inspiee.TAG_INFO,"Personalities: " + mPersonalities.toString());
	}
}
