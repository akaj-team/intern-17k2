package vn.asiantech.internship.note;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datbu on 25-06-2017.
 */
public class NoteDatabase extends Activity {
    public static final String TABLE_CONTACTS = "table_note";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String NOTE = "note";
    public static final String TIME = "time";
    public static final String IMAGE = "image";
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private DatabaseHandler mDataHandler;

    public NoteDatabase(Context context) {
        this.mContext = context;
    }

    public NoteDatabase open() throws SQLException {
        mDataHandler = new DatabaseHandler(mContext);
        mSQLiteDatabase = mDataHandler.getWritableDatabase();
        return this;
    }

    public void close() {
        mDataHandler.close();
    }

    public long insertNote(ItemNote itemNote) {
        ContentValues values = new ContentValues();
        values.put(TITLE, itemNote.getTitle());
        values.put(NOTE, itemNote.getNote());
        values.put(IMAGE, itemNote.getImage());
        values.put(TIME, itemNote.getTime());
        return mSQLiteDatabase.insert(TABLE_CONTACTS, null, values);
    }

    public List<ItemNote> getList() {
        List<ItemNote> notes = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            ItemNote note = new ItemNote();
            note.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            note.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            note.setNote(cursor.getString(cursor.getColumnIndex(NOTE)));
            note.setTime(cursor.getString(cursor.getColumnIndex(TIME)));
            note.setImage(cursor.getString(cursor.getColumnIndex(IMAGE)));
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

    public long editNote(ItemNote note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, note.getTitle());
        contentValues.put(NOTE, note.getNote());
        contentValues.put(TIME, note.getTime());
        contentValues.put(IMAGE, note.getImage());
        return mSQLiteDatabase.update(TABLE_CONTACTS, contentValues, ID + " = " + note.getId(), null);
    }

    public long deleteById(int id) {
        return mSQLiteDatabase.delete(TABLE_CONTACTS, ID + " = " + id, null);
    }

    public int deleteAll() {
        return mSQLiteDatabase.delete(TABLE_CONTACTS, null, null);
    }
}
