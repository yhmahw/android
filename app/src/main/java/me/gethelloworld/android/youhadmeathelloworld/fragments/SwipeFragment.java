package me.gethelloworld.android.youhadmeathelloworld.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.andtinder.view.CardContainer;
import com.loopj.android.image.SmartImageView;
import android.widget.IconButton;


import org.apache.http.auth.AUTH;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.view.CardView;
import me.gethelloworld.android.youhadmeathelloworld.MainActivity;
import me.gethelloworld.android.youhadmeathelloworld.R;
import me.gethelloworld.android.youhadmeathelloworld.RootFragmentInteractionListener;
import me.gethelloworld.android.youhadmeathelloworld.api.APIManager;
import me.gethelloworld.android.youhadmeathelloworld.api.GitHubUser;
import me.gethelloworld.android.youhadmeathelloworld.api.Hackathon;
import me.gethelloworld.android.youhadmeathelloworld.api.Vote;
import me.gethelloworld.android.youhadmeathelloworld.auth.AuthenticationManager;
import me.gethelloworld.android.youhadmeathelloworld.data.HackathonDataManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SwipeFragment extends Fragment implements Callback<GitHubUser> {

    private RootFragmentInteractionListener  mListener;
    public CardView cardView;
    IconButton accept;
    IconButton reject;

    public SwipeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_swipe, container, false);
        accept = (IconButton) v.findViewById(R.id.button_accept);
        reject = (IconButton) v.findViewById(R.id.button_reject);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hackathon = HackathonDataManager.getCurrentHackathonId(getActivity());
                String userId = AuthenticationManager.getUsername(getActivity());
                Vote vote = new Vote(hackathon, userId, true);
                APIManager.getAPI(getActivity()).sendVote(userId, vote, new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        Log.d("debug", "Success");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("debug", "Failure");
                    }
                });
                getNextUser();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hackathon = HackathonDataManager.getCurrentHackathonId(getActivity());
                String userId = AuthenticationManager.getUsername(getActivity());
                Vote vote = new Vote(hackathon, userId, false);
                APIManager.getAPI(getActivity()).sendVote(userId, vote, new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        Log.d("debug", "Success");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("debug", "Failure");
                    }
                });
                getNextUser();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        APIManager.getGitHubApi(getActivity()).getUser(this);

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

    @Override
    public void success(GitHubUser gitHubUser, Response response) {

        Card card = createCardForUser(gitHubUser); //Change this to enter the user ID of a match
        cardView.setCard(card);

    }

//    public void setUpCardView(){
//        GridView gridview = (GridView) getActivity().findViewById(R.id.gridview);
//        gridview.setAdapter(new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return 7;
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return position;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                TextView tv = (TextView) getActivity().findViewById(R.id.tech_view);
//                return tv;
//            }
//        });
//    }

    private Card createCardForUser(GitHubUser user) {
        cardView = (CardView) getActivity().findViewById(R.id.user_card);
        Card card = new Card(getActivity());
        card.setSwipeable(true);
        ((SmartImageView)cardView.findViewById(R.id.card_thumbnail_image)).setImageUrl(user.getAvatar_url());
        ((TextView)cardView.findViewById(R.id.card_main_inner_simple_title)).setText(user.getName());
        card.setOnExpandAnimatorEndListener(new Card.OnExpandAnimatorEndListener() {
            @Override
            public void onExpandEnd(Card card) {
                Log.d("debug", "the card has expanded");
            }
        });
        return card;
    }

    @Override
    public void failure(RetrofitError error) {
        //Error
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    public void getNextUser(){
        //select a new user
        //currently dummy data, how do i get the next user?
        GitHubUser newUser = new GitHubUser();
        newUser.setLogin("mhoc");
        newUser.setName("Steve Jobs");
        newUser.setAvatar_url("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQTsgr8IQAvo0uSwwP1tdL2XIAIFxR0rAI9Y0qJip5mBzHvRpxu");
        //populate the card data
        Card newCard = createCardForUser(newUser);
        //display the card
        cardView.replaceCard(newCard);
    }


}
