package com.drexel.cs451.dr.who;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class DetailedActivity extends Activity {
	private Activity act;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_profile);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Bundle extras = getIntent().getExtras();
		String text = extras.getString("text");
		final String img = extras.getString("Img");
		String title = extras.getString("title");
		FadingActionBarHelper helper = new FadingActionBarHelper()
        .actionBarBackground(R.drawable.ab_background)
        .headerLayout(R.layout.profile_header)
        .contentLayout(R.layout.activity_profile);
		if(img.endsWith(".mp4")){
			helper = new FadingActionBarHelper()
	        .actionBarBackground(R.drawable.ab_background)
	        .headerLayout(R.layout.profile_header)
	        .contentLayout(R.layout.activity_profile_video);
		}
    setContentView(helper.createView(this));
    setTitle(title);
    TextView tv = (TextView) findViewById(R.id.profileText);
	tv.setText(text);
    ImageView im = (ImageView) findViewById(R.id.profile_image);
    im.getLayoutParams().height = 375;
    act = this;
    if(img.endsWith(".mp4")){
    	String thumb = extras.getString("thumb");
    	UrlImageViewHelper.setUrlDrawable(im, thumb);
    	ImageView im1 = (ImageView) findViewById(R.id.imageView);
    	im1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*Intent intent = new Intent(Intent.ACTION_VIEW);

	            intent.setDataAndType(Uri.parse(img), "video/*");

	            startActivity(Intent.createChooser(intent, "Complete action using"));*/
	            
	            Intent intent = new Intent(act, VideoActivity.class);
				intent.putExtra("url", img);
				act.startActivity(intent);
			}
    		
    	});
        
    }else{
    	try{
	    	String thumb = extras.getString("thumb");
	    	if(thumb == null){
	    		thumb = img;
	    	}
	    	UrlImageViewHelper.setUrlDrawable(im, thumb);
    	}catch(Exception e){
    		UrlImageViewHelper.setUrlDrawable(im, img);
    	}
    }
    
    helper.initActionBar(this);
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
