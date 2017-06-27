package vn.asiantech.internship.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * create and action with database
 * <p>
 * Created by Hai on 6/19/2017.
 */
class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Note_Manager";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NOTE = "Note";
    public static final String COLUMN_NOTE_ID = "Note_Id";
    public static final String COLUMN_NOTE_DAY_OF_WEEK = "Day_Of_Week";
    public static final String COLUMN_NOTE_DAY_OF_MONTH = "Day_Of_Month";
    public static final String COLUMN_NOTE_TIME = "Time";
    public static final String COLUMN_NOTE_TITLE = "Title";
    public static final String COLUMN_NOTE_CONTENT = "Note_Content";
    public static final String COLUMN_NOTE_IMAGE_URI = "Image_Uri";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NOTE + "(" + COLUMN_NOTE_ID + " Integer primary key, "
                + COLUMN_NOTE_DAY_OF_WEEK + " text, " + COLUMN_NOTE_DAY_OF_MONTH + " text, "
                + COLUMN_NOTE_TIME + " text, " + COLUMN_NOTE_TITLE + " text, "
                + COLUMN_NOTE_CONTENT + " text, " + COLUMN_NOTE_IMAGE_URI + " text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NOTE);
    }


}
