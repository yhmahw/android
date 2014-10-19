package me.gethelloworld.android.youhadmeathelloworld.controller;

import android.util.Log;

import me.gethelloworld.android.youhadmeathelloworld.api.MatchesCollections;

/**
 * Created by david on 10/18/14.
 */
public class MatchesManager {
    private static MatchesCollections MATCHES;

    public static void setMatchesCollection(MatchesCollections collection) {
        Log.d("MatchManager", "Set matches object!");

        Log.d("MatchManager", "" + collection.getInterests() );
        Log.d("MatchManager", "" + collection.getPotential() );
        Log.d("MatchManager", "" + collection.getMatches() );
        Log.d("MatchManager", "" + collection.getRejects() );

        MATCHES = collection;
    }

    public static MatchesCollections getMatchesCollection() {
        Log.d("MatchManager", "Getting matches object");
        return MATCHES;
    }
}
