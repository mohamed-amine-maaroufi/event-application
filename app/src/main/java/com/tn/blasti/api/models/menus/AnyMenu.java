package com.tn.blasti.api.models.menus;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amine 15/12/2018.
 */

public class AnyMenu implements Parcelable {
    private Double mID;
    private String mName;
    private boolean isMainMenu;

    public AnyMenu(Double ID, String name, boolean isMainMenu) {
        mID = ID;
        mName = name;
        this.isMainMenu = isMainMenu;
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

    public boolean isMainMenu() {
        return isMainMenu;
    }

    public void setMainMenu(boolean mainMenu) {
        isMainMenu = mainMenu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mID);
        dest.writeString(mName);
        dest.writeByte((byte) (isMainMenu ? 1 : 0));
    }

    protected AnyMenu(Parcel in) {
        mID = in.readDouble();
        mName = in.readString();
        isMainMenu = in.readByte() != 0;
    }

    public static Creator<AnyMenu> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<AnyMenu> CREATOR = new Creator<AnyMenu>() {
        @Override
        public AnyMenu createFromParcel(Parcel source) {
            return new AnyMenu(source);
        }

        @Override
        public AnyMenu[] newArray(int size) {
            return new AnyMenu[size];
        }
    };
}
