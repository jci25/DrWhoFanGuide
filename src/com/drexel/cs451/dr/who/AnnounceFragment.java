package com.drexel.cs451.dr.who;

import java.util.ArrayList;
import java.util.Locale;

import com.drexel.cs451.dr.who.load.ServerCalls;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import it.gmariotti.cardslib.demo.fragment.BaseFragment;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;

public class AnnounceFragment extends BaseFragment {

    protected ScrollView mScrollView;
	public static final String ARG_PAGE_NUMBER = "page_number";
	private ServerCalls sc = new ServerCalls();
	ArrayList<Card> cards = new ArrayList<Card>();
	
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

        initCards();
    }
	
	
	
	 
	 private void initCards() {

		 //AnnounceTask mAnnounceTask = new AnnounceTask(this.getActivity());
		 //mAnnounceTask.execute("http://www.bbc.co.uk/blogs/doctorwho/");
		 ArrayList<String[]> itemsList = sc.getAnnouncements(1, 10);
		 
		 for (int i=0;i<itemsList.size();i++){
	            AnnounceCard card = new AnnounceCard(this.getActivity(),itemsList.get(i)[0],itemsList.get(i)[1],itemsList.get(i)[2],itemsList.get(i)[3]);
	            cards.add(card);
	        }
	        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this.getActivity(),cards);

	        CardListView listView = (CardListView) getActivity().findViewById(R.id.announce_list_cards);
	        if (listView!=null){
	            listView.setAdapter(mCardArrayAdapter);
	        }
	    }
}


