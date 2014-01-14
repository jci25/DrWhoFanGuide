package com.drexel.cs451.dr.who;

import java.util.ArrayList;
import java.util.Locale;

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

	        ArrayList<Card> cards = new ArrayList<Card>();
	        for (int i=0;i<200;i++){
	            AnnounceCard card = new AnnounceCard(getActivity(),"My title "+i,"Inner text "+i);
	            cards.add(card);
	        }

	        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

	        CardListView listView = (CardListView) getActivity().findViewById(R.id.announce_list_cards);
	        if (listView!=null){
	            listView.setAdapter(mCardArrayAdapter);
	        }
	    }
}
