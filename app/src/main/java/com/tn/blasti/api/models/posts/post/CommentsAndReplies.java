package com.tn.blasti.api.models.posts.post;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.tn.blasti.utility.AppUtils;

/**
 * Created by amine 15/12/2018.
 */
public class CommentsAndReplies implements Parcelable {
    @SerializedName("id")
    private Double mID;
    @SerializedName("parent")
    private Double mParent;
    @SerializedName("author_name")
    private String mAuthorName;
    @SerializedName("date")
    private String mOldDate;
    @SerializedName("content")
    private Content mContent = new Content();
    @SerializedName("author_avatar_urls")
    private AuthorAvaterUrl mAuthorAvaterUrl = new AuthorAvaterUrl();

    private String formattedDate;

    public CommentsAndReplies() {
    }

    ;

    public CommentsAndReplies(Double ID, Double parent, String authorName, String oldDate, Content content, AuthorAvaterUrl authorAvaterUrl) {
        mID = ID;
        mParent = parent;
        mAuthorName = authorName;
        mOldDate = oldDate;
        mContent = content;
        mAuthorAvaterUrl = authorAvaterUrl;
    }

    public Double getID() {
        return mID;
    }

    public void setID(Double ID) {
        mID = ID;
    }

    public Double getParent() {
        return mParent;
    }

    public void setParent(Double parent) {
        mParent = parent;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public void setAuthorName(String authorName) {
        mAuthorName = authorName;
    }

    public String getOldDate() {
        return mOldDate;
    }

    public void setOldDate(String oldDate) {
        mOldDate = oldDate;
    }

    public Content getContent() {
        return mContent;
    }

    public void setContent(Content content) {
        mContent = content;
    }

    public AuthorAvaterUrl getAuthorAvaterUrl() {
        return mAuthorAvaterUrl;
    }

    public void setAuthorAvaterUrl(AuthorAvaterUrl authorAvaterUrl) {
        mAuthorAvaterUrl = authorAvaterUrl;
    }

    public static Creator<CommentsAndReplies> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mID);
        dest.writeDouble(mParent);
        dest.writeString(mAuthorName);
        dest.writeString(mOldDate);
        dest.writeString(formattedDate);
        dest.writeParcelable(mContent, flags);
        dest.writeParcelable(mAuthorAvaterUrl, flags);
    }

    protected CommentsAndReplies(Parcel in) {
        mID = in.readDouble();
        mParent = in.readDouble();
        mAuthorName = in.readString();
        mOldDate = in.readString();
        formattedDate = in.readString();
        mContent = in.readParcelable(Content.class.getClassLoader());
        mAuthorAvaterUrl = in.readParcelable(AuthorAvaterUrl.class.getClassLoader());
    }

    public static final Creator<CommentsAndReplies> CREATOR = new Creator<CommentsAndReplies>() {
        @Override
        public CommentsAndReplies createFromParcel(Parcel source) {
            return new CommentsAndReplies(source);
        }

        @Override
        public CommentsAndReplies[] newArray(int size) {
            return new CommentsAndReplies[size];
        }
    };

    public String getFormattedDate() {
        return AppUtils.getFormattedDate(mOldDate);
    }

}
