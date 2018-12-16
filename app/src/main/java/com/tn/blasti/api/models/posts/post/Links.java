package com.tn.blasti.api.models.posts.post;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amine 15/12/2018.
 */

public class Links implements Parcelable {

    @SerializedName("replies")
    private List<Replies> mRepliesList = new ArrayList<>();

    public Links() {
    }

    public Links(List<Replies> repliesList) {
        mRepliesList = repliesList;
    }

    public List<Replies> getRepliesList() {
        return mRepliesList;
    }

    public void setRepliesList(List<Replies> repliesList) {
        mRepliesList = repliesList;
    }

    public static Creator<Links> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(mRepliesList);
    }

    protected Links(Parcel in) {
        in.readList(mRepliesList, Replies.class.getClassLoader());
    }

    public static final Creator<Links> CREATOR = new Creator<Links>() {
        @Override
        public Links createFromParcel(Parcel source) {
            return new Links(source);
        }

        @Override
        public Links[] newArray(int size) {
            return new Links[size];
        }
    };

}
