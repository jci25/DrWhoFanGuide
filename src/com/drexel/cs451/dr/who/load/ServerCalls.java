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
		
		final String URL = "http://www.chaosfalcon.com:8089/getannouncments?first="+f+"&count="+c;
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
				item[3] = jObject.get("announcment_id").toString();
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
