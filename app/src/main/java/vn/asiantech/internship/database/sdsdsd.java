package vn.asiantech.internship.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.models.Feed;
import vn.asiantech.internship.models.Note;

/**
 * Created by root on 6/19/17.
 */

public class sdsdsd {

    // Table Names
    private static final String TABLE_FEEDS = "FEEDS";

    // Common column names
    private static final String KEY_ID = "id";

    // FEEDS Table - column
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_URI_AVATAR = "uri_avatar";
    private static final String KEY_LIST_IMAGES = "list_images";
    // FEEDS table create statement
    private static final String CREATE_TABLE_FEEDS = "CREATE TABLE "
            + TABLE_FEEDS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_URI_AVATAR
            + " TEXT" + KEY_LIST_IMAGES + " TEXT" + ")";

    /**
     * get all FEEDS
     *
     * @return List<Note> FEEDS
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
                feed.setIdImgThumb(c.getString(c.getColumnIndex(KEY_LIST_IMAGES)));

                // adding to Note list
                feeds.add(feed);
            } while (c.moveToNext());
        }
        return feeds;
    }

    /**
     * get Note
     *
     * @param note_id is a ID of Note want to get
     * @return note from id
     */
    public Note getNote(long note_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_FEEDS + " WHERE "
                + KEY_ID + " = " + note_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Note note = new Note();
        note.setId(c.getInt((c.getColumnIndex(KEY_ID))));
        note.setNoteTile(c.getString((c.getColumnIndex(KEY_NAME))));
        note.setNoteDescription((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
        note.setNoteDate(c.getString(c.getColumnIndex(KEY_URI_AVATAR)));
        note.setNoteImagesThumb(c.getString(c.getColumnIndex(KEY_LIST_IMAGES)));

        return note;
    }

    /**
     * update note
     * @param note is a note wanna update
     * @return 1 = true, 0 = false;
     */
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, note.getNoteTile());
        values.put(KEY_DESCRIPTION, note.getNoteDescription());
        values.put(KEY_URI_AVATAR, note.getNoteDate());
        values.put(KEY_LIST_IMAGES, note.getNoteImagesThumb());

        // updating row
        return db.update(TABLE_FEEDS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    /**
     * delete Note
     * @param note_id is a id wanna delete
     */
    public void deleteNote(long note_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FEEDS, KEY_ID + " = ?",
                new String[]{String.valueOf(note_id)});
    }

    /**
     * Create new note to database
     * @param note is a note wanna add to database
     */
    public void createNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, note.getNoteTile());
        values.put(KEY_DESCRIPTION, note.getNoteDescription());
        values.put(KEY_URI_AVATAR, note.getNoteDate());
        values.put(KEY_LIST_IMAGES, note.getNoteImagesThumb());
        db.insert(TABLE_FEEDS, null, values);
    }
}
