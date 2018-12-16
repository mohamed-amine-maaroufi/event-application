package com.tn.blasti.api.models.posts.commons;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amine 15/12/2018.
 */

public class FullSize implements Parcelable {
    @SerializedName("source_url")
    private String mSourceUrl;

    public FullSize() {

    }

    public FullSize(String sourceUrl) {
        mSourceUrl = sourceUrl;
    }

    public String getSourceUrl() {
        return mSourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        mSourceUrl = sourceUrl;
    }

    public static Creator<FullSize> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mSourceUrl);
    }

    protected FullSize(Parcel in) {
        mSourceUrl = in.readString();
    }

    public static final Creator<FullSize> CREATOR = new Creator<FullSize>() {
        @Override
        public FullSize createFromParcel(Parcel source) {
            return new FullSize(source);
        }

        @Override
        public FullSize[] newArray(int size) {
            return new FullSize[size];
        }
    };


}