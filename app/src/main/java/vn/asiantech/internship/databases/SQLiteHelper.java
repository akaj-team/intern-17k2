package vn.asiantech.internship.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.asiantech.internship.models.FeedItem;

/**
 * create database and query
 *
 * Created by Hai on 6/19/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Feed_Manager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FEED = "Feed";
    private static final String COLUMN_USER_NAME = "User_Name";
    private static final String COLUMN_IMAGE = "Image";
    private static final String COLUMN_STATUS = "Status";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE_FEED + "("
                + COLUMN_USER_NAME + " text, "
                + COLUMN_IMAGE + " text, "
                + COLUMN_STATUS + " text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_FEED);
        onCreate(db);
    }

    public void addFeed(FeedItem feedItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, feedItem.getName());
        values.put(COLUMN_IMAGE, Arrays.toString(feedItem.getImageArray()));
        values.put(COLUMN_STATUS, feedItem.getStatus());
        db.insert(TABLE_FEED, null, values);
        db.close();
    }


    public List<FeedItem> getAllFeedItems() {
        List<FeedItem> feedItems = new ArrayList<>();
        String selectQuery = "select * from " + TABLE_FEED;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                FeedItem feedItem = new FeedItem();
                feedItem.setName(cursor.getString(0));
                feedItem.setImageArray(convertArray(cursor.getString(1)));
                feedItem.setStatus(cursor.getString(2));
                feedItems.add(feedItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return feedItems;
    }

    //convert String to int array
    private int[] convertArray(String str) {
        String[] items = str.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
        int[] results = new int[items.length];
        for (int i = 0; i < items.length; i++) {
            try {
                results[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
            }
        }
        return results;
    }
}
