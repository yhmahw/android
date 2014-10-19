package me.gethelloworld.android.youhadmeathelloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import me.gethelloworld.android.youhadmeathelloworld.api.APIManager;
import me.gethelloworld.android.youhadmeathelloworld.api.MatchesCollections;
import me.gethelloworld.android.youhadmeathelloworld.auth.AuthenticationManager;
import me.gethelloworld.android.youhadmeathelloworld.controller.MatchesManager;
import me.gethelloworld.android.youhadmeathelloworld.data.HackathonDataManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class PreparingMatchesActivity extends Activity implements Callback<MatchesCollections> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparing_matches);

        APIManager.getAPI(this).getMatchesForUser(AuthenticationManager.getUsername(this),
                HackathonDataManager.getCurrentHackathonId(this),
                AuthenticationManager.getAuthToken(this),
                this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.preparing_matches, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void success(MatchesCollections matchesCollections, Response response) {
        Log.d("Prep", "Got object : " + matchesCollections.getPotential().size());
        MatchesManager.setMatchesCollection(matchesCollections);

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void failure(RetrofitError error) {
        Log.d("Matches", "Failure to get matches" + error.getResponse().getStatus() + " " + error.getLocalizedMessage());

        MatchesCollections mc = new MatchesCollections();

        List<String> a = new ArrayList<String>();
        List<String> b = new ArrayList<String>();
        List<String> c = new ArrayList<String>();


        a.add("vidia");
        a.add("fieldn");
        a.add("garr741");
        a.add("mhoc");

        b.add("vidia");
        b.add("fieldn");
        b.add("garr741");
        b.add("mhoc");

        c.add("vidia");
        c.add("fieldn");
        c.add("garr741");
        c.add("mhoc");

        mc.setInterests(a);
        mc.setPotential(b);
        mc.setRejects(c);

        MatchesManager.setMatchesCollection(mc);

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
