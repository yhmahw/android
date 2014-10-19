package me.gethelloworld.android.youhadmeathelloworld.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.IconButton;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.util.List;
import java.util.Random;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;
import me.gethelloworld.android.youhadmeathelloworld.R;
import me.gethelloworld.android.youhadmeathelloworld.RootFragmentInteractionListener;
import me.gethelloworld.android.youhadmeathelloworld.api.APIManager;
import me.gethelloworld.android.youhadmeathelloworld.api.GitHubUser;
import me.gethelloworld.android.youhadmeathelloworld.api.UserData;
import me.gethelloworld.android.youhadmeathelloworld.api.Vote;
import me.gethelloworld.android.youhadmeathelloworld.auth.AuthenticationManager;
import me.gethelloworld.android.youhadmeathelloworld.controller.MatchesManager;
import me.gethelloworld.android.youhadmeathelloworld.data.HackathonDataManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SwipeFragment extends Fragment {

    private RootFragmentInteractionListener mListener;
    public CardView cardView;
    IconButton accept;
    IconButton reject;
    private String userId;

    public SwipeFragment() {
    }

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
                //cardView.setCard(null);
                String hackathon = HackathonDataManager.getCurrentHackathonId(getActivity());
                String userId = AuthenticationManager.getUsername(getActivity());
                Vote vote = new Vote(hackathon, SwipeFragment.this.userId, true);
                APIManager.getAPI(getActivity()).sendVote(userId, vote, new Callback<Void>() {
                    @Override
                    public void success(Void s, Response response) {
                        Log.d("vote", "Success");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("vote", "Failure" + error.getLocalizedMessage());
                    }
                });
                getNextUser();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cardView.setCard(null);
                String hackathon = HackathonDataManager.getCurrentHackathonId(getActivity());
                String userId = AuthenticationManager.getUsername(getActivity());
                Vote vote = new Vote(hackathon, SwipeFragment.this.userId, false);
                APIManager.getAPI(getActivity()).sendVote(userId, vote, new Callback<Void>() {
                    @Override
                    public void success(Void s, Response response) {
                        Log.d("vote", "Success");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("vote", "Failure" + error.getLocalizedMessage());
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

        getNextUser();

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
            mListener = (RootFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void displayCard(UserData userData, GitHubUser gitHubUser) {

        Card card = createCardForUser(gitHubUser, userData); //Change this to enter the user ID of a match

        if(cardView.getCard() == null)
            cardView.setCard(card);
        else
            cardView.replaceCard(card);
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

    private Card createCardForUser(GitHubUser user, UserData userData) {

        Log.d("createCard", user.getLogin() + " " + userData.getUserId());

        cardView = (CardView) getActivity().findViewById(R.id.user_card);
        Card card = new Card(getActivity());

        card.setSwipeable(false);

        ((SmartImageView) cardView.findViewById(R.id.card_thumbnail_image)).setImageUrl(user.getAvatar_url());
        ((TextView) cardView.findViewById(R.id.card_main_inner_simple_title)).setText(user.getName());
        ((TextView) cardView.findViewById(R.id.card_profile_description)).setText(userData.getAdvert());



        ((TextView)cardView.findViewById(R.id.show_platforms)).setText( asCommaList(userData.getPlatforms()) );
        ((TextView)cardView.findViewById(R.id.show_languages)).setText( asCommaList(userData.getLanguages()) );



        card.setOnExpandAnimatorEndListener(new Card.OnExpandAnimatorEndListener() {
            @Override
            public void onExpandEnd(Card card) {
                Log.d("debug", "the card has expanded");
            }
        });
        return card;
    }

    private String asCommaList(List<String> strings) {
        if(strings == null) return "";

        String foo = strings.get(0);
        for(int i = 1; i < strings.size(); i++) {
            foo += ", " + strings.get(i);
        }
        return foo;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void getNextUser() {
        Random rand = new Random();

        if(MatchesManager.getMatchesCollection().getPotential() == null || MatchesManager.getMatchesCollection().getPotential().size() <= 0) {
            getView().findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.cards_ui).setVisibility(View.INVISIBLE);
        } else {
            userId = MatchesManager.getMatchesCollection().getPotential().get(rand.nextInt(MatchesManager.getMatchesCollection().getPotential().size()));
            Log.d("NextUser", "Got the next user : " + userId);
            APIManager.getGitHubApi(getActivity()).getUser(userId, new Callback<GitHubUser>() {
                @Override
                public void success(final GitHubUser gitHubUser, Response response) {
                    Log.d("GitHub", "Revieved next from GH");
                    APIManager.getAPI(getActivity()).getUserData(gitHubUser.getLogin(), new Callback<UserData>() {
                        @Override
                        public void success(UserData userData, Response response) {
                            Log.e("GitHub", "got next");

                            displayCard(userData, gitHubUser);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.e("API", "Error getting next");

                        }
                    });
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("GitHub", "Error getting next" + error.getLocalizedMessage());
                }
            });
        }

    }


}
