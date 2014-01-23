package com.drexel.cs451.dr.who;

import it.gmariotti.cardslib.demo.fragment.BaseFragment;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;

import com.drexel.cs451.dr.who.load.AnnounceTask;
import com.drexel.cs451.dr.who.load.BioTask;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;

public class BioChooserFragment extends BaseFragment {

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
        View rootView = inflater.inflate(R.layout.fragment_bio_chooser, container, false);
        int i = getArguments().getInt(ARG_PAGE_NUMBER);
        String page = getResources().getStringArray(R.array.pages_array)[i];

        getActivity().setTitle(page);
        
        WindowManager wm = (WindowManager) getActivity().getBaseContext().getSystemService(Context.WINDOW_SERVICE); 
        Display screen = wm.getDefaultDisplay();  
        
        ChooserBioCard charCard = new ChooserBioCard(this.getActivity(), "Characters", "http://ichef.bbci.co.uk/images/ic/528xn/p01m3j1s.jpg", "http://www.bbc.co.uk/programmes/b006q2x0/features/characters", this.getActivity(), getFragmentManager());
        CardView cardView1 = (CardView) rootView.findViewById(R.id.characters_choice_card);
        cardView1.getLayoutParams().height = (int) (screen.getHeight()/2.5);
        cardView1.setCard(charCard);
        
        ChooserBioCard monsCard = new ChooserBioCard(this.getActivity(), "Monsters", "http://ichef.bbci.co.uk/images/ic/528xn/p01m3hpy.jpg", "http://www.bbc.co.uk/programmes/b006q2x0/features/monsters", this.getActivity(), getFragmentManager());
        CardView cardView2 = (CardView) rootView.findViewById(R.id.monsters_choice_card);
        cardView2.getLayoutParams().height = (int) (screen.getHeight()/2.5);
        cardView2.setCard(monsCard);
        
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCards();
    }
	
	
	
	 
	 private void initCards() {

		 
	    }
	 
	 
}

