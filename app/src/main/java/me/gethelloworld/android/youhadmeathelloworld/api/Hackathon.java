package me.gethelloworld.android.youhadmeathelloworld.api;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 10/18/14.
 */
public class Hackathon implements Parcelable {
    private String name;
    private String startDate; //ISO Format
    private String thumb; //url to thumbnail for hackathon
    private String description;
    private String endDate;
    private Location location;
    private List<String> members;


    public Hackathon(Parcel in) {
        name = in.readString();
        startDate = in.readString();
        thumb = in.readString();
        description = in.readString();
        endDate = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());

        members = new ArrayList<String>();
        in.readStringList(members);
    }

    @Override
    public int describeContents(){
        return 682724624;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(startDate);
        dest.writeString(thumb);
        dest.writeString(description);
        dest.writeString(endDate);
        dest.writeParcelable(location, 0);
        dest.writeStringList(members);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Hackathon createFromParcel(Parcel in) {
            return new Hackathon(in);
        }

        public Hackathon[] newArray(int size) {
            return new Hackathon[size];
        }
    };



    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
