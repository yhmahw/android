package me.gethelloworld.android.youhadmeathelloworld.api;


import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by david on 10/18/14.
 */
public interface HelloAPI {

    @POST("/login")
    void login(@Body LoginData data, Callback<UserData> userAuthDataCallback);

    @GET("/hackathon")
    void getHackathons(Callback<List<Hackathon>> hackathonListCallback);


    //TODO: Figure out if I even need to use this.
    @PUT("/hackathon/{name}/join/{userId}")
    void joinHackathon(@Path("name") String name, @Path("userId") String userId);
}