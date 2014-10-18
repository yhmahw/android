package me.gethelloworld.android.youhadmeathelloworld.adapters;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.List;

import me.gethelloworld.android.youhadmeathelloworld.api.Hackathon;

/**
 * Created by david on 10/18/14.
 */
public class HackathonsListAdapter implements ListAdapter {

    List<Hackathon> mHackathons;

    public HackathonsListAdapter(List<Hackathon> hackathons) {
        mHackathons = hackathons;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mHackathons.size();
    }

    @Override
    public Object getItem(int position) {
        return mHackathons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return mHackathons.isEmpty();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}
