package me.gethelloworld.android.youhadmeathelloworld.controller;


import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.gethelloworld.android.youhadmeathelloworld.fragments.MatchesFragment;
import me.gethelloworld.android.youhadmeathelloworld.fragments.MomentsFragment;
import me.gethelloworld.android.youhadmeathelloworld.fragments.SwipeFragment;

/**
 * Created by david on 10/18/14.
 */
public class MainFragmentsStore {
    private static List<FragmentEntry> FRAGMENTS = new ArrayList<FragmentEntry>();


    private static List<FragmentEntry> getFRAGMENTS() {
        if(FRAGMENTS.isEmpty()) {
            FRAGMENTS.add(new FragmentEntry("Meet People", new SwipeFragment()));
            FRAGMENTS.add(new FragmentEntry("My Matches", new MatchesFragment()));
            FRAGMENTS.add(new FragmentEntry("Hack Moments", new MomentsFragment()));
        }

        return FRAGMENTS;
    }

    public static Fragment getFragmentByLocation(int location) {
        return getFRAGMENTS().get(location).fragment;
    }

    public static String getFragmentNameFromLocation(int location) {
        return getFRAGMENTS().get(location).name;
    }

    public static int getNumFragments() {
        return getFRAGMENTS().size();
    }

    static class FragmentEntry {
        private Fragment fragment;
        private String name;

        public FragmentEntry(String name, Fragment fragment) {
            this.fragment = fragment;
            this.name = name;
        }
    }
}
