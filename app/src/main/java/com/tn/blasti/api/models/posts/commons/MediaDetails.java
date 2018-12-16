package com.tn.blasti.api.models.posts.commons;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amine 15/12/2018.
 */

public class MediaDetails implements Parcelable {
    @SerializedName("sizes")
    private Sizes mSizes = new Sizes();

    public MediaDetails() {
    }

    public MediaDetails(Sizes sizes) {
        mSizes = sizes;
    }

    public Sizes getSizes() {
        return mSizes;
    }

    public void setSizes(Sizes sizes) {
        mSizes = sizes;
    }

    public static Creator<MediaDetails> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mSizes, flags);
    }

    protected MediaDetails(Parcel in) {
        mSizes = in.readParcelable(Sizes.class.getClassLoader());
    }

    public static final Creator<MediaDetails> CREATOR = new Creator<MediaDetails>() {
        @Override
        public MediaDetails createFromParcel(Parcel source) {
            return new MediaDetails(source);
        }

        @Override
        public MediaDetails[] newArray(int size) {
            return new MediaDetails[size];
        }
    };

}
