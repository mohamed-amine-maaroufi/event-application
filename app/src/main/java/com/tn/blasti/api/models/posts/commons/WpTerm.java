package com.tn.blasti.api.models.posts.commons;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amine 15/12/2018.
 */

public class WpTerm implements Parcelable {
    @SerializedName("name")
    private String mName;

    public WpTerm() {

    }

    public WpTerm(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public static Creator<WpTerm> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
    }

    protected WpTerm(Parcel in) {
        mName = in.readString();
    }

    public static final Creator<WpTerm> CREATOR = new Creator<WpTerm>() {
        @Override
        public WpTerm createFromParcel(Parcel source) {
            return new WpTerm(source);
        }

        @Override
        public WpTerm[] newArray(int size) {
            return new WpTerm[size];
        }
    };


}