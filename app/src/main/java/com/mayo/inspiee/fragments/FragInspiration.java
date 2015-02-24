package com.mayo.inspiee.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.widget.Button;

import com.mayo.inspiee.Inspiee;
import com.mayo.inspiee.R;
import com.mayo.inspiee.activities.ActInspiration;
import com.mayo.inspiee.utils.StringUtils;

@SuppressWarnings("unused")
public class FragInspiration extends Fragment {
	 
	private static final String TAG_INFO = "Inspiee";
	private static final String FOLDER_NAME = "personalities";
	
	private WebView mInspireText;
	private Button mMoreButton;
	
	private String[] mPersonalities;
	private String folder;
	private String person;
	private String quote;
	
	private Context mContext;
	private AssetManager mAssetManager;
	private int mPersonality;
	private int prevFolderChoice;
	private int choice;
	
	private Animation mFadeIn;
	private String mCurrentQuote;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mContext = getActivity();
		mAssetManager = mContext.getAssets();
		mPersonalities = mContext.getResources().getStringArray(R.array.personalities);
		
		mFadeIn = new AlphaAnimation(0,1);
		mFadeIn.setInterpolator(new DecelerateInterpolator());
		mFadeIn.setStartOffset(200);
		mFadeIn.setDuration(1400);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_inspiration, container, false);

		mInspireText = (WebView) view.findViewById(R.id.inspireText);
		mPersonality = 0;
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		/*if(ActInspiration.ShouldInspired){
			ActInspiration.ShouldInspired = false;
			quote = getRandomQuote();
		}*/
	
		if(savedInstanceState != null)
			quote = savedInstanceState.getString("quote");
		updateQuote(quote);
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onResume() {		
		super.onResume();
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putString("quote", quote);
	}
	
	
	
	public void updateQuote(String quote){
		if(quote == null)
			quote = getRandomQuote();
		
		String mimeType = "text/html";
		String encoding = "utf-8";
		String bodyContent = "<div style=\"text-align:justify;font-size:30px;font-family:gabrielle;\">" + 
		                      quote + "</div>" + "<br/>" + 
				             "<div style=\"text-align:right;margin-right:10px;font-size:15px;\">"+ "- " + 
		                     StringUtils.getNameInCaps(person) +"</div>";
		
		String htmlPage = "<html>"+
						  "<head><meta>"+
						  "<style>"+
						  "@font-face{font-family:\"gabrielle\";src:url(\'file:///android_asset/fonts/gabrielle.ttf\')}"+
						  "</style>"+
						  "</meta></head>"+
						  "<body>"+
						  bodyContent+
						  "</body>"+
						  "</html>";
	//setting animation causes some unexpected view appearing	
//	    mInspireText.startAnimation(mFadeIn);
	    mInspireText.loadDataWithBaseURL("fake://not/needed",htmlPage, mimeType, encoding, "");
	}
	

	public String getPersonalityFolder(){ return folder; }
	
	public String getPersonality(){return person;}
	
	private String getRandomQuote(){
		
		Random generator = new Random();
		String[] filesList;
		int dotAt; 
		try {
			filesList = mAssetManager.list("quotes");
			int count = filesList.length;			
			/*choice = generator.nextInt(count);
			person = filesList[choice];
			dotAt = person.indexOf(".txt");
			person = person.substring(0, dotAt);*/
			
			while(true){
				choice = generator.nextInt(count);
				person = filesList[choice];
				dotAt = person.indexOf(".txt");
				person = person.substring(0, dotAt);
				
				if(Inspiee.SELECTED_PERSONALITIES.indexOf(StringUtils.getNameInCaps(person)) != -1)
					break;
			}
			Log.i(TAG_INFO,"Person: " + person);
			
			InputStream is = mAssetManager.open("quotes/" + filesList[choice]);
			BufferedReader bf = new BufferedReader(new InputStreamReader(is));
			
			int quotesCount = Integer.parseInt(bf.readLine()); 
			choice = generator.nextInt(quotesCount);
			
			for(int index = 0; index <= choice; index++){
				mCurrentQuote = bf.readLine();				
			}
			
			
		} catch (IOException e) {
			Log.i(TAG_INFO,"FragInspiration IOException: " + e.getMessage());
		}
		
		
		return mCurrentQuote;
	}
	
	public String getCurrentPersonality(){return person;}
	public String getCurrentQuote(){return mCurrentQuote;}
}
