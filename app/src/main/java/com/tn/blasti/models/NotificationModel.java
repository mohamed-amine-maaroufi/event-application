package com.tn.blasti.models;

/**
 * Created by amine 15/12/2018.
 */
public class NotificationModel {

    private int id;
    private String title;
    private String message;
    private boolean isUnread;
    private String postId;

    public NotificationModel(int id, String title, String message, boolean isUnread, String url) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.isUnread = isUnread;
        this.postId = url;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }


    public String getPostId() {
        return postId;
    }

    public boolean isUnread() {
        return isUnread;
    }
}
