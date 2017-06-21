package vn.asiantech.internship.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.models.FeedItem;

/**
 * create database and query
 * <p>
 * Created by Hai on 6/19/2017.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "list_image.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FEED = "images";
    private String mPath;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mPath = context.getFilesDir().getPath() + DATABASE_NAME;
        copyDatabase(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void copyDatabase(Context context) {
        this.getReadableDatabase();
        try {
            InputStream is = context.getAssets().open(DATABASE_NAME);
            OutputStream os = new FileOutputStream(mPath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<FeedItem> getAllFeedItems() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READONLY);
        List<FeedItem> feedItems = new ArrayList<>();
        String selectQuery = "select * from " + TABLE_FEED;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                FeedItem feedItem = new FeedItem();
                feedItem.setName(cursor.getString(1));
                feedItem.setStatus(cursor.getString(2));
                feedItem.setImageArray(convertArray(cursor.getString(3)));
                feedItems.add(feedItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return feedItems;
    }

    //convert String to int array
    private String[] convertArray(String str) {
        return str.split(",");
    }
}
