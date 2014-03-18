package com.drexel.cs451.dr.who.views;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.drexel.cs451.dr.who.DetailedActivity;
import com.drexel.cs451.dr.who.R;
import com.drexel.cs451.dr.who.R.drawable;
import com.drexel.cs451.dr.who.R.id;
import com.drexel.cs451.dr.who.R.layout;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

public class EpisodeCard extends Card{

    protected String season_id;
	public String episode_nbr;
	protected String mediaUrl;
	public String episode_desc;
	public String episode_name;
	protected String thumbnail;
    protected Context context;
    protected Activity act;

    public EpisodeCard(Context context, String season_id, String episode_nbr, String mediaUrl, String episode_desc, String episode_name, String thumbnail, Activity a) {
        super(context, R.layout.card_announce);
        //this.act=activity;
        this.season_id=season_id;
        this.episode_nbr=episode_nbr;
        this.mediaUrl=mediaUrl;
        this.episode_desc=episode_desc;
        this.episode_name=episode_name;
        this.context = context;
        this.thumbnail = thumbnail;
        act = a;
        init();
    }

    private void init(){

        //Create a CardHeader
        CardHeader header = new CardHeader(this.getContext());

        //Set the header title
        header.setTitle(episode_nbr + episode_name);
        
      //Add Header to card
        addCardHeader(header);
    

       

        //Add ClickListener
        setOnClickListener(new OnCardClickListener() {

			@Override
			public void onClick(Card card, View view) {
				
				Intent intent = new Intent(context, DetailedActivity.class);
				intent.putExtra("text", episode_desc);
				intent.putExtra("Img", mediaUrl);
				intent.putExtra("thumb", thumbnail);
				intent.putExtra("title", episode_nbr+ episode_name);
				context.startActivity(intent);
			}
        });

        //Set the card inner text
        //setTitle(mTitleMain);
        
        

    }
    
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
    	
        //Retrieve elements
        TextView previewText = (TextView) parent.findViewById(R.id.text);
        previewText.setText(this.episode_desc);
        ImageView im = (ImageView) parent.findViewById(R.id.imageView);
        UrlImageViewHelper.setUrlDrawable(im, this.thumbnail, new UrlImageViewCallback() {
            @Override
            public void onLoaded(ImageView im, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
            	
                	try {
                		
                		
                		
                		Bitmap icon = loadedBitmap;

                		int screenWidth = act.getWindowManager().getDefaultDisplay().getWidth();
                		
                		int bw = icon.getWidth();
                		float t = (float) screenWidth / (float) bw;

                		//im.setImageBitmap(icon);
                		im.getLayoutParams().width = screenWidth;
                		im.getLayoutParams().height = (int) (icon.getHeight() * t);
                		// The following line is the one that scales your bitmap.
                		Bitmap scaledIcon = Bitmap.createScaledBitmap(icon, screenWidth, (int) (icon.getHeight() * t), false);
                		//im.setImageBitmap(scaledIcon);
                		im.getLayoutParams().width = screenWidth;
                		im.getLayoutParams().height = (int) (scaledIcon.getHeight());
                		} catch (Exception e) {
                		}
            	
                
            }
        });
    }
        	
        
    
    
    

}