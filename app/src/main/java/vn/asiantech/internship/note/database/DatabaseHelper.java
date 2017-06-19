package vn.asiantech.internship.note.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by at-dinhvo on 19/06/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "TABLE_NOTE";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_CONTENT = "content";
    public static final String COL_PATH = "path";
    public static final String COL_DATE = "date";
    public static final String COL_TIME = "time";
    private static final String DATABASE_NAME = "DATABASE_NOTE";
    private static final int VERSION = 1;

    private static DatabaseHelper sDatabaseHelper;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreateTable = "CREATE TABLE " + TABLE_NAME
                + "(" + COL_ID + " INTEGER PRIMARY KEY,"
                + COL_TITLE + " TEXT NOT NULL,"
                + COL_CONTENT + " TEXT,"
                + COL_PATH + " TEXT,"
                + COL_DATE + " DATE,"
                + COL_TIME + " TIME"
                + ");";
        sqLiteDatabase.execSQL(sqlCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i != i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }

    public static DatabaseHelper getInstance(Context context){
        if(sDatabaseHelper == null){
            sDatabaseHelper = new DatabaseHelper(context);
        }
        return sDatabaseHelper;
    }
}
