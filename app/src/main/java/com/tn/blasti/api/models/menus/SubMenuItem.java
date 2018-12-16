package com.tn.blasti.api.models.menus;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amine 15/12/2018.
 */

public class SubMenuItem implements Parcelable {

    @SerializedName("id")
    private Double mID;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("object_id")
    private Double mObjectID;
    @SerializedName("object")
    private String mObject;
    @SerializedName("children")
    private List<SubMenuItem> mSubMenuItems = new ArrayList<>();

    public SubMenuItem() {

    }

    public SubMenuItem(Double ID, String title, String url, Double objectID, String object, List<SubMenuItem> subMenuItems) {
        mID = ID;
        mTitle = title;
        mUrl = url;
        mObjectID = objectID;
        mObject = object;
        mSubMenuItems = subMenuItems;
    }

    public Double getID() {
        return mID;
    }

    public void setID(Double ID) {
        mID = ID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public Double getObjectID() {
        return mObjectID;
    }

    public void setObjectID(Double objectID) {
        mObjectID = objectID;
    }

    public String getObject() {
        return mObject;
    }

    public void setObject(String object) {
        mObject = object;
    }

    public List<SubMenuItem> getSubMenuItems() {
        return mSubMenuItems;
    }

    public void setSubMenuItems(List<SubMenuItem> subMenuItems) {
        mSubMenuItems = subMenuItems;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mID);
        dest.writeString(mTitle);
        dest.writeString(mUrl);
        dest.writeDouble(mObjectID);
        dest.writeString(mObject);
        dest.writeList(mSubMenuItems);
    }

    protected SubMenuItem(Parcel in) {
        mID = in.readDouble();
        mTitle = in.readString();
        mUrl = in.readString();
        mObjectID = in.readDouble();
        mObject = in.readString();
        in.readList(mSubMenuItems, SubMenuItem.class.getClassLoader());
    }

    public static final Creator<SubMenuItem> CREATOR = new Creator<SubMenuItem>() {
        @Override
        public SubMenuItem createFromParcel(Parcel source) {
            return new SubMenuItem(source);
        }

        @Override
        public SubMenuItem[] newArray(int size) {
            return new SubMenuItem[size];
        }
    };
}
