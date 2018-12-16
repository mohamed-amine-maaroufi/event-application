package com.tn.blasti.api.models.posts.commons;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amine 15/12/2018.
 */

public class Sizes implements Parcelable {
    @SerializedName("full")
    private FullSize mFullSize = new FullSize();


    public Sizes() {
    }

    ;

    public Sizes(FullSize fullSize) {
        mFullSize = fullSize;
    }

    public FullSize getFullSize() {
        return mFullSize;
    }

    public void setFullSize(FullSize fullSize) {
        mFullSize = fullSize;
    }

    public static Creator<Sizes> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mFullSize, flags);
    }

    protected Sizes(Parcel in) {
        mFullSize = in.readParcelable(FullSize.class.getClassLoader());
    }

    public static final Creator<Sizes> CREATOR = new Creator<Sizes>() {
        @Override
        public Sizes createFromParcel(Parcel source) {
            return new Sizes(source);
        }

        @Override
        public Sizes[] newArray(int size) {
            return new Sizes[size];
        }
    };

}
