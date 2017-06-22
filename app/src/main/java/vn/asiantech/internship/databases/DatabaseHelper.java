package vn.asiantech.internship.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.models.Note;

/**
 * create and action with database
 * <p>
 * Created by Hai on 6/19/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Note_Manager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NOTE = "Note";
    private static final String COLUMN_NOTE_ID = "Note_Id";
    private static final String COLUMN_NOTE_DAY_OF_WEEK = "Day_Of_Week";
    private static final String COLUMN_NOTE_DAY_OF_MONTH = "Day_Of_Month";
    private static final String COLUMN_NOTE_TIME = "Time";
    private static final String COLUMN_NOTE_TITLE = "Title";
    private static final String COLUMN_NOTE_CONTENT = "Note_Content";
    private static final String COLUMN_NOTE_IMAGE_URI = "Image_Uri";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NOTE + "(" + COLUMN_NOTE_ID + " Integer primary key, "
                + COLUMN_NOTE_DAY_OF_WEEK + " text, " + COLUMN_NOTE_DAY_OF_MONTH + " text, "
                + COLUMN_NOTE_TIME + " text, " + COLUMN_NOTE_TITLE + " text, "
                + COLUMN_NOTE_CONTENT + " text, " + COLUMN_NOTE_IMAGE_URI + " text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NOTE);
    }

    public void insertNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_DAY_OF_WEEK, note.getDayOfWeek());
        values.put(COLUMN_NOTE_DAY_OF_MONTH, note.getDayOfMonth());
        values.put(COLUMN_NOTE_TIME, note.getTime());
        values.put(COLUMN_NOTE_TITLE, note.getTitle());
        values.put(COLUMN_NOTE_CONTENT, note.getContent());
        values.put(COLUMN_NOTE_IMAGE_URI, note.getImage());
        db.insert(TABLE_NOTE, null, values);
        db.close();
    }

//    public int updateNote(Note note) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NOTE_DAY_OF_WEEK, note.getDayOfWeek());
//        values.put(COLUMN_NOTE_DAY_OF_MONTH, note.getDayOfMonth());
//        values.put(COLUMN_NOTE_TIME, note.getTime());
//        values.put(COLUMN_NOTE_TITLE, note.getTitle());
//        values.put(COLUMN_NOTE_CONTENT, note.getContent());
//        values.put(COLUMN_NOTE_IMAGE_URI, note.getImage());
//        return db.update(TABLE_NOTE, values, COLUMN_NOTE_TITLE + "=?", new String[]{String.valueOf(note.getTitle())});
//    }

    public List<Note> getAllNote() {
        List<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setDayOfWeek(cursor.getString(1));
                note.setDayOfMonth(cursor.getString(2));
                note.setTime(cursor.getString(3));
                note.setTitle(cursor.getString(4));
                note.setContent(cursor.getString(5));
                note.setImage(cursor.getString(6));
                // Thêm vào danh sách.
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }
}
