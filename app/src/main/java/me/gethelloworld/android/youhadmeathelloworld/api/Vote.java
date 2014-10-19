package me.gethelloworld.android.youhadmeathelloworld.api;

/**
 * Created by tylorgarrett on 10/18/14.
 */
public class Vote {
    private String hackathon;
    private String voted;
    private boolean yes;

    public Vote(String hackathon, String voted, boolean yes) {
        this.hackathon = hackathon;
        this.voted = voted;
        this.yes = yes;
    }
}
