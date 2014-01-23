package com.drexel.cs451.dr.who;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.Card.OnCardClickListener;

public class ChooserBioCard extends Card {

    protected String headerTitle, title, img, url;
    protected Activity act;
    private FragmentManager fm;

    public ChooserBioCard(Context context, String title, String img, String url, Activity act, FragmentManager fm) {
    	super(context, R.layout.card_bio_chooser);
        this.title=title;
        this.img=img;
        this.url=url;
        this.act=act;
        this.fm=fm;
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
				if(url.contains("features") || url.contains("characters") || url.contains("-monsters") || url.contains("monsters-") ){
					Fragment fragment = new BioFragment();
		    		Bundle bundle = new Bundle();
		    		bundle.putString("URL", url);
		    		fragment.setArguments(bundle);
		    		FragmentTransaction ft = fm.beginTransaction(); 
		            ft.replace(R.id.content_frame, fragment);
		            ft.addToBackStack(null);
		            ft.commit();
		            act.setTitle(title);
				}else{
					Intent intent = new Intent(act.getBaseContext(), ProfleViewer.class);
					intent.putExtra("URL", url);
					intent.putExtra("Img", img);
					act.startActivity(intent);
				}
			}
        });

        //Set the card inner text
        //setTitle(title);
        
        

    }
    
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
    	TextView tv = (TextView) parent.findViewById(R.id.chooserTitle);
    	tv.setText(title);
    	
        ImageView im = (ImageView) parent.findViewById(R.id.chooserBioImage);
        //im.setScaleX((float) 4);
        //im.setScaleY((float) 4);
        UrlImageViewHelper.setUrlDrawable(im, this.img);
        
    }

}