package me.gethelloworld.android.youhadmeathelloworld.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.CardExpand;
import me.gethelloworld.android.youhadmeathelloworld.R;

/**
 * Created by tylorgarrett on 10/18/14.
 */
public class CustomExpandCard extends CardExpand {

    Context mContext;

    public CustomExpandCard(Context context) {
        super(context, R.layout.card_expand_layout);
        mContext = context;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        if (view == null) return;

        TextView textView = (TextView) view.findViewById(R.id.tech);
        textView.setText("Technology I Know");

        TextView textView1 = (TextView) view.findViewById(R.id.hackathons);
        textView1.setText("Previous Hackathons");




    }


}
