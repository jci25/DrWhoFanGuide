package com.drexel.cs451.dr.who;

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
import it.gmariotti.cardslib.library.internal.Card.OnCardClickListener;

public class BioCard extends Card {

    protected String headerTitle, title, img, url;
    protected Activity act;

    public BioCard(Context context, String title, String img, String url, Activity act) {
    	super(context, R.layout.card_bio);
        this.title=title;
        this.img=img;
        this.url=url;
        this.act=act;
        init();
    }


    private void init(){

        //Create a CardHeader
        //CardHeader header = new CardHeader(this.getContext());

        //Set the header title
        //header.setTitle(this.title);
        //addCardHeader(header);

        //Add ClickListener
        setOnClickListener(new OnCardClickListener() {

			@Override
			public void onClick(Card card, View view) {
				Intent intent = new Intent(act.getBaseContext(), WebViewer.class);
				intent.putExtra("URL", url);
				act.startActivity(intent);
			}
        });

        //Set the card inner text
        //setTitle(mTitleMain);
        
        

    }
    
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
    	TextView tv = (TextView) parent.findViewById(R.id.charTitle);
    	tv.setText(title);
        ImageView im = (ImageView) parent.findViewById(R.id.bioImage);
        im.setScaleX((float) 2.5);
        im.setScaleY((float) 2.5);
        UrlImageViewHelper.setUrlDrawable(im, this.img);
        
    }

}