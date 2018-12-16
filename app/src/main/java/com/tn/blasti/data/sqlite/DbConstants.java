package com.tn.blasti.data.sqlite;

import android.provider.BaseColumns;

/**
 * Created by amine 15/12/2018.
 */
public class DbConstants implements BaseColumns {

    // commons
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    public static final String COLUMN_NAME_NULLABLE = null;

    // notification
    public static final String NOTIFICATION_TABLE_NAME = "notifications";

    public static final String COLUMN_NOT_TITLE = "not_title";
    public static final String COLUMN_NOT_MESSAGE = "not_message";
    public static final String COLUMN_NOT_READ_STATUS = "not_status";
    public static final String COLUMN_NOT_CONTENT_URL = "content_url";

    public static final String SQL_CREATE_NOTIFICATION_ENTRIES =
            "CREATE TABLE " + NOTIFICATION_TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NOT_TITLE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NOT_MESSAGE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NOT_CONTENT_URL + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NOT_READ_STATUS + TEXT_TYPE +
                    " )";


    public static final String SQL_DELETE_NOTIFICATION_ENTRIES =
            "DROP TABLE IF EXISTS " + NOTIFICATION_TABLE_NAME;


    // favourites
    public static final String FAVOURITE_TABLE_NAME = "favourite";

    public static final String POST_ID = "post_id";
    public static final String POST_IMAGE = "post_image";
    public static final String POST_TITLE = "post_title";
    public static final String POST_DATE = "post_date";
    public static final String POST_CATEGORY = "category_name";

    public static final String SQL_CREATE_FAVOURITE_ENTRIES =
            "CREATE TABLE " + FAVOURITE_TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    POST_ID + INTEGER_TYPE + COMMA_SEP +
                    POST_IMAGE + TEXT_TYPE + COMMA_SEP +
                    POST_TITLE + TEXT_TYPE + COMMA_SEP +
                    POST_DATE + TEXT_TYPE + COMMA_SEP +
                    POST_CATEGORY + TEXT_TYPE + " )";


    public static final String SQL_DELETE_FAVOURITE_ENTRIES =
            "DROP TABLE IF EXISTS " + FAVOURITE_TABLE_NAME;


    // selectable categories
    public static final String CATEGORY_TABLE_NAME = "category";

    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_ORDER = "category_order";

    public static final String SQL_CREATE_CATEGORY_ENTRIES =
            "CREATE TABLE " + CATEGORY_TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    CATEGORY_ID + INTEGER_TYPE + COMMA_SEP +
                    CATEGORY_NAME + TEXT_TYPE + COMMA_SEP +
                    CATEGORY_ORDER + INTEGER_TYPE + " )";


    public static final String SQL_DELETE_CATEGORY_ENTRIES =
            "DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME;

}
