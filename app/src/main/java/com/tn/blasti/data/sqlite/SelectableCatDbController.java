package com.tn.blasti.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tn.blasti.models.FavouriteModel;
import com.tn.blasti.models.SelectableCategoryModel;

import java.util.ArrayList;

/**
 * Created by amine 15/12/2018.
 */
public class SelectableCatDbController {

    private SQLiteDatabase db;

    public SelectableCatDbController(Context context) {
        db = DbHelper.getInstance(context).getWritableDatabase();
    }

    public int insertData(int categoryId, String categoryName, int categoryOrder) {

        ContentValues values = new ContentValues();
        values.put(DbConstants.CATEGORY_ID, categoryId);
        values.put(DbConstants.CATEGORY_NAME, categoryName);
        values.put(DbConstants.CATEGORY_ORDER, categoryOrder);

        // Insert the new row, returning the primary key value of the new row
        return (int) db.insert(
                DbConstants.CATEGORY_TABLE_NAME,
                DbConstants.COLUMN_NAME_NULLABLE,
                values);
    }

    public ArrayList<SelectableCategoryModel> getAllData() {


        String[] projection = {
                DbConstants._ID,
                DbConstants.CATEGORY_ID,
                DbConstants.CATEGORY_NAME,
                DbConstants.CATEGORY_ORDER
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = DbConstants.CATEGORY_ORDER;

        Cursor c = db.query(
                DbConstants.CATEGORY_TABLE_NAME,  // The table name to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return fetchData(c);
    }

    private ArrayList<SelectableCategoryModel> fetchData(Cursor c) {
        ArrayList<SelectableCategoryModel> catDataArray = new ArrayList<>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    // get  the  data into array,or class variable
                    int itemId = c.getInt(c.getColumnIndexOrThrow(DbConstants._ID));
                    int categoryId = c.getInt(c.getColumnIndexOrThrow(DbConstants.CATEGORY_ID));
                    String categoryName = c.getString(c.getColumnIndexOrThrow(DbConstants.CATEGORY_NAME));
                    int categoryOrder = c.getInt(c.getColumnIndexOrThrow(DbConstants.CATEGORY_ORDER));

                    // wrap up data list and return
                    catDataArray.add(new SelectableCategoryModel(itemId, categoryId, categoryName, categoryOrder));
                } while (c.moveToNext());
            }
            c.close();
        }
        return catDataArray;
    }

    public void deleteCat(int itemId) {
        // Which row to update, based on the ID
        String selection = DbConstants.CATEGORY_ID + "=?";
        String[] selectionArgs = {String.valueOf(itemId)};

        db.delete(
                DbConstants.CATEGORY_TABLE_NAME,
                selection,
                selectionArgs);
    }

    public void deleteAllCAT() {
        db.delete(
                DbConstants.CATEGORY_TABLE_NAME,
                null,
                null);
    }

    public void updateCategoryOrder(int itemId, int categoryOrder) {

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(DbConstants.CATEGORY_ORDER, categoryOrder);

        // Which row to update, based on the ID
        String selection = DbConstants._ID + "=?";
        String[] selectionArgs = {String.valueOf(itemId)};

        db.update(
                DbConstants.CATEGORY_TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

}
