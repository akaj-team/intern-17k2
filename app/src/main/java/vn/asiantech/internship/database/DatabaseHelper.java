package vn.asiantech.internship.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.models.Feed;
import vn.asiantech.internship.models.Note;

/**
 * Created by root on 6/19/17.
 * Database helper
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String SEPARATOR = "____";
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "INTERNSHIP_DATABASE";

    // Table Names
    private static final String TABLE_NOTES = "NOTES";
    private static final String TABLE_FEEDS = "FEEDS";

    // Common column names
    private static final String KEY_ID = "id";

    // NOTES Table - column
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATE = "date";
    private static final String KEY_IMAGE_URI = "image_uri";
    // FEEDS Table - column
    private static final String KEY_NAME = "name";
    private static final String KEY_URI_AVATAR = "uri_avatar";
    private static final String KEY_LIST_IMAGES = "list_images";

    // NOTES table create statement
    private static final String CREATE_TABLE_NOTES = "CREATE TABLE "
            + TABLE_NOTES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE
            + " TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_DATE
            + " DATETIME" + KEY_IMAGE_URI + " TEXT" + ")";

    // FEEDS table create statement
    private static final String CREATE_TABLE_FEEDS = "CREATE TABLE "
            + TABLE_FEEDS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_URI_AVATAR
            + " TEXT" + KEY_LIST_IMAGES + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context,  DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NOTES);
        db.execSQL(CREATE_TABLE_FEEDS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * @param array want to cover
     * @return string after cover
     */
    public static String convertArrayToString(String[] array) {
        String str = "";
        for (int i = 0; i < array.length; i++) {
            str = str + array[i];
            // Do not append comma at the end of last element
            if (i < array.length - 1) {
                str = str + SEPARATOR;
            }
        }
        return str;
    }

    /**
     * @param str is string before cover
     * @return array after cover
     */
    public static String[] convertStringToArray(String str) {
        String[] arr = str.split(SEPARATOR);
        return arr;
    }

    /**
     * get all Notes
     *
     * @return List<Note> Notes
     */
    public List<Note> getAllNotes() {

        List<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NOTES;

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

        String selectQuery = "SELECT  * FROM " + TABLE_NOTES + " WHERE "
                + KEY_ID + " = " + note_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Note note = new Note();
        note.setId(c.getInt((c.getColumnIndex(KEY_ID))));
        note.setNoteTile(c.getString((c.getColumnIndex(KEY_TITLE))));
        note.setNoteDescription((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
        note.setNoteDate(c.getString(c.getColumnIndex(KEY_DATE)));
        note.setNoteImagesThumb(c.getString(c.getColumnIndex(KEY_IMAGE_URI)));

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


    /**
     * get all FEEDS
     *
     * @return List<Feed> FEEDS
     */
    public List<Feed> getAllFeeds() {

        List<Feed> feeds = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_FEEDS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Feed feed = new Feed();
                feed.setName(c.getString((c.getColumnIndex(KEY_NAME))));
                feed.setDescription((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
                feed.setIdImgAvatar(c.getString(c.getColumnIndex(KEY_URI_AVATAR)));
                String[] s = convertStringToArray(c.getString(c.getColumnIndex(KEY_LIST_IMAGES)));
                feed.setIdImgThumb(s);

                // adding to Feed list
                feeds.add(feed);
            } while (c.moveToNext());
        }
        return feeds;
    }

    /**
     * get Feed
     *
     * @param Feed_id is a ID of Feed want to get
     * @return Feed from id
     */
    public Feed getFeed(long Feed_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_FEEDS + " WHERE "
                + KEY_ID + " = " + Feed_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Feed Feed = new Feed();
        Feed.setName(c.getString((c.getColumnIndex(KEY_NAME))));
        Feed.setDescription((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
        Feed.setIdImgAvatar(c.getString(c.getColumnIndex(KEY_URI_AVATAR)));
        String[] s = convertStringToArray(c.getString(c.getColumnIndex(KEY_LIST_IMAGES)));
        Feed.setIdImgThumb(s);

        return Feed;
    }


    /**
     * delete Feed
     *
     * @param Feed_id is a id wanna delete
     */
    public void deleteFeed(long Feed_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FEEDS, KEY_ID + " = ?",
                new String[]{String.valueOf(Feed_id)});
    }

    /**
     * Create new Feed to database
     *
     * @param feed is a Feed wanna add to database
     */
    public void createFeed(Feed feed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, feed.getName());
        values.put(KEY_DESCRIPTION, feed.getDescription());
        values.put(KEY_URI_AVATAR, feed.getIdImgAvatar());
        String s = convertArrayToString(feed.getIdImgThumb());
        values.put(KEY_LIST_IMAGES, s);
        db.insert(TABLE_FEEDS, null, values);
    }
}
