package me.gethelloworld.android.youhadmeathelloworld;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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


public class LoginActivity extends Activity implements OAuthCallback {

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

        JSONObject data = new JSONObject();
        try {
            data.put("state", UUID.randomUUID().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        oauth.popup("github", data, this);
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
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                HttpClient client = new DefaultHttpClient();
                Uri.Builder builder  = new Uri.Builder();
                builder.scheme("http").authority("api-yhmahw.herokuapp.com").appendPath("login");
                HttpPost postRequest = new HttpPost(builder.toString());
                JSONObject jObject = new JSONObject();
                StringEntity se = null;
                try {
                    Log.d("OAUTH", "Status: " + oAuthData.status + "|" + oAuthData.error + " Token from oauthio: " + oAuthData.token);
                    jObject.put("access_token", oAuthData.token);
                    se = new StringEntity(jObject.toString());
                }catch(Exception e){
                    e.printStackTrace();
                }
                postRequest.setHeader("Content-type", "application/json");
                postRequest.setEntity(se);
                HttpResponse response = null;
                String jsonResp = null;
                try{
                    response = client.execute(postRequest);

                    Log.d("OAUTH", "" + response.getStatusLine().getStatusCode() );

                    jsonResp = EntityUtils.toString(response.getEntity());

                    Log.d("OAUTH", "" + jsonResp);

                }catch(IOException e){
                    e.printStackTrace();

                }
                return null;
            }
        }.execute();
    }
}
