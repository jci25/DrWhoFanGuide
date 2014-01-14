package com.drexel.cs451.dr.who;

import java.util.Locale;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AnnounceFragment extends Fragment {
	public static final String ARG_PAGE_NUMBER = "page_number";
	
	public AnnounceFragment() {
        // Empty constructor required for fragment subclasses
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
}
