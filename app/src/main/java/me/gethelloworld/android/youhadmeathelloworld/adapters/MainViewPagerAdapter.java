package me.gethelloworld.android.youhadmeathelloworld.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import me.gethelloworld.android.youhadmeathelloworld.controller.MainFragmentsStore;

/**
 * Created by david on 10/18/14.
 */
public class MainViewPagerAdapter extends FragmentStatePagerAdapter {


    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return MainFragmentsStore.getFragmentByLocation(i);
    }

    @Override
    public int getCount() {
        return MainFragmentsStore.getNumFragments();
    }
}
