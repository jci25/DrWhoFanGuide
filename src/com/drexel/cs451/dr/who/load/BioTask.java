package com.drexel.cs451.dr.who.load;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardGridView;
import it.gmariotti.cardslib.library.view.CardListView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import com.drexel.cs451.dr.who.AnnounceCard;
import com.drexel.cs451.dr.who.BioCard;
import com.drexel.cs451.dr.who.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

//current url http://www.bbc.co.uk/blogs/doctorwho/
public class BioTask extends AsyncTask<String, Void, String> {
	private String[] items;
	private Activity calledActivity;
	private FragmentManager fm;
	private ArrayList<String[]> itemsList = new ArrayList<String[]>();
	
	public BioTask(Activity act, FragmentManager fragmentManager){
		calledActivity = act;
		fm = fragmentManager;
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
        for (int i=0;i<itemsList.size();i++){
            BioCard card = new BioCard(calledActivity,itemsList.get(i)[0],itemsList.get(i)[1],itemsList.get(i)[2],calledActivity, fm);
            cards.add(card);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(calledActivity,cards);

        CardGridView gridView = (CardGridView) calledActivity.findViewById(R.id.bio_grid_base);
        if (gridView!=null){
            gridView.setAdapter(mCardArrayAdapter);
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
	//System.out.println("start" + html);    </ul>
	String[] content = html.split("<li class=\"person");
	if( fullUrl.contains("-characters")){
		content = html.split("li class=\"item individual\"");
	}else if((fullUrl.contains("-monsters") || fullUrl.contains("monsters-")) && content.length <= 1){
		content = html.split("<li class=\"item individual\"");
	}
	String artTitle = null;
	String artImg = null;
	String artURL = null;
	for(int i = 1; i < content.length; i++){
		
		if(i == content.length - 1){
			content[i] = content[i].substring(0, content[i].indexOf("</ul>"));
		}
		
		
		if(fullUrl.contains("-characters") || fullUrl.contains("-monsters") || fullUrl.contains("monsters-")){
			artURL = content[i].substring(content[i].indexOf("href=\"")+6, content[i].indexOf("\">"));
			artURL = "http://www.bbc.co.uk"+artURL;
			System.out.println(artURL);
			content[i] = content[i].substring(content[i].indexOf("\">"));
			artTitle = content[i].substring(content[i].indexOf("<span class=\"title\">")+20, content[i].indexOf("</span>"));
			System.out.println(artTitle);
			content[i] = content[i].substring(content[i].indexOf("</span>"));
			artImg = content[i].substring(content[i].indexOf("src=\"")+5, content[i].indexOf("\" height"));
			System.out.println(artImg);
			
		}else{
			content[i] = content[i].substring(content[i].indexOf("\">")+2);
			artURL = content[i].substring(content[i].indexOf("href=\"")+6, content[i].indexOf("\">"));
			artURL = "http://www.bbc.co.uk"+artURL;
			content[i] = content[i].substring(content[i].indexOf("\">"));
			artImg = content[i].substring(content[i].indexOf("img src=\"")+9, content[i].indexOf("\" "));
			content[i] = content[i].substring(content[i].indexOf("\" "));
			artTitle = content[i].substring(content[i].indexOf("</span>")+7, content[i].indexOf("</a>"));
		}
		
		
		items = new String[3];
		items[0] = artTitle;
		items[1] = artImg;
		items[2] = artURL;
		//System.out.println(items[0]);
		itemsList.add(items);
		//System.out.println(itemsList.get(itemsList.size()-1)[0]);
	}
	
	
	return content[0];
	
	
	}
}