package me.gethelloworld.android.youhadmeathelloworld.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.util.List;

import me.gethelloworld.android.youhadmeathelloworld.R;
import me.gethelloworld.android.youhadmeathelloworld.api.Hackathon;

/**
 * Created by david on 10/18/14.
 */
public class HackathonsListAdapter implements ListAdapter {

    List<Hackathon> mHackathons;
    Context mContext;

    public HackathonsListAdapter(Context context, List<Hackathon> hackathons) {
        mHackathons = hackathons;
        mContext = context;
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
    public Hackathon getItem(int position) {
        return mHackathons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getName().hashCode();
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
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return mHackathons.isEmpty();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.hackathon_listitem, parent, false);

        ((TextView) convertView.findViewById(R.id.hackathonlist_name)).setText(getItem(position).getName());
        ((SmartImageView) convertView.findViewById(R.id.hackathinlist_image)).setImageUrl("http://boilermake.org/assets/hammers-f9c4f94620747178d87f76dd046ffee8.png");

        convertView.setTag(getItem(position));


        return convertView;
    }

}
