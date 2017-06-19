package vn.asiantech.internship.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hai on 6/19/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Feed_Manager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FEED = "Feed";
    private static final String COLUMN_ID = "Feed_Id";
    private static final String COLUMN_USER_NAME = "User_Name";
    private static final String COLUMN_IMAGE = "Image";
    private static final String COLUMN_STATUS = "Status";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE_FEED + "(" + COLUMN_ID + " integer primary key, "
                + COLUMN_USER_NAME + " text, " + COLUMN_IMAGE + " text, "
                + COLUMN_STATUS + " text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_FEED);
        onCreate(db);
    }
}
