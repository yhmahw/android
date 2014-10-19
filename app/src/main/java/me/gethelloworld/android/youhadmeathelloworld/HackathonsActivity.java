package me.gethelloworld.android.youhadmeathelloworld;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import me.gethelloworld.android.youhadmeathelloworld.adapters.HackathonsListAdapter;
import me.gethelloworld.android.youhadmeathelloworld.api.APIManager;
import me.gethelloworld.android.youhadmeathelloworld.api.Hackathon;
import me.gethelloworld.android.youhadmeathelloworld.auth.AuthenticationManager;
import me.gethelloworld.android.youhadmeathelloworld.data.HackathonDataManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class HackathonsActivity extends ListActivity implements Callback<List<Hackathon>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        APIManager.getAPI(this).getHackathons(this);

        Log.d("HackathonActivity", "onCreate()");
    }

    @Override
    public void success(List<Hackathon> hackathons, Response response) {
        if (hackathons.size() == 0) {
            Toast.makeText(this, "There are currently no hackathons", Toast.LENGTH_SHORT).show();
            return;
        }
        setListAdapter(new HackathonsListAdapter(this, hackathons));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Hackathon hackathon = (Hackathon) v.getTag();

        HackathonDataManager.setCurrentHackathon(this, hackathon);

        APIManager.getAPI(this).joinHackathon(hackathon.getName(), AuthenticationManager.getUsername(this), new Callback<Void>() {
            @Override
            public void success(Void s, Response response) {
                startActivity(new Intent(HackathonsActivity.this, PreparingMatchesActivity.class));
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Hackathon Join", "Error " + error.getLocalizedMessage());
            }
        });
    }

    @Override
    public void failure(RetrofitError error) {
        setError();
    }

    private void setError() {
        Toast.makeText(this, "There was an error loading hackathons", Toast.LENGTH_SHORT).show();
    }
}
