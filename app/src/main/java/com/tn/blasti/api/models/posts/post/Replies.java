package com.tn.blasti.api.models.posts.post;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amine 15/12/2018.
 */

public class Replies implements Parcelable {
    @SerializedName("href")
    String mHref;

    public Replies() {
    }

    ;

    public Replies(String href) {
        mHref = href;
    }

    public String getHref() {
        return mHref;
    }

    public void setHref(String href) {
        mHref = href;
    }

    public static Creator<Replies> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mHref);
    }

    protected Replies(Parcel in) {
        mHref = in.readString();
    }

    public static final Creator<Replies> CREATOR = new Creator<Replies>() {
        @Override
        public Replies createFromParcel(Parcel source) {
            return new Replies(source);
        }

        @Override
        public Replies[] newArray(int size) {
            return new Replies[size];
        }
    };


}