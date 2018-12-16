package com.tn.blasti.api.models.category;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amine 15/12/2018.
 */

public class Category implements Parcelable {
    @SerializedName("id")
    private Double mID;
    @SerializedName("name")
    private String mName;
    @SerializedName("parent")
    private Double mParent;
    @SerializedName("count")
    private Double mCount;

    private boolean mIsCategorySelected = false;

    private int mCategoryOrder;

    public Category(Double ID, String name, Double parent, Double count) {
        mID = ID;
        mName = name;
        mParent = parent;
        mCount = count;
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

    public Double getParent() {
        return mParent;
    }

    public void setParent(Double parent) {
        mParent = parent;
    }

    public Double getCount() {
        return mCount;
    }

    public void setCount(Double count) {
        mCount = count;
    }

    public boolean isCategorySelected() {
        return mIsCategorySelected;
    }

    public void setCategorySelected(boolean categorySelected) {
        mIsCategorySelected = categorySelected;
    }

    public int getCategoryOrder() {
        return mCategoryOrder;
    }

    public void setCategoryOrder(int mCategoryOrder) {
        this.mCategoryOrder = mCategoryOrder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mID);
        dest.writeString(mName);
        dest.writeDouble(mParent);
        dest.writeDouble(mCount);
        dest.writeInt(mIsCategorySelected ? 1 : 0);
        dest.writeInt(mCategoryOrder);
    }

    protected Category(Parcel in) {
        mID = in.readDouble();
        mName = in.readString();
        mParent = in.readDouble();
        mCount = in.readDouble();
        mIsCategorySelected = in.readInt() != 0;
        mCategoryOrder = in.readInt();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

}
