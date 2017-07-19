package vn.asiantech.internship.note.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import vn.asiantech.internship.note.model.Note;

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

    public NoteDatabase open() {
        mDatabaseHelper = new NoteOpenHelper(mContext);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDatabaseHelper.close();
    }

    public long createData(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteOpenHelper.COL_TITLE, note.getTitle());
        contentValues.put(NoteOpenHelper.COL_CONTENT, note.getContent());
        contentValues.put(NoteOpenHelper.COL_PATH, note.getPath());
        contentValues.put(NoteOpenHelper.COL_DATETIME, note.getDatetime());
        return mSqLiteDatabase.insert(NoteOpenHelper.TABLE_NAME, null, contentValues);
    }

    public ArrayList<Note> getAllData() {
        ArrayList<Note> notes = new ArrayList<>();
        String[] columns = new String[]{
                NoteOpenHelper.COL_ID,
                NoteOpenHelper.COL_TITLE,
                NoteOpenHelper.COL_CONTENT,
                NoteOpenHelper.COL_PATH,
                NoteOpenHelper.COL_DATETIME};
        Cursor cursor = mSqLiteDatabase.query(NoteOpenHelper.TABLE_NAME, columns, null, null, null, null, null);
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
        String sql = "SELECT * FROM " + NoteOpenHelper.TABLE_NAME + " WHERE " + NoteOpenHelper.COL_ID + " = " + id;
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

    public boolean updateNote(int id, String title, String content) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteOpenHelper.COL_TITLE, title);
        contentValues.put(NoteOpenHelper.COL_CONTENT, content);
        return mSqLiteDatabase.update(NoteOpenHelper.TABLE_NAME, contentValues,
                NoteOpenHelper.COL_ID + " = " + id, null) > 0;
    }

    public boolean deleteNote(int id) {
        return mSqLiteDatabase.delete(NoteOpenHelper.TABLE_NAME, NoteOpenHelper.COL_ID + "=" + id, null) > 0;
    }
}
