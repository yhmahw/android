package me.gethelloworld.android.youhadmeathelloworld.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by david on 10/18/14.
 */
public class Location implements Parcelable{
    String place;
    String address;
    String city;
    String state;
    String country;

    public Location(Parcel in) {
        place = in.readString();
        address = in.readString();
        city = in.readString();
        state = in.readString();
        country = in.readString();
    }

    @Override
    public int describeContents() {
        return 2135235;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(place);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(country);

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
