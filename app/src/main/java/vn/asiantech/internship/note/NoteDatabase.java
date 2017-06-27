package vn.asiantech.internship.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by datbu on 25-06-2017.
 */

public class NoteDatabase {
    private static final String TABLE_CONTACTS = "tablenote";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String NOTE = "note";
    private static final String TIME = "time";
    private static final String IMAGE = "image";
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private DatabaseHandler mDataHandler;

    public NoteDatabase(Context context) {
        this.mContext = context;
    }

    public NoteDatabase open() throws IOException {
        mDataHandler = new DatabaseHandler(mContext);
        mSQLiteDatabase = mDataHandler.getWritableDatabase();
        return this;
    }

    public void close() {
        mDataHandler.close();
    }

    public long addNote(ItemNote itemNote) {
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
            note.setTime(cursor.getString(cursor.getColumnIndex(TIME)));
            note.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            note.setNote(cursor.getString(cursor.getColumnIndex(NOTE)));
            note.setImage(cursor.getString(cursor.getColumnIndex(IMAGE)));
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

    public int delete(ItemNote note) {
        return mSQLiteDatabase.delete(TABLE_CONTACTS, TIME + " = ? ", new String[]{note.getTime() + ""});
    }

    public int deleteAll() {
        return mSQLiteDatabase.delete(TABLE_CONTACTS, null, null);
    }
}
