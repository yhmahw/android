package me.gethelloworld.android.youhadmeathelloworld.data;

import me.gethelloworld.android.youhadmeathelloworld.api.Hackathon;

/**
 * Created by david on 10/18/14.
 */
public class HackathonDataManager {
    private static Hackathon currentHackathon;

    public static void setCurrentHackathon(Hackathon currentHackathon) {
        HackathonDataManager.currentHackathon = currentHackathon;
    }

    public static Hackathon getCurrentHackathon() {
        return currentHackathon;
    }
}
