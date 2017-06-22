package vn.asiantech.internship.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.helpers.NoteOpenHelper;
import vn.asiantech.internship.models.Note;

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

    public long addNote(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE, note.getDate());
        contentValues.put(COLUMN_TITLE, note.getTitle());
        contentValues.put(COLUMN_CONTENT, note.getContent());
        contentValues.put(COLUMN_URL_IMAGE, note.getUrlImage());
        return mSQLiteDatabase.insert(TABLE_NOTE, null, contentValues);
    }

    public List<Note> getList() {
        List<Note> notes = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NOTE, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Note note = new Note();
            note.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
            note.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            note.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)));
            note.setUrlImage(cursor.getString(cursor.getColumnIndex(COLUMN_URL_IMAGE)));
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

    public int delete(Note note) {
        return mSQLiteDatabase.delete(TABLE_NOTE, COLUMN_DATE + " = ? ", new String[]{note.getDate() + ""});
    }

    public int deleteAll() {
        return mSQLiteDatabase.delete(TABLE_NOTE, null, null);
    }
}
