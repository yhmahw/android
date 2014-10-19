package me.gethelloworld.android.youhadmeathelloworld.api;


import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by david on 10/18/14.
 */
public interface HelloAPI {

    @POST("/login")
    void login(@Body LoginData data, Callback<UserData> userAuthDataCallback);

    @GET("/hackathon")
    void getHackathons(Callback<List<Hackathon>> hackathonListCallback);


    @PUT("/user/{userid}")
    void updateUser(@Path("userid") String userId, @Body Map<String, Object> fields, Callback<Void> callback);

    @PUT("/user/{userId}/vote")
    void sendVote(@Path("userId") String userId, @Body Vote vote, Callback<String> callback);

    @GET("/user/{name}/hackathon/{hackname}/matches")
    void getMatchesForUser(@Path("name") String username, @Path("hackname") String hackathonName, @Query("accessToken") String accessToken, Callback<MatchesCollections> matchesCollectionsCallback);

    @PUT("/hackathon/{name}/join/{userId}")
    void joinHackathon(@Path("name") String hackName, @Path("userId") String userId, Callback<Void> stringCallback);
}
