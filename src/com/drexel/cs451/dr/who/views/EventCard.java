package com.drexel.cs451.dr.who.views;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.GregorianCalendar;

import com.drexel.cs451.dr.who.R;
import com.drexel.cs451.dr.who.R.drawable;
import com.drexel.cs451.dr.who.R.id;
import com.drexel.cs451.dr.who.R.layout;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

public class EventCard extends Card{

    protected String end_date, mediaUrl;
	public String event_id;
	protected String event_desc;
	protected String header;
	protected String start_date;
    protected FragmentManager fm;
    protected Context context;
    protected Activity act;
    protected Fragment frag;
    //protected Activity act;

    public EventCard(Context context, String end_date, String mediaurl, String event_id, String event_desc, String header, String start_date, FragmentManager fm, Activity act, Fragment frag) {
        super(context, R.layout.card_season);
        //this.act=activity;
        this.end_date =end_date;
        this.mediaUrl = mediaurl;
        this.event_id=event_id;
        this.event_desc=event_desc;
        this.header=header;
        this.start_date=start_date;
        this.context = context;
        this.fm = fm;
        this.act = act;
        this.frag = frag;
        init();
    }

    private void init(){

        //Create a CardHeader
        CardHeader header1 = new CardHeader(this.getContext());

        //Set the header title
        header1.setTitle(this.header);
        //Set visible the expand/collapse button
        header1.setButtonExpandVisible(true);
        addCardHeader(header1);


        //Add ClickListener
        setOnClickListener(new OnCardClickListener() {

			@Override
			public void onClick(Card card, View view) {
				Intent calIntent = new Intent(Intent.ACTION_INSERT); 
				calIntent.setType("vnd.android.cursor.item/event");    
				calIntent.putExtra(Events.TITLE, header); 
				calIntent.putExtra(Events.DESCRIPTION, event_desc); 
				 
				int year, month, dayOfMonth, hourOfDay, minute;
				String[] sep = start_date.split(" ");
				String[] fir  = sep[0].split("-");
				year= Integer.parseInt(fir[0]);
				month= Integer.parseInt(fir[1]);
				dayOfMonth= Integer.parseInt(fir[2]);
				fir  = sep[1].split(":");
				hourOfDay= Integer.parseInt(fir[0]);
				minute= Integer.parseInt(fir[1]);
				
				GregorianCalendar calDate = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute);
				calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false); 
				calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 
				     calDate.getTimeInMillis()); 
				
				sep = end_date.split(" ");
				fir  = sep[0].split("-");
				year= Integer.parseInt(fir[0]);
				month= Integer.parseInt(fir[1]);
				dayOfMonth= Integer.parseInt(fir[2]);
				fir  = sep[1].split(":");
				hourOfDay= Integer.parseInt(fir[0]);
				minute= Integer.parseInt(fir[1]);
				
				calDate = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute);
				calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 
				     calDate.getTimeInMillis()); 
				 
				act.startActivity(calIntent);
			}
        });
        

       

        //This provides a simple (and useless) expand area
       // System.out.println(event_desc + " " + start_date+ " " +end_date);
        CustomExpandCard expand = new CustomExpandCard(this.getContext(), event_desc, start_date, end_date);
        //Set inner title in Expand Area
        //expand.setTitle(event_desc);
        //expand.setInnerLayout(mInnerLayout);
        addCardExpand(expand);

        //Set the card inner text
        //setTitle(mTitleMain);
        
        

    }
    
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
    	
        //Retrieve elements
        ImageView im = (ImageView) parent.findViewById(R.id.imageView);
       
        UrlImageViewHelper.setUrlDrawable(im, this.mediaUrl, R.drawable.ic_launcher);
        
    }
    
    
}