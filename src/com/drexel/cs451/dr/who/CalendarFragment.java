package com.drexel.cs451.dr.who;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

public class CalendarFragment extends Fragment {
	public static final String ARG_PAGE_NUMBER = "page_number";
	int oldYear = -1;
    int oldMonth = -1;
	
	public CalendarFragment(){
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        int i = getArguments().getInt(ARG_PAGE_NUMBER);
        String page = getResources().getStringArray(R.array.pages_array)[i];

        getActivity().setTitle(page);
         
        CalendarView calendar = (CalendarView) rootView.findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new OnDateChangeListener(){
        
	        @Override
	        public void onSelectedDayChange(CalendarView view,
	        int year, int month, int dayOfMonth) {
	        	if(oldYear == year && oldMonth == month){}else{
	        	String URL = null;
	        	if(month <9){
	        		URL = "http://www.bbc.co.uk/programmes/b006q2x0/broadcasts/"+year+"/0"+(month+1);
	        	}else{
	        		URL = "http://www.bbc.co.uk/programmes/b006q2x0/broadcasts/"+year+"/"+(month+1);
	        	}
	        	oldYear = year;
	        	oldMonth = month;
	        	Intent intent = new Intent(getActivity().getBaseContext(), WebViewer.class);
				intent.putExtra("URL", URL);
				getActivity().startActivity(intent);
	        }}
	        
        });
        
        
        
        
        return rootView;
    }

}
