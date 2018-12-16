package com.tn.blasti.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tn.blasti.models.FavouriteModel;
import com.tn.blasti.models.NotificationModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by amine 15/12/2018.
 */
public class FavouriteDbController {

    private SQLiteDatabase db;

    public FavouriteDbController(Context context) {
        db = DbHelper.getInstance(context).getWritableDatabase();
    }

    public int insertData(int postId, String postImage, String postTitle, String postDate, String postCategory) {

        ContentValues values = new ContentValues();
        values.put(DbConstants.POST_ID, postId);
        values.put(DbConstants.POST_IMAGE, postImage);
        values.put(DbConstants.POST_TITLE, postTitle);
        values.put(DbConstants.POST_DATE, postDate);
        values.put(DbConstants.POST_CATEGORY, postCategory);

        // Insert the new row, returning the primary key value of the new row
        return (int) db.insert(
                DbConstants.FAVOURITE_TABLE_NAME,
                DbConstants.COLUMN_NAME_NULLABLE,
                values);
    }

    public ArrayList<FavouriteModel> getAllData() {


        String[] projection = {
                DbConstants._ID,
                DbConstants.POST_IMAGE,
                DbConstants.POST_ID,
                DbConstants.POST_TITLE,
                DbConstants.POST_DATE,
                DbConstants.POST_CATEGORY,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = DbConstants._ID + " DESC";

        Cursor c = db.query(
                DbConstants.FAVOURITE_TABLE_NAME,  // The table name to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return fetchData(c);
    }

    private ArrayList<FavouriteModel> fetchData(Cursor c) {
        ArrayList<FavouriteModel> favDataArray = new ArrayList<>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    // get  the  data into array,or class variable
                    int itemId = c.getInt(c.getColumnIndexOrThrow(DbConstants._ID));
                    int postId = c.getInt(c.getColumnIndexOrThrow(DbConstants.POST_ID));
                    String postImage = c.getString(c.getColumnIndexOrThrow(DbConstants.POST_IMAGE));
                    String postTitle = c.getString(c.getColumnIndexOrThrow(DbConstants.POST_TITLE));
                    String postDate = c.getString(c.getColumnIndexOrThrow(DbConstants.POST_DATE));
                    String postCategory = c.getString(c.getColumnIndexOrThrow(DbConstants.POST_CATEGORY));


                    // wrap up data list and return
                    favDataArray.add(new FavouriteModel(itemId, postId, postImage, postTitle, postDate, postCategory));
                } while (c.moveToNext());
            }
            c.close();
        }
        return favDataArray;
    }

    public void deleteFav(int itemId) {
        // Which row to update, based on the ID
        String selection = DbConstants.POST_ID + "=?";
        String[] selectionArgs = {String.valueOf(itemId)};

        db.delete(
                DbConstants.FAVOURITE_TABLE_NAME,
                selection,
                selectionArgs);
    }

    public void deleteAllFav() {
        db.delete(
                DbConstants.FAVOURITE_TABLE_NAME,
                null,
                null);
    }

}
