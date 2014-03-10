package com.drexel.cs451.dr.who;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.drexel.cs451.dr.who.load.ServerCalls;
import com.drexel.cs451.dr.who.views.SeasonCard;

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

public class CharactersFragment extends BaseFragment {

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
        new initCards(this).execute();
    }
    
    
    @Override
    public void onPause(){
    	super.onPause();
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	
    }
	
	
	 
	 
	 
	 private class initCards extends AsyncTask<Void, Void, ArrayList<String[]>> {

		 protected CharactersFragment sf;
		 
		 protected initCards(CharactersFragment sf){
			 this.sf = sf;
		 }

		 @Override
			protected void onPostExecute(ArrayList<String[]> result) {
				// TODO Auto-generated method stub
			 try{
				 ArrayList<String[]> itemsList = result;
				 
					
				 
				 for (int i=0;i<itemsList.size();i++){
			            SeasonCard card = new SeasonCard(getActivity(),itemsList.get(i)[0],itemsList.get(i)[2],itemsList.get(i)[1], getActivity().getFragmentManager(), getActivity(), new CharacterFragment());
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
					ArrayList<String[]> itemsList = sc.getCharacters();
					
					return itemsList;
				}catch(Exception e){
					return null;
				}
			}
			
	    }
	 
	 
	 
			
	    
	 
}