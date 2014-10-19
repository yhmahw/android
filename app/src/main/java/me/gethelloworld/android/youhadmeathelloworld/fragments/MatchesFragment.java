package me.gethelloworld.android.youhadmeathelloworld.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import me.gethelloworld.android.youhadmeathelloworld.ChatActivity;
import me.gethelloworld.android.youhadmeathelloworld.R;
import me.gethelloworld.android.youhadmeathelloworld.RootFragmentInteractionListener;
import me.gethelloworld.android.youhadmeathelloworld.controller.MatchesManager;
import me.gethelloworld.android.youhadmeathelloworld.listeners.OnPageToFragmentListener;

public class MatchesFragment extends ListFragment implements OnPageToFragmentListener {

    private static final String[] TESTDATA = new String[]{"Susan", "Jordan", "Michael", "Gregory"};


    private RootFragmentInteractionListener mListener;

    public MatchesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (RootFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPageToFragment() {
        Log.d("Page", "Matches selected");

        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, MatchesManager.getMatchesCollection().getMatches()));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(getActivity(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putString("match_user", TESTDATA[position]);

        intent.putExtras(b);

        startActivity(intent);

    }
}
