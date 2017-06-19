package vn.asiantech.internship.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.helpers.FeedOpenHelper;
import vn.asiantech.internship.models.FeedItem;

/**
 * Created by PC on 6/19/2017.
 */

public class FeedDatabase extends Activity {
    private Context mContext;
    private FeedOpenHelper mOpenHelper;
    private SQLiteDatabase mDb;

    public FeedDatabase(Context mContext) {
        this.mContext = mContext;
    }

    public FeedDatabase open() throws SQLException {
        mOpenHelper = new FeedOpenHelper(mContext);
        mDb = mOpenHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mOpenHelper.close();
    }

    public long insertFeed(FeedItem feed) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedOpenHelper.COLUMN_USER_NAME, feed.getUserName());
        contentValues.put(FeedOpenHelper.COLUMN_TEXT, feed.getText());
        String s = "";
        for (int i = 0; i < feed.getPhotoList().length; i++) {
            s += feed.getPhotoList()[i];
            if (i < feed.getPhotoList().length - 1) {
                s += ",";
            }
        }
        Log.i("tag11", s);
        contentValues.put(FeedOpenHelper.COLUMN_PHOTO_LIST, s);
        return mDb.insert(FeedOpenHelper.FEED_TABLE, null, contentValues);
    }

    public List<FeedItem> getFeeds() {
        String[] columns = {FeedOpenHelper.COLUMN_USER_NAME, FeedOpenHelper.COLUMN_PHOTO_LIST, FeedOpenHelper.COLUMN_TEXT};
        Cursor cursor = mDb.query(FeedOpenHelper.FEED_TABLE, columns, null, null, null, null, null);
        int indexUserName = cursor.getColumnIndex(FeedOpenHelper.COLUMN_USER_NAME);
        int indexPhotoList = cursor.getColumnIndex(FeedOpenHelper.COLUMN_PHOTO_LIST);
        int indexText = cursor.getColumnIndex(FeedOpenHelper.COLUMN_TEXT);
        List<FeedItem> results = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            results.add(new FeedItem(cursor.getString(indexUserName), cursor.getString(indexPhotoList), cursor.getString(indexText)));
        }
        cursor.close();
        return results;
    }
}
