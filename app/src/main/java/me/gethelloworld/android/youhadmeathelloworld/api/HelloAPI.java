package me.gethelloworld.android.youhadmeathelloworld.api;


import java.util.List;

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


    //TODO: Figure out if I even need to use this.
    @PUT("/hackathon/{name}/join/{userId}")
    void joinHackathon(@Path("name") String name, @Path("userId") String userId);

    @PUT("/user/{userId}/vote")
    void sendVote(@Path("userId") String userId, @Body Vote vote, Callback<String> callback);

    @GET("/user/{name}/hackathon/{hackname}/matches")
    void getMatchesForUser(@Path("name") String username, @Path("hackname") String hackathonName, @Query("accessToken") String accessToken, Callback<MatchesCollections> matchesCollectionsCallback);

    @PUT("/hackathon/{name}/join/{userId}")
    void joinHackathon(@Path("name") String hackName, @Path("userId") String userId, Callback<Void> stringCallback);

    @POST("/gimbal/{gimbalId}/enter")
    void gimbalEnter(@Path("gimbalId") String gimbalId, @Body GimbalData gimbalData, Callback<Void> callback);

    @POST("/gimbal/{gimbalId}/exit")
    void gimbalExit(@Path("gimbalId") String gimbalId, @Body GimbalData gimbalData, Callback<Void> callback);

    @GET("/gimbal/{gimbalId}")
    void gimbal(@Path("gimbalId") String gimbalId, Callback<UserIdList> callback);
}
