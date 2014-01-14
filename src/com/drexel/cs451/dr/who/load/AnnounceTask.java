package com.drexel.cs451.dr.who.load;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import com.drexel.cs451.dr.who.AnnounceCard;
import com.drexel.cs451.dr.who.R;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

//current url http://www.bbc.co.uk/blogs/doctorwho/
public class AnnounceTask extends AsyncTask<String, Void, String> {
	private String[] items;
	private Activity calledActivity;
	private ArrayList<String[]> itemsList = new ArrayList<String[]>();
	
	public AnnounceTask(Activity act){
		calledActivity = act;
	}
	@Override
	protected String doInBackground(String... input) {
		//TODO: get info and parse web
			String next = buffer(input[0]);	
		return next;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
	}
	
	@Override
	protected void onPostExecute(String result) {
		ArrayList<Card> cards = new ArrayList<Card>();
		System.out.println("WHAT WHAT " + itemsList.size());
        for (int i=0;i<itemsList.size();i++){
            AnnounceCard card = new AnnounceCard(calledActivity,itemsList.get(i)[0],itemsList.get(i)[2],itemsList.get(i)[1],itemsList.get(i)[3],itemsList.get(i)[5],calledActivity);
            cards.add(card);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(calledActivity,cards);

        CardListView listView = (CardListView) calledActivity.findViewById(R.id.announce_list_cards);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }
	}


public String buffer(String announceUrl){
	System.out.println("do buffer");
	String sources = "";
	URL nextUrl;
	try {
		nextUrl = new URL(announceUrl);
		BufferedReader in = new BufferedReader( new InputStreamReader(nextUrl.openStream()));
		String inputLine;

		while ((inputLine = in.readLine()) != null){
			sources = sources + " " + inputLine;
			//System.out.println(inputLine);
		}
		System.out.println(sources);
		in.close();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println(e.toString());
		e.printStackTrace();
	}
	announceUrl = readSource(sources, announceUrl);

	return announceUrl;
}


/*
 * used for parsing html
 * @html = html source code of page
 * @return = array of specific information
 * 
 */
public String readSource(String html, String fullUrl){
	//System.out.println("start" + html);
	String[] content = html.split("<h3>");
	String artTitle = null;
	String artDate = null;
	String artAuth = null;
	String artImg = null;
	String preview = null;
	String artURL = null;
	for(int i = 1; i < content.length; i++){
		artURL = content[i].substring(content[i].indexOf("\"")+1, content[i].indexOf("\">"));
		artURL = "http://www.bbc.co.uk" + artURL;
		artTitle = content[i].substring(content[i].indexOf(">")+1, content[i].indexOf("</a>"));
		artDate = content[i].substring(content[i].indexOf("\"date\">")+7, content[i].indexOf("</p>"));
		artAuth = content[i].substring(content[i].indexOf("\"authorName\">")+13, content[i].indexOf("</span>"));
		System.out.println(artURL);
		System.out.println(artTitle);
		System.out.println(artDate);
		System.out.println(artAuth);
		String[] secondContent = content[i].split("\"asset\">");
		artImg = secondContent[1].substring(secondContent[1].indexOf("src=\"")+5, secondContent[1].indexOf("\" w"));
		preview = secondContent[1].substring(0, secondContent[1].indexOf("/p>"));
		preview = preview.substring(preview.lastIndexOf("</span>")+7);
		System.out.println(artImg);
		if(!preview.isEmpty()){
			if(preview.length() > 36){
				System.out.println(preview.substring(0, 30));
			}else{
				System.out.println(preview);
			}
		}
		items = new String[6];
		items[0] = artTitle;
		items[1] = artDate;
		items[2] = artAuth;
		items[3] = artImg;
		items[4] = preview;
		items[5] = artURL;
		//System.out.println(items[0]);
		itemsList.add(items);
		//System.out.println(itemsList.get(itemsList.size()-1)[0]);
	}
	
	
	return content[0];
	
	
	}
}