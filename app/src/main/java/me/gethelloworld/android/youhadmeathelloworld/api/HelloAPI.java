package me.gethelloworld.android.youhadmeathelloworld.api;


import java.util.List;
import java.util.Map;

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

    @GET("/user/{userId}")
    void getUserData(@Path("userId") String username, Callback<UserData> UserDataCallback);

    //TODO: Figure out if I even need to use this.
    @PUT("/hackathon/{name}/join/{userId}")
    void joinHackathon(@Path("name") String name, @Path("userId") String userId);

    @PUT("/user/{userid}")
    void updateUser(@Path("userid") String userId, @Body Map<String, Object> fields, Callback<Void> callback);

    @PUT("/user/{userId}/vote")
    void sendVote(@Path("userId") String userId, @Body Vote vote, Callback<Void> callback);

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

    @POST("/tweet")
    void sendTweet(@Body Tweet tweet, Callback<Void> callback);

    @POST("/image")
    void sendImage(@Body ImageData image, Callback<Void> callback);
}
