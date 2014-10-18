package me.gethelloworld.android.youhadmeathelloworld;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.channels.AsynchronousCloseException;
import java.util.UUID;

import io.oauth.OAuth;
import io.oauth.OAuthCallback;
import io.oauth.OAuthData;
import me.gethelloworld.android.youhadmeathelloworld.api.APIManager;
import me.gethelloworld.android.youhadmeathelloworld.api.LoginData;
import me.gethelloworld.android.youhadmeathelloworld.api.UserData;
import me.gethelloworld.android.youhadmeathelloworld.auth.AuthenticationManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends Activity implements OAuthCallback, Callback<UserData> {

    OAuth oauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        oauth = new OAuth(this);
        oauth.initialize(getString(R.string.oauthio_pub));
    }

    @Override
    protected void onStart() {
        super.onStart();

        String token = AuthenticationManager.getAuthToken(this);
        Log.d("OAUTH", "Stored token: " + token);
        if( token != null ) {
            openApp();
        }
    }

    private void openApp() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
    public void onFinished(final OAuthData oAuthData) {
        LoginData loginData = new LoginData(oAuthData.token);
        Log.d("OAUTH", "Token Recieved from oauth.io: " + oAuthData.token);
        AuthenticationManager.setAuthToken(this, oAuthData.token);

        APIManager.getAPI(this).login(loginData, this);
    }

    @Override
    public void success(UserData userData, Response response) {
        openApp();
    }

    @Override
    public void failure(RetrofitError error) {
        //TODO: Show error dialog.
        Toast.makeText(this, "Login Failed. Please try again.", Toast.LENGTH_SHORT).show();
        Log.e("API", "Login failed with the API: " + error.getLocalizedMessage());
    }

    public void login(View view) {
        JSONObject data = new JSONObject();
        try {
            data.put("state", "nsgwthqergnhtj64w5htrynsj5645hywhtgst5hjqget6hq45h6j7uk6n75wb6teuejw6hrstdgvjmudtu");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        oauth.popup("github", data, this);
    }
}
