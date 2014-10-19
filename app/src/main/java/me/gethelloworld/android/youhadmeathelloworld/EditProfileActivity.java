package me.gethelloworld.android.youhadmeathelloworld;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.gethelloworld.android.youhadmeathelloworld.api.APIManager;
import me.gethelloworld.android.youhadmeathelloworld.api.GitHubUser;
import me.gethelloworld.android.youhadmeathelloworld.api.Hackathon;
import me.gethelloworld.android.youhadmeathelloworld.api.UserData;
import me.gethelloworld.android.youhadmeathelloworld.auth.AuthenticationManager;
import me.gethelloworld.android.youhadmeathelloworld.data.HackathonDataManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class EditProfileActivity extends Activity implements Callback<UserData> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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

    public void onDone(View view) {

        String advert = ((EditText) findViewById(R.id.edit_hackathonAdvert)).getText().toString();
        String[] languages = ((EditText) findViewById(R.id.edit_languages)).getText().toString().split(",");
        String[] platforms = ((EditText) findViewById(R.id.edit_platforms)).getText().toString().split(",");

        Map<String, Object> userUpdate = new HashMap<String, Object>();

        userUpdate.put("languages", languages);
        userUpdate.put("platforms", platforms);
        userUpdate.put("advert", advert);


        APIManager.getAPI(this).updateUser(AuthenticationManager.getUsername(this), userUpdate, new Callback<Void>() {
            @Override
            public void success(Void aVoid, Response response) {
                Log.d("User", "Updated successfully");

                onBackPressed();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("User", "Update failed" + error.getLocalizedMessage());

            }
        });

    }

    @Override
    public void success(UserData userData, Response response) {
        ((EditText)findViewById(R.id.edit_platforms)).setText( asCommaList(userData.getPlatforms()) );
        ((EditText)findViewById(R.id.edit_languages)).setText( asCommaList(userData.getLanguages()) );
        ((EditText)findViewById(R.id.edit_hackathonAdvert)).setText( userData.getAdvert() );

        findViewById(R.id.edits).setVisibility(View.VISIBLE);
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
    public void failure(RetrofitError error) {
        Toast.makeText(this, "There was an error loading this profile", Toast.LENGTH_SHORT).show();

    }
}
