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
        ImageView im = (ImageView) view.findViewById(R.id.imageView);

        System.out.println(mediaUrl);
        if(mediaUrl.endsWith(".mp4")){
        	
        	im.setImageResource(R.drawable.playvideo);
        	// Get the ImageView and its bitmap
            Drawable drawing = im.getDrawable();
            Bitmap bitmap = ((BitmapDrawable)drawing).getBitmap();

            // Get current dimensions
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            
         // Determine how much to scale: the dimension requiring less scaling is
            // closer to the its side. This way the image always stays inside your
            // bounding box AND either x/y axis touches it.
            float xScale = ((float) 500) / width;
            float yScale = ((float) 500) / height;
            float scale = (xScale <= yScale) ? xScale : yScale;

            // Create a matrix for the scaling and add the scaling data
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);

            // Create a new bitmap and convert it to a format understood by the ImageView
            Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            BitmapDrawable result = new BitmapDrawable(scaledBitmap);
            width = scaledBitmap.getWidth();
            height = scaledBitmap.getHeight();

            // Apply the scaled bitmap
            im.setImageDrawable(result);

            // Now change ImageView's dimensions to match the scaled image
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) im.getLayoutParams();
            params.width = width;
            params.height = height;
            im.setLayoutParams(params);
            
        	im.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Intent.ACTION_VIEW);

		            intent.setDataAndType(Uri.parse(mediaUrl), "video/*");

		            act.startActivity(Intent.createChooser(intent, "Complete action using"));
				}
        		
        	});
        }else{
        	UrlImageViewHelper.setUrlDrawable(im, this.thumbnail, R.drawable.ic_launcher);
        	im.setOnClickListener(new OnClickListener() {

    			@Override
    			public void onClick(View view) {
    				Intent intent = new Intent(context, DetailedActivity.class);
    				intent.putExtra("text", episode_desc);
    				intent.putExtra("Img", thumbnail);
    				intent.putExtra("title", episode_nbr + episode_name);
    				context.startActivity(intent);
    			}
            });
        }
        
    }
    
    

}