package com.mayo.inspiee.fragments;


import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;*/
import com.mayo.inspiee.R;
import com.mayo.inspiee.utils.Communications.FinishActivity;


public class FragLogin extends Fragment{

	private Context mContext;
//	private UiLifecycleHelper uiHelper;
//	private LoginButton mAuthButton;
	private String mLoginText;
	private FinishActivity mFinishActivity;
	private String mFBToken;
	
	/*private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};
	
	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
			Log.i("Inspiee","onSessionStateChange");
		if (state.isOpened()) {
//			mAuthButton.setVisibility(View.INVISIBLE);
			mFBToken = session.getAccessToken();
			Log.i("Inspiee","FB Token: " + mFBToken);
			if(mFBToken != null){
				mAuthButton.setVisibility(View.INVISIBLE);
				mFinishActivity.finishActivity();
			}
			
		} else if (state.isClosed()) {
			mAuthButton.setVisibility(View.VISIBLE);
		}
	}*/

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mContext = getActivity();
		mFinishActivity = (FinishActivity) mContext;				
		mLoginText = getResources().getString(R.string.fb_login);		
//		uiHelper = new UiLifecycleHelper(getActivity(), callback);
//		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_login, container, false);

//		mAuthButton = (LoginButton) view.findViewById(R.id.authButton);
//		mAuthButton.setFragment(this);
//		mAuthButton.setReadPermissions(Arrays.asList("public_profile", "email","user_birthday", "read_friendlists"));
//		mAuthButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//
//		mAuthButton.setText(mLoginText);

		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
//		Session session = Session.getActiveSession();
//		if (session != null && (session.isOpened() || session.isClosed())) {
//			onSessionStateChange(session, session.getState(), null);
//		}
//		uiHelper.onResume();
        mFinishActivity.finishActivity();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
//		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
//		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
//		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
//		uiHelper.onSaveInstanceState(outState);
	}
	
}
