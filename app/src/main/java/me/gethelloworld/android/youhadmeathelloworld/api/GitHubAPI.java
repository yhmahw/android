package me.gethelloworld.android.youhadmeathelloworld.api;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by david on 10/18/14.
 */
public interface GitHubAPI {

    @GET("/user")
    void getUser(Callback<GitHubUser> callback);
}
