package com.drexel.cs451.dr.who.views;

/*
 * ******************************************************************************
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

import com.drexel.cs451.dr.who.R;
import com.drexel.cs451.dr.who.R.id;
import com.drexel.cs451.dr.who.R.layout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.CardExpand;

/**
 * This class provides an example of custom expand/collapse area.
 * It uses carddemo_example_inner_expand layout.
 * <p/>
 * You have to override the {@link #setupInnerViewElements(android.view.ViewGroup, android.view.View)});
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CustomExpandCard extends CardExpand {

    int count;
    String event_desc;
    String start_date;
    String end_date;
    

    public CustomExpandCard(Context context, String event_desc, String start_date, String end_date) {
        super(context, R.layout.carddemo_example_inner_expand);
        this.event_desc = event_desc;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public CustomExpandCard(Context context,int i) {
        super(context, R.layout.carddemo_example_inner_expand);
        count = i;
    }

    //You can set you properties here (example buttons visibility)

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        if (view == null) return;

        //Retrieve TextView elements
        TextView tx1 = (TextView) view.findViewById(R.id.carddemo_expand_text1);
        TextView tx2 = (TextView) view.findViewById(R.id.carddemo_expand_text2);
       

        tx1.setText(start_date + " to " + end_date);
        tx2.setText(event_desc);
    }
}