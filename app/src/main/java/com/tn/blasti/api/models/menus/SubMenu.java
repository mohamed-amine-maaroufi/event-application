package com.tn.blasti.api.models.menus;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amine 15/12/2018.
 */

public class SubMenu implements Parcelable {

    @SerializedName("ID")
    private Double mID;
    @SerializedName("name")
    private String mName;
    @SerializedName("count")
    private Double mCount;
    @SerializedName("items")
    private List<SubMenuItem> mSubMenus = new ArrayList<>();

    public SubMenu() {

    }

    public SubMenu(Double ID, String name, Double count, List<SubMenuItem> subMenus) {
        mID = ID;
        mName = name;
        mCount = count;
        mSubMenus = subMenus;
    }

    public Double getID() {
        return mID;
    }

    public void setID(Double ID) {
        mID = ID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Double getCount() {
        return mCount;
    }

    public void setCount(Double count) {
        mCount = count;
    }

    public void setSubMenus(List<SubMenuItem> subMenus) {
        mSubMenus = subMenus;
    }

    public List<SubMenuItem> getSubMenus() {
        return mSubMenus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mID);

        dest.writeList(mSubMenus);
    }

    protected SubMenu(Parcel in) {
        mID = in.readDouble();
        mName = in.readString();
        mCount = in.readDouble();
        in.readList(mSubMenus, SubMenu.class.getClassLoader());
    }

    public static Creator<SubMenu> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<SubMenu> CREATOR = new Creator<SubMenu>() {
        @Override
        public SubMenu createFromParcel(Parcel source) {
            return new SubMenu(source);
        }

        @Override
        public SubMenu[] newArray(int size) {
            return new SubMenu[size];
        }
    };
}
