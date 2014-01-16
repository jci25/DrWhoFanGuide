package com.drexel.cs451.dr.who;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class WebViewer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_web_viewer);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Bundle extras = getIntent().getExtras();
		String url = extras.getString("URL");
		getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON); 
		WebView webView = (WebView) findViewById(R.id.webView1);
		//webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.loadUrl(url);
		System.out.println(url);
		final Activity here = this;
		webView.setWebChromeClient(new WebChromeClient() {
	        public void onProgressChanged(WebView view, int progress)  
	        {
	         here.setProgress(progress * 100); //Make the bar disappear after URL is loaded
	 
	         // Return the app name after finish loading
	            if(progress == 100);
	               //MyActivity.setTitle(R.string.app_name);
	          }
	        });
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        finish();
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
}
