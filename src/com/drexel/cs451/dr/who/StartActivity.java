package com.drexel.cs451.dr.who;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.drexel.cs451.dr.who.load.ServerCalls;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class StartActivity extends Activity {

	private String username;
	private String pass;
	private ServerCalls sc = new ServerCalls();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		getActionBar().hide();
		
		//setup view
		
		View.OnClickListener loginHandler = new View.OnClickListener() {
		    public void onClick(View v) {
		     
		    	Intent intent = new Intent(getBaseContext(), LoginActivity.class);
		        startActivity(intent);
		        overridePendingTransition(R.anim.slide_in_right,
		                R.anim.slide_out_left);
		    }
		  };
		  
	  View.OnClickListener signupHandler = new View.OnClickListener() {
		    public void onClick(View v) {
		     
		    	Intent intent = new Intent(getBaseContext(), SignupActivity.class);
		        startActivity(intent);
		        overridePendingTransition(android.R.anim.slide_in_left,
		                android.R.anim.slide_out_right);
		    }
		  };
		
		Button bLogin = (Button) findViewById(R.id.loignButton);
		bLogin.setOnClickListener(loginHandler);
		
		Button bSignup = (Button) findViewById(R.id.signupButton);
		bSignup.setOnClickListener(signupHandler);
		
		
		
		//check if login state is saved
		SharedPreferences settings = this.getSharedPreferences("prefs", 0);
	    username = settings.getString("user", null);
	    pass = settings.getString("pass", null);
	    if(username == null || pass == null){
	    	//womp womp: nothing is saved
	    	
	    }else{
	    	//check if user and pass is good
	    	new login(this).execute();
	    	
	    }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	
	private class login extends AsyncTask<String, Void, Integer> {
		Activity act;
		
		private login(Activity a){
			act =a;
		}
		
		@Override
		protected Integer doInBackground(String... input) {
			//TODO: get info and parse web
			int login = sc.doLogin(username, pass);
			return login;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			
			if(result == 1){
	    		Intent intent = new Intent(getBaseContext(), MainActivity.class);
		        startActivity(intent);
		        act.finish();
	    	}
		}



	}

}
