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

    protected String mTitleHeader, mediaUrl, previewText, id;
    protected Context context;
    //protected Activity act;

    public AnnounceCard(Context context, String titleHeader, String mediaUrl, String text, String id) {
        super(context, R.layout.card_announce);
        //this.act=activity;
        this.mTitleHeader=titleHeader;
        this.mediaUrl=mediaUrl;
        this.previewText=text;
        this.id=id;
        this.context = context;
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
				Intent intent = new Intent(context, AnnouncementActivity.class);
				intent.putExtra("text", previewText);
				intent.putExtra("Img", mediaUrl);
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
        previewText.setText(this.previewText);
        ImageView im = (ImageView) parent.findViewById(R.id.imageView);
        UrlImageViewHelper.setUrlDrawable(im, this.mediaUrl, R.drawable.ic_launcher);
        
    }

}