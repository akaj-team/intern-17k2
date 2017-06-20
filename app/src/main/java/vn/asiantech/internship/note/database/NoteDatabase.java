package vn.asiantech.internship.note.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import vn.asiantech.internship.note.model.Note;

import static vn.asiantech.internship.note.database.DatabaseHelper.COL_CONTENT;
import static vn.asiantech.internship.note.database.DatabaseHelper.COL_DATE;
import static vn.asiantech.internship.note.database.DatabaseHelper.COL_ID;
import static vn.asiantech.internship.note.database.DatabaseHelper.COL_PATH;
import static vn.asiantech.internship.note.database.DatabaseHelper.COL_TIME;
import static vn.asiantech.internship.note.database.DatabaseHelper.COL_TITLE;

/**
 * Created by at-dinhvo on 19/06/2017.
 */
public class NoteDatabase {

    private Context mContext;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;

    public NoteDatabase(Context context) {
        mContext = context;
    }

    public NoteDatabase open() {
        mDatabaseHelper = DatabaseHelper.getInstance(mContext);
        mSqLiteDatabase = mDatabaseHelper.getReadableDatabase();
        Log.e("create table___________", "" + DatabaseHelper.getInstance(mContext));
        return this;
    }

    public void close() {
        mDatabaseHelper.close();
    }

    public long createData(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, note.getId());
        contentValues.put(DatabaseHelper.COL_TITLE, note.getTitle());
        contentValues.put(DatabaseHelper.COL_CONTENT, note.getContent());
        contentValues.put(DatabaseHelper.COL_PATH, note.getPath());
        contentValues.put(DatabaseHelper.COL_DATE, note.getDate());
        contentValues.put(DatabaseHelper.COL_TIME, note.getTime());
        return mSqLiteDatabase.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }

    public ArrayList<Note> getAllData() {
        ArrayList<Note> notes = new ArrayList<>();
        String[] columns = {COL_ID, COL_TITLE, COL_CONTENT, COL_PATH, COL_DATE, COL_TIME};
//        String sqlSelect = "SELECT * FROM " + DatabaseHelper.TABLE_NAME;

        Cursor cursor = mSqLiteDatabase.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        Note note;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            note = new Note(cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5));
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

    public int deleteNoteAll() {
        return mSqLiteDatabase.delete(DatabaseHelper.TABLE_NAME, null, null);
    }
}
