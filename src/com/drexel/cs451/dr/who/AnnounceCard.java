package com.drexel.cs451.dr.who;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

public class AnnounceCard extends Card{

    protected String mTitleHeader;
    protected String mTitleMain;

    public AnnounceCard(Context context,String titleHeader,String titleMain) {
        super(context, R.layout.card_announce);
        this.mTitleHeader=titleHeader;
        this.mTitleMain=titleMain;
        init();
    }

    private void init(){

        //Create a CardHeader
        CardHeader header = new CardHeader(this.getContext());

        //Set the header title
        header.setTitle(mTitleHeader);
        addCardHeader(header);

        //Add ClickListener
        setOnClickListener(new OnCardClickListener() {

			@Override
			public void onClick(Card card, View view) {
				Toast.makeText(getContext(), "Click Listener card=" + mTitleHeader, Toast.LENGTH_SHORT).show();
			}
        });

        //Set the card inner text
        setTitle(mTitleMain);
    }

}