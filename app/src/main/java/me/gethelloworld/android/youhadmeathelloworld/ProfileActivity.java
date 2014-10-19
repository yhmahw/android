package me.gethelloworld.android.youhadmeathelloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import me.gethelloworld.android.youhadmeathelloworld.adapters.HackathonsListAdapter;
import me.gethelloworld.android.youhadmeathelloworld.api.APIManager;
import me.gethelloworld.android.youhadmeathelloworld.api.UserData;
import me.gethelloworld.android.youhadmeathelloworld.auth.AuthenticationManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;


public class ProfileActivity extends Activity implements Callback<UserData> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        APIManager.getAPI(this).getUserData(AuthenticationManager.getUsername(this), this);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
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
    public void success(UserData userData, Response response) {

    }

    @Override
    public void failure(RetrofitError error) {
        setError();
    }

    private void setError() {
        Toast.makeText(this, "There was an error loading this profile", Toast.LENGTH_SHORT).show();

    }
}
