package vn.asiantech.internship.databases;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.helpers.NoteOpenHelper;
import vn.asiantech.internship.models.NoteItem;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/20/2017
 */
public class NoteDatabase extends Activity {
    private SQLiteDatabase mDb;
    private NoteOpenHelper mOpenHelper;
    private Context mContext;

    public NoteDatabase(Context context) {
        mContext = context;
    }

    public NoteDatabase open() throws SQLException {
        mOpenHelper = new NoteOpenHelper(mContext);
        mDb = mOpenHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mOpenHelper.close();
    }

    public long insertNote(NoteItem note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteOpenHelper.COLUMN_TITLE, note.getTitle());
        contentValues.put(NoteOpenHelper.COLUMN_CONTENT, note.getContent());
        contentValues.put(NoteOpenHelper.COLUMN_IMAGE, note.getImage());
        contentValues.put(NoteOpenHelper.COLUMN_TIME, note.getTime());
        return mDb.insert(NoteOpenHelper.TABLE_NOTES, null, contentValues);
    }

    public List<NoteItem> getNoteList() {
        List<NoteItem> listNote = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + NoteOpenHelper.TABLE_NOTES + " ORDER BY " + NoteOpenHelper.COLUMN_TIME + " DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listNote.add(new NoteItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getLong(4)));
            cursor.moveToNext();
        }
        cursor.close();
        return listNote;
    }

    public long editNote(NoteItem note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteOpenHelper.COLUMN_TITLE, note.getTitle());
        contentValues.put(NoteOpenHelper.COLUMN_CONTENT, note.getContent());
        contentValues.put(NoteOpenHelper.COLUMN_IMAGE, note.getImage());
        contentValues.put(NoteOpenHelper.COLUMN_TIME, note.getTime());
        return mDb.update(NoteOpenHelper.TABLE_NOTES, contentValues, NoteOpenHelper.COLUMN_ID + " = " + note.getId(), null);
    }

    public long deleteAll() {
        return mDb.delete(NoteOpenHelper.TABLE_NOTES, null, null);
    }

    public long deleteById(int id) {
        return mDb.delete(NoteOpenHelper.TABLE_NOTES, NoteOpenHelper.COLUMN_ID + " = " + id, null);
    }
}
