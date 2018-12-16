package com.tn.blasti.api.models.posts.commons;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amine 15/12/2018.
 */
public class Title implements Parcelable {
    @SerializedName("rendered")
    String mRendered;

    public Title() {

    }

    public Title(String rendered) {
        mRendered = rendered;
    }

    public String getRendered() {
        return mRendered;
    }

    public void setRendered(String rendered) {
        mRendered = rendered;
    }

    public static Creator<Title> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mRendered);
    }

    protected Title(Parcel in) {
        mRendered = in.readString();
    }

    public static final Creator<Title> CREATOR = new Creator<Title>() {
        @Override
        public Title createFromParcel(Parcel source) {
            return new Title(source);
        }

        @Override
        public Title[] newArray(int size) {
            return new Title[size];
        }
    };


}