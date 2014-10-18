package me.gethelloworld.android.youhadmeathelloworld.data;

import android.content.Context;

import java.security.PrivilegedAction;

import me.gethelloworld.android.youhadmeathelloworld.api.Hackathon;

/**
 * Created by david on 10/18/14.
 */
public class HackathonDataManager {
    private static String currentHackathon;

    public static void setCurrentHackathon(Context c, Hackathon currentHackathon) {
        HackathonDataManager.currentHackathon = currentHackathon.getName();
        c.getSharedPreferences("hackathons", Context.MODE_PRIVATE).edit().putString("current", currentHackathon.getName()).commit();

    }

    public static String getCurrentHackathonId(Context context) {
        if(currentHackathon == null) {
            currentHackathon = context.getSharedPreferences("hackathons", Context.MODE_PRIVATE).getString("current", null);
        }
        return currentHackathon;
    }
}
