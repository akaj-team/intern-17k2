package vn.asiantech.internship.note.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import vn.asiantech.internship.note.model.Note;

import static vn.asiantech.internship.note.database.NoteOpenHelper.COL_CONTENT;
import static vn.asiantech.internship.note.database.NoteOpenHelper.COL_DATETIME;
import static vn.asiantech.internship.note.database.NoteOpenHelper.COL_ID;
import static vn.asiantech.internship.note.database.NoteOpenHelper.COL_PATH;
import static vn.asiantech.internship.note.database.NoteOpenHelper.COL_TITLE;
import static vn.asiantech.internship.note.database.NoteOpenHelper.TABLE_NAME;

/**
 * Created by at-dinhvo on 19/06/2017.
 */
public class NoteDatabase {

    private Context mContext;
    private NoteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;

    public NoteDatabase(Context context) {
        mContext = context;
    }

    public NoteDatabase open() { // context null if don't init constructor in activitycreated
        mDatabaseHelper = new NoteOpenHelper(mContext);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDatabaseHelper.close();
    }

    public long createData(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TITLE, note.getTitle());
        contentValues.put(COL_CONTENT, note.getContent());
        contentValues.put(COL_PATH, note.getPath());
        contentValues.put(COL_DATETIME, note.getDatetime());
        return mSqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<Note> getAllData() {
        ArrayList<Note> notes = new ArrayList<>();
        String[] columns = new String[]{COL_ID, COL_TITLE, COL_CONTENT, COL_PATH, COL_DATETIME};
        Cursor cursor = mSqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        Note note;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            note = new Note(cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4));
            note.setId(cursor.getInt(0));
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

    public Note getItem(int id) {
        Note note = new Note();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = " + id;
        Cursor cursor = mSqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
            note.setId(cursor.getInt(0));
            note.setTitle(cursor.getString(1));
            note.setContent(cursor.getString(2));
            note.setPath(cursor.getString(3));
            note.setDatetime(cursor.getString(4));
            cursor.close();
        }
        return note;
    }

    public boolean deleteNote(int id) {
        return mSqLiteDatabase.delete(TABLE_NAME, COL_ID + "=" + id, null) > 0;
    }
}
