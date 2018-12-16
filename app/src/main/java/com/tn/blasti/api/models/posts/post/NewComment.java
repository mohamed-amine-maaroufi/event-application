package com.tn.blasti.api.models.posts.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by amine 15/12/2018.
 */

public class NewComment {
    @SerializedName("author_name")
    @Expose
    String authorName;
    @SerializedName("author_email")
    @Expose
    String authorEmail;
    @SerializedName("content")
    @Expose
    String contentText;

    public NewComment(String authorName, String authorEmail, String content) {
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.contentText = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getContent() {
        return contentText;
    }

    public void setContent(String content) {
        this.contentText = content;
    }


}

