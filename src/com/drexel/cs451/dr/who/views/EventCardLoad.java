package com.drexel.cs451.dr.who.views;

import com.drexel.cs451.dr.who.EventFragment;
import com.drexel.cs451.dr.who.R;
import com.drexel.cs451.dr.who.R.layout;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

public class EventCardLoad extends Card{

    protected Context context;
    protected EventFragment af;
    protected int count, pos;
    //protected Activity act;

    public EventCardLoad(Context context, EventFragment sf, int count, int pos) {
        super(context, R.layout.card_announce_load);
        //this.act=activity;
        this.context = context;
        this.af = sf;
        this.count = count;
        this.pos = pos;
        init();
    }

    private void init(){

        //Create a CardHeader
        CardHeader header = new CardHeader(this.getContext());

        //Set the header title
        header.setTitle("Loading more..");
        addCardHeader(header);


        //Set the card inner text
        //setTitle(mTitleMain);
        
        

    }
    
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
    	//remove and load more
    	af.removeCard(count, pos);
    	
        
    }

}