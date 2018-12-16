package com.tn.blasti.api.models.posts.commons;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amine 15/12/2018.
 */

public class Embedded implements Parcelable {

    @SerializedName("author")
    private List<Author> mAuthors = new ArrayList<>();
    @SerializedName("wp:featuredmedia")
    private List<WpFeaturedMedia> mWpFeaturedMedias = new ArrayList<>();
    @SerializedName("wp:term")
    private List<List<WpTerm>> mWpTerms = new ArrayList<>();


    public Embedded() {
    }

    public Embedded(List<Author> authors, List<WpFeaturedMedia> wpFeaturedMedias, List<List<WpTerm>> wpTerms) {
        mAuthors = authors;
        mWpFeaturedMedias = wpFeaturedMedias;
        mWpTerms = wpTerms;
    }

    public List<Author> getAuthors() {
        return mAuthors;
    }

    public void setAuthors(List<Author> authors) {
        mAuthors = authors;
    }

    public List<WpFeaturedMedia> getWpFeaturedMedias() {
        return mWpFeaturedMedias;
    }

    public void setWpFeaturedMedias(List<WpFeaturedMedia> wpFeaturedMedias) {
        mWpFeaturedMedias = wpFeaturedMedias;
    }

    public List<List<WpTerm>> getWpTerms() {
        return mWpTerms;
    }

    public void setWpTerms(List<List<WpTerm>> wpTerms) {
        mWpTerms = wpTerms;
    }

    public static Creator<Embedded> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(mAuthors);
        dest.writeList(mWpFeaturedMedias);
        dest.writeList(mWpTerms);
    }

    protected Embedded(Parcel in) {
        in.readList(mAuthors, Author.class.getClassLoader());
        in.readList(mWpFeaturedMedias, WpFeaturedMedia.class.getClassLoader());
        in.readList(mWpTerms, WpTerm.class.getClassLoader());
    }

    public static final Creator<Embedded> CREATOR = new Creator<Embedded>() {
        @Override
        public Embedded createFromParcel(Parcel source) {
            return new Embedded(source);
        }

        @Override
        public Embedded[] newArray(int size) {
            return new Embedded[size];
        }
    };

}
