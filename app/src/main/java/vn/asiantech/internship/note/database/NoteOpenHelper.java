package vn.asiantech.internship.note.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by at-dinhvo on 20/06/2017.
 */
public class NoteOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "TABLE_NOTE";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_CONTENT = "content";
    public static final String COL_PATH = "path";
    public static final String COL_DATE = "date";
    public static final String COL_TIME = "time";
    private static final String DATABASE_NAME = "DATABASE_NOTE";
    private static final int VERSION = 1;

    private String sqlCreateTable = "CREATE TABLE " + TABLE_NAME
            + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_TITLE + " TEXT NOT NULL, "
            + COL_CONTENT + " TEXT, "
            + COL_PATH + " TEXT, "
            + COL_DATE + " TEXT, "
            + COL_TIME + " TEXT "
            + ");";

    public NoteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
