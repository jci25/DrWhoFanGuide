package com.drexel.cs451.dr.who.views;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

public class CharacterCard extends Card{

    protected String season_id, episode_nbr, mediaUrl, episode_desc, episode_name;
    protected Context context;
    //protected Activity act;

    public CharacterCard(Context context, String season_id, String episode_nbr, String mediaUrl, String episode_desc, String episode_name) {
        super(context, R.layout.card_announce);
        //this.act=activity;
        this.season_id=season_id;
        this.episode_nbr=episode_nbr;
        this.mediaUrl=mediaUrl;
        this.episode_desc=episode_desc;
        this.episode_name=episode_name;
        this.context = context;
        init();
    }

    private void init(){

        //Create a CardHeader
        CardHeader header = new CardHeader(this.getContext());

        //Set the header title
        header.setTitle("Ep " + episode_nbr + ": " + episode_name);
        addCardHeader(header);

        System.out.println(mediaUrl);
        //Add ClickListener
        setOnClickListener(new OnCardClickListener() {

			@Override
			public void onClick(Card card, View view) {
				Intent intent = new Intent(context, DetailedActivity.class);
				intent.putExtra("text", episode_desc);
				intent.putExtra("Img", mediaUrl);
				intent.putExtra("title", "Ep " + episode_nbr + ": " + episode_name);
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
        if(mediaUrl.endsWith(".mp4")){
        	
        	//UrlImageViewHelper.setUrlDrawable(im, this.mediaUrl, R.drawable.drwhologo);
        	im.setImageResource(R.drawable.drwhologo);
        }else{
        	UrlImageViewHelper.setUrlDrawable(im, this.mediaUrl, R.drawable.ic_launcher);
        }
        
    }
    
    public class LoadVideoThumbnail extends AsyncTask<Object, Void, Bitmap>{
    	ImageView im;
    	public LoadVideoThumbnail(ImageView i){
    		im = i;
    	}
    	@Override
        protected Bitmap doInBackground(Object... params) {
    		try {

            String mMediaPath = "http://commonsware.com/misc/test2.3gp";
            Log.e("TEST Chirag","<< thumbnail doInBackground"+ mMediaPath);
            FileOutputStream out;
            File land=new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/portland.jpg");

                Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(mMediaPath, MediaStore.Video.Thumbnails.MICRO_KIND);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        out=new  FileOutputStream(land.getPath());
                        out.write(byteArray);
                        out.close();
                 return bitmap;

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return null;
            }
    	
        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if(result != null){
                 im.setImageBitmap(result);
            }
            Log.e("TEST Chirag","====> End");
        }

    
    }

}