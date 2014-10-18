package me.gethelloworld.android.youhadmeathelloworld.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import it.gmariotti.cardslib.library.internal.CardExpand;

/**
 * Created by tylorgarrett on 10/18/14.
 */
public class CustomExpandCard extends CardExpand {

    public CustomExpandCard(Context context) {
        super(context);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        if (view == null) return;
    }


}
