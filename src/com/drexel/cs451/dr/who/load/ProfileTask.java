package com.drexel.cs451.dr.who.load;

import it.gmariotti.cardslib.library.view.CardGridView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import com.drexel.cs451.dr.who.R;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//current url http://www.bbc.co.uk/blogs/doctorwho/
public class ProfileTask extends AsyncTask<String, Void, String> {
	private String[] items;
	private Activity calledActivity;
	private ArrayList<String[]> itemsList = new ArrayList<String[]>();
	
	public ProfileTask(Activity act){
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

		TextView tv = (TextView) calledActivity.findViewById(R.id.profileText);
		tv.setText(Html.fromHtml(result));

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
	String[] content = html.split("<div id=\"content\">");
	String sourceHtml = content[1].substring(0, content[1].indexOf("</div>"));
	
	
	return sourceHtml;
	
	
	}
}