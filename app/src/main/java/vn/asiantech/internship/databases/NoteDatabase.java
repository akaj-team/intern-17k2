package vn.asiantech.internship.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.models.Note;

/**
 * query database
 * <p>
 * Created by Hai on 6/27/2017.
 */
public class NoteDatabase {
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;

    public NoteDatabase(Context context) {
        mContext = context;
    }

    public NoteDatabase open() {
        DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
        mSQLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void insertNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NOTE_DAY_OF_WEEK, note.getDayOfWeek());
        values.put(DatabaseHelper.COLUMN_NOTE_DAY_OF_MONTH, note.getDayOfMonth());
        values.put(DatabaseHelper.COLUMN_NOTE_TIME, note.getTime());
        values.put(DatabaseHelper.COLUMN_NOTE_TITLE, note.getTitle());
        values.put(DatabaseHelper.COLUMN_NOTE_CONTENT, note.getContent());
        values.put(DatabaseHelper.COLUMN_NOTE_IMAGE_URI, note.getImage());
        mSQLiteDatabase.insert(DatabaseHelper.TABLE_NOTE, null, values);
        mSQLiteDatabase.close();
    }

    public int updateNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NOTE_DAY_OF_WEEK, note.getDayOfWeek());
        values.put(DatabaseHelper.COLUMN_NOTE_DAY_OF_MONTH, note.getDayOfMonth());
        values.put(DatabaseHelper.COLUMN_NOTE_TIME, note.getTime());
        values.put(DatabaseHelper.COLUMN_NOTE_TITLE, note.getTitle());
        values.put(DatabaseHelper.COLUMN_NOTE_CONTENT, note.getContent());
        values.put(DatabaseHelper.COLUMN_NOTE_IMAGE_URI, note.getImage());
        return mSQLiteDatabase.update(DatabaseHelper.TABLE_NOTE, values, DatabaseHelper.COLUMN_NOTE_ID + " = " + note.getId(), null);
    }

    public void deleteNote(Note note) {
        mSQLiteDatabase.delete(DatabaseHelper.TABLE_NOTE, DatabaseHelper.COLUMN_NOTE_ID + " = " + note.getId(), null);
        mSQLiteDatabase.close();
    }

    public List<Note> getAllNote() {
        List<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_NOTE;
        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(0));
                note.setDayOfWeek(cursor.getString(1));
                note.setDayOfMonth(cursor.getString(2));
                note.setTime(cursor.getString(3));
                note.setTitle(cursor.getString(4));
                note.setContent(cursor.getString(5));
                note.setImage(cursor.getString(6));
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }
}
