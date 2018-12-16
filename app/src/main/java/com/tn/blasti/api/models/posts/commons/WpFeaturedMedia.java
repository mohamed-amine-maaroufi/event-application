package com.tn.blasti.api.models.posts.commons;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amine 15/12/2018.
 */

public class WpFeaturedMedia implements Parcelable {
    @SerializedName("media_details")
    private MediaDetails mMediaDetails = new MediaDetails();

    public WpFeaturedMedia(MediaDetails mediaDetails) {
        mMediaDetails = mediaDetails;
    }

    public MediaDetails getMediaDetails() {
        return mMediaDetails;
    }

    public void setMediaDetails(MediaDetails mediaDetails) {
        mMediaDetails = mediaDetails;
    }

    public static Creator<WpFeaturedMedia> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeParcelable(mMediaDetails, flags);
    }

    protected WpFeaturedMedia(Parcel in) {
        mMediaDetails = in.readParcelable(MediaDetails.class.getClassLoader());
    }

    public static final Creator<WpFeaturedMedia> CREATOR = new Creator<WpFeaturedMedia>() {
        @Override
        public WpFeaturedMedia createFromParcel(Parcel source) {
            return new WpFeaturedMedia(source);
        }

        @Override
        public WpFeaturedMedia[] newArray(int size) {
            return new WpFeaturedMedia[size];
        }
    };

}
