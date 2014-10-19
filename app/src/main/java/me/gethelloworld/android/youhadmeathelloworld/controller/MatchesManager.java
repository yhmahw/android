package me.gethelloworld.android.youhadmeathelloworld.controller;

import me.gethelloworld.android.youhadmeathelloworld.api.MatchesCollections;

/**
 * Created by david on 10/18/14.
 */
public class MatchesManager {
    private static MatchesCollections MATCHES;

    public static void setMatchesCollection(MatchesCollections collection) {
        MATCHES = collection;
    }

    public static MatchesCollections getMatchesCollection() {
        return MATCHES;
    }
}
