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
import it.gmariotti.cardslib.library.view.CardView;

public class AnnounceCard extends Card{

    protected String mTitleHeader;
    protected String Author, Date, Image, URL;
    protected Activity act;

    public AnnounceCard(Context context,String titleHeader,String Auth, String Date, String Img, String URL, Activity activity) {
        super(context, R.layout.card_announce);
        this.act=activity;
        this.mTitleHeader=titleHeader;
        this.Author=Auth;
        this.Date=Date;
        this.Image=Img;
        this.URL=URL;
        init();
    }

    private void init(){

        //Create a CardHeader
        CardHeader header = new CardHeader(this.getContext());

        //Set the header title
        header.setTitle(mTitleHeader);
        addCardHeader(header);

        //Add ClickListener
        setOnClickListener(new OnCardClickListener() {

			@Override
			public void onClick(Card card, View view) {
				Intent intent = new Intent(act.getBaseContext(), WebViewer.class);
				intent.putExtra("URL", URL);
				act.startActivity(intent);
			}
        });

        //Set the card inner text
        //setTitle(mTitleMain);
        
        

    }
    
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        //Retrieve elements
        TextView Author = (TextView) parent.findViewById(R.id.Author);
        //Author.setText(this.Author);
        TextView Date = (TextView) parent.findViewById(R.id.date);
        Date.setText(this.Date);
        ImageView im = (ImageView) parent.findViewById(R.id.imageView);
        UrlImageViewHelper.setUrlDrawable(im, this.Image);
        
    }

}