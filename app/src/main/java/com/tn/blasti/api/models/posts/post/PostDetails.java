package com.tn.blasti.api.models.posts.post;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.tn.blasti.api.models.posts.commons.Embedded;
import com.tn.blasti.api.models.posts.commons.Title;

/**
 * Created by amine 15/12/2018.
 */

public class PostDetails implements Parcelable {
    @SerializedName("id")
    private Double mID;
    @SerializedName("link")
    private String mPageUrl;
    @SerializedName("title")
    private Title mTitle = new Title();
    @SerializedName("content")
    private Content mContent = new Content();
    @SerializedName("_embedded")
    private Embedded mEmbedded = new Embedded();
    @SerializedName("date")
    private String mOldDate;
    @SerializedName("_links")
    private Links mLinks = new Links();

    public PostDetails(Double ID, String pageUrl, Title title, Content content, Embedded embedded, String oldDate, Links links) {
        mID = ID;
        mPageUrl = pageUrl;
        mTitle = title;
        mContent = content;
        mEmbedded = embedded;
        mOldDate = oldDate;
        mLinks = links;
    }

    public Double getID() {
        return mID;
    }

    public void setID(Double ID) {
        mID = ID;
    }

    public String getPageUrl() {
        return mPageUrl;
    }

    public void setPageUrl(String pageUrl) {
        mPageUrl = pageUrl;
    }

    public Title getTitle() {
        return mTitle;
    }

    public void setTitle(Title title) {
        mTitle = title;
    }

    public Content getContent() {
        return mContent;
    }

    public void setContent(Content content) {
        mContent = content;
    }

    public Embedded getEmbedded() {
        return mEmbedded;
    }

    public void setEmbedded(Embedded embedded) {
        mEmbedded = embedded;
    }

    public String getOldDate() {
        return mOldDate;
    }

    public void setOldDate(String oldDate) {
        mOldDate = oldDate;
    }

    public Links getLinks() {
        return mLinks;
    }

    public void setLinks(Links links) {
        mLinks = links;
    }

    public static Creator<PostDetails> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mID);
        dest.writeString(mPageUrl);
        dest.writeParcelable(mTitle, flags);
        dest.writeParcelable(mContent, flags);
        dest.writeParcelable(mEmbedded, flags);
        dest.writeString(mOldDate);
        dest.writeParcelable(mLinks, flags);
    }

    protected PostDetails(Parcel in) {
        mID = in.readDouble();
        mPageUrl = in.readString();
        mTitle = in.readParcelable(Title.class.getClassLoader());
        mContent = in.readParcelable(Content.class.getClassLoader());
        mEmbedded = in.readParcelable(Embedded.class.getClassLoader());
        mOldDate = in.readString();
        mLinks = in.readParcelable(Links.class.getClassLoader());
    }

    public static final Creator<PostDetails> CREATOR = new Creator<PostDetails>() {
        @Override
        public PostDetails createFromParcel(Parcel source) {
            return new PostDetails(source);
        }

        @Override
        public PostDetails[] newArray(int size) {
            return new PostDetails[size];
        }
    };

}

