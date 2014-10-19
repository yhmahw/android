package me.gethelloworld.android.youhadmeathelloworld.api;

import android.content.Context;

import me.gethelloworld.android.youhadmeathelloworld.auth.AuthenticationManager;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by david on 10/18/14.
 */
public class APIManager {

    private static HelloAPI INSTANCE;
    private static GitHubAPI GH_INSTANCE;

    public static HelloAPI getAPI(final Context context) {
        if (INSTANCE == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://api-yhmahw.herokuapp.com")
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addHeader("Authentication", AuthenticationManager.getAuthToken(context));
                        }
                    })
                    .build();

            INSTANCE = restAdapter.create(HelloAPI.class);
        }
        return INSTANCE;
    }


    public static GitHubAPI getGitHubApi(final Context context) {
        if (GH_INSTANCE == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://api.github.com")
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addHeader("Authorization", "token " + AuthenticationManager.getAuthToken(context));
                        }
                    })
                    .build();

            GH_INSTANCE = restAdapter.create(GitHubAPI.class);
        }
        return GH_INSTANCE;
    }
}
