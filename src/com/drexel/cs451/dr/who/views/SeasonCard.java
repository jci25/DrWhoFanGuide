package com.drexel.cs451.dr.who.views;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.drexel.cs451.dr.who.R;
import com.drexel.cs451.dr.who.R.drawable;
import com.drexel.cs451.dr.who.R.id;
import com.drexel.cs451.dr.who.R.layout;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

public class SeasonCard extends Card{

    public String season_id;
	protected String mediaUrl;
	protected String season_desc;
    protected FragmentManager fm;
    protected Context context;
    protected Activity act;
    protected Fragment frag;
    //protected Activity act;

    public SeasonCard(Context context, String season_id, String season_desc, String mediaUrl, FragmentManager fm, Activity act, Fragment frag) {
        super(context, R.layout.card_season);
        //this.act=activity;
        this.season_id = season_id;
        this.season_desc = season_desc;
        this.mediaUrl = mediaUrl;
        this.context = context;
        this.fm = fm;
        this.act = act;
        this.frag = frag;
        init();
    }

    private void init(){

        //Create a CardHeader
        CardHeader header = new CardHeader(this.getContext());

        //Set the header title
        header.setTitle(season_desc);
        addCardHeader(header);

        //Add ClickListener
        setOnClickListener(new OnCardClickListener() {

			@Override
			public void onClick(Card card, View view) {
				//change fragment
				Fragment fragment = frag;
	    		Bundle bundle = new Bundle();
	    		bundle.putString("season", season_id);
	    		bundle.putString("desc", season_desc);
	    		fragment.setArguments(bundle);
	    		
	    		FragmentTransaction ft = fm.beginTransaction(); 
	            ft.replace(R.id.content_frame, fragment);
	            ft.addToBackStack(null);
	            ft.commit();
	            System.out.println("moved");
	            //act.setTitle(season_desc);
			}
        });

        //Set the card inner text
        //setTitle(mTitleMain);
        System.out.println("body");
        

    }
    
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
    	
        //Retrieve elements
        ImageView im = (ImageView) view.findViewById(R.id.imageView);
        
        UrlImageViewHelper.setUrlDrawable(im, this.mediaUrl, new UrlImageViewCallback() {
            @Override
            public void onLoaded(ImageView im, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
               
                		
                		
                		Bitmap icon = loadedBitmap;

                		int screenWidth = act.getWindowManager().getDefaultDisplay().getWidth();
                		
                		int bw = icon.getWidth();
                		float t = (float) screenWidth / (float) bw;

                		//im.setImageBitmap(icon);
                		im.getLayoutParams().width = screenWidth;
                		im.getLayoutParams().height = (int) (icon.getHeight() * t);
                		// The following line is the one that scales your bitmap.
                		System.out.println(screenWidth + " " + (int) (icon.getHeight() * t));
                		Bitmap scaledIcon = Bitmap.createScaledBitmap(icon, screenWidth, (int) (icon.getHeight() * t), false);
                		//im.setImageBitmap(scaledIcon);
                		im.getLayoutParams().width = screenWidth;
                		im.getLayoutParams().height = (int) (scaledIcon.getHeight());
                
            }
        });
        
        
    }
    
    
}