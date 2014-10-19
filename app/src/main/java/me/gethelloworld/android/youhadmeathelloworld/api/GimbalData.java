package me.gethelloworld.android.youhadmeathelloworld.api;

/**
 * Created by tylorgarrett on 10/19/14.
 */
public class GimbalData {
    private String userId;

    public GimbalData(String userId){
        this.userId = userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId(){
        return userId;
    }
}
