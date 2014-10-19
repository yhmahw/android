package me.gethelloworld.android.youhadmeathelloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

import org.w3c.dom.Text;

import java.util.List;

import me.gethelloworld.android.youhadmeathelloworld.adapters.HackathonsListAdapter;
import me.gethelloworld.android.youhadmeathelloworld.api.APIManager;
import me.gethelloworld.android.youhadmeathelloworld.api.GitHubUser;
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

        APIManager.getGitHubApi(this).getUser(new Callback<GitHubUser>() {
            @Override
            public void success(GitHubUser gitHubUser, Response response) {
                //Success of github request.
                ((SmartImageView)findViewById(R.id.profileImage)).setImageUrl(gitHubUser.getAvatar_url());
                ((TextView)findViewById(R.id.navigation_name)).setText(gitHubUser.getName());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Github", "Github req failed | " + error.getLocalizedMessage());
            }
        });

        findViewById(R.id.edits).setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onResume() {
        super.onResume();
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
        if (id == R.id.action_edit) {
            startActivity(new Intent(this, EditProfileActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void success(UserData userData, Response response) {
        ((TextView)findViewById(R.id.show_platforms)).setText( asCommaList(userData.getPlatforms()) );
        ((TextView)findViewById(R.id.show_languages)).setText( asCommaList(userData.getLanguages()) );
        ((TextView)findViewById(R.id.show_advert)).setText( userData.getAdvert() );

        findViewById(R.id.edits).setVisibility(View.VISIBLE);
    }

    private String asCommaList(List<String> strings) {
        String foo = strings.get(0);
        for(int i = 1; i < strings.size(); i++) {
            foo += ", " + strings.get(i);
        }
        return foo;
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(this, "There was an error loading this profile", Toast.LENGTH_SHORT).show();

    }

}
