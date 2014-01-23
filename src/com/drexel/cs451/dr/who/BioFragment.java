package com.drexel.cs451.dr.who;

import it.gmariotti.cardslib.demo.fragment.BaseFragment;

import com.drexel.cs451.dr.who.load.AnnounceTask;
import com.drexel.cs451.dr.who.load.BioTask;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class BioFragment extends BaseFragment {

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
        View rootView = inflater.inflate(R.layout.fragment_bio, container, false);
        int i = getArguments().getInt(ARG_PAGE_NUMBER);
        //String page = getResources().getStringArray(R.array.pages_array)[i];

        //getActivity().setTitle(page);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        String myUrl = bundle.getString("URL", "http://www.bbc.co.uk/programmes/b006q2x0/features/characters");
        initCards(myUrl);
    }
	
	
	
	 
	 private void initCards(String url) {

		 BioTask mBioTask = new BioTask(this.getActivity(), getFragmentManager());
			mBioTask.execute(url);
			
		 
	    }
	 
	 
}

