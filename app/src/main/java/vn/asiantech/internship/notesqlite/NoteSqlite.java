package vn.asiantech.internship.notesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sony on 19/06/2017.
 */

public class NoteSqlite {
    private static final String DB_NAME = "database";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NOTE = "Note";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_DAY = "Day";
    private static final String COLUMN_MONTH = "Month";
    private static final String COLUMN_HOUR = "Hour";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_CONTENT = "Content";
    public static final String COLUMN_PATH = "Path Image";
    public static final String COLUMN_ID = "_id";

    private static Context context;
    static SQLiteDatabase db;
    private OpenHelper openHelper;

    public NoteSqlite(Context c) {
        NoteSqlite.context = c;
    }

    public NoteSqlite open() throws SQLException {
        openHelper = new OpenHelper(context);
        db = openHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        openHelper.close();
    }


    public long createData(Note note) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE, note.getDate());
        cv.put(COLUMN_DAY, note.getDay());
        cv.put(COLUMN_MONTH, note.getMonth());
        cv.put(COLUMN_HOUR, note.getHour());
        cv.put(COLUMN_TITLE, note.getTitle());
        cv.put(COLUMN_CONTENT, note.getContent());
        cv.put(COLUMN_PATH, note.getPathImage());
        return db.insert(TABLE_NOTE, null, cv);
    }

    public List<Note> getNotes() {
        List<Note> notes = new ArrayList<>();
        Cursor c = db.query(TABLE_NOTE, null, null, null, null, null, null);
        int id = c.getColumnIndex(COLUMN_ID);
        int date = c.getColumnIndex(COLUMN_DATE);
        int day = c.getColumnIndex(COLUMN_DAY);
        int month = c.getColumnIndex(COLUMN_MONTH);
        int hour = c.getColumnIndex(COLUMN_HOUR);
        int title = c.getColumnIndex(COLUMN_TITLE);
        int content = c.getColumnIndex(COLUMN_CONTENT);
        int pathImage = c.getColumnIndex(COLUMN_PATH);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            notes.add(new Note(c.getInt(id), c.getString(date), c.getString(day), c.getString(month), c.getString(hour), c.getString(title), c.getString(content), c.getString(pathImage)));
        }
        return notes;
    }

    public int delete(int id) {
        return db.delete(TABLE_NOTE, COLUMN_ID + "='" + id + "'", null);
    }


    private static class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase arg0) {
            arg0.execSQL("CREATE TABLE " + TABLE_NOTE + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_DATE + " TEXT NOT NULL, "
                    + COLUMN_DAY + " TEXT NOT NULL, "
                    + COLUMN_MONTH + " TEXT NOT NULL, "
                    + COLUMN_HOUR + " TEXT NOT NULL, "
                    + COLUMN_TITLE + " TEXT NOT NULL, "
                    + COLUMN_CONTENT + " TEXT NOT NULL, "
                    + COLUMN_PATH + " TEXT NOT NULL); ");
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
            onCreate(arg0);
        }
    }
}
