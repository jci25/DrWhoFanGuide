package com.drexel.cs451.dr.who;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.drexel.cs451.dr.who.load.ServerCalls;
import com.drexel.cs451.dr.who.views.EpisodeCard;

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
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import it.gmariotti.cardslib.demo.fragment.BaseFragment;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.Card.OnCardClickListener;
import it.gmariotti.cardslib.library.view.CardListView;

public class EpisodeFragment extends BaseFragment implements OnQueryTextListener{

	protected ScrollView mScrollView;
	public static final String ARG_PAGE_NUMBER = "page_number";
	private ServerCalls sc = new ServerCalls();
	ArrayList<Card> cards = new ArrayList<Card>();
	CardListView listView;
	CardArrayAdapter mCardArrayAdapter;
	int count = 5;
	String season;
	
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
	public void onCreateOptionsMenu(
	      Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.episodes, menu);
		MenuItem item = menu.findItem(R.id.action_search);
		
        SearchView sv = (SearchView) item.getActionView();
        
        sv.setOnQueryTextListener(this);
        item.setActionView(sv);
        //System.out.println("got here");
        super.onCreateOptionsMenu(menu, inflater);
	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = this.getArguments();
        season = bundle.getString("season", "Unknown");
        getActivity().setTitle("Season "+season);
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

		 protected EpisodeFragment ef;
		 
		 protected initCards(EpisodeFragment ef){
			 this.ef = ef;
		 }

		 @Override
			protected void onPostExecute(ArrayList<String[]> result) {
				// TODO Auto-generated method stub
			 ArrayList<String[]> itemsList = result;
			 
			 for (int i=0;i<itemsList.size();i++){
		            EpisodeCard card = new EpisodeCard(getActivity(),itemsList.get(i)[0],itemsList.get(i)[1],itemsList.get(i)[2],itemsList.get(i)[3],itemsList.get(i)[4], itemsList.get(i)[5], getActivity());
		            cards.add(card);
		        }
			 
		        mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

		        listView = (CardListView) getActivity().findViewById(R.id.announce_list_cards);
		        if (listView!=null){
		            listView.setAdapter(mCardArrayAdapter);
		        }
				
		        ef.getActivity().runOnUiThread(new Runnable() {
	                public void run() {
	                    // some code #3 (that needs to be ran in UI thread)
	               	 //mCardArrayAdapter.addAll(cards);
	               	
	               	 //listView.invalidateViews();
	               	ef.getActivity().setProgressBarIndeterminateVisibility(false);
	                }
	            });
			}

	        @Override
	        protected void onProgressUpdate(Void... values) {}

			@Override
			protected ArrayList<String[]> doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				ef.getActivity().runOnUiThread(new Runnable() {
	                public void run() {
	                    // some code #3 (that needs to be ran in UI thread)
	               	 //mCardArrayAdapter.addAll(cards);
	               	
	               	 //listView.invalidateViews();
	               	ef.getActivity().setProgressBarIndeterminateVisibility(true);
	                }
	            });
				
				ArrayList<String[]> itemsList = sc.getEpisodesBySeason(season);
				
				return itemsList;
			}
			
	    }
	 
	 @Override
		public boolean onQueryTextChange(String arg0) {
			// TODO Auto-generated method stub
			System.out.println(arg0);
			final ArrayList<Card> cardSearch = new ArrayList<Card>();
			for(int i = 0; i < cards.size(); i++){
				EpisodeCard ec = (EpisodeCard) cards.get(i);
				String name = ec.episode_desc + " " + ec.episode_nbr + " " + ec.episode_name;
				if(name.toLowerCase().contains(arg0.toLowerCase())){
					cardSearch.add(cards.get(i));
				}
			}
			CardArrayAdapter searchAdapter = new CardArrayAdapter(this.getActivity(),cardSearch);
			listView.setAdapter(searchAdapter);
			listView.invalidateViews();
			return true;
		}

		@Override
		public boolean onQueryTextSubmit(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}
	 
}