package me.gethelloworld.android.youhadmeathelloworld.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andtinder.view.CardContainer;
import com.loopj.android.image.SmartImageView;
import android.widget.IconButton;


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
import me.gethelloworld.android.youhadmeathelloworld.views.CustomExpandCard;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SwipeFragment extends Fragment implements Callback<GitHubUser> {

    private RootFragmentInteractionListener  mListener;
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

        createCardForUser(gitHubUser); //Change this to enter the user ID of a match

    }

    private void createCardForUser(GitHubUser user) {

        CardHeader cardHeader = new CardHeader(getActivity());

        CardView cardView = (CardView) getActivity().findViewById(R.id.tylor_card);

        Card card = new Card(getActivity());

        CardExpand cardExpand = new CustomExpandCard(getActivity());

        card.setSwipeable(true);

        ((SmartImageView)cardView.findViewById(R.id.card_thumbnail_image)).setImageUrl(user.getAvatar_url());
        ((TextView)cardView.findViewById(R.id.card_main_inner_simple_title)).setText(user.getName() + " (" + user.getLogin() + ")");

        cardHeader.setButtonExpandVisible(true);
        card.addCardHeader(cardHeader);
        cardExpand.setTitle("Tylor Garrett's Info");
        card.addCardExpand(cardExpand);
        card.setOnExpandAnimatorEndListener(new Card.OnExpandAnimatorEndListener() {
            @Override
            public void onExpandEnd(Card card) {
                Log.d("tylor", "the card has expanded");
            }
        });
        cardView.setCard(card);
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

    public Card createCard(){
        CardHeader cardHeader = new CardHeader(getActivity());
        Card card = new Card(getActivity());
        CustomExpandCard customExpandCard = new CustomExpandCard(getActivity());
        card.setSwipeable(true);
        cardHeader.setButtonExpandVisible(true);
        card.addCardHeader(cardHeader);
        return card;
    }


}
