package vn.asiantech.internship.feed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datbu on 19-06-2017.
 */

class DataBaseFeed {
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_IMAGE = "image";
    private static final String KEY_NAME = "name";
    private static final String KEY_TITLE = "title";

    private DataBaseHandler mDbHandle;
    private SQLiteDatabase mDb;
    private Context mContext;

    DataBaseFeed(Context mContext) {
        this.mContext = mContext;
        mDbHandle = new DataBaseHandler(mContext);
    }

    long insertItemFeed(Feed feed) {
        SQLiteDatabase db = mDbHandle.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String s = "";
        for (int i = 0; i < feed.getImages().length; i++) {
            s += feed.getImages()[i];
            if (i < feed.getImages().length - 1) {
                s += ", ";
            }
        }
        contentValues.put(KEY_IMAGE, s);
        contentValues.put(KEY_NAME, feed.getName());
        contentValues.put(KEY_TITLE, feed.getTitle());
        db.insert(TABLE_CONTACTS, null, contentValues);
        return db.insert(TABLE_CONTACTS, null, contentValues);
    }

    public List<Feed> getFeeds() {
        String[] columns = {KEY_NAME, KEY_IMAGE, KEY_TITLE};
        Cursor cursor = mDb.query(TABLE_CONTACTS, columns, null, null, null, null, null);
        int indexUserName = cursor.getColumnIndex(KEY_NAME);
        int indexPhotoList = cursor.getColumnIndex(KEY_IMAGE);
        int indexText = cursor.getColumnIndex(KEY_TITLE);
        List<Feed> results = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            results.add(new Feed(cursor.getString(indexUserName), cursor.getString(indexPhotoList), cursor.getString(indexText)));
        }
        cursor.close();
        return results;
    }
}
