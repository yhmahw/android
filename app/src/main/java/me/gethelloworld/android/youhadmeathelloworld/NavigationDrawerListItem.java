package me.gethelloworld.android.youhadmeathelloworld;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 10/18/14.
 */
public class NavigationDrawerListItem {

    private static List<NavigationDrawerListItem> ITEMS;

    public static List<NavigationDrawerListItem> getItems() {
        if (ITEMS == null) {
            ITEMS = new ArrayList<NavigationDrawerListItem>();
            ITEMS.add(new NavigationDrawerListItem(HackathonsActivity.class, "Change Hackathon"));
            ITEMS.add(new NavigationDrawerListItem(ProfileActivity.class, "View Profile"));
        }
        return ITEMS;
    }

    private Class<? extends Activity> activityClazz;
    private String name;


    public NavigationDrawerListItem(Class<? extends Activity> activityClazz, String name) {
        this.activityClazz = activityClazz;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Class<? extends Activity> getActivityClazz() {
        return activityClazz;
    }
}
