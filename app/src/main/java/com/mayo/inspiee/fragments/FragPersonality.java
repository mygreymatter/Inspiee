package com.mayo.inspiee.fragments;

import java.io.IOException;
import java.io.InputStream;

import android.app.Fragment;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.mayo.inspiee.R;
import com.mayo.inspiee.data.BioData;
import com.mayo.inspiee.utils.StringUtils;

public class FragPersonality extends Fragment {
	private static final String TAG_INFO = "Inspiee";

	// private TextView mPersonDetails;
	// private FlowTextView mPersonDetails;
	private WebView mPersonDetails;
	private ImageView mPersonFace;

	private Context mContext;
	private AssetManager mAssetManager;

	private Typeface mAngelTears;

	private String personality;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		mAssetManager = mContext.getAssets();

		Bundle args = getArguments();
		personality = args.getString("personality");

		// mAngelTears = Typeface.createFromAsset(mAssetManager,
		// "fonts/angel_tears.ttf");
		mAngelTears = Typeface.createFromAsset(mAssetManager,
				"fonts/gabrielle.ttf");

		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		getActivity().getActionBar().setDisplayShowHomeEnabled(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_personality, container,
				false);

		// mPersonDetails = (TextView) view.findViewById(R.id.personDetails);
		mPersonDetails = (WebView) view.findViewById(R.id.personDetails);

		// mPersonDetails.setTypeface(mAngelTears);
		return view;
	}

	@Override
	public void onResume() {
//		setPersonalityFace();
		setPersonalityBio();
		super.onResume();
	}

	private void setPersonalityFace() {

		try {
			String[] filelist = mAssetManager.list("faces");

			if (filelist != null) {

				personality = "abraham_lincon";
				InputStream is = mAssetManager.open("faces/" + personality
						+ ".jpg");

				if (is != null) {
					Log.i(TAG_INFO, "Path: " + "faces/" + personality + ".jpg");
					Bitmap bitmap = BitmapFactory.decodeStream(is);
					Drawable d = new BitmapDrawable(mContext.getResources(),
							bitmap);
					Log.i(TAG_INFO, "Drawable: " + d);
					mPersonFace.setImageBitmap(bitmap);
				} else {
					Log.i(TAG_INFO, "InputStream is null");
				}
			}
		} catch (IOException e) {
			Log.i(TAG_INFO, "Exception: " + e.getMessage());
		}
	}

	private void setPersonalityBio() {
		String bio = BioData.bioData.get(personality);
		String name = StringUtils.getNameInCaps(personality);
		String mimeType = "text/html";
		String encoding = "utf-8";
	    String html = "<div style=\"text-align:center;background-color:red;\"><h1>" + name + "</h1></div>" + 
				"<p style=\"text-align:justify;\">" +
				"<img src=\"file:///android_asset/faces/" + personality +".jpg\"" + 
				"style=\"float:left;margin:10px;width:100px;height:100px;\" />" +
				bio +
				"</p>";
	    mPersonDetails.loadDataWithBaseURL("fake://not/needed", html, mimeType, encoding, "");

		// mPersonDetails.setText(bio);
	}

	
}
