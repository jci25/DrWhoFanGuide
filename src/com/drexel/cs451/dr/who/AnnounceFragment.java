package com.drexel.cs451.dr.who;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.drexel.cs451.dr.who.load.ServerCalls;
import com.drexel.cs451.dr.who.views.AnnounceCard;
import com.drexel.cs451.dr.who.views.AnnounceCardLoad;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.SearchView;

import it.gmariotti.cardslib.demo.fragment.BaseFragment;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.Card.OnCardClickListener;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;

public class AnnounceFragment extends BaseFragment {

    protected ScrollView mScrollView;
	public static final String ARG_PAGE_NUMBER = "page_number";
	private ServerCalls sc = new ServerCalls();
	ArrayList<Card> cards = new ArrayList<Card>();
	CardListView listView;
	CardArrayAdapter mCardArrayAdapter;
	int count = 5;
	protected Menu menu;
	
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

        setHasOptionsMenu(true);
        getActivity().setTitle(page);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       
        initCards ic = new initCards(this);
		 ic.execute();
    }
    
    @Override
	public void onCreateOptionsMenu(
	      Menu menu, MenuInflater inflater) {
		this.menu = menu;
        //System.out.println("got here");
        super.onCreateOptionsMenu(menu, inflater);
	}
	
	public void removeCard(int pos, int cardPos){
		
		removeCard rc = new removeCard(this, pos, cardPos);
		 rc.execute();
		 
	}
	
	 
	 
	private class initCards extends AsyncTask<Void, Void, ArrayList<String[]>> {

		 protected AnnounceFragment af;
		 
		 protected initCards(AnnounceFragment af){
			 this.af = af;
		 }

		 @Override
			protected void onPostExecute(ArrayList<String[]> result) {
				// TODO Auto-generated method stub
			 ArrayList<String[]> itemsList = result;
			 
				
			 
			 for (int i=0;i<itemsList.size();i++){
		            AnnounceCard card = new AnnounceCard(getActivity(),itemsList.get(i)[0],itemsList.get(i)[1],itemsList.get(i)[2],itemsList.get(i)[3], getActivity());
		            cards.add(card);
		        }
			 if(itemsList.size() % count == 0 && itemsList.size() != 0){
				 AnnounceCard card1 = (AnnounceCard) cards.get(cards.size()-1);
				 AnnounceCardLoad card = new AnnounceCardLoad(getActivity(), af, Integer.parseInt(card1.id), cards.size());
				 card.setOnClickListener(new OnCardClickListener(){

					@Override
					public void onClick(Card card, View view) {
						// TODO Auto-generated method stub
						AnnounceCard card1 = (AnnounceCard) cards.get(cards.size()-2);
						System.out.println(card1.id);
						removeCard(Integer.parseInt(card1.id), cards.size()-1);
					}
					 
				 });
		            cards.add(card);
			 }
		        mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

		        listView = (CardListView) getActivity().findViewById(R.id.announce_list_cards);
		        if (listView!=null){
		            listView.setAdapter(mCardArrayAdapter);
		        }
		        af.getActivity().runOnUiThread(new Runnable() {
	                public void run() {
	                    // some code #3 (that needs to be ran in UI thread)
	               	 //mCardArrayAdapter.addAll(cards);
	               	
	               	 //listView.invalidateViews();
	               	af.getActivity().setProgressBarIndeterminateVisibility(false);
	                }
	            });
			}

	        @Override
	        protected void onProgressUpdate(Void... values) {}

			@Override
			protected ArrayList<String[]> doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				af.getActivity().runOnUiThread(new Runnable() {
	                public void run() {
	                    // some code #3 (that needs to be ran in UI thread)
	               	 //mCardArrayAdapter.addAll(cards);
	               	
	               	 //listView.invalidateViews();
	               	af.getActivity().setProgressBarIndeterminateVisibility(true);
	                }
	            });
				ArrayList<String[]> itemsList = sc.getAnnouncements(0, count);
				
				return itemsList;
			}
			
	    }
	
	private class removeCard extends AsyncTask<Void, Void, ArrayList<String[]>> {

		 protected AnnounceFragment af;
		 protected int pos, cardPos;
		 
		 protected removeCard(AnnounceFragment af, int pos, int cardPos){
			 this.af = af;
			 this.pos = pos;
			 this.cardPos = cardPos;
		 }

		 @Override
			protected void onPostExecute(ArrayList<String[]> result) {
			 ArrayList<String[]> itemsList = result;
			 for (int i=0;i<itemsList.size();i++){
		            AnnounceCard card = new AnnounceCard(getActivity(),itemsList.get(i)[0],itemsList.get(i)[1],itemsList.get(i)[2],itemsList.get(i)[3], getActivity());
		            cards.add(card);
		        }
			 AnnounceCard card1 = (AnnounceCard) cards.get(cards.size()-1);
			 if(itemsList.size() % count == 0 && itemsList.size() != 0){
				 cards.remove(cardPos);
				 if(!card1.id.equals("1")){
					 AnnounceCardLoad card = new AnnounceCardLoad(getActivity(), af, Integer.parseInt(card1.id), mCardArrayAdapter.getCount() );
				 card.setOnClickListener(new OnCardClickListener(){

						@Override
						public void onClick(Card card, View view) {
							// TODO Auto-generated method stub
							AnnounceCard card1 = (AnnounceCard) cards.get(cards.size()-2);
							System.out.println(mCardArrayAdapter.getCount());
							removeCard(Integer.parseInt(card1.id), mCardArrayAdapter.getCount()-1 );
						}
						 
					 });
		            cards.add(card);
				 }
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
			 af.getActivity().runOnUiThread(new Runnable() {
	                public void run() {
	                    // some code #3 (that needs to be ran in UI thread)
	               	 //mCardArrayAdapter.addAll(cards);
	               	
	               	 //listView.invalidateViews();
	               	af.getActivity().setProgressBarIndeterminateVisibility(false);
	                }
	            });
			}

	        @Override
	        protected void onProgressUpdate(Void... values) {}

			@Override
			protected ArrayList<String[]> doInBackground(Void... arg0) {
				af.getActivity().runOnUiThread(new Runnable() {
	                public void run() {
	                    // some code #3 (that needs to be ran in UI thread)
	               	 //mCardArrayAdapter.addAll(cards);
	               	
	               	 //listView.invalidateViews();
	               	af.getActivity().setProgressBarIndeterminateVisibility(true);
	                }
	            });
				// TODO Auto-generated method stub
				ArrayList<String[]> itemsList = sc.getAnnouncements(pos-1, count);
				return itemsList;
			}
			
	    }
}