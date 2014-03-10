package com.drexel.cs451.dr.who;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.drexel.cs451.dr.who.load.ServerCalls;
import com.drexel.cs451.dr.who.views.SeasonCard;
import com.drexel.cs451.dr.who.views.SeasonCardLoad;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import it.gmariotti.cardslib.demo.fragment.BaseFragment;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.Card.OnCardClickListener;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;

public class SeasonFragment extends BaseFragment {

    protected ScrollView mScrollView;
	public static final String ARG_PAGE_NUMBER = "page_number";
	private ServerCalls sc = new ServerCalls();
	ArrayList<Card> cards = new ArrayList<Card>();
	CardListView listView;
	CardArrayAdapter mCardArrayAdapter;
	int count = 5;
	
	@Override
    public int getTitleResourceId() {
		//not used anymore
        return 0;
    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_announce, container, false);
        int i = getArguments().getInt(ARG_PAGE_NUMBER);
        String page = getResources().getStringArray(R.array.pages_array)[i];

        getActivity().setTitle(page);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try{
        	mCardArrayAdapter.clear();
        }catch(Exception e){
        	
        }
        try{
        	new initCards(this).execute();
        }catch(Exception e){
        	
        }
    }
    
    
    @Override
    public void onPause(){
    	super.onPause();
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	
    }
	
	public void removeCard(int pos, int cardPos){
		
		removeCard rc = new removeCard(this, pos, cardPos);
		 rc.execute();
		 
	}
	
	 
	 
	 
	 private class initCards extends AsyncTask<Void, Void, ArrayList<String[]>> {

		 protected SeasonFragment sf;
		 
		 protected initCards(SeasonFragment sf){
			 this.sf = sf;
		 }

		 @Override
			protected void onPostExecute(ArrayList<String[]> result) {
				// TODO Auto-generated method stub
			 try{
				 ArrayList<String[]> itemsList = result;
				 
					
				 
				 for (int i=0;i<itemsList.size();i++){
			            SeasonCard card = new SeasonCard(getActivity(),itemsList.get(i)[0],itemsList.get(i)[1],itemsList.get(i)[2], getActivity().getFragmentManager(), getActivity(), new EpisodeFragment());
			            cards.add(card);
			        }
				 if(itemsList.size() % count == 0 && itemsList.size() != 0){
					 SeasonCard card1 = (SeasonCard) cards.get(cards.size()-1);
					 SeasonCardLoad card = new SeasonCardLoad(getActivity(), sf, Integer.parseInt(card1.season_id), cards.size());
					 cards.add(card);
				 }
			        mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);
	
			        listView = (CardListView) getActivity().findViewById(R.id.announce_list_cards);
			        if (listView!=null){
			            listView.setAdapter(mCardArrayAdapter);
			        }
			        sf.getActivity().runOnUiThread(new Runnable() {
		                public void run() {
		                    // some code #3 (that needs to be ran in UI thread)
		               	 //mCardArrayAdapter.addAll(cards);
		               	
		               	 //listView.invalidateViews();
		               	sf.getActivity().setProgressBarIndeterminateVisibility(false);
		                }
		            });
			 }catch(Exception e){
				 
			 }
			}

	        @Override
	        protected void onProgressUpdate(Void... values) {}

			@Override
			protected ArrayList<String[]> doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				try{
					sf.getActivity().runOnUiThread(new Runnable() {
		                public void run() {
		                    // some code #3 (that needs to be ran in UI thread)
		               	 //mCardArrayAdapter.addAll(cards);
		               	
		               	 //listView.invalidateViews();
		                	try{
		                		sf.getActivity().setProgressBarIndeterminateVisibility(true);
		                	}catch(Exception e){
		                		
		                	}
		                }
		            });
					ArrayList<String[]> itemsList = sc.getSeasons(1, count);
					
					return itemsList;
				}catch(Exception e){
					return null;
				}
			}
			
	    }
	 
	 
	 private class removeCard extends AsyncTask<Void, Void, ArrayList<String[]>> {

		 protected SeasonFragment sf;
		 protected int pos, cardPos;
		 
		 protected removeCard(SeasonFragment sf, int pos, int cardPos){
			 this.sf = sf;
			 this.pos = pos;
			 this.cardPos = cardPos;
		 }

		 @Override
			protected void onPostExecute(ArrayList<String[]> result) {
			 ArrayList<String[]> itemsList = result;
			 System.out.println(itemsList.size());
			 for (int i=0;i<itemsList.size();i++){
		            SeasonCard card = new SeasonCard(getActivity(),itemsList.get(i)[0],itemsList.get(i)[1],itemsList.get(i)[2], getActivity().getFragmentManager(), getActivity(), new EpisodeFragment());
		            cards.add(card);
		        }
			 SeasonCard card1 = (SeasonCard) cards.get(cards.size()-1);
			 if(itemsList.size() % count == 0 && itemsList.size() != 0){
				 cards.remove(cardPos);
					 SeasonCardLoad card = new SeasonCardLoad(getActivity(), sf, Integer.parseInt(card1.season_id), mCardArrayAdapter.getCount() );
				 card.setOnClickListener(new OnCardClickListener(){

						@Override
						public void onClick(Card card, View view) {
							// TODO Auto-generated method stub
							SeasonCard card1 = (SeasonCard) cards.get(cards.size()-2);
							System.out.println(mCardArrayAdapter.getCount());
							removeCard(Integer.parseInt(card1.season_id), mCardArrayAdapter.getCount()-1 );
						}
						 
					 });
		            cards.add(card);
		            getActivity().runOnUiThread(new Runnable() {
		                public void run() {
		                    // some code #3 (that needs to be ran in UI thread)
		               	 //mCardArrayAdapter.addAll(cards);
		               	
		               	 //listView.invalidateViews();
		               	mCardArrayAdapter.notifyDataSetChanged();
		                }
		            });
			 }else{
				 System.out.println("else");
				// this.cards.get(pos).getCardHeader().setTitle("Loaded all announcements");
				 cards.remove(cardPos);
				 getActivity().runOnUiThread(new Runnable() {
		                public void run() {
		                    // some code #3 (that needs to be ran in UI thread)
		               	 //mCardArrayAdapter.addAll(cards);
		               	
		               	 //listView.invalidateViews();
		                mCardArrayAdapter.notifyDataSetChanged();
		                }
		            });
			 }
			 sf.getActivity().runOnUiThread(new Runnable() {
	                public void run() {
	                    // some code #3 (that needs to be ran in UI thread)
	               	 //mCardArrayAdapter.addAll(cards);
	               	
	               	 //listView.invalidateViews();
	               	sf.getActivity().setProgressBarIndeterminateVisibility(false);
	                }
	            });
			}

	        @Override
	        protected void onProgressUpdate(Void... values) {}

			@Override
			protected ArrayList<String[]> doInBackground(Void... arg0) {
				sf.getActivity().runOnUiThread(new Runnable() {
	                public void run() {
	                    // some code #3 (that needs to be ran in UI thread)
	               	 //mCardArrayAdapter.addAll(cards);
	               	
	               	 //listView.invalidateViews();
	               	sf.getActivity().setProgressBarIndeterminateVisibility(true);
	                }
	            });
				// TODO Auto-generated method stub
				ArrayList<String[]> itemsList = sc.getSeasons(pos+1, count);
				return itemsList;
			}
			
	    }
	 
}