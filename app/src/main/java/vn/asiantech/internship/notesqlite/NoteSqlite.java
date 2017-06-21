package vn.asiantech.internship.notesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to create and save data.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-20
 */
class NoteSqlite {
    static final String DB_NAME = "database";
    static final int DB_VERSION = 1;
    static final String TABLE_NOTE = "TableNote";
    static final String COLUMN_DATE = "Date";
    static final String COLUMN_DAY = "Day";
    static final String COLUMN_MONTH = "Month";
    static final String COLUMN_HOUR = "Hour";
    static final String COLUMN_PATH_IMAGE = "ImagePath";
    static final String COLUMN_TITLE = "Title";
    static final String COLUMN_CONTENT = "Content";
    static final String COLUMN_ID = "_id";

    private final Context mContext;
    private SQLiteDatabase mDatabase;
    private OpenHelper mOpenHelper;

    NoteSqlite(Context context) {
        this.mContext = context;
    }

    void open() throws SQLException {
        mOpenHelper = new OpenHelper(mContext);
        mDatabase = mOpenHelper.getWritableDatabase();
    }

    void close() {
        mOpenHelper.close();
    }


    long createNote(Note note) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE, note.getDayOfWeek());
        cv.put(COLUMN_DAY, note.getDay());
        cv.put(COLUMN_MONTH, note.getMonth());
        cv.put(COLUMN_HOUR, note.getHour());
        cv.put(COLUMN_TITLE, note.getTitle());
        cv.put(COLUMN_CONTENT, note.getContent());
        cv.put(COLUMN_PATH_IMAGE, note.getPathImage());
        return mDatabase.insert(TABLE_NOTE, null, cv);
    }

    List<Note> getNotes() {
        List<Note> users = new ArrayList<>();
        Cursor c = mDatabase.query(TABLE_NOTE, null, null, null, null, null, null);
        int id = c.getColumnIndex(COLUMN_ID);
        int date = c.getColumnIndex(COLUMN_DATE);
        int day = c.getColumnIndex(COLUMN_DAY);
        int month = c.getColumnIndex(COLUMN_MONTH);
        int hour = c.getColumnIndex(COLUMN_HOUR);
        int title = c.getColumnIndex(COLUMN_TITLE);
        int content = c.getColumnIndex(COLUMN_CONTENT);
        int image = c.getColumnIndex(COLUMN_PATH_IMAGE);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Note note = new Note(c.getString(date), c.getString(day), c.getString(month), c.getString(hour), c.getString(title), c.getString(content), c.getString((image)));
            note.setId(c.getInt(id));
            users.add(note);
        }
        c.close();
        return users;
    }
}
