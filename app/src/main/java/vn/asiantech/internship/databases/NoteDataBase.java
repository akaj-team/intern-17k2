package vn.asiantech.internship.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import vn.asiantech.internship.helpers.NoteOpenHelper;

/**
 * Created by ducle on 20/06/2017.
 */

public class NoteDataBase {
    public static final String DATABASE_NAME = "DB_NOTE";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NOTE = "NOTE";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_URL_IMAGE = "url_image";

    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private NoteOpenHelper mNoteOpenHelper;

    public NoteDataBase(Context context) {
        this.mContext = context;
    }

    public NoteDataBase open() throws IOException {
        mNoteOpenHelper = new NoteOpenHelper(mContext);
        mSQLiteDatabase = mNoteOpenHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mNoteOpenHelper.close();
    }
}
