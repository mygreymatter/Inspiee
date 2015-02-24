package com.mayo.inspiee.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mayo.inspiee.R;
import com.mayo.inspiee.utils.StringUtils;

public class FragQuotesList extends Fragment {
	private static final String TAG_INFO = "Inspiee";
	
	private Context mContext;
	private AssetManager mAssetManager;
	
	private ListView mQuotesList;
	private WebView mQuote; 
	
	private String mPerson;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		mAssetManager = mContext.getAssets();

		Bundle args = getArguments();
		mPerson = args.getString("personality");
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		getActivity().getActionBar().setDisplayShowHomeEnabled(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_list_quotes, container,false);
		mQuotesList = (ListView) view.findViewById(R.id.quotesList);
		QuotesAdapter adapter = new QuotesAdapter(mContext,getAllQuotes(mPerson));
		mQuotesList.setAdapter(adapter);
//		mQuotesList.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,mNames));

		return view;
	}
	
	//QuotesAdapter
	private class QuotesAdapter extends ArrayAdapter<String>{
		
		LayoutInflater inflater;
		ArrayList<String> quotes;
		ViewHolder viewHolder;
		
		final String mimeType = "text/html";
		final String encoding = "utf-8";
		
		public QuotesAdapter(Context context,ArrayList<String> quotes) {
			super(context, -1);
			
			inflater = LayoutInflater.from(context);
			this.quotes = quotes;
			Log.i(TAG_INFO,"Number of Quotes: "  + Integer.toString(quotes.size()));
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View quoteView = convertView;
			
			if(quoteView == null){
				quoteView = inflater.inflate(R.layout.row_quote, parent, false);
				viewHolder = new ViewHolder(quoteView);
				
				quoteView.setTag(viewHolder);
			}else{				
				viewHolder = (ViewHolder) convertView.getTag();				
			}
			Log.i(TAG_INFO,"Quote at " + Integer.toString(position) + " : " + quotes.get(position));
			
			/*String bodyContent = "<div style=\"text-align:justify;font-size:30px;font-family:gabrielle;\">" + 
			                     getRandomQuote() + "</div>" + "<br/>" + 
					             "<div style=\"text-align:right;margin-right:10px;font-size:15px;\">"+ "- " + 
			                     StringUtils.getNameInCaps(person) +"</div>";*/
			
			String htmlPage = "<html>"+
							  "<head><meta>"+
							  "<style>"+
							  "@font-face{font-family:\"gabrielle\";src:url(\'file:///android_asset/fonts/gabrielle.ttf\')}"+
							  "</style>"+
							  "</meta></head>"+
							  "<body>"+
							  quotes.get(position)+
							  "</body>"+
							  "</html>";
		    
		    
			viewHolder.getQuoteTextView().loadDataWithBaseURL("fake://not/needed",htmlPage, mimeType, encoding, "");;
			
			return quoteView;
		}
		
		@Override
		public int getCount() {
			
			return quotes.size();
		}
	
		//View Holder Class 
		class ViewHolder{
			
			View view;
			WebView quoteTextView;
			
			public ViewHolder(View view){
				this.view = view;
			}
			
			public WebView getQuoteTextView(){
				if(quoteTextView == null){
					quoteTextView = (WebView) view.findViewById(R.id.quote);
				}
				return quoteTextView;
			}
		}//View Holder Class
		
	}	//QuotesAdapter
	
	
	
	private ArrayList<String> getAllQuotes(String person){
		ArrayList<String> quotes = new ArrayList<String>();
		try {
			InputStream is = mAssetManager.open("quotes/" + person + ".txt");
			BufferedReader bf = new BufferedReader(new InputStreamReader(is));
			
			int quotesCount = Integer.parseInt(bf.readLine());
			
			for(int index=0; index<quotesCount; index++){
				quotes.add(bf.readLine());
			}
			
		} catch (IOException e) {
			Log.i(TAG_INFO,"IOException: " + e.getMessage());
		}
		Log.i(TAG_INFO,"Number of Quotes: "  + Integer.toString(quotes.size()));
		return quotes;
	}
	

}
