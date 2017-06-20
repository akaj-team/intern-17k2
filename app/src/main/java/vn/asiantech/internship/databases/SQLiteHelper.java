package vn.asiantech.internship.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.asiantech.internship.models.FeedItem;

/**
 * create database and query
 * <p>
 * Created by Hai on 6/19/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Feed_Manager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FEED = "images";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_IMAGE = "link";
    private static final String COLUMN_DECRIPTION = "decription";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        String link = context.getFilesDir().getParent() + "/databases/" + DATABASE_NAME;
        copyDatabase(context, link);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String query = "create table " + TABLE_FEED + "("
//                + COLUMN_USER_NAME + " text, "
//                + COLUMN_IMAGE + " text, "
//                + COLUMN_STATUS + " text)";
//        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table if exists " + TABLE_FEED);
//        onCreate(db);
    }

    private void copyDatabase(Context context, String link) {
        try {
            InputStream is = context.getAssets().open(DATABASE_NAME);
            OutputStream os = new FileOutputStream(link);
            byte[] buffer = new byte[1024];
            int lenght;
            while ((lenght = is.read(buffer)) > 0) {
                os.write(buffer, 0, lenght);
            }
            os.flush();
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFeed(FeedItem feedItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, feedItem.getName());
        values.put(COLUMN_IMAGE, Arrays.toString(feedItem.getImageArray()));
        values.put(COLUMN_DECRIPTION, feedItem.getStatus());
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
        String[] items = str.split(",");
        int[] results = new int[items.length];
        for (int i = 0; i < items.length; i++) {
            results[i] = Integer.parseInt(items[i]);
        }
        return results;
    }
}
