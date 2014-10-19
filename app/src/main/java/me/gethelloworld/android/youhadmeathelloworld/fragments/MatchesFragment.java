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
import me.gethelloworld.android.youhadmeathelloworld.MainActivity;
import me.gethelloworld.android.youhadmeathelloworld.R;
import me.gethelloworld.android.youhadmeathelloworld.RootFragmentInteractionListener;
import me.gethelloworld.android.youhadmeathelloworld.api.APIManager;
import me.gethelloworld.android.youhadmeathelloworld.api.MatchesCollections;
import me.gethelloworld.android.youhadmeathelloworld.auth.AuthenticationManager;
import me.gethelloworld.android.youhadmeathelloworld.controller.MatchesManager;
import me.gethelloworld.android.youhadmeathelloworld.data.HackathonDataManager;
import me.gethelloworld.android.youhadmeathelloworld.listeners.OnPageToFragmentListener;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MatchesFragment extends ListFragment implements OnPageToFragmentListener, View.OnClickListener, Callback<MatchesCollections> {

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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.refresh).setOnClickListener(this);
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
        b.putString("match_user", MatchesManager.getMatchesCollection().getMatches().get(position));

        intent.putExtras(b);

        startActivity(intent);

    }

    @Override
    public void onClick(View v) {

        APIManager.getAPI(getActivity()).getMatchesForUser(AuthenticationManager.getUsername(getActivity()),
                HackathonDataManager.getCurrentHackathonId(getActivity()),
                AuthenticationManager.getAuthToken(getActivity()),
                this);

    }

    @Override
    public void success(MatchesCollections matchesCollections, Response response) {
        Log.d("Prep", "Got object : " + matchesCollections.getPotential().size());
        MatchesManager.setMatchesCollection(matchesCollections);
        onPageToFragment();
    }

    @Override
    public void failure(RetrofitError error) {
        Log.d("Matches", "Failure to get matches" + error.getResponse().getStatus() + " " + error.getLocalizedMessage());


    }
}
