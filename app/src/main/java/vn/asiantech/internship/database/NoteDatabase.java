package vn.asiantech.internship.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.helpers.NoteOpenHelper;
import vn.asiantech.internship.models.Note;

/**
 * Database for Note
 * Created by huypham on 22/06/2017.
 */
public class NoteDatabase extends Activity {
    private SQLiteDatabase mDatabase;
    private NoteOpenHelper mOpenHelper;
    private Context mContext;

    public NoteDatabase(Context context) {
        this.mContext = context;
    }

    public NoteDatabase open() {
        mOpenHelper = new NoteOpenHelper(mContext);
        mDatabase = mOpenHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        mDatabase.close();
    }

    public long insertNote(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteOpenHelper.COLUMN_TITLE, note.getTitle());
        contentValues.put(NoteOpenHelper.COLUMN_CONTENT, note.getContent());
        contentValues.put(NoteOpenHelper.COLUMN_IMAGE, note.getImage());
        contentValues.put(NoteOpenHelper.COLUMN_TIME, note.getTime());
        return mDatabase.insert(NoteOpenHelper.TABLE, null, contentValues);
    }

    public long updateNote(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteOpenHelper.COLUMN_TITLE, note.getTitle());
        contentValues.put(NoteOpenHelper.COLUMN_CONTENT, note.getContent());
        contentValues.put(NoteOpenHelper.COLUMN_IMAGE, note.getImage());
        contentValues.put(NoteOpenHelper.COLUMN_TIME, note.getTime());
        return mDatabase.update(NoteOpenHelper.TABLE, contentValues, NoteOpenHelper.COLUMN_ID + "=" + note.getId(), null);
    }

    public long deleteAll() {
        return mDatabase.delete(NoteOpenHelper.TABLE, null, null);
    }

    public long deleteItem(int id) {
        return mDatabase.delete(NoteOpenHelper.TABLE, NoteOpenHelper.COLUMN_ID + "=" + id, null);
    }

    public List<Note> getNoteList() {
        List<Note> noteList = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + NoteOpenHelper.TABLE + " ORDER BY " + NoteOpenHelper.COLUMN_TIME + " ASC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            noteList.add(new Note(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getLong(4)));
            cursor.moveToNext();
        }
        cursor.close();
        return noteList;
    }
}
