package com.tn.blasti.models;

import com.tn.blasti.utility.AppUtils;

/**
 * Created by amine 15/12/2018.
 */

public class FavouriteModel {
    int id;
    int postId;
    String postImageUrl;
    String postTitle;
    String mOldDate;
    private String formattedDate;
    String postCategory;

    public FavouriteModel(int id, int postId, String postImageUrl, String postTitle, String postDate, String postCategory) {
        this.id = id;
        this.postId = postId;
        this.postImageUrl = postImageUrl;
        this.postTitle = postTitle;
        this.mOldDate = postDate;
        this.formattedDate = AppUtils.getFormattedDate(mOldDate);
        this.postCategory = postCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getOldDate() {
        return mOldDate;
    }

    public void setOldDate(String oldDate) {
        this.mOldDate = oldDate;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(String postCategory) {
        this.postCategory = postCategory;
    }
}
