package vn.asiantech.internship.note.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by at-dinhvo on 20/06/2017.
 */
public class NoteOpenHelper extends SQLiteOpenHelper {

    static final String TABLE_NAME = "TABLE_NOTE";
    static final String COL_ID = "id";
    static final String COL_TITLE = "title";
    static final String COL_CONTENT = "content";
    static final String COL_PATH = "path";
    static final String COL_DATETIME = "datetime";
    private static final String DATABASE_NAME = "DATABASE_NOTE";
    private static final int VERSION = 1;
    private static NoteOpenHelper mNoteOpenHelper;

    public static NoteOpenHelper getNoteOpenHelper(Context context) {
        if (mNoteOpenHelper == null) {
            mNoteOpenHelper = new NoteOpenHelper(context);
        }
        return mNoteOpenHelper;
    }

    public NoteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreateTable = "CREATE TABLE " + TABLE_NAME
                + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT NOT NULL, "
                + COL_CONTENT + " TEXT, "
                + COL_PATH + " TEXT, "
                + COL_DATETIME + " TEXT "
                + ");";
        sqLiteDatabase.execSQL(sqlCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
