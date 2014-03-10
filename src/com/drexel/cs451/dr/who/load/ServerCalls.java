package com.drexel.cs451.dr.who.load;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.drexel.cs451.dr.who.R;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

public class ServerCalls extends Activity {
	
	Context callingContext;
	String SetServerString = "";
	
	
	public ServerCalls(){
		callingContext = getBaseContext();
	}
	
	public ServerCalls(Context c){
		callingContext = c;
	}
	
public ArrayList<String[]> getAnnouncements(int f, int c){
		
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] item = new String[4];
		
		final String URL = "http://www.chaosfalcon.com:8089/getannouncements?first="+f+"&count="+c;
		System.out.println(URL);
		String response = httpGet(URL);
		try{
			JSONObject jObject = new JSONObject(response);
			JSONArray jArr = jObject.getJSONArray("results");
			for(int i = 0; i < jArr.length(); i++){
				item = new String[4];
				jObject = jArr.getJSONObject(i);
				item[0] = jObject.get("header").toString();
				item[1] = jObject.get("mediaurl").toString();
				item[2] = jObject.get("text").toString();
				item[3] = jObject.get("announcement_id").toString();
				list.add(item);
			}
		}catch(Exception e){
			System.err.println(e);
		}
			
		
		return list;
	}

public ArrayList<String[]> getSeasons(int f, int c){
	
	ArrayList<String[]> list = new ArrayList<String[]>();
	String[] item = new String[3];
	
	final String URL = "http://www.chaosfalcon.com:8089/getseasons?first="+f+"&count="+c;
	System.out.println(URL);
	String response = httpGet(URL);
	try{
		JSONObject jObject = new JSONObject(response);
		JSONArray jArr = jObject.getJSONArray("results");
		for(int i = 0; i < jArr.length(); i++){
			item = new String[3];
			jObject = jArr.getJSONObject(i);
			item[0] = jObject.get("season_id").toString();
			item[1] = jObject.get("season_desc").toString();
			item[2] = jObject.get("mediaurl").toString();
			list.add(item);
		}
	}catch(Exception e){
		System.err.println(e);
	}
		
	
	return list;
}

public ArrayList<String[]> getEvents(int f, int c){
	
	ArrayList<String[]> list = new ArrayList<String[]>();
	String[] item = new String[6];
	
	final String URL = "http://www.chaosfalcon.com:8089/getevents?first="+f+"&count="+c;
	System.out.println(URL);
	String response = httpGet(URL);
	try{
		JSONObject jObject = new JSONObject(response);
		JSONArray jArr = jObject.getJSONArray("results");
		for(int i = 0; i < jArr.length(); i++){
			item = new String[6];
			jObject = jArr.getJSONObject(i);
			item[0] = jObject.get("end_date").toString();
			item[1] = jObject.get("mediaurl").toString();
			item[2] = jObject.get("event_id").toString();
			item[3] = jObject.get("event_desc").toString();
			item[4] = jObject.get("header").toString();
			item[5] = jObject.get("start_date").toString();
			list.add(item);
		}
	}catch(Exception e){
		System.err.println(e);
	}
		
	
	return list;
}

public ArrayList<String[]> getCharacters(){
	
	ArrayList<String[]> list = new ArrayList<String[]>();
	String[] item = new String[3];
	
	final String URL = "http://www.chaosfalcon.com:8089/getcharactertitles";
	System.out.println(URL);
	String response = httpGet(URL);
	try{
		JSONObject jObject = new JSONObject(response);
		JSONArray jArr = jObject.getJSONArray("results");
		for(int i = 0; i < jArr.length(); i++){
			item = new String[3];
			jObject = jArr.getJSONObject(i);
			item[0] = jObject.get("title_id").toString();
			item[1] = jObject.get("mediaurl").toString();
			item[2] = jObject.get("title").toString();
			list.add(item);
		}
	}catch(Exception e){
		System.err.println(e);
	}
		
	
	return list;
}

public ArrayList<String[]> getEpisodesBySeason(String id){
	
	ArrayList<String[]> list = new ArrayList<String[]>();
	String[] item = new String[6];
	
	final String URL = "http://www.chaosfalcon.com:8089/getepisodesbyseason?season_id="+id;
	System.out.println(URL);
	String response = httpGet(URL);
	try{
		JSONObject jObject = new JSONObject(response);
		JSONArray jArr = jObject.getJSONArray("results");
		for(int i = 0; i < jArr.length(); i++){
			item = new String[6];
			jObject = jArr.getJSONObject(i);
			item[0] = jObject.get("season_id").toString();
			item[1] = "Ep " + jObject.get("episode_nbr").toString()+ ": ";
			item[2] = jObject.get("mediaurl").toString();
			item[3] = jObject.get("episode_desc").toString();
			item[4] = jObject.get("episode_name").toString();
			item[5] = jObject.get("thumbnail").toString();
			
			list.add(item);
		}
	}catch(Exception e){
		System.err.println(e);
	}
		
	
	return list;
}

public ArrayList<String[]> getCharactersByTitle(String id){
	
	ArrayList<String[]> list = new ArrayList<String[]>();
	String[] item = new String[5];
	
	final String URL = "http://www.chaosfalcon.com:8089/getcharacters?title_id="+id;
	System.out.println(URL);
	String response = httpGet(URL);
	try{
		JSONObject jObject = new JSONObject(response);
		JSONArray jArr = jObject.getJSONArray("results");
		for(int i = 0; i < jArr.length(); i++){
			item = new String[5];
			jObject = jArr.getJSONObject(i);
			item[0] = jObject.get("character_id").toString();
			item[1] = "";
			item[2] = jObject.get("mediaurl").toString();
			item[3] = jObject.get("bio").toString();
			item[4] = jObject.get("character").toString();
			
			list.add(item);
		}
	}catch(Exception e){
		System.err.println(e);
	}
		
	
	return list;
}
	
	public int doLogin(String u, String p){
		//final String URL = "http://chaosfalcon.com:8089/login?username=grover3&password=123abcd";
		final String URL = "http://chaosfalcon.com:8089/login?username="+u+"&password="+p;
		 String response = httpGet(URL);
			try{
				JSONObject jObject = new JSONObject(response);
				jObject.getString("success");
				return 1;
				
			}catch(JSONException e){
				try{
					JSONObject jObject = new JSONObject(response);
					jObject.getString("error"); //password erorr
					return 2;
				}catch(JSONException e2){
					//username error
					
				}
			}
		
		return 0;
		
	}

	public int doSignup(String u, String p, String c,
			String e) {
			final String URL = "http://chaosfalcon.com:8089/adduser?username="+u+"&password="+p+"&confirm_password="+c+"&email="+e;
		 
			System.out.println(URL);
			String response = httpGet(URL);
			try{
				JSONObject jObject = new JSONObject(response);
				jObject.getString("success");
				return 1;
				
			}catch(JSONException e1){
				try{
					JSONObject jObject = new JSONObject(response);
					jObject.getString("email_error");
					return 2;
				}catch(JSONException e2){
				}
				try{
					JSONObject jObject = new JSONObject(response);
					jObject.getString("password_error");
					return 3;
				}catch(JSONException e2){
				}
				try{
					JSONObject jObject = new JSONObject(response);
					jObject.getString("username_error");
					return 4;
				}catch(JSONException e2){
				}
				try{
					JSONObject jObject = new JSONObject(response);
					jObject.getString("password_len_error");
					return 5;
				}catch(JSONException e2){
				}
				try{
					JSONObject jObject = new JSONObject(response);
					jObject.getString("email_syntax_error");
					return 6;
				}catch(JSONException e2){
				}
				
				return 0;
			}
	}
	
	private String httpGet(final String URL){
		SetServerString = null;
		Thread thread = new Thread(new Runnable(){
			 
		    @Override
		    public void run() {
		        try {
		        	HttpClient Client = new DefaultHttpClient();
					
					
					
					HttpGet httpget = new HttpGet(URL);
			          ResponseHandler<String> responseHandler = new BasicResponseHandler();
			          SetServerString = Client.execute(httpget, responseHandler);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		});

		thread.start(); 
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SetServerString;
	}
	
}
