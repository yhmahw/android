package me.gethelloworld.android.youhadmeathelloworld.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andtinder.view.CardContainer;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;
import me.gethelloworld.android.youhadmeathelloworld.R;
import me.gethelloworld.android.youhadmeathelloworld.RootFragmentInteractionListener;

public class SwipeFragment extends Fragment {

    private RootFragmentInteractionListener  mListener;

    public SwipeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_swipe, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createCards();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (RootFragmentInteractionListener ) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    public void createCards(){
        Card card = new Card(getActivity());
        CardHeader header = new CardHeader(getActivity());
        card.addCardHeader(header);
        CardView cardView = (CardView) getActivity().findViewById(R.id.card);
        cardView.setCard(card);
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }



}
