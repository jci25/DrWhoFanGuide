package com.drexel.cs451.dr.who;

import com.drexel.cs451.dr.who.load.BioTask;
import com.drexel.cs451.dr.who.load.ProfileTask;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class AnnouncementActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_profile);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Bundle extras = getIntent().getExtras();
		String text = extras.getString("text");
		String img = extras.getString("Img");
		FadingActionBarHelper helper = new FadingActionBarHelper()
        .actionBarBackground(R.drawable.ab_background)
        .headerLayout(R.layout.profile_header)
        .contentLayout(R.layout.activity_profile);
    setContentView(helper.createView(this));
    TextView tv = (TextView) findViewById(R.id.profileText);
	tv.setText(text);
    ImageView im = (ImageView) findViewById(R.id.profile_image);
    im.getLayoutParams().height = 375;
    UrlImageViewHelper.setUrlDrawable(im, img);
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
