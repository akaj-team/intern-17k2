package vn.asiantech.internship.day11.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 20/06/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_DAY = "day";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    static final String DATABASE_TABLE = "note";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_note";
    private static final String TYPE_TEXT = "TEXT";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_note = "CREATE TABLE " + DATABASE_TABLE + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " " + TYPE_TEXT + ","
                + KEY_DESCRIPTION + " " + TYPE_TEXT + ","
                + KEY_IMAGE + " " + TYPE_TEXT + ","
                + KEY_DAY + " " + TYPE_TEXT + ","
                + KEY_DATE + " " + TYPE_TEXT + ","
                + KEY_TIME + " " + TYPE_TEXT + ")";
        db.execSQL(create_table_note);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + DATABASE_TABLE);
        onCreate(db);
    }
}
