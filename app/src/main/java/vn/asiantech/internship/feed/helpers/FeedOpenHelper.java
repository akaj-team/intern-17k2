package vn.asiantech.internship.feed.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PC on 6/19/2017.
 */

public class FeedOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DB_FEED";
    public static final String FEED_TABLE = "Feed";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_PHOTO_LIST = "photos";
    public static final String COLUMN_TEXT = "text";

    public FeedOpenHelper(Context context) {
        super(context, "ten", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
