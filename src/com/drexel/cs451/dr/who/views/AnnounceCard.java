package com.drexel.cs451.dr.who.views;

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
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

public class AnnounceCard extends Card{

    protected String mTitleHeader, mediaUrl, previewText;
	public String id;
    protected Context context;
    protected Activity act;

    public AnnounceCard(Context context, String titleHeader, String mediaUrl, String text, String id, Activity activity) {
        super(context, R.layout.card_announce);
        this.act=activity;
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
				Intent intent = new Intent(context, DetailedActivity.class);
				intent.putExtra("text", previewText);
				intent.putExtra("Img", mediaUrl);
				intent.putExtra("title", mTitleHeader);
				context.startActivity(intent);
			}
        });

        //Set the card inner text
        //setTitle(mTitleMain);
        
        

    }
    
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
    	System.out.println("Inner");
        //Retrieve elements
        TextView previewText = (TextView) parent.findViewById(R.id.text);
        previewText.setText(this.previewText);
        ImageView im = (ImageView) parent.findViewById(R.id.imageView);
        UrlImageViewHelper.setUrlDrawable(im, this.mediaUrl, new UrlImageViewCallback() {
            @Override
            public void onLoaded(ImageView im, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
            	
                	try {
                		
                		
                		System.out.println("TRY ");
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
                			System.out.println("ERROR ");
                		}
            
                
            }
        });
        
    }

}