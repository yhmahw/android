package me.gethelloworld.android.youhadmeathelloworld.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by david on 10/18/14.
 */
public interface GitHubAPI {

    @GET("/user")
    void getUser(Callback<GitHubUser> callback);

    @GET("/user/{username}")
    void getUser(@Path("username") String username, Callback<GitHubUser> callback);
}
