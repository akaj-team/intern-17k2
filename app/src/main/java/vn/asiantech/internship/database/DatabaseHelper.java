package vn.asiantech.internship.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.models.Note;

/**
 * Created by root on 6/19/17.
 * Database helper
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "INTERNSHIP_DATABASE";

    // Table Names
    private static final String TABLE_NOTES = "NOTES";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String TEXT = " TEXT,";
    private static final String SELECT_FROM = "SELECT * FROM ";

    // NOTES Table - column
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "decription";
    private static final String KEY_DATE = "date";
    private static final String KEY_IMAGE_URI = "image_uri";

    // TODO NOTES table create statement
    private static final String CREATE_TABLE_NOTES = "CREATE TABLE "
            + TABLE_NOTES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE
            + TEXT + KEY_DESCRIPTION + TEXT + KEY_DATE
            + " DATETIME, " + KEY_IMAGE_URI + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

    /**
     * get all Notes
     *
     * @return List<Note> Notes
     */
    public List<Note> getAllNotes() {

        List<Note> notes = new ArrayList<>();
        String selectQuery = SELECT_FROM + TABLE_NOTES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                note.setNoteTile(c.getString((c.getColumnIndex(KEY_TITLE))));
                note.setNoteDescription((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
                note.setNoteDate(c.getString(c.getColumnIndex(KEY_DATE)));
                note.setNoteImagesThumb(c.getString(c.getColumnIndex(KEY_IMAGE_URI)));

                // adding to Note list
                notes.add(note);
            } while (c.moveToNext());
        }
        c.close();
        return notes;
    }

    /**
     * get Note
     *
     * @param note_id is a ID of Note want to get
     * @return note from id
     */
    public Note getNote(long note_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = SELECT_FROM + TABLE_NOTES + " WHERE "
                + KEY_ID + " = " + note_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
        }
        Note note = new Note();
        note.setId(c.getInt((c.getColumnIndex(KEY_ID))));
        note.setNoteTile(c.getString((c.getColumnIndex(KEY_TITLE))));
        note.setNoteDescription((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
        note.setNoteDate(c.getString(c.getColumnIndex(KEY_DATE)));
        note.setNoteImagesThumb(c.getString(c.getColumnIndex(KEY_IMAGE_URI)));

        c.close();
        return note;
    }

    /**
     * update note
     *
     * @param note is a note wanna update
     * @return 1 = true, 0 = false;
     */
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getNoteTile());
        values.put(KEY_DESCRIPTION, note.getNoteDescription());
        values.put(KEY_DATE, note.getNoteDate());
        values.put(KEY_IMAGE_URI, note.getNoteImagesThumb());

        // updating row
        return db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    /**
     * delete Note
     *
     * @param note_id is a id wanna delete
     */
    public void deleteNote(long note_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " = ?",
                new String[]{String.valueOf(note_id)});
    }

    /**
     * Create new note to database
     *
     * @param note is a note wanna add to database
     */
    public void createNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getNoteTile());
        values.put(KEY_DESCRIPTION, note.getNoteDescription());
        values.put(KEY_DATE, note.getNoteDate());
        values.put(KEY_IMAGE_URI, note.getNoteImagesThumb());
        db.insert(TABLE_NOTES, null, values);
    }
}
