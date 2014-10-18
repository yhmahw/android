package me.gethelloworld.android.youhadmeathelloworld;

import android.app.ListActivity;
import android.os.Bundle;

import java.util.List;

import me.gethelloworld.android.youhadmeathelloworld.adapters.HackathonsListAdapter;
import me.gethelloworld.android.youhadmeathelloworld.api.APIManager;
import me.gethelloworld.android.youhadmeathelloworld.api.Hackathon;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class HackathonsActivity extends ListActivity implements Callback<List<Hackathon>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setEmpty();

        APIManager.getAPI(this).getHackathons(this);


    }

    private void setEmpty() {
        //TODO: Set to a loading page.
    }

    @Override
    public void success(List<Hackathon> hackathons, Response response) {
        setListAdapter(new HackathonsListAdapter(hackathons));
    }

    @Override
    public void failure(RetrofitError error) {
        setError();
    }

    private void setError() {

    }
}
